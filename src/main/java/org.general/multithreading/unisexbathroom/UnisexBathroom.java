package multithreading.unisexbathroom;

import java.util.concurrent.Semaphore;

public class UnisexBathroom {

    static final String MALE = "male";
    static final String FEMALE = "female";

    static final String NONE = "none";

    private int empCount = 0;
    private String inUseBy = NONE;

    private Semaphore maxAllowed = new Semaphore(3);
    private void useBathroom(String name) throws InterruptedException {
        System.out.println(name + " using bathroom, employees in bathroom: " + empCount);
        Thread.sleep(4000);
        System.out.println(name + " done using bathroom");
        System.out.println();
    }

    public void maleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(FEMALE)) {
                System.out.println("MALE HAVE TO WAIT....");
                wait();
            }
//            maxAllowed.acquire();
            empCount++;
            inUseBy = MALE;
        }
        maxAllowed.acquire();
        useBathroom(name);
        maxAllowed.release();

        synchronized (this) {
            empCount--;
            if (empCount == 0) {
                inUseBy = NONE;
            }
            notifyAll();
        }
    }

    public void femaleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(MALE)) {
                System.out.println("FEMALE HAVE TO WAIT....");
                wait();
            }
//            maxAllowed.acquire();
            empCount++;
            inUseBy = FEMALE;
        }

        maxAllowed.acquire();
        useBathroom(name);
        maxAllowed.release();

        synchronized (this) {
            empCount--;
            if (empCount == 0) {
                inUseBy = NONE;
            }
            notifyAll();
        }
    }
}
