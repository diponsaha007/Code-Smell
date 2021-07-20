package com.jetbrains;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static int workerThreadCount = 0;
    public static void main(String[] args) {
        int id = 1;

        try {
            ServerSocket serverSocket = new ServerSocket (5120);

            while (true) {
                Socket s = serverSocket.accept ();        //TCP Connection
                WorkerThread wt = new WorkerThread (s, id);
                wt.setThread (wt);
                Thread t = new Thread (wt);
                t.start ();
                workerThreadCount++;
                System.out.println ("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);
                id++;
            }
        } catch (Exception e) {
            System.err.println ("Problem in ServerSocket operation. Exiting main.");
        }
    }
}
