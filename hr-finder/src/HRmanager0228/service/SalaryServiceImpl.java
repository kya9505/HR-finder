package HRmanager0228.service;

import java.util.Optional;
import java.util.stream.Collectors;

public class SalaryServiceImpl implements SalaryService {


    public Optional<Employee> findEmployee(int employeeId) {
        for(Employee employee : employees) {
            if(employee.getEmployeeId() == employeeId) {
                return Optional.of(employee); // 직원 찾으면 Optional에 담아서 반환
            }
        }
        return Optional.empty(); // 찾지 못하면 빈 Optional 반환
    }


    private double calculateSalTax(double annualSalary) {
        double tax = 0.0;

        // 한국 기준으로 작성함. hr 데베 기준으로 바꿔야 할 수도 있음.
        if (annualSalary <= 14000000) {
            tax = annualSalary * 0.06;
        } else if (annualSalary <= 50000000) {
            tax = 14000000 * 0.06 + (annualSalary - 14000000) * 0.15;
        } else if (annualSalary <= 88000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (annualSalary - 50000000) * 0.24;
        } else if (annualSalary <= 150000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (88000000 - 50000000) * 0.24 + (annualSalary - 88000000) * 0.35;
        } else if (annualSalary <= 300000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35 + (annualSalary - 150000000) * 0.38;
        } else if (annualSalary <= 500000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35 + (300000000 - 150000000) * 0.38 + (annualSalary - 300000000) * 0.4;
        } else if (annualSalary <= 1000000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35 + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4 + (annualSalary - 500000000) * 0.42;
        } else {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35 + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4 + (1000000000 - 500000000) * 0.42 + (annualSalary - 1000000000) * 0.45;
        }

        double localTax = tax * 0.1; // 지방소득세

        return tax + localTax;
    }
    @Override
    public double getSalaryBeforeTax(int employeeId) {

        Optional<Employee> matchedEmp = findEmployee(employeeId);

        double empSalary = matchedEmp
                .map(Employee::getSalary)
        .orElse("Unknown Employee."); // 기본값 이렇게 돌려주면 되는지 다시 확인하기.
        return empSalary;
    }

    @Override
    public double getSalaryAfterTax(int employeeId) {

        Optional<Employee> matchedEmp = findEmployee(employeeId);

        double tax = calculateSalTax(employeeId);
        double beforeTax = getSalaryBeforeTax(employeeId);
        double afterTax = beforeTax - tax;

        return afterTax;
    }

    @Override
    public double getAnnualSalary(int employeeId) { // before tax

       double salary = getSalaryBeforeTax(employeeId);

        return salary * 12;
    }


    @Override
    public double getAvgAnnSalJobId(int jobId) { //
        List<Employee> matchedEmployees = employees.stream()
                .filter(emp -> jobId.equals(emp.getJobId()))
        .collect(Collectors.toList()); // 매칭되는 직원들을 리스트에 담기
        double totalSal =matchedEmployees.stream()
                .mapToDouble(emp -> emp.getsalary() * 12) // mapToDouble 을 이용해 바로 double 타입을 반환, 집계함수sum() 쓸 수 있도록함.
                .sum();

        int count = matchedEmployees.size();
        double avgAnnSalJobId = totalSal / count;
        return avgAnnSalJobId;
    }

    @Override
    public double getAverageSalaryDpt(int departmentId) { // 같은 부서에서 평균 연봉
        //  직원 리스트에서 부서아이디를 갖고 있는 직원 모두 찾기 => 객체에 넣기.
        // 1. 객체안의  모든 직원의 연봉 더하기.
        // 2. 부서의 직원 수 구하기.
        List<Employee> matchedEmployees = employees.stream()
                .filter(emp -> departmentId.equals(emp.getDepartmentId()))
                .collect(Collectors.toList()); // 매칭되는 직원들을 리스트에 담기
        double totalSal =matchedEmployees.stream()
                .mapToDouble(emp -> emp.getsalary() * 12) // mapToDouble 을 이용해 바로 double 타입을 반환, 집계함수sum() 쓸 수 있도록함.
                .sum();

        int count = matchedEmployees.size();
        double avgAnnSalDptId= totalSal / count;
        return avgAnnSalDptId;

    }

    // 성과별로 연봉인상하지 않고, 인상률 넣고 시뮬레이션 해보기.
    @Override
    public double simulateAnnSalaryRaise(int employeeId, double percentage) {
            double simulatedSal = 0.0;
        Optional<Employee> matchedEmpl = findEmployee(employeeId);
        if(matchedEmpl.isPresent()) {
           simulatedSal = matchedEmpl.map(emp -> (emp.getSalary() * 12) * (1 + percentage * 0.01))

        }else System.out.println("Employee not found.");


        return simulatedSal;
    }

    @Override
    public boolean isBudgetExceeded(double budget) {
        // 회사의 버젯 정보 받기.
        // 모든 직원의 annualSalary를 계산하여 더하기. // getAnnualSalary() 정도는 employee 객체안에 있어도됨(?)// 혹은 직원객체 속성필드로 추가하기.
        // employees list 에서 가져오기
        double totalSalary = 0;
        for (Employee employee: employees) {
            totalSalary += getAnnualSalary(employee.getId); // 클래스 내에서 만든 메소드임.
        }
        return totalSalary > budget;
    }

    @Override
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade) {
        char grade = getPerfGrade(employeeId); // 직원의 등급 가져오기
        double annualSalary = employee.getSalary() * 12;

        // 등급에 따른 연봉 인상률 매핑, 인상된 연봉을 반환
        double increasedSalary = switch (grade) {
            case 'S' -> annualSalary * 0.15; // 이 자체가 반환값
            case 'A' -> annualSalary * 0.1;
            case 'B' -> annualSalary * 0.55;
            case 'C' -> annualSalary * 0.35;
            case 'D' -> annualSalary * 0.01;
            default -> {
                System.out.println("Invalid grade. Please check the employee's performance grade again.");
                yield 0.0;
            }

        };
        return increasedSalary;
    }

    // 임직원 관리쪽의 직원 검색 메소드를 여기서 사용해도 되려나?
    private Employee searchEmployee(int employeeId) {
        for(Employee employee : employees) {
            if(employee.getEmployeeId() == employeeId) {
                return employee;
            }
        }
        return null; // 못찾으면 null 반환.
    }
    // 특정 직원의 성과점수에 따라 등급을 매김. 프라이빗 메소드.
    private char getPerfGrade(int employeeId) {
        // id로 찾은 직원의 점수 조회.
        int score = searchEmployee(employeeId).getPerfScore(); // 사원 객체의 평가 점수. // getPerfScore는 특정 직원의 점수 필드값.
        int scoreOneDigit = score / 10; // 나머지 빼고 십의 자리만

        // break 문이 필요 없는 enhanced switch
        char grade = switch (scoreOneDigit) {
            case 10 -> 'S';
            case 9 -> (score >= 95) ? 'S' : 'A'; // 95 above is S
            case 8 -> 'B';
            case 7 -> (score >= 75) ? 'B' : 'C';
            default -> 'D';
        };

        return grade;
    }

}
