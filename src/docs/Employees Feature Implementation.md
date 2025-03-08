# 임직원관리 구현

### DAO

1. **public <T>  <*List*<*Employees*>> findEmployee(String searchMenu, T serchvalue)** :
  - Employees table에서 찾은 객체를 List<Employees>  findList 에 저장
  - parameter
    - String searchMenu : 검색의 기준
    - T serchvalue : 검색할 값 (filed type이 모두 달라 T tpye으로 받은 후 타입 검증 후 String으로 변환)
2. **public Optional<List<Employees>>findDuration(java.sql.Date startDate, java.sql.Date endDate)**
  - *job_history join*
  - *job_history* table에서 찾은 employees객체를 List<Employees>  findList 에 저장
3. **public Optional<List<Employees>> loadEmployee()**
  - Employees table data를 loadEmployeeList에 저장

### EmployeeServiceImpl

### search

1. **public List<Employees> searchByEmpId(int employee_id) ：**사원번호를 기준으로 검색
2. **public List<Employees>  searchByLastname(String last_name** ) ： Last_name 으로 검색
3. **public List<Employees> searchByFirstname(String First_name) :** First name으로 검색
4. **public List<Employees> searchByJobId(String job_id) :** 직업으로 검색
5. **public List<Employees> searchByHireDate(String hire_date) :** 입사일로 검색
6. **public List<Employees> searchByEmploymentDuration(Date startDate, Date endDate) :** 근무시작일과 퇴사일로 검색
7. **public List<Employees> serchSubMenu(List<Employees> searchList)**
  - 조건에 맞는 직원의 수
  - 조건에 맞는 직원의 정보 조회

### sort

1. ***public List*<*Employees*> sortByEmpId() :** 사원번호를 기준으로  정렬
2. ***public List*<*Employees*> sortByName() :** 이름 기준으로 정렬
3. ***public List*<*Employees*> sortByJHireDate() :** 입사일 기준으로 정렬
  - job_history 는 퇴사한 직원의 기록만 있어서 애매함

    ⇒ HireDate 기준 정렬로 변경

4. ***public List*<*Employees*> sortByJobId() :** 직업이름 기준으로 정렬 ****
5. ***public List*<*Employees*> sortSubmenu(*List*<*Employees*> *sortList* , *Comparator*<*Employees*> *comparator*)**
  - 오름차순
  - 내림차순

### Employees

1. toSting : test를 위해 임의로 Overide