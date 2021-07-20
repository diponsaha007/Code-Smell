package com.jetbrains;

import java.io.PrintWriter;

public class BMessage {
    private String message;

    public BMessage(String message) {
        this.message = message;
    }
    public void sendMessage(PrintWriter pr, Users thisUser)
    {
        if(thisUser.getType ().compareTo ("admin")==0) {
            for (int i = 0; i < WorkerThread.getWorkerThreads ().size (); i++) {
                if (WorkerThread.getWorkerThreads ().get (i).getThisUser ().getUsername ().compareTo (thisUser.getUsername ()) != 0) {
                    if(WorkerThread.getWorkerThreads ().get (i).getThisUser ().getLoggedin ())
                        WorkerThread.getWorkerThreads ().get (i).getPr ().println ("Admin " + thisUser.getUsername () + " says : " + message);
                }
            }
            pr.println ("Message sent Successfully");
        }
        else {
            pr.println ("Message could not be sent");
        }

    }

}
