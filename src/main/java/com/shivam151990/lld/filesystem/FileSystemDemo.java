package com.shivam151990.lld.filesystem;

public class FileSystemDemo {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        fs.mkdir("/home/shivam/docs");
        fs.touch("/home/shivam/docs/resume.pdf", 200);
        fs.touch("/home/shivam/docs/notes.txt", 50);
        fs.touch("/home/shivam/a.txt", 10);
        fs.mkdir("/tmp");

        System.out.println(fs.ls("/"));                       // [home, tmp]
        System.out.println(fs.ls("/home/shivam"));            // [a.txt, docs]
        System.out.println(fs.ls("/home/shivam/docs"));       // [notes.txt, resume.pdf]
        System.out.println(fs.ls("/home/shivam/a.txt"));      // [a.txt]
        System.out.println(fs.size("/home/shivam/docs"));     // 250
        System.out.println(fs.size("/home"));                 // 260
        System.out.println(fs.size("/"));                     // 260

        expectFailure(() -> fs.mkdir("/home/shivam"));                    // duplicate
        expectFailure(() -> fs.touch("/home/shivam/a.txt", 5));           // duplicate file
        expectFailure(() -> fs.mkdir("/home/shivam/a.txt/sub"));          // file in the path
        expectFailure(() -> fs.ls("/nope"));                              // missing
        expectFailure(() -> fs.mkdir("home"));                            // not absolute
        expectFailure(() -> fs.rm("/"));                                  // root

        fs.rm("/home/shivam/docs");
        System.out.println(fs.ls("/home/shivam"));            // [a.txt]
        System.out.println(fs.size("/"));                     // 10
    }

    private static void expectFailure(Runnable action) {
        try {
            action.run();
            System.out.println("FAIL: expected an exception");
        } catch (FileSystemException e) {
            System.out.println("OK  : " + e.getMessage());
        }
    }
}

