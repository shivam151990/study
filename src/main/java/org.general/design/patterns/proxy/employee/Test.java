package design.patterns.proxy.employee;

public class Test {

    public static void main(String[] args) {
        EmployeeDao p = new EmployeeDaoProxy(new EmployeeDaoImpl());

        p.deleteEmployee("NORMAL", "ID");
    }
}
