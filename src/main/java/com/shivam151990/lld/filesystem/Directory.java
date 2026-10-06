package com.shivam151990.lld.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Directory extends Node {
    // TreeMap keeps children sorted by name, so ls() is alphabetical for free.
    private final Map<String, Node> children = new TreeMap<>();

    Directory(String name) {
        super(name);
    }

    Node get(String name) {
        return children.get(name);
    }

    void add(Node node) {
        children.put(node.getName(), node);
    }

    Node remove(String name) {
        return children.remove(name);
    }

    List<String> childNames() {
        return new ArrayList<>(children.keySet());
    }

    /** Never checks whether a child is a File or a Directory: that is the Composite pattern. */
    @Override
    long size() {
        long total = 0;
        for (Node child : children.values()) {
            total += child.size();
        }
        return total;
    }

    @Override
    boolean isDirectory() {
        return true;
    }
}
