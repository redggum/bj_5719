import com.sun.deploy.util.ArrayUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Test");

        int N, M, S, D, U, V, P;
        ArrayList<ArrayList<Integer>> arList = new ArrayList<ArrayList<Integer>>();
        PriorityQueue<VandD> pQueue = new PriorityQueue<VandD>();
        int[] parent;
        int[][] p;
        int[] dist;
        String[] strs;

        while(true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            strs = br.readLine().split(" ");

            N = Integer.parseInt(strs[0]);
            M = Integer.parseInt(strs[1]);

            if (N == 0 && M == 0) {
                break;
            }

            for (int i = 0; i < N; i++) {
                arList.add(new ArrayList<Integer>());
            }

            parent = new int[N];
            p = new int[N][N];
            dist = new int[N];

            Arrays.fill(dist, Integer.MAX_VALUE);

            strs = br.readLine().split(" ");

            S = Integer.parseInt(strs[0]);
            D = Integer.parseInt(strs[1]);

            dist[S] = 0;

            for (int tc = 1; tc <= M; tc++) {
                strs = br.readLine().split(" ");

                U = Integer.parseInt(strs[0]);
                V = Integer.parseInt(strs[1]);
                P = Integer.parseInt(strs[1]);

                p[U][V] = P;

                arList.get(U).add(V);
            }

            pQueue.add(new VandD(S, dist[S]));

            while (pQueue.isEmpty()) {
                VandD vd;
                vd = pQueue.peek();
                pQueue.poll();

                int v = vd.v;
                int d = vd.d;

                for (int i = 0; i < arList.get(v).size(); i++) {
                    int nextV = arList.get(v).get(i);

                    if (d != Integer.MAX_VALUE && d + p[v][nextV] < dist[nextV]) {
                        dist[nextV] = d + p[v][nextV];
                        pQueue.add(new VandD(nextV, dist[nextV]));
                        parent[nextV] = v;
                    }
                }
            }
        }
    }
}

class VandD implements Comparable<VandD> {
    int v;
    int d;

    VandD(int v, int d) {
        this.v = v;
        this.d = d;
    }

    @Override
    public int compareTo(VandD comp) {

        if (this.d < comp.d) {
            return -1;
        } else if (this.d > comp.d) {
            return 1;
        } else {
            return 0;
        }
    }
}
