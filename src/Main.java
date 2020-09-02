public class Main {
    static final int SIZE = 10000000;
    static int HALF = SIZE / 2;
    static float[] someArr = new float[SIZE];


    public static void main(String[] args) {
        nonParallel();
        parallel();
    }
    static void calc(float [] a, int idx){
        for (int i = 0; i < a.length; i++) {
            a[i] = (float) (someArr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void nonParallel() {

        for (int i = 0; i < SIZE; i++) {
            someArr[i] = 1;
        }
        long start = System.currentTimeMillis();
        calc(someArr,0);
        long finish = System.currentTimeMillis();
        System.out.println("Время выполнения первого метода: " + (finish-start));
    }

    public static void parallel() {
        for (int i = 0; i < SIZE; i++) {
            someArr[i] = 1;
        }
        long start = System.currentTimeMillis();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(a1, 0, someArr, 0, HALF);
        System.arraycopy(a2, 0, someArr, HALF, HALF);

        Thread thread1 = new Thread(() -> {
            calc(a1,0);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            calc(a1,HALF);
        });
        thread2.start();
        try{
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, someArr, 0, HALF);
        System.arraycopy(a2, 0, someArr, HALF, HALF);

        long finish = System.currentTimeMillis();
        System.out.println("Время выполнения второго метода: " + (finish - start));
    }
}
