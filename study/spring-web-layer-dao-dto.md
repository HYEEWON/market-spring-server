# 스프링 웹 계층

<br>

<img src="https://user-images.githubusercontent.com/38900338/162606226-f03c9386-ed96-41a8-9d52-666ab208d17e.png" width="700px">

<br>

## 프레젠테이션 계층
- 클라이언트의 요청 및 응답을 처리
- `View` + `Controller`
 
## 서비스 계층
- `비즈니스 로직` 처리
- 프레젠테이션에서 도메인/Repository에 접근하지 않고 비지니스 로직을 처리할 수 있게 함
- `Service`

## 데이터 엑세스 계층
- ORM (Mybatis, Hibernate)를 주로 사용하는 계층
- DB에 data를 CRUD(Create, Read, Update, Drop)하는 계층
- `DAO`/`Repository`  

## Domain(Entity)
* DB 테이블과 1:1 매칭
* Entity를 통해 데이터를 DB에 저장

<br>

# DAO와 DTO

<br>

## DAO (Data Access Object)
* `DB에 접근`하는 객체
* `Repository`라고도 부름
* 단일 데이터의 접근 및 갱신을 의미 -> Service: 하나 이상의 DAO를 이용해 비지니스 로직을 처리, 트랜잭션의 단위
* 사용할 DB, 어떤 드라이브와 로그인 정보를 사용할 것인가 등을 표시

<br>

## DTO(Data Transfer Object)
* 다른 말로 VO(Value Object)
* 계층(컨트롤러, 뷰, 비즈니스 계층 등)간 `데이터 교환(전송)`을 위한 자바 빈
* `로직을 가지지 않음`: 속성과 속성에 접근하기 위한 getter, setter가 있고 추가적으로 toString(), equals() 가능
* NotNull, Size 등 `Validation` 역할을 하면 좋음

### DTO가 필요한 이유
* Entity에 Setter를 두지 않는 것이 좋음
  * 하지만, Setter가 없으면 Controller에서 값의 할당이 되지 않기 때문에 `DTO를 만들고 Setter를 두어 값을 처리`
  * Entity에 Setter가 있더라도 이를 `Request/Response`에서 사용하지 않는 것이 좋음 <br>
    -> Entity는 데이터 갱신에 신중해야 하지만 `Request/Response`는 수정할 일이 많음
* 도메인의 모든 필드가 Request/Response에 사용되지 않을 수 있고, API를 위해 도메인이 수정되면 안되기 때문에 사용

<br>

### DTO 예시
* Entity 클래스

```java
@Getter
@NoArgsConstructor
@Entity
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long order_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Long product_id;

    @Column(nullable = false)
    private Long purchase_check;

    @Builder
    public Order(Long user_id, Long product_id, Long purchase_check) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.purchase_check = purchase_check;
    }
}
```
* DTO 클래스
```java
@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Long user_id;
    private Long product_id;
    private Long purchase_check;
}
```