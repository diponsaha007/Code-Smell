package com.jetbrains;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class CMessage {
    private Socket socket;
    private String username, text, filename;
    private BufferedReader br;

    public CMessage(String username, String text,Socket s, BufferedReader br) {
        this.br = br;
        this.socket = s;
        this.username = username;
        this.text = text;
    }

    public void sendMessage(Users thisUser, PrintWriter pr) {
        String ss = "User " + thisUser.getUsername () + " says: " + text;
        for (int i = 0; i < WorkerThread.getWorkerThreads ().size (); i++) {
            if (WorkerThread.getWorkerThreads ().get (i).getThisUser ().getUsername ().compareTo (username) == 0) {
                if (WorkerThread.getWorkerThreads ().get (i).getThisUser ().getLoggedin ()) {
                    WorkerThread.getWorkerThreads ().get (i).getPr ().println (ss);
                }
            }
        }
    }

    public void sendFile(Users thisUser,String string) throws Exception{
        String ss = "User " + thisUser.getUsername () + " sent you a new file ";
        try {
            String strRecv = br.readLine ();

            int filesize = Integer.parseInt (strRecv);


            FileOutputStream fos = new FileOutputStream (string);
            BufferedOutputStream bos = new BufferedOutputStream (fos);

            /*byte[] contents = new byte[10000];
            InputStream is = socket.getInputStream ();*/
            int bytesRead = 0;
            int total = 0;
            while (total != filesize)
            {
                byte[] contents = new byte[10000];
                InputStream is = socket.getInputStream ();
                bytesRead = is.read (contents);
                total += bytesRead;
                bos.write (contents, 0, bytesRead);
                bos.flush ();
                //System.out.println (bytesRead);
            }
            bos.flush ();
        } catch (Exception e) {
            System.err.println ("Could not transfer file.");
        }



    }
}

