package design.patterns.bridge.shape;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Shape s1 = new Rectangle(new RedColor());
        s1.draw();
        s1.draw();

    }

    public int[] findDiagonalOrder(int[][] mat) {
        int r = mat.length;
        int c = mat[0].length;
        int[] result = new int[r * c];
        LinkedHashMap<Integer, List<Integer>> res = new LinkedHashMap<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int k = i + j;
                res.putIfAbsent(k, new ArrayList<>());
                res.get(k).add(mat[i][j]);
            }
        }
        int idx = 0;
        boolean flip = true;
        for (Map.Entry<Integer, List<Integer>> entry: res.entrySet()) {
            List<Integer> cur = entry.getValue();
            if (flip) {
                Collections.reverse(cur);
                flip = false;
            } else {
                flip = true;
            }
            for (int ele: cur) {
                result[idx++] = ele;
            }
        }
        return result;
    }
}
