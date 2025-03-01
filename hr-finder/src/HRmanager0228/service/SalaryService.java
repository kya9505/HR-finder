package HRmanager0228.service;

public interface SalaryService {
    // Grade에 따라서
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade);
}
