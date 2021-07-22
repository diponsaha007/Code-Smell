package com.company;

import java.io.DataOutputStream;
import java.io.IOException;

public class Observer implements ObserverInterface{
    private int ID;
    private boolean logged_in;
    private DataOutputStream dos;
    Observer(int id, DataOutputStream DOS)
    {
        dos = DOS;
        ID=id;
        logged_in=true;
    }
    @Override
    public void update(String s) throws IOException {
        if(logged_in){
            dos.writeUTF(s);
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void logout() {
        logged_in=false;
    }
}
