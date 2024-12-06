package design.patterns.proxy.internet;

public class ProxyInternetAccess implements InternetAccess {

    private final String employeeName;
    private InternetAccess realAccess;

    public ProxyInternetAccess(String employeeName, InternetAccess realAccess) {
        this.employeeName = employeeName;
        this.realAccess = realAccess;
    }

    @Override
    public void grantInternetAccess() {
        if (getRole(employeeName) > 4) {
//            realAccess = new OfficeInternet(employeeName);
            realAccess.grantInternetAccess();
        } else {
            System.out.println("No Internet access granted. Your job level is below 5");
        }
    }
    private int getRole(String empName) {
        return 9;
    }
}