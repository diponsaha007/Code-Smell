package com.jetbrains;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LMessage {
    private  String username, password,type;

    public LMessage(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public void Login(Users thisUser,PrintWriter pr)throws Exception
    {
        boolean ret= false;
        BufferedReader reader = new BufferedReader (new FileReader ("C:\\Users\\APURBA SAHA\\Desktop\\Offline\\src\\com\\jetbrains\\Users.txt"));
        String line;
        ArrayList<String> str = new ArrayList<> ();
        while ((line = reader.readLine ()) != null) {
            for (String val : line.split ("[ \n]")) {
                if(!val.isEmpty ())
                    str.add (val);
            }
        }
        for(int i=0;i<str.size ();i+=3)
        {
            if(username.compareTo (str.get (i))==0 && password.compareTo (str.get (i+1))==0 && type.compareTo (str.get (i+2)) ==0)
            {
                pr.println ("User "+ username+" logged in!");
                thisUser.setLoggedin (true);
                thisUser.setUsername (str.get (i));
                thisUser.setPassword (str.get (i+1));
                thisUser.setType (str.get (i+2));
                ret=true;
                break;
            }
        }
        if(!ret)
        {
            pr.println ("Sorry, Can't recognize you!");
        }

    }

}
