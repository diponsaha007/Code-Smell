package com.jetbrains;

import java.io.PrintWriter;

public class SMessage {
    String command , text ;

    public SMessage(String command, String text) {
        this.command = command;
        this.text = text;
    }

    public void action(PrintWriter pr,Users thisUser)
    {
        System.out.println ("User "+thisUser.getUsername ()+" says: "+text);
        if(command.compareTo ("show")==0)
        {
            String s ="Users Logged in are: ";
            for(int i=0;i<WorkerThread.getWorkerThreads ().size ();i++)
            {
                if(WorkerThread.getWorkerThreads ().get (i).getThisUser ().getLoggedin ())
                {
                    s= s+ WorkerThread.getWorkerThreads ().get (i).getThisUser ().getUsername ()+ " ";
                }
            }
            pr.println (s);

        }
        else if(command.compareTo ("logout")==0)
        {
            thisUser.setLoggedin (false);
        }

    }
}
