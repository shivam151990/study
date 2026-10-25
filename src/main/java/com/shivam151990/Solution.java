package com.shivam151990;

class Solution {

    private int[] parent;
    private int[] rank;

    private int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    private void union(int x, int y) {
        int xParent = find(x);
        int yParent = find(y);

        if (xParent == yParent) {
            return;
        }

        if (rank[xParent] < rank[yParent]) {
            parent[xParent] = yParent;
        } else if (rank[yParent] < rank[xParent]) {
            parent[yParent] = xParent;
        } else {
            parent[xParent] = yParent;
            rank[yParent]++;
        }
    }

    public boolean isCycle(int V, int[][] edges) {
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
        }
        for (int[] ed: edges) {
            int u = ed[0];
            int v = ed[1];
            if (u < v) {
                int uParent = find(u);
                int vParent = find(v);
                if (uParent == vParent) {
                    return true;
                } else {
                    union(u, v);
                }
            }
        }
        return false;
    }
}