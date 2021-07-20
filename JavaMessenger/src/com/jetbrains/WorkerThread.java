package com.jetbrains;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerThread implements Runnable {
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pr;
    private int id ;
    private static ArrayList<WorkerThread>workerThreads= new ArrayList<> ();
    private Users thisUser;


    public WorkerThread(Socket s, int id)throws Exception {
        this.socket = s;
        try {
            this.is = this.socket.getInputStream ();
            this.os = this.socket.getOutputStream ();
            pr = new PrintWriter (this.os, true);
        } catch (Exception e) {
        }
        this.id = id;
        thisUser= new Users ();
    }

    public void run(){
        BufferedReader br = new BufferedReader (new InputStreamReader (this.is));
        String string;
        while (true) {
            try {
                if ((string = br.readLine ()) != null) {
                    //Extract Strings
                    ArrayList<String> str = new ArrayList<> ();
                    for (String val : string.split ("[#\n]")) {
                        if(!val.isEmpty ()) {
                            str.add (val);
                            //System.out.println (val);
                        }
                    }

                    //LMESSAGE
                    if (str.get (0).compareTo ("L") == 0 ) {
                        if(!thisUser.getLoggedin ()) {
                            LMessage lMessage = new LMessage (str.get (1), str.get (2), str.get (3));
                            lMessage.Login (thisUser,pr);
                        }
                        else {
                            pr.println ("You are already logged in.");
                        }
                    }

                    //SMESSAGE
                    if (str.get (0).compareTo ("S") == 0 ) {
                        if( thisUser.getLoggedin ()) {
                            SMessage sMessage = new SMessage ( str.get (1), str.get (2));
                            sMessage.action (pr,thisUser);
                            if(thisUser.getLoggedin ()==false)
                            {
                                break;
                            }
                        }
                        else {
                            pr.println ("Please log in first!");
                        }

                    }

                    //BMESSAGE
                    if (str.get (0).compareTo ("B") == 0 ) {
                        if( thisUser.getLoggedin ()) {
                            BMessage bMessage= new BMessage (str.get (1));
                            bMessage.sendMessage (pr,thisUser);
                        }
                        else {
                            pr.println ("Please log in first!");
                        }
                    }

                    //CMESSAGE
                    if (str.get (0).compareTo ("C") == 0 ) {
                        if( thisUser.getLoggedin ()) {
                            CMessage cMessage= new CMessage (str.get (1),str.get (2),socket,br);
                            cMessage.sendMessage (thisUser,pr);
                            if(str.size ()>3)
                            {
                                cMessage.sendFile(thisUser,str.get (3));
                            }
                        }
                        else {
                            pr.println ("Please log in first!");
                        }

                    }

                }

            }catch (Exception e)
            {
                break;
            }
        }

        try {
            this.is.close ();
            this.os.close ();
            this.socket.close ();
        } catch (Exception e) {

        }

        Main.workerThreadCount--;
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getIs() {
        return is;
    }


    public OutputStream getOs() {
        return os;
    }

    public PrintWriter getPr() {
        return pr;
    }

    public void setThread(WorkerThread wt)
    {
        workerThreads.add (wt);
    }

    public Users getThisUser() {
        return thisUser;
    }

    public static ArrayList<WorkerThread> getWorkerThreads() {
        return workerThreads;
    }
}
