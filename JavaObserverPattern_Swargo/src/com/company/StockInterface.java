package com.company;

import java.io.IOException;

public interface StockInterface {
    void SubscribeObserver(ObserverInterface O) throws IOException;
    void UnsubscribeObserver(ObserverInterface O) throws IOException;
    void NotifyObservers() throws IOException;
    void IncreaseStockPrice(float p) throws IOException;
    void DecreaseStockPrice(float p) throws IOException;
    void ChangeStockCount(int c) throws IOException;
    String getName();
    int getCount();
    float getPrice();
}
