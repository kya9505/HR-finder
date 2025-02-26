1. 인터페이스만드는 이유 
2. 인사관리시스템에서 필요한 인터페이스들  - 서비스 인터페이스, 급여 관리 인터페이스 PayrollService, 근태 관리 인터페이스 AttendanceService , LeaveService 휴가관리인터페이스, PerformanceService  

인터페이스를 구현하는 클래스들은 인터페이스에 선언된 메소드들을 반드시 구현해야 한다. 
구현체(클래스)를 변경하는것이 쉬워진다. 

hr 데이터베이스를 기반으로 데이터를 이용해 구현할 수 있는 기능들을 추가해보기. 

# 인터페이스 
EmployeeService 
- addEmployee
- updateEmployee
- deleteEmployee 
- Employee getEmployeeById(int id)
- List<Employee> getAllEmployee

SortEmployee 
- sortBy() - 특정 기준을 기준으로 직원을 정렬한다. 

PerformanceService 
- 연말 평가에 따라 등급을 나눠 직원별 등급을 부여받는다. 
- getEmployeesOfGrade - 특정 등급의 직원들을 구한다. 
- increaseSalary - 특정 지원이나, 직원 리스트의 월급을 인상한다. 

PayrollService 
- processPayroll(int employeeId)
- double calculateSalary(int employeeId) - 어떤걸 파라미터로 설정해야 적절할까 
- List<Payroll> getPayrollHistory(int employeeId

AttendanceService 
- void clockIn(int employeeId) - 출근 시각을 구한다. 
- void clockOut(int employeeId) - 퇴근 시각을 구한다. 
- List<Attendace> getAttendanceRecords(int 

LeaveService 
- int getLeavesLeft(int id, int year) - 이번년도 직원의 남은 연차수를 구한다. 
- int getLeavesSpent(int id, int year)


# 클래스 
