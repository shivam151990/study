package design.patterns.proxy.employee;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void createEmployee(String clientId, String empId) {
        System.out.println("Created Employee: " + empId);
    }

    @Override
    public void deleteEmployee(String clientId, String empId) {
        System.out.println("Deleted employee: " + empId);
    }
}
