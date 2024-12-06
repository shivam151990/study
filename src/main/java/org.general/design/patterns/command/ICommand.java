package design.patterns.command;

/**
 * Turns a request or a behaviour into a standalone object that contains
 * everything about the request and encapsulates all the relevant info needed to performa action
 */
public interface ICommand {
    void execute();
}
