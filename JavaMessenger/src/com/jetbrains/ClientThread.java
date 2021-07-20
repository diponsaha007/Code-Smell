package com.jetbrains;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread implements Runnable {
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private Socket clientSocket;

    public ClientThread(Socket socket, PrintWriter outToServer) throws Exception {
        clientSocket = socket;
        this.outToServer = outToServer;
        inFromServer = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
    }

    public void run() {
        while (true) {
            try {
                String modifiedSentence = inFromServer.readLine ();
                if (modifiedSentence != null) {
                    if(modifiedSentence.compareTo ("#DL#")==0)
                    {
                        try {
                            String string = inFromServer.readLine ();
                            InputStream in = null;
                            OutputStream out = null;

                            try {
                                in = clientSocket.getInputStream();
                            } catch (IOException ex) {
                                System.out.println("Can't get socket input stream. ");
                            }

                            try {
                                out = new FileOutputStream("capture1.jpg");
                            } catch (FileNotFoundException ex) {
                                System.out.println("File not found.");
                            }

                            byte[] bytes = new byte[79688];

                            int count;
                            while ((count = in.read(bytes)) > 0) {
                                out.write(bytes, 0, count);
                            }

                            out.close();
                            in.close();

                        } catch (Exception e) {
                            System.err.println ("Could not transfer file.");
                        }
                    }
                    else {
                        System.out.println (modifiedSentence);
                    }
                }
                else {
                    inFromServer.close ();
                    outToServer.close ();
                    clientSocket.close ();
                    System.exit (0);
                }
            } catch (Exception e) {

            }
        }

    }
}
