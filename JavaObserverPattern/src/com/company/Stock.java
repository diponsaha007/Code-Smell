package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Stock implements StockInterface{
    List<ObserverInterface> ObserverList = new ArrayList<>();
    private float StockPrice;
    private int StockCount;
    String StockName;

    Stock(String s)
    {
        StringTokenizer st = new StringTokenizer(s, " ");
        StockName = st.nextToken();
        StockCount = Integer.valueOf(st.nextToken());
        StockPrice = Float.valueOf(st.nextToken());
    }

    @Override
    public void SubscribeObserver(ObserverInterface O) throws IOException {
        String messege = "Client "+O.getID()+" Subscribed "+"Stock: "+StockName;
        O.update(messege);
        //System.out.println("Stock: "+StockName+" Client "+O.getID()+" Subscribed");
        ObserverList.add(O);
    }

    @Override
    public void UnsubscribeObserver(ObserverInterface O) throws IOException {
        String messege = "Client "+O.getID()+" Unsubscribed "+"Stock: "+StockName;
        O.update(messege);
        //System.out.println("Stock: "+StockName+" Client "+O.getID()+" Unsubscribed");
        ObserverList.remove(O);
    }

    @Override
    public void NotifyObservers() throws IOException {
        String UpdateMessege = "You subscribed stock "+ StockName +". Its current price: "+Float.toString(StockPrice)+", number of available stocks: "+Integer.toString(StockCount);
        for(ObserverInterface o: ObserverList)
        {
            o.update(UpdateMessege);
        }
        //System.out.println(UpdateMessege);
        //System.exit(0);
    }

    @Override
    public void IncreaseStockPrice(float p) throws IOException {
        StockPrice+=p;
        NotifyObservers();
    }

    @Override
    public void DecreaseStockPrice(float p) throws IOException {
        StockPrice-=p;
        NotifyObservers();
    }

    @Override
    public void ChangeStockCount(int c) throws IOException {
        StockCount = c;
        NotifyObservers();
    }

    @Override
    public String getName() {
        return StockName;
    }

    @Override
    public float getPrice() {
        return StockPrice;
    }

    @Override
    public int getCount() {
        return StockCount;
    }
}
