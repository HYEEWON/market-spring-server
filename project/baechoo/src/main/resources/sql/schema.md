# 스키마 정리

## 테이블 설명

### Member 테이블

|Column|PK|Type|Nullable|Unique|Explanation|
|:--:|:--:|:--:|:--:|:--:|:--|
|member_idx|O||X|O|Member 테이블의 기본키|
|id|||X|O|회원 아이디|
|encrypt_password|||X|X|암호화된 회원의 비밀번호|
|nickname|||X|O|회원 별명|
|birthdate|||X|X|회원 생년월일|
|authorities|||X|X|회원 권한<br>여러 개 가능|

### Authority 테이블
|Column|PK|Type|Nullable|Unique|Explanation|
|:--:|:--:|:--:|:--:|:--:|:--|
|authority|O|VARCHAR(25)|X|O|권한 이름|

<br>

## DB 네이밍 규칙
