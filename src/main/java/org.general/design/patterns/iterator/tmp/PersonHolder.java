package design.patterns.iterator.tmp;

import java.util.ArrayList;
import java.util.List;

public class PersonHolder {

    private final List<Person> males;
    private final List<Person> females;

    public PersonHolder() {
        males = new ArrayList<>();
        females = new ArrayList<>();
    }

    public void addPerson(Person person) {
        if (Gender.MALE == person.getGender()) {
            males.add(person);
        } else {
            females.add(person);
        }
    }

    /**
     * Get the iterator for the collection of people
     */
    public Iterator getIterator(Gender gender) {
        if (Gender.MALE == gender) {
            return new MaleIterator();
        } else {
            return new FemaleIterator();
        }
    }


    private class MaleIterator implements Iterator {
        private int idx = 0;
        @Override
        public Person next() {
            if (idx >= males.size()) {
                throw new RuntimeException("NO MALES");
            }
            return males.get(idx++);
        }

        @Override
        public boolean hasNext() {
            return idx < males.size();
        }
    }

    private class FemaleIterator implements Iterator {
        private int idx = 0;
        @Override
        public Person next() {
            if (idx >= females.size()) {
                throw new RuntimeException("NO FEMALES");
            }
            return females.get(idx++);
        }

        @Override
        public boolean hasNext() {
            return idx < females.size();
        }
    }
}
