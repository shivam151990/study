package design.patterns.template.online_offline;

public class OfflineStore extends PhoneOrder
{
    public OfflineStore() {
    }
    @Override
    void selectProduct() {
        System.out.println("selecting the product in offline store");
    }

    @Override
    void makePayment() {
        System.out.println("making the payment in offline store");
    }

    @Override
    void deliverProduct() {
        System.out.println("product delivered in hands in offline store");
    }
}
