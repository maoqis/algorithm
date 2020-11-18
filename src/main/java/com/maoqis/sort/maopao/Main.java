package com.maoqis.sort.maopao;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static final boolean Debug = true;
    public static int[] arrays = null;
    private static Integer readLastPos = -1;
    private static int mNum;
    public static Object mPosLock = new Object();

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        mNum = sc.nextInt();
        arrays = new int[mNum];
        sys("--start--");
        sys(mNum);
        A linkA = A.createLinkA(2, mNum - 1);
        while (sc.hasNext()) {
            int i = sc.nextInt();
            readLastPos++;
            sys("read=" + i);
            linkA.add(i);
        }


    }

    public static class A {
        public A(int endPos) {
            this.endPos = endPos;
        }

        public A next;
        public Integer maxV;
        public int endPos;
        public int curPos = -1;
        Thread thread = null;
        public LinkedBlockingDeque<Integer> unDo = new LinkedBlockingDeque<Integer>();

        public void add(int cur) {
            unDo.offer(cur);
            startSingleThread();
        }

        public void startSingleThread() {
            if (thread == null) {
                synchronized (this) {
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            public void run() {
                                while (curPos <= endPos) {
                                    Integer peek = null;
                                    try {
                                        peek = unDo.take();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int state = 0;
                                    if (maxV == null) {
                                        state = 0;
                                    } else {

                                        if (peek > maxV) {
                                            state = 1;

                                        } else {
                                            state = 2;
                                        }
                                    }
                                    switch (state) {
                                        case 0:
                                            maxV = peek;
                                            break;
                                        case 1:
                                            if (next != null) {
                                                next.add(maxV);
                                            }
                                            maxV = peek;
                                            break;
                                        case 2:
                                            if (next != null) {
                                                next.add(peek);
                                            }
                                            break;
                                    }
                                    curPos++;
                                    sys(Thread.currentThread().getName() + " curPos=" + curPos + " state=" + state + " peek=" + peek + " maxV=" + maxV);

                                }

                                sys(endPos + " maxV=" + maxV);
                                if (next == null) {
                                    sys(endPos + "计算完成");
                                }


                            }


                        });
                        thread.start();
                    }
                }
            }
        }

        public static A createLinkA(int threadNum, int endPos) {
            A ret = null;
            A last = null;

            for (int i = 0; i < threadNum; i++) {
                A a = new A(endPos);
                if (i == 0) {
                    ret = a;
                }
                if (last != null) {
                    last.next = a;
                }
                endPos--;
                last = a;
            }

            return ret;
        }

    }


    public void sys(Object o) {
        sys(o.toString());
    }

    public static void sys(String out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(int out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(float out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(boolean out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(double out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(long out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(char out) {
        if (Debug) {
            System.out.println(out);
        }
    }

    public static void sys(char[] out) {
        if (Debug) {
            System.out.println(out);
        }
    }
}