package HRmanager0228.service;

public interface SalaryService {
    // public double getSalaryBeforeTax(int employeeId);

    public double getSalaryAfterTax(int employeeId);

    public double getAnnualSalary(int employeeId);

    public double getAvgAnnSalJobId(int jobId); // 직무의 평균 연봉
    public double getAverageSalaryDpt(int departmentId);
    // 보통 월급 인상 시뮬레이션은 안돌리니 안해도 될 것 같음.
    // public double simulateSalaryRaise(int employeeId, double percentage);
    public boolean isBudgetExceeded(double budget); // budget 이 매년 바뀔 수 있으니 파라미터로.
    public double simulateAnnSalaryRaise(int employeeId, double percentage);
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade);

    // 추가가능한 메소드들 :
    // 퇴직금 계산 메소드
    // 급여명세서 출력 메소드
    // 직원의 연봉 이력 출력 메소드

}
