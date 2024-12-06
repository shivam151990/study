package design.patterns.chainofresponsability;

public interface RequestHandler {
    void handleRequest(Request request);
    void setNextHandler(RequestHandler handler);
}
