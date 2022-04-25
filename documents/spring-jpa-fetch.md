# JPA

<br>

## 목차
* [Fetch의 종류](#fetch의-종류)
  * [즉시 로딩](#즉시-로딩)
  * [지연 로딩](#지연-로딩)
  * [Default Fetch 전략](#default-fetch-전략)
  * [조인 전략](#조인-전략)
  * [추천 방법](#추천-방법)
* [Proxy](#proxy)
* [1차 캐시와 2차 캐시의 차이](#1차-캐시와-2차-캐시의-차이)
* [N+1 문제](#n1-문제)

<br>

## Fetch의 종류

### 즉시 로딩
* 엔티티를 조회할 때, 연관된 엔테테도 함께 조회

```java
@Entity
public class Member { 
    @Id 
    @Column(name = "member_id") 
    private String id; 

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "team_id", nullable = false) 
    private Team team; 
}  

Member member = em.find(Member.class, "member1"); 
Team team = member.getTeam();
```

* `em.find()`에서 `Join`을 Member와 연관된 Team도 바로 조회
* NULL이 허용(nullable = true)되면 `Letf Outer Join`으로 조회
  * 내부 조인이 성능에 유리하기 때문에 `NOT NULL` 조건을 통해 `내부 조인`을 사용할 수 있음

### 지연 로딩
* 연관된 엔티티를 실제 사용할 때 조회

```java
@Entity
public class Member { 
    @Id 
    @Column(name = "member_id") 
    private String id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false) 
    private Team team; 
}  

Member member = em.find(Member.class, "member1"); 
Team team = member.getTeam();
team.getName(); // 실제 객체 사용
```
* `em.find()`에서 Member만 조회하고 Team은 조회하지 않음
* team 변수에 `프록시 객체`를 넣어 두고, 이 객체가 실제 사용될 때까지 데이터 로딩을 미룸
* 실제로 객체가 사용될 때, DB를 조회해 프록시 객체를 초기화

### Default Fetch 전략
* `@ManyToOne`, `@OneToOne`: 즉시 로딩
* `@OneToMany`, `@ManyToMany`: 지연 로딩

### 조인 전략
* 연관된 엔티티가 1개이면 즉시 로딩, 여러 개이면 지연 로딩을 사용
* 1대 N일 경우, 즉시 로딩을 사용하면 수 많은 데이터가 함께 로딩될 가능성이 있음

> 1: 팀, N: 회원

```java
@ManyToOne(fetch = FetchType.EAGER, optional = true) // 외부 조인
@ManyToOne(fetch = FetchType.EAGER, optional = false) // 내부 조인
```
* 외래 키로 NULL이 가능할 경우, 팀이 없는 회원도 조회하기 위해 외부 조인
* 외래 키로 NULL이 불가능할 경우, 내부 조인

```java
@OneToMany(fetch = FetchType.LAZY, optional = false) // 외부 조인
@OneToMany(fetch = FetchType.LAZY, optional = true) // 외부 조인
```
*  팀에서 회원을 조회할 때, 팀에 회원이 한 명도 없는 팀을 조회하면 팀까지 조회되지 않는 문제가 발생하므로 외부 조인

### 추천 방법
* 모든 연관 관계에서 `지연 로딩` 사용, 이후 꼭 필요한 곳에만 즉시 로딩 사용
* 컬렉션을 1개 이상 즉시 로딩하는 것은 권장하지 않음

<br>

## Proxy
* 실제 엔티티 객체 대신에 사용되는 객체
* 실제 클래스를 상속받아 생성
* 프록시 객체의 메서드를 호출했을 때, 실제 엔티티가 없다면 영속성 컨텍스트에 `프록시 초기화`를 요청함 -> 영속성 컨텍스트는 DB를 조회해 실제 엔티티 객체를 생성

```java
// 실제 객체 얻기 // 영속성 컨텍스트에 엔티티가 없을 경우 DB 조회
Entity realEntity = EntityManager.find(Entity.class, ID);

// 프록시 객체 얻기 // DB를 조회하지 않음
Entity proxyEntity = EntityManager.getReference(Entity.class, ID); 
proxyEntity.getProperty(); // 실제로 SELECT 쿼리가 실행됨
```

### Proxy 초기화
* `EntityManager.getReference()`: 프록시 객체 생성
  * `Persistence Context`에 엔티티가 존재하는 경우, `proxyEntity.getProperty()`를 해도 실제 엔티티를 반환
* 프록시의 메소드 호출
  * 프록시 객체에는 실제 엔티티를 참조하는 변수(`target`)가 있지만, `Persistence Context`에 엔티티가 없기 때문에 `NULL`을 가리키고 있음
* DB에 접근해 엔티티를 조회 (초기화 요청)
* 엔티티가 `Persistence Context`에 저장 (실제 엔티티에 대한 참조가 `target`에 저장, 1차 캐시에 저장)
* 메소드 결과 반환

<br>

## 1차 캐시와 2차 캐시의 차이

#### 1차 캐시
* 영속성 컨텍스트에서 엔티티를 저장하는 장소
* 일반적으로 트랜잭션을 시작하고 종료할 때까지 유효
* 같은 엔티티가 있으면 동일한 객체임을 보장

#### 2차 캐시
* 애플리케이션 범위의 캐시, 공유 캐시
* 애플리케이션이 종료될때까지 캐시 유지
* `동시성`을 극대화 하기 위해 캐시한 객체가 아닌 `복사본`을 반환
* 영속성 컨텍스트가 다르면 동일한 객체임을 보장하지 않음

<br>

## N+1 문제
* 조회 시, 1개의 쿼리를 생각하고 설계했으나 예상하지 못했던 쿼리 N개 더 발생하는 문제
* `연관 관계`에 의해 다른 객체가 함께 조회되어 N+1 문제가 발생함

### 원인
#### 즉시 로딩

> 전체 데이터를 조회하고 Eager가 감지되어 N 쿼리가 추가로 발생

```java
@Entity
public class User { 
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user") 
    private Set<Article> articles = emptySet();
```

```java
User user = userRepository.findAll();
```
* `findAll()` 메소드를 실행하면 모든 `User` 정보를 조회하는 **쿼리 1개** 발생
* 이때, Eager Fetch가 감지되어 모든 `User`의 `Article`을 조회하는 **쿼리 N개**가 발생

#### 지연 로딩

> 전체 데이터를 조회한 후, 연관된 객체가 사용될 때 N개 쿼리가 발생

* `findAll()` 메소드를 실행하면 모든 `User` 정보를 조회하는 **쿼리 1개** 발생
* 이후. 프록시 객체를 사용할때, **쿼리 N개**  발생
  * `User`에 대한 조회가 완료되어 `Join`은 불가능하고 N개 쿼리 발생
 
### 지연 로딩에서의 해결 방법: Fetch Join

* 연관된 엔티티나 컬렉션을 한 번에 함께 조회하는 역할

#### 방법1: JPQL에 Fetch Join 임을 명시
```java
@Query("select distinct u from User u left join fetch u.articles") 
List<User> findAll();
```

* 아래와 같이 Join만 할 경우, N+1 문제는 해결되지 않음
```java
// 지연 로딩이 걸려 있고 프록시를 가져오는 것이라 N+1 문제는 계속 발생
@Query("select distinct u from User u left join u.articles") 
List<User> findAll();
```

#### 방법 2: @EntityGraph 사용
```java
@EntityGraph(attributePaths = {"articles"}, type = EntityGraphType.FETCH) @Query("select distinct u from User u left join u.articles") 
List<User> findAllEntityGraph();
```

### Pagination 문제
* Fetch Join을 할 경우, 모든 데이터를 `인메모리`에 가져와 애플리케이션 단에서 처리해 `Out of Memory` 에러가 발생할 수 있음
* 아래와 같은 경고가 발생함
```
firstResult/maxResults specified with collection fetch; applying in memory!
```

* ~ToOne 관계에서는 Fetch Join을 사용해 Pagination을 해도 됨
* `@BatchSize`를 사용하면 N개의 쿼리가 아니라 `1개의 쿼리`를 날릴 수 있음

```java
@BatchSize(size = 10)
@OneToMany(fetch = FetchType.EAGER, mappedBy = "user") 
private Set<Article> articles = emptySet()
```

```sql
where user_id in (); // Where 절의 조건에 Batch Size만큼만 실행되게 함
```

* @Fetch(FetchMode.SUBSELECT)
  *BatchSize(size = 무한대)와 동일

```sql
 where user_id in (
    select user_id from user_table;
)
```

### 두개 이상의 Collection Join

* 2개 이상의 Fetch Join을 할 경우, `MultipleBagFetchException` 예외 발생
* `@BatchSize`를 사용해 해결 가능
