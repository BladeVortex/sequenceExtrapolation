package sample
import java.util.ArrayList;
import java.math.BigDecimal;

public class Controller {
    private ArrayList<ArrayList<Double>> A = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> C = new ArrayList<ArrayList<Double>>();
    private int M, N;
    private String elements;
    private boolean exA = false, exB = false, exC = false;

    public Controller(String k) {
        String[] h = k.split(",");
        int g = h.length;
        String s = "";

        for (int j = 0; j < g; j++) {
            for (int i = 0; i < g; i++)
                s += String.valueOf(Math.pow(j + 1, g - (i + 1))) + ",";
            s += h[j] + ",";
        }

        N = g;
        M = g + 1;
        elements = s.substring(0, s.length() - 1);
    }

    private void populateA() {
        String[] f = elements.split(",");
        if (f.length != M * N) exA = false;
        else {
            for (int i = 0; i < N; i++) {
                A.add(new ArrayList<Double>());
                for (int j = 0; j < M; j++) {
                    A.get(i).add(Double.valueOf(f[i * M + j]));
                }
                //System.out.println("i = " + i + ": " + A.get(i));
            }
            exA = true;
        }
    }

    private void populateB() {
        if(A.isEmpty()) exB = false;
        else {
            ArrayList<Double> div = new ArrayList<Double>();
            double[][] vbn = new double[N][M];
            for(int t = 0; t < N; t++)
                for(int r = 0; r < M; r++)
                    vbn[t][r] = A.get(t).get(r);

            for(int f = 0; f < N-1; f++) {
                for (int k = f + 1; k < N; k++)
                    div.add(vbn[k][f] / vbn[f][f]);

                for(int j = f + 1; j < N; j++)
                    for (int y = 0; y < M; y++)
                        vbn[j][y] = vbn[j][y] - (vbn[f][y] * div.get((j-f)-1));
                div.clear();
            }

            for(int t = 0; t < N; t++) {
                B.add(new ArrayList<Double>());
                for(int r = 0; r < M; r++)
                    B.get(t).add(vbn[t][r]);
            }
            exB = true;
        }
    }

    private void populateC() {
        if(B.isEmpty()) exC = false;
        else {
            ArrayList<Double> div = new ArrayList<Double>();
            double[][] vbn = new double[N][M];
            for(int t = 0; t < N; t++)
                for(int r = 0; r < M; r++)
                    vbn[t][r] = B.get(t).get(r);

            double pivot;
            for(int f = N-1; f >= 0; f--) {
                pivot = vbn[f][f];
                for(int k = f-1; k >= 0; k--)
                    div.add(vbn[k][f] / vbn[f][f]);

                for(int j = f-1; j >= 0; j--)
                    for (int y = f; y < M; y++)
                        vbn[j][y] = vbn[j][y] - (vbn[f][y] * div.get((f-j)-1));

                for(int j = f; j < M; j++)
                    vbn[f][j] = vbn[f][j] / pivot;
                div.clear();
            }

            for(int t = 0; t < N; t++) {
                C.add(new ArrayList<Double>());
                for(int r = 0; r < M; r++)
                    C.get(t).add(vbn[t][r]);
            }
            exC = true;
        }
    }

    private BigDecimal truncate(double x, int numberofDecimals) {
        if (x > 0) return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        else return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }

    public boolean printAUG() {
        if (!exA) {
            populateA();
            if (!exA) return false;
        }
        //System.out.println(A);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(truncate(A.get(i).get(j), 2) + "\t");
            }
            System.out.println();
        }
        return true;
    }

    public boolean printEF() {
        if (!exA) {
            populateA();
            if (!exA) return false;
        }
        if (!exB) {
            populateB();
            if (!exB) return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(truncate(B.get(i).get(j), 2) + "\t");
            }
            System.out.println();
        }

        return true;
    }

    public boolean printRREF() {
        if (!exA) {
            populateA();
            if (!exA) return false;
        } if (!exB) {
            populateB();
            if (!exB) return false;
        } if (!exC) {
            populateC();
            if (!exC) return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(truncate(C.get(i).get(j), 2) + "\t");
            }
            System.out.println();
        }

        return true;
    }

    public boolean poly() {
        if (!exA) {
            populateA();
            if (!exA) return false;
        } if (!exB) {
            populateB();
            if (!exB) return false;
        } if (!exC) {
            populateC();
            if (!exC) return false;
        }

        System.out.print("y = ");
        int r;
        for(int i = N-1; i >= 0; i--) {
            if(i != N-1) System.out.print(" + ");
            System.out.print(truncate(C.get(i).get(N), 2) + "x");
            r = N-i-1;
            if(i<N-2) System.out.print("^" + r);
        }
        return true;
    }
}
