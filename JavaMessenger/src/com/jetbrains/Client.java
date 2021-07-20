package com.jetbrains;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public static void main(String argv[]) throws Exception {
        String sentence;
        BufferedReader inFromUser = new BufferedReader (new InputStreamReader (System.in));
        Socket clientSocket = new Socket ("192.168.0.113", 5120);
        PrintWriter outToServer = new PrintWriter (clientSocket.getOutputStream (), true);
        ClientThread clientThread = new ClientThread (clientSocket, outToServer);
        Thread t = new Thread (clientThread);
        t.start ();
        while (true) {
            sentence = inFromUser.readLine ();
            outToServer.println (sentence);
            ArrayList<String> str = new ArrayList<> ();
            for (String val : sentence.split ("[#\n]")) {
                if(!val.isEmpty ()) {
                    str.add (val);
                    //System.out.println (val);
                }
            }
            if(str.get (0).compareTo ("C")==0 && str.size ()>3 )
            {
                try {
                    File file = new File ("C:\\Users\\APURBA SAHA\\Desktop\\Offline\\src\\com\\Files\\"+str.get (3));
                    FileInputStream fis = new FileInputStream (file);
                    BufferedInputStream bis = new BufferedInputStream (fis);
                    OutputStream os = clientSocket.getOutputStream ();
                    long fileLength = file.length ();
                    outToServer.println (String.valueOf (fileLength));        //These two lines are used
                    outToServer.flush ();                                    //to send the file size in bytes.

                    long current = 0;

                    while (current != fileLength) {
                        int size = 10000;
                        if (fileLength - current >= size)
                            current += size;
                        else {
                            size = (int) (fileLength - current);
                            current = fileLength;
                        }
                        byte[] contents = new byte[size];
                        bis.read (contents, 0, size);
                        os.write (contents);
                        os.flush ();
                    }
                    os.flush ();
                    System.out.println ("File sent successfully!");
                } catch (Exception e) {
                    System.err.println ("Could not transfer file.");
                }
                //outToServer.println ("Downloaded.");
                //outToServer.flush ();

            }
        }
    }
}


