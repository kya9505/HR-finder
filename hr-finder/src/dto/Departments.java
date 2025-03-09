package dto;

import lombok.Data;

@Data
public class Departments {
    private int department_id;
    private String department_name;
    private Integer manager_id;
    private Integer location_id;

    public static DepartmentsBuilder builder() {
        return new DepartmentsBuilder();
    }

    public static class DepartmentsBuilder {
        private int department_id;
        private String department_name;
        private Integer manager_id = null;
        private Integer location_id = null;

        public DepartmentsBuilder department_id(int department_id) {
            this.department_id = department_id;
            return this;
        }

        public DepartmentsBuilder department_name(String department_name) {
            this.department_name = department_name;
            return this;
        }

        public DepartmentsBuilder manager_id(Integer manager_id) {
            this.manager_id = manager_id;
            return this;
        }

        public DepartmentsBuilder location_id(Integer location_id) {
            this.location_id = location_id;
            return this;
        }

        public Departments build() {
            Departments department = new Departments();
            department.department_id = this.department_id;
            department.department_name = this.department_name;
            department.manager_id = this.manager_id;
            department.location_id = this.location_id;
            return department;
        }
    }
}
