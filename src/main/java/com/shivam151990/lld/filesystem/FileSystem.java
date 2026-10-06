package com.shivam151990.lld.filesystem;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final Directory root = new Directory("/");

    /** Creates the directory, plus any missing parents (like mkdir -p). Rejects duplicates. */
    public void mkdir(String path) {
        List<String> parts = split(path);
        String leaf = lastOf(parts, path);
        Directory parent = ensureDirectories(parts.subList(0, parts.size() - 1));
        if (parent.get(leaf) != null) {
            throw new FileSystemException("Already exists: " + path);
        }
        parent.add(new Directory(leaf));
    }

    /** Creates a file of the given size, plus any missing parent directories. Rejects duplicates. */
    public void touch(String path, long size) {
        if (size < 0) {
            throw new FileSystemException("Size cannot be negative: " + size);
        }
        List<String> parts = split(path);
        String leaf = lastOf(parts, path);
        Directory parent = ensureDirectories(parts.subList(0, parts.size() - 1));
        if (parent.get(leaf) != null) {
            throw new FileSystemException("Already exists: " + path);
        }
        parent.add(new File(leaf, size));
    }

    /** Directory: its children's names, sorted. File: just the file's own name. */
    public List<String> ls(String path) {
        Node node = resolve(path);
        if (node.isDirectory()) {
            return ((Directory) node).childNames();
        }
        return List.of(node.getName());
    }

    /** File: its bytes. Directory: recursive total of everything under it. */
    public long size(String path) {
        return resolve(path).size();
    }

    /** Removes a file or a whole directory subtree. The root cannot be removed. */
    public void rm(String path) {
        List<String> parts = split(path);
        String leaf = lastOf(parts, path);
        Node parent = resolve(parts.subList(0, parts.size() - 1));
        if (!parent.isDirectory() || ((Directory) parent).remove(leaf) == null) {
            throw new FileSystemException("No such file or directory: " + path);
        }
    }

    // ---------- helpers ----------

    private List<String> split(String path) {
        if (path == null || !path.startsWith("/")) {
            throw new FileSystemException("Path must be absolute: " + path);
        }
        List<String> parts = new ArrayList<>();
        for (String part : path.split("/")) {
            if (part.isEmpty()) {
                continue; // tolerates "//" and a trailing "/"
            }
            if (part.equals(".") || part.equals("..")) {
                throw new FileSystemException("Relative segments are not supported: " + path);
            }
            parts.add(part);
        }
        return parts;
    }

    private String lastOf(List<String> parts, String path) {
        if (parts.isEmpty()) {
            throw new FileSystemException("Operation not allowed on root: " + path);
        }
        return parts.get(parts.size() - 1);
    }

    private Node resolve(String path) {
        return resolve(split(path));
    }

    private Node resolve(List<String> parts) {
        Node current = root;
        for (String part : parts) {
            if (!current.isDirectory()) {
                throw new FileSystemException("Not a directory: " + current.getName());
            }
            current = ((Directory) current).get(part);
            if (current == null) {
                throw new FileSystemException("No such file or directory: " + part);
            }
        }
        return current;
    }

    /**
     * Walks down, creating missing directories. Fails if a path segment is a file.
     * No rollback is needed: once one segment is missing, everything below it is missing too,
     * so a failure (a file in the way) can only happen before anything has been created.
     */
    private Directory ensureDirectories(List<String> parts) {
        Directory current = root;
        for (String part : parts) {
            Node next = current.get(part);
            if (next == null) {
                Directory created = new Directory(part);
                current.add(created);
                current = created;
            } else if (next.isDirectory()) {
                current = (Directory) next;
            } else {
                throw new FileSystemException("Not a directory: " + part);
            }
        }
        return current;
    }


}
