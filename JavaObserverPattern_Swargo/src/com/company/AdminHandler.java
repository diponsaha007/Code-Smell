package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class AdminHandler implements Runnable {
    Scanner scn = new Scanner(System.in);
    private int id;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;

    // constructor
    public AdminHandler(Socket s,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        System.out.println("Constructor Passed");
    }

    @Override
    public void run() {

        String received;
        while (true) {
            try {
                // receive the string
                received = dis.readUTF();

                System.out.println("Admin: " + received);

                if (received.equals("logout")) {

                    this.s.close();
                    System.exit(0);
                    break;
                }


                StringTokenizer st = new StringTokenizer(received, " ");
                if(st.countTokens()!=3){
                    dos.writeUTF("Invalid Command");
                    continue;
                }
                String Command = st.nextToken();
                String StockName = st.nextToken();
                Float Value = Float.valueOf(st.nextToken());
                int cnt;

                System.out.println(Command+StockName);
                for (StockInterface SI : Server.StockList) {

                    if (StockName.equals(SI.getName())) {
                        if(Command.equals("C")){
                            cnt = Math.round(Value);
                            SI.ChangeStockCount(cnt);
                        }
                        else if(Command.equals("I")){
                            SI.IncreaseStockPrice(Value);
                        }
                        else if(Command.equals("D")){
                            SI.DecreaseStockPrice(Value);
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
