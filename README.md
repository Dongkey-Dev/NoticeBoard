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

todo : 

1. [dongkey.dothome.co.kr/myadmin](http://dongkey.dothome.co.kr/myadmin) 에서 user 추가하여 클라이언트용 사용자 만들기.
2. 클라이언트용 DB 사용자로 php 만들기
3. user(register / login) / content table 구축
4. android studio에 연동 : 사용자 ← post(recyclerview) ← gson(to list) ← php ← DB(dothome)

# ~7월 14일

회원가입 기능을 구현하였다. 

애플리케이션에서 서버 php로 회원가입 정보를 전달하는 방법을 적용하는데 성공했다.

Volley 라이브러리를 사용하여 Post방식으로 php에 데이터를 접근할 수 있었다.

![image](https://user-images.githubusercontent.com/57933815/87441072-a27ab480-c62d-11ea-857b-5e72345ab1ff.png)

![image](https://user-images.githubusercontent.com/57933815/87441088-a7d7ff00-c62d-11ea-8ceb-5e1ede5719b1.png)

Todo : 

1. 로그인 기능 구현 
2. php 예외처리(동일 아이디 회원가입, email서식 등)

# ~7월 22일

1. 로그인 기능을 구현하였고 ,
2. php 예외처리를 하였다. (동일 아이디 회원가입, email서식 등)

회원가입 기능은 잘 작동하는데, 로그인에서 select sql이 제대로 작동되질 않아, 한동안 삽질을 하였다.

![KakaoTalk_20200722_021127175](https://user-images.githubusercontent.com/57933815/88085509-e5ee9900-cbc0-11ea-8402-642f9ef527f4.png)

무척 단순한 select sql이 제대로 작동되질 않아 stmt, bind_param과 같은 라이브러리를 시도하다, 문제의 원인이 php문이 아닌것같아 android studio 에서 POST로 받은 $_POST['id']와 $_POST['pswd'] 변수를 확인해본 결과, 

id, pswd변수가 'androidx.Appcompact.~~~'로 잡히는것을 확인하고 android studio 코드를 자세하게 살펴보았다.

![KakaoTalk_20200722_021129946](https://user-images.githubusercontent.com/57933815/88085508-e5560280-cbc0-11ea-92a0-c4d6251a7d4e.png)

위 사진에서 val id:String = userid?.text.toString() 와 같은 EditText타입의 변수를 text.toString()으로 변환하는 코드가 누락되어 발생한 것이다. 기존 코드는 text.toString() 대신, toString() 코드만 사용했었는데, text.toString() 메서드를 사용해야한다.

![20200722_021249](https://user-images.githubusercontent.com/57933815/88085505-e424d580-cbc0-11ea-80bf-073aaa2832f2.png)

위 문제를 해결하고나니 로그인 기능이 정상적으로 작동한다. (좌측은 로그인 성공, 우측은 실패)

![KakaoTalk_20200722_021137264](https://user-images.githubusercontent.com/57933815/88085507-e5560280-cbc0-11ea-9d89-5ad31f590d79.png)

join.php(회원가입 php)에서 위 두개의 네모박스 코드를 이용해 전달받은 Email변수에 '@' 가 포함되어 있는지 체크하고, 입력받은 Id 변수로 select 쿼리를 날려 결과값이 있는지 확인함으로 php 예외처리를 구현하였다.

