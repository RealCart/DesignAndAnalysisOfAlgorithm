package main.com.beka.assignment.features;

public final class Metrics {

    public long comparisons;
    public long moves;
    public long allocations;
    public int maxDepth;
    private int depth;

    public void enter() { depth++; if (depth > maxDepth) maxDepth = depth; }
    public void exit()  { depth--; }
    public void cmp()   { comparisons++; }
    public void mv(long k) { moves += k; }
    public void alloc(long bytes) { allocations += bytes; }
    public void reset() { comparisons = moves = allocations = 0; maxDepth = depth = 0; }
}

