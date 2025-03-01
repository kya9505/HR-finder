package HRmanager0228.service;

public interface SalaryService {
    public double getSalaryBeforeTax(int employeeId);

    public double getSalaryAfterTax(int employeeId);

    public double getAnnualSalary(int employeeId);
    //public double getAvgAnnSalJobId(int jobId)
    public double getAvgAnnSalJobId(int jobId); // 직무의 평균 연봉
    public double getAverageSalaryDpt(int departmentId);
    public double simulateSalaryRaise(int employeeId, double percentage);
    public boolean isBudgetExceeded(double budget); // budget 이 매년 바뀔 수 있으니 파라미터로.

    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade);
}
