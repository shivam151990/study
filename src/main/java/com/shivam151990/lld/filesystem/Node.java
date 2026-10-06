package com.shivam151990.lld.filesystem;

abstract class Node {
    private final String name;

    Node(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    abstract long size();

    abstract boolean isDirectory();
}