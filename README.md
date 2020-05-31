# Notice Board

## ~5월 31일

Android studio / Kotlin를 이용한 게시판 개발.

게시판을 선택한 이유 - 가장 간단하면서 DB, android studio, 웹 서버를 복합적으로 활용하면서 개발할 수 있는 간단한 아이템이기 떄문이다. 게시판 개발에 성공하면 기본적인 서비스 어플리케이션의 메커니즘을 이해할 수 있어, 추후에 많은 서비스를 지원하는 어플리케이션도 개발할 수 있다.

개발 순서

1. 개발하고자 하는 어플리케이션의 구조도 개발

![Notice%20Board%20460e21e49c984a838830b0df46d2d840/20200520_021013.png](Notice%20Board%20460e21e49c984a838830b0df46d2d840/20200520_021013.png)

2. 구조도를 android studio에서 구현

- MainActivity 구현
    - Recycler view 개발
- LoginActivity 구현
    - Login 서비스부터 사용자의 정보를 담을 DB가 필요
    - 닷홈에 DB 업로드 이후, 어플리케이션과의 연동 (진행중)