package service;

import java.util.Optional;
import java.util.stream.Collectors;

public class SalaryServiceImpl implements SalaryService {

    /**
     * employeeId 에 해당하는 직원을 찾는다.
     * @param employeeId
     * @return Optional : employeeId에 해당하는 직원이 있으면 Optional 에 담아 반환, 없으면 빈 Optional 반환
     */
    public Optional<Employee> findEmployee(int employeeId) {
        for(Employee employee : employees) {
            if(employee.getEmployeeId() == employeeId) {
                return Optional.of(employee); // 직원 찾으면 Optional에 담아서 반환
            }
        }
        return Optional.empty(); // 찾지 못하면 빈 Optional 반환
    }

    /**
     * annualSalary 에 따른 세금을 계산한다.
     * @param annualSalary
     * @return double : 연봉에서 떼는 세금 총액을 반홚
     */
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

    /**
     * 직원의 세후 연봉을 계산한다.
     * @param employeeId
     * @return double : 직원의 세후 연봉 반환
     */
    @Override
    public double getSalaryAfterTax(int employeeId) {

        Optional<Employee> matchedEmp = findEmployee(employeeId);

        double tax = calculateSalTax(employeeId);
        double beforeTax = getSalaryBeforeTax(employeeId);
        double afterTax = beforeTax - tax;

        return afterTax;
    }

    /**
     * 직원의 연봉을 계산한다.
     * @param employeeId
     * @return double : 직원의 연봉
     */
    @Override
    public double getAnnualSalary(int employeeId) { // before tax

       double salary = getSalaryBeforeTax(employeeId);

        return salary * 12;
    }

    /**
     * jobId에 해당하는 직원들의 평균 연봉을 계산한다.
     * @param jobId
     * @return double : jobId 의 평균 연봉
     */
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

    /**
     * 주어지는 부서의 평균 연봉을 계산한다.
     * @param departmentId
     * @return double : 주어진 부서의 평균 연봉을 반환.
     */
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

    /**
     * 임의의 인상률을 넣고 직원의 인상된 연봉을 계산한다.
     * @param employeeId
     * @param percentage : 임의의 인상률
     * @return double : 직원의 인상된 연봉
     */
    // 성과별로 연봉인상하지 않고, 인상률 넣고 시뮬레이션 해보기.
    @Override
    public double simulateAnnSalaryRaise(int employeeId, double percentage) {
            double simulatedSal = 0.0;
        Optional<Employee> matchedEmpl = findEmployee(employeeId);
        if(matchedEmpl.isPresent()) { // optional 객체가 존재한다면
            // map() 이 Optional<Double> 반환, Optional에 값이 존재하면 orElse() 가 double 반환
           simulatedSal = matchedEmpl.map(emp -> (emp.getSalary() * 12) * (1 + percentage * 0.01)).orElse(0.0);
        }else System.out.println("Employee not found."); // optional 객체가 비어있다면


        return simulatedSal;
    }

    /**
     * 현재 모든 직원의 연봉이 주어지는 회사의 예산을 초과하는지 검사한다.
     * @param budget : 회사의 예산
     * @return boolean : 회사의 예산을 초과하면 true, 초과하지 않으면 false를 반환한다.
     */
    @Override
    public boolean isBudgetExceeded(double budget) {
        // 회사의 버젯 정보 받기.
        // 모든 직원의 annualSalary를 계산하여 더하기.
        // employees list 에서 가져오기
        double totalSalary = 0;
        for (Employee employee: employees) {
            totalSalary += getAnnualSalary(employee.getId); // 클래스 내에서 만든 메소드임.
        }
        return totalSalary > budget;
    }

    /**
     * 성과등급에 따라 정해진 인상률을 적용해 직원의 연봉을 인상한다.
     * @param employeeId
     * @param performanceGrade
     * @return double : 인상된 연봉 금액
     */
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


