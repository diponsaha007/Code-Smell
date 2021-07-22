package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class ClientHandler implements Runnable {
    Scanner scn = new Scanner(System.in);
    private int id;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;

    // constructor
    public ClientHandler(Socket s, int id,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.id = id;
        this.s = s;
    }

    @Override
    public void run() {

        String received;
        while (true) {
            try {
                // receive the string
                received = dis.readUTF();

                System.out.println("Client "+Integer.toString(id)+": "+received);

                if (received.equals("logout")) {
                    Server.ObserverList.get(id).logout();
                    this.s.close();
                    break;
                }


                StringTokenizer st = new StringTokenizer(received, " ");
                if(st.countTokens()!=2)
                {
                    dos.writeUTF("Invalid Command");
                    continue;
                }
                String Subscription = st.nextToken();
                String StockName = st.nextToken();


                for (StockInterface SI : Server.StockList) {
                    //System.out.println(StockName+SI.getName());
                    if (StockName.equals(SI.getName())) {
                        if (Subscription.equals("S")) {
                            SI.SubscribeObserver(Server.ObserverList.get(id));
                        } else if (Subscription.equals("U")) {
                            SI.UnsubscribeObserver(Server.ObserverList.get(id));
                        }
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
