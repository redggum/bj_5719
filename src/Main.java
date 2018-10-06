import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] ar;
    static PriorityQueue<VandD> pQueue;
    static int[] parent;
    static int[][] p;
    static int[] dist;
    static ArrayList<Integer> childs;

    static int minDist;

    static int N, M, S, D, U, V, P;

    public static void main(String[] args) throws IOException {

        String[] strs;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            pQueue = new PriorityQueue<VandD>();

            strs = br.readLine().split(" ");

            N = Integer.parseInt(strs[0]);
            M = Integer.parseInt(strs[1]);

            ar = new int[N][N];
//            System.out.println("N : " + N + ", M : " + M);

            if (N == 0 && M == 0) {
                break;
            }

            parent = new int[N];
            childs = new ArrayList<Integer>();
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

                ar[U][V] = 1;

                if (V == D) {
                    childs.add(U);
                }
            }

            Dijkstra(S);

//            System.out.println("First dist of D : " + dist[D]);

            eraseShortestPath(D);

            Arrays.fill(parent, 0);
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[S] = 0;

//            System.out.println("dist[D] : " + dist[D]);

            Dijkstra(S);

            System.out.println(dist[D] != Integer.MAX_VALUE ? dist[D] : -1);
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

            for (int nextV = 0; nextV < ar[v].length; nextV++) {
                if (ar[v][nextV] == 0) {
                    continue;
                }
//                System.out.println("nextV : " + nextV);
//                System.out.println("p[" + v + "][" + nextV + "] : " + p[v][nextV]);
                if (d != Integer.MAX_VALUE && d + p[v][nextV] < dist[nextV] && p[v][nextV] != 0) {
                    dist[nextV] = d + p[v][nextV];
//                    System.out.println(nextV + ": dist = " + dist[nextV]);

                    pQueue.add(new VandD(nextV, dist[nextV]));
                    parent[nextV] = v + 1;
                }
            }
        }
    }

    static void eraseShortestPath(int d) {
        Queue<Integer> q = new LinkedList<Integer>();

        ((LinkedList<Integer>) q).push(d);

        while(q.size() > 0) {
            int dest = ((LinkedList<Integer>) q).pop();

            for (int i = 0; i < N; i++) {
//                System.out.println("p[" + i + "][" + dest + "] : " + p[i][dest]);
                if (dist[dest] == dist[i] + p[i][dest] && p[i][dest] != 0) {
//                    System.out.println("Erased v (dest : " + dest + ") : " + i);
                    p[i][dest] = 0;
                    ((LinkedList<Integer>) q).push(i);
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
