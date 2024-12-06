package design.patterns.template.online_offline;

public class BuyPhone {
    public static void main(String[] args) {
        PhoneOrder offlineStore = new OfflineStore();
        offlineStore.createOrder();
        System.out.println("-------------------------------------------------");
        PhoneOrder onlineStore = new OnlineStore();
        onlineStore.createOrder();
    }
}
