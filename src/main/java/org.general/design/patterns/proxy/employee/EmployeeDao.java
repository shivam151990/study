package design.patterns.proxy.employee;

public interface EmployeeDao {

    void createEmployee(String clientId, String empId);

    void deleteEmployee(String clientId, String empId);
}
