package HRmanager0228.service;

public interface SalaryService {
    double getSalaryBeforeTax(int employeeId);

    double getSalaryAfterTax(int employeeId);

    double getAnnualSalary(int employeeId);
   double getAverageAnnSalary(int employeeId);

    double getAverageSalaryDpt(int departmentId);
    double simulateSalaryRaise(int employeeId, double percentage);
    boolean isBudgetExceeded(double budget); // budget 이 매년 바뀔 수 있으니 파라미터로.


    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade);
}
