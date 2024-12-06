package design.patterns.chainofresponsability;

public class Main {

    public static void main(String[] args) {
        RequestHandler h1 = new RequestHandler1();
        RequestHandler h2 = new RequestHandler2();

        h1.setNextHandler(h2);

        Request r1 = new Request(Priority.HIGH);
        Request r2 = new Request(Priority.MEDIUM);

        h1.handleRequest(r1);
    }
}
