<h1>⛰산갈래: 등산 어플리케이션 종합설계</h1>

[소개영상](https://youtu.be/mUXI-4cuQaU) | 
[최종보고서](https://drive.google.com/file/d/1v9iSlUMUhS_8RK49XYEVQySo7eocnxI2/view?usp=sharing) |
[안드로이드 리포지토리](https://github.com/JuhoLeedev/Sangallae)


<h2>⚙주요 기능</h2>

<b>1. 등산로/산 추천</b>

<ul>
  <li><b>맞춤 등산로</b>: 사용자의 등산 기록과 등산로들의 메타데이터를 분석하여 사용자 맞춤 등산로 추천</li>
  <li><b>인기 등산로</b>: 최근 24개월 이내에 따라가기 횟수가 많은 인기 등산로</li>
  <li><b>인기 산</b>: 현재 계절의 순위를 반영한 인기 산 목록</li>
  <li><b>근처 산</b>: 현재 유저의 위치로 부터 50km 이내에 있는 산 목록</li>
</ul>
    


<b>2. 키워드 검색</b>
    
<ul>
  <li><b>등산로 검색</b>: 등산로명, 산 이름, 지역 명 등의 키워드로 검색</li>
</ul>
  

<b>3. 등산로 안내 및 측정</b>
<ul>
  <li><b>경로 안내</b>: 선택한 등산로의 경로를 지도에 표시</li>
  <li><b>등산 기록 측정</b>: 측정 시간, 걸은 시간, 이동한 거리, 남은 거리, 현재 고도, 예상 도착 시간, 진행률을 화면에 표시. 3초 간격으로 현재 유저가 위치한 곳의 위도, 경도, 고도와 시간을 기록.</li>
  <li><b>GPX 파일 저장</b>: 측정 종료 후, 등산 데이터를 GPX 파일로 생성. 로컬 저장소 및 서버 스토리지(AWS S3)에 저장</li>
</ul>

  
  
<b>4. 마이페이지, 등산 통계</b>

<ul>
  <li><b>프로필 관리</b>: 닉네임, 키, 몸무게 등 유저의 프로필 수정 가능</li>
  <li><b>등산 통계</b>: 등산 거리, 등산 시간, 이동 시간, 오르막합, 내리막합, 속력, 페이스 등의 합계, 평균, 최고 기록을 제공</li>
  <li><b>등산 기록</b>: 사용자의 등산 기록을 최신순으로 확인 가능
</ul>

<h2>💻개발 환경</h2>
Server Framework SpringBoot 2.4.4
Web server Nginx
Database MariaDB 10.4.13
Cloud Storage AWS S3
Cloud Server Hosting AWS EC2
Cloud Database AWS RDS


<h2>👩‍👧‍👦팀원 역할</h2>
공통: 추천 알고리즘 개발, GPX 크롤링 및 메타데이터 추출
<table>
  <thead>
    <tr>
      <th>이름</th>
      <th>역할</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>박기수</th>
      <td align:left>데이터베이스 설계<br>
        API 서버, 추천 서버 개발<br>
      등산 기록 측정 및 파일 업로드 구현 (안드로이드)</td>
    </tr>
    <tr>
      <th>이주호</th>
      <td align=left>UI 설계, 안드로이드 개발</td>
    </tr>
    <tr>
      <th>김현지</th>
      <td align=left>UI 설계, 안드로이드 개발</td>
    </tr>
  </tbody>
</table>
