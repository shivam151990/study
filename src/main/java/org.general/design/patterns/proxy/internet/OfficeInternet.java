package design.patterns.proxy.internet;

public class OfficeInternet implements InternetAccess {
    private final String employeeName;

    public OfficeInternet(String empName) {
        this.employeeName = empName;
    }
    @Override
    public void grantInternetAccess() {
        System.out.println("Internet Access granted for employee: " + employeeName);
    }
}
