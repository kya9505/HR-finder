package HRmanager0301.service;

import java.util.Date;

public class EmployeeServiceImpl {

    //사원 번호를 기준으로 검색
    public void findByEmpId(int employee_id){}

    //이름으로 검색 - last name 검색
    public void findByLastname(String last_name ){}
    //이름 검색 - first name 검색
    public void findByFirstname(String First_name){}

    //직업 검색
    public void findByJobId(String job_id){}


    //고용일/근속기간 검색 - 고용일 검색
    public void findByHireDate(Date hire_date){}
    //고용일/근속기간 검색 - 근무기간 검색
    public void findByEmploymentDuration(Date startDate, Date endDate){}
    //고용일/근속기간 검색 - 연차 검색
    public void getEmployeesByServiceYears(int minYears, int maxYears){}
    //근무지역으로 검색 - 지역으로 검색 department,location join
    public void findByLocation(int location_id){}

    //subMenu : 직원수만 확인 하거나 해당 직원의 정보를 조회할 수 있다.
    public <T> void serchSubMenu(T serchReturn){}

}
