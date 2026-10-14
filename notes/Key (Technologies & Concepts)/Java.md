# JVM Architecture
**The Java Virtual Machine (JVM) architecture consists of several main components:**

1. **Class Loader Subsystem**
    - Responsible for loading class files (.class) into the JVM.
    - Performs loading, linking (verification, preparation, resolution), and initialization of classes and interfaces.
2. **Runtime Data Areas**
    - These are memory areas used during execution:
        - **Method Area:** Stores class metadata, static variables, and method code. (In Java 8+, this is called Metaspace.)
        - **Heap:** Stores all objects and their instance variables. This is the main area for dynamic memory allocation.
        - **JVM Stacks:** Each thread has its own stack, which stores frames for method calls, local variables, and partial results.
        - **Program Counter (PC) Register:** Each thread has its own PC register, which keeps track of the current instruction being executed.
        - **Native Method Stack:** Used for native (non-Java) method calls, typically written in C/C++.
3. **Execution Engine**
    - Executes the bytecode loaded into the JVM.
    - Contains:
        - **Interpreter:** Reads and executes bytecode instructions one by one.
        - **Just-In-Time (JIT) Compiler:** Compiles frequently executed bytecode into native machine code for better performance.
        - **Garbage Collector:** Automatically manages memory by reclaiming objects that are no longer in use.
4. **Native Interface (JNI)**
    - Allows the JVM to call and be called by native applications and libraries written in other languages like C or C++.
5. **Native Method Libraries**
    - Libraries (often written in C/C++) that the JVM can use via the native interface.