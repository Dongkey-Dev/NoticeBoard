# ~5월 31일

Android studio / Kotlin를 이용한 게시판 개발.

게시판을 선택한 이유 - 가장 간단하면서 DB, android studio, 웹 서버를 복합적으로 활용하면서 개발할 수 있는 간단한 아이템이기 떄문이다. 게시판 개발에 성공하면 기본적인 서비스 어플리케이션의 메커니즘을 이해할 수 있어, 추후에 많은 서비스를 지원하는 어플리케이션도 개발할 수 있다.

개발 순서

1. 개발하고자 하는 어플리케이션의 구조도 개발 (draw.io)

![20200520_021013](https://user-images.githubusercontent.com/57933815/83353913-c0979880-a390-11ea-9b5f-3c64120444f7.png)

2. 구조도를 android studio에서 구현

- MainActivity 구현
    - Recycler view 개발
- LoginActivity 구현
    - Login 서비스부터 사용자의 정보를 담을 DB가 필요
    - 닷홈에 DB 업로드 이후, 어플리케이션과의 연동 (진행중)

# ~6월 3일

1. 닷홈에 DB 업로드하는것 구현. 
    1. 닷홈에 데이터베이스를 구축하려 하니, 단순히 php의 mysqli() 와 query로 구축한다는 것이 이해가 되지않았다. 
    2. 알고보니 닷홈에서 php / ftp 등 웹서버를 지원하기 때문에, query를 날리면 바로 DB가 구축이 된다 한다.
    3. 사실 AWS나 Firebase를 이용하지않고 닷홈으로 데이터베이스를 구축하게된 이유는 실제로 서버를 구축할 때, 처음부터 만들어지는 과정을 API의 도움없이 개발하고 싶어서이다.

![20200603_201300_1](https://user-images.githubusercontent.com/57933815/83630364-abac4680-a5d6-11ea-929a-ab140f9afddf.png)

    덕분에 PHP와 Vim을 많이 공부하게 되었고, 도움도 되었다.

![20200603_201300_2](https://user-images.githubusercontent.com/57933815/83630370-ad760a00-a5d6-11ea-8c4d-c0de19e7ec58.png)
![20200603_201300_3](https://user-images.githubusercontent.com/57933815/83630371-ae0ea080-a5d6-11ea-9577-e488f6035db0.png)
DB에 데이터를 삽입이 잘 되는것을 확인할 수 있다.
