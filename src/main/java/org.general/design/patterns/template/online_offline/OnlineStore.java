package design.patterns.template.online_offline;

public class OnlineStore extends PhoneOrder
{
    @Override
    void selectProduct() {
        System.out.println("selecting the product in online store");
    }

    @Override
    void makePayment() {
        System.out.println("making the payment in online store");
    }

    @Override
    void deliverProduct() {
        System.out.println("product delivered at home of the client");
    }
}
