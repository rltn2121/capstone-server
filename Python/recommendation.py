import numpy as np
import pandas as pd
import pymysql

from sklearn.metrics.pairwise import cosine_similarity
# from sklearn.metrics.pairwise import euclidean_distances

from sklearn.preprocessing import StandardScaler
# from sklearn.preprocessing import MinMaxScaler
# from sklearn.preprocessing import MaxAbsScaler
# from sklearn.preprocessing import RobustScaler


# 전역변수 선언부
conn = None
cur = None
sql = ""
record_cnt_per_user = 100

def get_recommend_course_list(df, gpx_id):
  Id = gpx_id
  #index = dataframe[dataframe['gpx_id'] == Id].index.values
  index = dataframe.index.stop
  new_CBF_standard = np.array(course_similarity)
  new_CBF_standard = new_CBF_standard.argsort()[:,::-1]

  sim_index = new_CBF_standard[index, :].reshape(-1)


  # #본인을 제외
  sim_index = sim_index[sim_index != index]

  #data frame으로 만들고 vote_count으로 정렬한 뒤 return
  # result = df.iloc[sim_index].sort_values('gpx_id', ascending=False)[:30]

  course_cnt_per_record = get_course_cnt(user_id)
  cnt = round(record_cnt_per_user / course_cnt_per_record)
  result = df.iloc[sim_index][:cnt]
  return result

def get_course_cnt(user_id):
  # print("user_id: ")
  # print(user_id)
  sql = 'select user_id, count(user_id) as cnt from record group by user_id'
  cur.execute(sql)
  result = cur.fetchall()

  user_record_cnt = pd.DataFrame(result, columns = ['user_id', 'cnt'])
  result = user_record_cnt.loc[user_record_cnt['user_id']==user_id, 'cnt']
  return result.values[0]

def get_table(table_name):
  if (table_name == 'course'):
    sql = 'select course_id, avg_speed, ele_dif, distance from course'
  elif(table_name == 'record'):
    sql = 'select record_id, avg_speed, ele_dif, distance, user_id from record'
  cur.execute(sql)
  result = cur.fetchall()

  if (table_name == 'course'):
    dataframe = pd.DataFrame(result, columns=['gpx_id', 'avg_speed', 'ele_dif', 'distance'])
  elif(table_name == 'record'):
    dataframe = pd.DataFrame(result, columns = ['record_id', 'avg_speed', 'ele_dif', 'distance', 'user_id'])
  return dataframe



# db 연결
conn = pymysql.connect(host = 'webservice.c7lmmj1i3i2l.ap-northeast-2.rds.amazonaws.com', user = 'hugebird', password='5ab5c87a', db = 'webservice', charset = 'utf8')
cur = conn.cursor()

# course 테이블 조회
#dataframe = get_table('course')
courses = get_table('course')

# record 테이블 조회
records = get_table('record')

dataframe = pd.DataFrame(courses, columns = ["avg_speed", "ele_dif", "distance"])


# conn.commit()

# my_data = []
my_data = pd.DataFrame(columns = ["course_id", "avg_speed", "ele_dif", "distance", "user_id"])
# print(my_data)
for index, record in records.iterrows():
  print(str(index + 1) + " / " + str(len(records)))
  record_id = int(record[0])
  temp_gpx_id = int(record_id + 1000000)
  avg_speed = record[1]
  ele_dif = record[2]
  distance = record[3]
  user_id = int(record[4])


  # 새 데이터 프레임 생성
  datas = dataframe
  #datas = datas.append({'gpx_id' : temp_gpx_id, 'avg_speed' : avg_speed, 'ele_dif' : ele_dif, 'distance': distance} , ignore_index=True)
  datas = datas.append({'avg_speed' : avg_speed, 'ele_dif' : ele_dif, 'distance': distance} , ignore_index=True)

  # 스케일링
  scaler = StandardScaler()
  scaler.fit(datas.values)
  data_scaled = scaler.transform(datas)
  data_scaled = pd.DataFrame(data_scaled, index = datas.index, columns = datas.columns)
  # print(data_scaled)

  # 유사도 계산
  course_similarity =  pd.DataFrame(cosine_similarity(data_scaled, data_scaled))
  course_similarity.index = data_scaled.index
  course_similarity.columns = data_scaled.index
  # print(course_similarity)
  
  # print(dataframe.index.stop)
  # 리스트 반환
  recommend_result=get_recommend_course_list(datas, dataframe.index.stop)
  #recommend_result=get_recommend_course_list(datas, gpx_id = record_id)
  #recommend_result=get_recommend_course_list(datas, gpx_id = record_id)['gpx_id'].values

  for idx, row in recommend_result.iterrows():
    course_id = courses.iloc[idx]['gpx_id']
    avg_speed = row[0]
    ele_dif = row[1]
    distance = row[2]
    
    df = pd.Series([course_id, avg_speed, ele_dif, distance, user_id], index = ['course_id', 'avg_speed', 'ele_dif', 'distance', 'user_id'])
    my_data = my_data.append(df, ignore_index=True)

  print(my_data)



sql = 'truncate recommend_course'
cur.execute(sql)


temp = pd.DataFrame(my_data, columns = ["course_id", "user_id"])
result = temp.values.tolist()

sql = 'INSERT INTO recommend_course(course_id, user_id) VALUES(%s, %s)'
cur.executemany(sql, result)
conn.commit()
cur.close()