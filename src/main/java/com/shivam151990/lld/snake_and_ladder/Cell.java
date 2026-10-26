package com.shivam151990.lld.snake_and_ladder;

public abstract class Cell {
    private int start;
    private int end;

    public Cell(int start, int end) {
        this.start = start;
        this.end = end;
    }

    abstract int getStart();
    abstract int getEnd();
}
