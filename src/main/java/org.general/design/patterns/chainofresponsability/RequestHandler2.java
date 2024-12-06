package design.patterns.chainofresponsability;

public class RequestHandler2 implements RequestHandler {

    private RequestHandler handler;

    @Override
    public void handleRequest(Request request) {
        if (request.getPriority() == Priority.HIGH) {
            System.out.println("High priority request executed");
        } else {
            handler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RequestHandler handler) {
        this.handler = handler;
    }
}
