import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {

    static ArrayList<ArrayList<Integer>> arList;
    static PriorityQueue<VandD> pQueue;
    static int[] parent;
    static int[][] p;
    static int[] dist;

    static int minDist;

    public static void main(String[] args) throws IOException {

        int N, M, S, D, U, V, P;

        String[] strs;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            arList = new ArrayList<ArrayList<Integer>>();
            pQueue = new PriorityQueue<VandD>();

            strs = br.readLine().split(" ");

            N = Integer.parseInt(strs[0]);
            M = Integer.parseInt(strs[1]);

//            System.out.println("N : " + N + ", M : " + M);

            if (N == 0 && M == 0) {
                break;
            }

            for (int i = 0; i < N; i++) {
                arList.add(new ArrayList<Integer>());
            }

            parent = new int[N];
            p = new int[N][N];
            dist = new int[N];

            minDist = Integer.MAX_VALUE;

            Arrays.fill(dist, Integer.MAX_VALUE);

            strs = br.readLine().split(" ");

            S = Integer.parseInt(strs[0]);
            D = Integer.parseInt(strs[1]);

//            System.out.println("S : " + S + ", D : " + D);

            dist[S] = 0;

            for (int tc = 1; tc <= M; tc++) {
                strs = br.readLine().split(" ");

                U = Integer.parseInt(strs[0]);
                V = Integer.parseInt(strs[1]);
                P = Integer.parseInt(strs[2]);

                p[U][V] = P;

                arList.get(U).add(V);
            }

            while(true) {
                Dijkstra(S);

//                System.out.println(dist[D]);

                if (minDist > dist[D]) {
                    minDist = dist[D];
                } else if (minDist < dist[D]) {
                    if (dist[D] != Integer.MAX_VALUE) {
                        minDist = dist[D];
                    } else {
                        minDist = -1;
                    }

                    break;
                }

                removeMinPath(D);

                Arrays.fill(parent, 0);
                Arrays.fill(dist, Integer.MAX_VALUE);
                dist[S] = 0;
            }

            System.out.println(minDist);
        }
    }

    static void Dijkstra(int s) {
        pQueue.add(new VandD(s, dist[s]));

        while (false == pQueue.isEmpty()) {
            VandD vd;
            vd = pQueue.peek();
            pQueue.poll();

            int v = vd.v;
            int d = vd.d;

//            System.out.println("v : " + v + ", d : " + d);

            for (int i = 0; i < arList.get(v).size(); i++) {
                int nextV = arList.get(v).get(i);
//                System.out.println("nextV : " + nextV);
                if (d != Integer.MAX_VALUE && d + p[v][nextV] < dist[nextV]) {
                    dist[nextV] = d + p[v][nextV];
//                    System.out.println(nextV + ": dist = " + dist[nextV]);

                    pQueue.add(new VandD(nextV, dist[nextV]));
                    parent[nextV] = v + 1;
                }
            }
        }
    }

    static void removeMinPath(int d) {
        int child = d;

        while(true) {
            if (parent[child] > 0) {
                for (int j = 0; j < arList.get(parent[child] - 1).size(); j++) {
                    if (arList.get(parent[child] - 1).get(j) == child) {
//                        System.out.println(child + "'s parent : " + (parent[child] - 1));
                        arList.get(parent[child] - 1).remove(j);

                        child = parent[child] - 1;
//                        System.out.println("new child : " + child);
                        break;
                    }
                }

                continue;
            }

            break;
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
