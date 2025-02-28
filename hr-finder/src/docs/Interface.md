특정 직원의 월급을 반환한다.
double getSalary(int employeeId);

세금을 적용한 후의 특정 직원의 월급을 반환한다.
double getSalary(int employeeId);

특정 직원의 연봉을 반환한다.
double getAnnualSalary(int employeeId);

boolean applyPerfBonus(int employeeId, String performanceGrade);
성과등급은 평가 점수에 따라 A, B, C, D 로 분류된다. 성과 등급에 따라 보너스를 자동 계산하여 지급한다. 이때 보너스를 계산한 연봉이 예산 한도를 초과하면 안된다. 성공하면 참, 실패하면 거짓을 반환한다.

직원의 연봉에 대한 보너스를 계산하여 반환한다.
double calculateBonus(int employeeId, double percentage);

특정 부서의  가장 높은 연봉을 반환한다.
double getHighestAnnualSalary(int departmentId);

특정 부서의 평균 연봉을 반환한다.
double getAverageAnnualSalary(int departmentId);

연봉 인상률을 적용했을 때의 예상 연봉을 반환한다.
double simulateSalaryRaise(int employeeId, double percentage);

boolean isBudgetExceeded(double budgetLimit);
전체 연봉 총액이 예산 한도를 초과했는지 확인한다.

List<SalaryRecord> getSalaryHistory(int employeeId);
직원의 연봉 변경 이력을 반환한다. SalaryRecord 에는 변경된 연봉 금액, 변경 날짜, 변경 사유, 변경을 진행한 사람이 누군지 대한 데이터를 반환한다. Double salary, LocalDateTime updatedAt, String reason, String updatedBy 등의 필드를 가진다. 