    /**
     * employeeId를 가진 직원의 성과점수에 따라 등급을 매긴다. 등급을 반환한다.
     * @param employeeId
     * @return char : 직원의 성과 등급 'S' ~ 'D'
     */
    private char getPerfGrade(int employeeId) {
        // id로 찾은 직원의 점수 조회.
        int score = searchEmployee(employeeId).getPerfScore(); // 사원 객체의 평가 점수. // getPerfScore는 특정 직원의 점수 필드값.
        int scoreOneDigit = score / 10; // 나머지 빼고 십의 자리만

        char grade = switch (scoreOneDigit) {
            case 10 -> 'S';
            case 9 -> (score >= 95) ? 'S' : 'A'; // 95 above is S
            case 8 -> 'B';
            case 7 -> (score >= 75) ? 'B' : 'C';
            default -> 'D';
        };

        return grade;


        private EmployeeDAO dao = new EmployeeDAO();

//        // 직원 정보를 Optional로 반환
//        public Optional<Employee> findEmployee(int employeeId) {
//            return Optional.ofNullable(dao.getEmployeeById(employeeId));
//        }

        // 직원의 세전 연봉 계산 (월급 * 12), 직원이 없으면 -1 반환 ==> 세전 연봉은 getSalary * 12 => 메소드 필요한지
        public double getEmployeeAnnualSalary(int employeeId) {
            return findEmployee(employeeId)
                    .map(emp -> emp.getSalary() * 12)
                    .orElse(-1.0);
        }

        // 부서의 직원들 평균 연봉 계산, 직원이 없으면 -1 반환 ==> 제꺼랑 겹치는 것 같습니다
        public double getDepartmentAverageAnnualSalary(int deptId) {
            List<Employee> emps = dao.getEmployeesByDepartment(deptId);
            if (emps.isEmpty()) return -1;
            double total = emps.stream().mapToDouble(e -> e.getSalary() * 12).sum();
            return total / emps.size();
        }

        // 퇴직금 계산: 최근 3달치 연봉의 80% 산출, 직원이 없으면 -1 반환
        public double calculateRetirementPay(int employeeId) {
            return findEmployee(employeeId)
                    .map(emp -> emp.getSalary() * 3 * 0.8)
                    .orElse(-1.0);
        }

        // 연봉 인상 적용: 인상률(%)을 적용하여 급여 수정 후 업데이트, 실패 시 false 반환
        public boolean applySalaryIncrease(int employeeId, double rate) {
            return findEmployee(employeeId)
                    .map(emp -> {
                        emp.setSalary(emp.getSalary() * (1 + rate / 100.0));
                        return dao.updateEmployee(emp);
                    }).orElse(false);
        }

        // 직종별 직원들의 평균 연봉 계산, 직원이 없으면 -1 반환
        public double getAvgAnnSalJobId(int jobId) {
            List<Employee> emps = dao.getEmployeesByJobId(jobId);
            if (emps.isEmpty()) return -1;
            double total = emps.stream().mapToDouble(e -> e.getSalary() * 12).sum();
            return total / emps.size();
        }

        // 보너스 계산: 월급의 일정 퍼센트(%)를 보너스로 산출
        public double calculateBonus(int employeeId, double performanceFactor) {
            return findEmployee(employeeId)
                    .map(emp -> emp.getSalary() * performanceFactor / 100)
                    .orElse(0.0);
        }

        // 오버타임 급여 계산: 시간당 급여 산출 후, 추가 근무시간과 1.5배 배율 적용
        public double calculateOvertimePay(int employeeId, double overtimeHours) {
            return findEmployee(employeeId)
                    .map(emp -> {
                        double hourlyWage = emp.getSalary() / 160; // 기준 160시간/월
                        return hourlyWage * overtimeHours * 1.5;
                    }).orElse(0.0);
        }

        // 급여 이력 조회 (더미 구현, 실제 구현 시 연동 필요)
//    public String getSalaryHistory(int employeeId) {
//        return employeeId + "??";
//    }

        // 전체 직원에 대해 일괄 연봉 인상 적용: 인상률(%) 적용 후 업데이트 성공한 직원 수 반환
        public int applyBatchSalaryIncrease(double percentage) {
            List<Employee> allEmployees = dao.getAllEmployees();
            int updatedCount = 0;
            for (Employee emp : allEmployees) {
                emp.setSalary(emp.getSalary() * (1 + percentage / 100));
                if (dao.updateEmployee(emp)) {
                    updatedCount++;
                }
            }
            return updatedCount;
        }

        // 직원 급여 직접 수정: 새로운 급여를 설정하고 업데이트, 실패 시 false 반환
        public boolean updateEmployeeSalary(int employeeId, double newSalary) {
            return findEmployee(employeeId)
                    .map(emp -> {
                        emp.setSalary(newSalary);
                        return dao.updateEmployee(emp);
                    }).orElse(false);
        }
    }

}
