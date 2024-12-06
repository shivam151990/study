package design.patterns.composite;

import java.util.HashSet;
import java.util.Set;

public class Directory implements FileSystemComponent {

    private String name;
    private Set<FileSystemComponent> components;

    public Directory(String name) {
        this.name = name;
        components = new HashSet<>();
    }

    public boolean addComponent(FileSystemComponent component) {
        return components.add(component);
    }

    public boolean remoComponent(FileSystemComponent component) {
        return components.remove(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component: components) {
            component.showDetails();
        }
    }
}
