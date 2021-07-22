package com.company;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{

    //Maintaining List
    static List<ObserverInterface> ObserverList = new ArrayList<>();
    static List<StockInterface> StockList = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        int i = 0;
        //Input Handling
        String path = "src/input.txt";
        File f = new File(path);
        Scanner sc = new Scanner(f);

        String AdminInitial="WELCOME TO STOCK EXCHANGE\n=============================================================\n" +
                "Stock Name    Stock Count     Stock Price\n";

        while(sc.hasNext())
        {
            String nextln = sc.nextLine();
            StockInterface SI = new Stock(nextln);
            StockList.add(SI);
            AdminInitial+=nextln+"\n";
        }
        AdminInitial += "=============================================================\nYou can increase and decrease the stock price " +
                "and change stock count using I, D and C command respectively. Type logout to stop the server.\n"+
                "=============================================================\n";


        //ADMIN SECTION
        ServerSocket ssadmin = new ServerSocket(4321);
        Socket sadmin;
        sadmin = ssadmin.accept();
        System.out.println("Admin Logged In");
        DataInputStream disAdmin = new DataInputStream(sadmin.getInputStream());
        DataOutputStream dosAdmin = new DataOutputStream(sadmin.getOutputStream());
        dosAdmin.writeUTF(AdminInitial);
        AdminHandler adminHandler = new AdminHandler(sadmin, disAdmin, dosAdmin);
        Thread tAdmin = new Thread(adminHandler);
        tAdmin.start();

        ServerSocket ss = new ServerSocket(1234);
        Socket s;

        while (true)
        {
            s = ss.accept();

            System.out.println("Client"+Integer.toString(i)+" Logged In : " + s);

            String ClientInitial="WELCOME TO STOCK EXCHANGE\n=============================================================\n" +
                    "Stock Name    Stock Count     Stock Price\n";
            for(StockInterface si: StockList)
            {
                ClientInitial+=si.getName()+"\t"+Integer.toString(si.getCount())+"\t"+Float.toString(si.getPrice())+"\n";
            }
            ClientInitial+="=============================================================\nYou can subscribe and unsubscribe using S and U respectively. Type logout to exit.\n" +
                    "=============================================================\n";

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF(ClientInitial);
            ObserverInterface o = new Observer(i, dos);
            ObserverList.add(o);

            ClientHandler  clientHandler = new ClientHandler(s, i, dis, dos);

            Thread t = new Thread(clientHandler);

            t.start();

            i++;

        }
    }
}

