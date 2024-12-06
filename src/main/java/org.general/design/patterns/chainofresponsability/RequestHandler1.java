package design.patterns.chainofresponsability;

public class RequestHandler1 implements RequestHandler {

    private RequestHandler handler;

    @Override
    public void handleRequest(Request request) {
        if (request.getPriority() == Priority.LOW) {
            System.out.println("Low priority executed");
        } else {
            handler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RequestHandler handler) {
        this.handler = handler;
    }
}
