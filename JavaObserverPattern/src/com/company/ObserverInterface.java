package com.company;

import java.io.IOException;

public interface ObserverInterface {
    void update(String s) throws IOException;
    int getID();
    void logout();
}
