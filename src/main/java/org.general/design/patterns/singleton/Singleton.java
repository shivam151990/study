package design.patterns.singleton;

import static java.util.Objects.isNull;

public class Singleton {

    private static Singleton instance;

    private Singleton(){
    }

    synchronized public static Singleton getInstance() {
        if (isNull(instance)) {
            synchronized (Singleton.class) {
                if (isNull(instance)) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
