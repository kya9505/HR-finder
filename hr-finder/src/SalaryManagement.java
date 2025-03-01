import org.w3c.dom.ls.LSOutput;

public class SalaryManagement implements SalaryManageable {

    @Override
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade) {


        char grade = getPerfGrade(employeeId); // 직원의 등급 가져오기
        double annualSalary = employee.getSalary() * 12;

        // 등급에 따른 연봉 인상률 매핑, 인상된 연봉을 반환 ?
        double increasedSalary = switch (grade) {
            // s 15%, A 10%, B 5.5 , C 3.5%, D 1%
            // getSalary * 12 해서 계산하기
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
        int score = searchEmployee(employeeId).getPerfScore(); // 사원 객체의 평가 점수.
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
