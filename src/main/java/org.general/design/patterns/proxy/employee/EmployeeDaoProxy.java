package design.patterns.proxy.employee;

public class EmployeeDaoProxy implements EmployeeDao {

    private final EmployeeDao employeeDao;

    public EmployeeDaoProxy(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void createEmployee(String clientId, String empId) {
        if (clientId.equals("NORMAL")) {
            employeeDao.createEmployee(clientId, empId);
        }
    }

    @Override
    public void deleteEmployee(String clientId, String empId) {
        if (clientId.equals("ADMIN")) {
            employeeDao.deleteEmployee(clientId, empId);
        } else {
            System.out.println(clientId + " does not have permission to delete");
        }
    }
}
