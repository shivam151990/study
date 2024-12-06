package design.patterns.template.online_offline;

public abstract class PhoneOrder {
    final void createOrder() {
        selectProduct();
        packProduct();
        makePayment();
        deliverProduct();
    }
    abstract void selectProduct();
    abstract void makePayment();
    void packProduct() {
        System.out.println("packing the product");
    }
    abstract void deliverProduct();
}
