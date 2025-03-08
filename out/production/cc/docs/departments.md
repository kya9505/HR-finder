## 0306

# Departments 개발 내용
### 강창선 개발 내용

## Controller
- run() : 메인 메뉴 실행 메서드
  - 내부에서 추가, 삭제, 수정 보조메뉴를 통해 서비스의 메서드들을 호출
  - addDepartment : Departments 객체 생성 메서드
## Service
- List<Departments> addDepartments(Departments departments);
  - 전달 받은 객체에서 department_id 를 받아 DAO의 countDepartmentId 메서드 호출
  - 전달 받은 count 의 개수가 0이라면 DAO의 추가 메서드 호출로 추가 실행
  - 전달 받은 count 의 개수가 0이 아니라면 중복 불가 메세지를 출력
- List<Departments> deleteDepartmentIfEmpty(int department_id);
  - 추가 메서드와 비슷, 이번에는 해당 id의 부서에 속한 사원의 수를 검사
  - 해당 id 부서에 소속된 사원이 없다면 DAO의 삭제 메서드 호출
  - 아니라면 실패 메세지 출력
- List<Departments> updateDepartmentsManger_id(int department_id, int manager_id);
  - DAO의 updateDepartmentsManager_id를 호출해서 해당 메서드 실행
- List<Departments> updateDepartments_name(int department_id, String department_name);
  - DAO의 updateDepartments_name를 호출해서 해당 메서드 실행

## DAO
- int countEmployeesByDepartment(int department_id);
  - 부서를 삭제할 때 해당 부서에 속한 사원을 확인하기 위한 메서드
- int countDepartmentId(int department_id);
  - department_id 의 중복을 확인하기 위한 메서드
- Optional<Departments> addDepartments(Departments departments);
  - 입력받은 객체에서 해당 데이터들을 쿼리문에 입력하여 쿼리문 실행
  - 이후에 해당 id를 가진 데이터를 뽑아와서 빌더로 객체 생성 후 반환
- Optional<Departments> deleteDepartments(int department_id);
  - 입력 받은 department_id를 통해 쿼리문에 대입하여 삭제 쿼리문 진행
  - 해당 데이터를 객체로 빌드하여 반환
- Optional<Departments> updateDepartmentsManager_id(int department_id, int manager_id);
  - 위의 메서드들과 동작 방식 같음
- Optional<Departments> updateDepartments_name(int department_id, String department_name);
  - 이하 동일

## 제약조건 삭제
- 테이블 간의 제약조건을 모두 삭제
- 트리거 trg_departments_manager_update를 만들어서 departments에서 manager_id를 변경했을 시 employees의 해당 manager_id도 변경되게 만듦

# 중요사항
departments 테이블의 제약조건을 삭제해야 정상 작동합니다.
DAO 밑에 주석으로 관련 트리거와 제약조건 확인 쿼리문을 남겼으니 외래키 제약조건 삭제 하고 테스트해주세요
