package com.company;
// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Admin
{
    final static int ServerPort = 4321;

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Here");
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        //System.out.println(s);
                        String msg="HAHA";
                        try {
                            msg = dis.readUTF();
                        }
                        catch (EOFException e)
                        {
                            System.out.println("Admin Logged Out");
                            System.exit(0);
                            break;
                        }
                        System.out.println(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }
                //System.out.println("Here2");
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}
