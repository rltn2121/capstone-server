<h1>코드 리스트</h1>
<ul>
  <li><b>crawling.py</b>: GPX 파일 크롤링</li>
  <li><b>metadata.py</b>: GPX 메타데이터 추출 (썸네일 포함)</li>
  <li><b>similarity comparison.ipynb</b> 유사도 비교 방식 비교 (코사인 유사도 vs 유클리드 dbtkeh)</li>
  <li><b>recommendation.py</b>: 유저 맞춤 추천 리스트 생성 후 DB 에 저장</li>
</ul>

<br>
<h2>1. crawling.py</h2>

<b>① 실행 환경</b>: jupyter notebook

<b>② 라이브러리</b>

<img src = "https://user-images.githubusercontent.com/54628612/129690320-ece9a4d5-ecec-4380-af4e-e32e70bfd688.png" width="40%">

<b>③ 수정할 부분</b>
<ul>
  <li>
  Selenium을 사용하기 위해 PC에 설치되어 있는 Chrome 버전에 맞는 <a href="https://chromedriver.chromium.org/downloads">chromedriver</a> 를 코드가 포함된 폴더에 다운로드 해야한다.
    
<div align="center"> 
  <img src = "https://user-images.githubusercontent.com/54628612/129690493-2964fa2b-e168-42bf-aaae-4f3dba21027e.png" width="80%">
  
  <b>[크롬 버전 확인]</b>: [설정] -> [Chrome 정보]
</div>
  </li>
   <li>
     <b>43번, 44번 줄</b>: gpx 파일 저장 경로 수정 폴더가 반드시 존재해야 함 , 한글 포함 시 실행 안됨

ex) dwDir = "C:\\Sangallae\\Downloads"

ex) svDir = "C:\\Users\\jooho\\Downloads\\save"
  </li>
  
   <li>
       <b>86번 줄</b>: csv 파일 저장 경로 수정 폴더가 반드시 존재해야 함 , 한글 포함 시 실행 안됨

ex) wb.save("C:\\Sangallae\\gpx_data.csv")
  </li>
</ul>

<b>④ 실행 결과</b>
<div align="center"> 
<img src = "https://user-images.githubusercontent.com/54628612/129690752-ebbd8bdd-4243-478a-a028-7b77a902d8ee.png" width="80%">

<b>[코드 실행 시 새 크롬 브라우저가 실행됨]</b>

<img src = "https://user-images.githubusercontent.com/54628612/129690784-6d5bb633-a4e9-4a12-8ce8-604444943702.png" width="80%">

<b>[GPX 파일 다운로드 됨]</b>
</div>


<br>
<h2>2. metadata.py</h2>

<b>① 실행 환경</b>: jupyter notebook

<b>② 라이브러리</b>

<img src = "https://user-images.githubusercontent.com/54628612/129693238-4bba7414-381b-4a52-8e0d-e44d3a65a386.png" width="35%">

<b>③ 수정할 부분</b>
<ul>

   <li>
     <b>160번 줄</b>: GPX 파일이 저장된 경로 설정 경로에 한글 포함 시 실행 안됨
     
ex) gpx_files = mod_glob.glob(r"C:\\Sangallae\\*.gpx")

  </li>
  
   <li>
      <b>367번 줄</b>: csv 파일이 저장될 경로 설정 경로에 한글 포함 시 실행 안됨
     
ex) wb.save("C:\\Sangallae\\gpx_data.csv")
  </li>
  </ul>

<b>④ 실행 결과</b>
<div align="center"> 
<img src = "https://user-images.githubusercontent.com/54628612/129693715-48507048-549f-4c17-b01b-44d70cd134de.png" width="80%">

<b>[썸네일 추출 및 메타 데이터 csv 파일 생성됨]</b>
</div>


<br>
<h2>3. similarity comparison.ipynb</h2>

<b>① 실행 환경</b>: google colaboratory , jupyter notebook

<b>② 라이브러리</b>

<img src = "https://user-images.githubusercontent.com/54628612/129694473-18d3ecba-c11f-4354-9edc-f195b3e1bca7.png" width="45%">

<b>③ 수정할 부분</b>: 없음

<b>④ 실행 결과</b>
<div align="center">
  
![image](https://user-images.githubusercontent.com/54628612/129694540-1ba23416-8bf2-4417-a51b-f8c29a711366.png)
![image](https://user-images.githubusercontent.com/54628612/129694552-af01bc65-3b91-4d1f-9b92-b1d436a34b70.png)


<b>[코사인 유사도 - Standard Scaling 결과]ㅤㅤ[유클리드 유사도 - L1 정규화 결과]</b>
</div>


<br>
<h2>4. recommendation.py</h2>

<b>① 실행 환경</b>: google colaboratory , jupyter notebook

<b>② 라이브러리</b>

<img src = "https://user-images.githubusercontent.com/54628612/129694787-8552bc90-91ef-4ac3-b825-774751434bae.png" width="40%">

<b>③ 수정할 부분</b>: 없음

<b>④ 실행 결과</b>



<div align="center"> 
  <img src = "https://user-images.githubusercontent.com/54628612/129694819-9dc6be51-e6f4-41ce-8126-db5bbdba1e84.png" width="70%">
  
  <b>[recommend_course 테이블 갱신됨]</b> 
</div>

<div align="center"> 

</div>
