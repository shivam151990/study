package com.shivam151990.lld.filesystem;

class File extends Node {
    private final long bytes;

    File(String name, long bytes) {
        super(name);
        this.bytes = bytes;
    }

    @Override
    long size() {
        return bytes;
    }

    @Override
    boolean isDirectory() {
        return false;
    }
}
