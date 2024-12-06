package design.patterns.iterator.tmp;

public class Runner {
    public static void main(String[] args) {
        Person p1 = new Person("Shivam", Gender.MALE);
        Person p2 = new Person("Ashish", Gender.MALE);
        Person p3 = new Person("Abhilasha", Gender.FEMALE);

        PersonHolder ph = new PersonHolder();
        ph.addPerson(p1);
        ph.addPerson(p2);
        ph.addPerson(p3);

        Iterator maleIt = ph.getIterator(Gender.MALE);
        while (maleIt.hasNext()) {
            System.out.println(maleIt.next());
        }
        System.out.println("---------------------------------------------------------");
        Iterator femaleIt = ph.getIterator(Gender.FEMALE);
        while (femaleIt.hasNext()) {
            System.out.println(femaleIt.next());
        }
    }
}
