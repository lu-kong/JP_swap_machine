package src.orderbook;

import src.basics.Utils;

public class Order {
    private BuySell buysell;
    private int amount;
    private double price;

    public Order(BuySell buysell, int amount, double price)
    {
        this.buysell = buysell;
        this.amount = amount;
        this.price = Utils.round(price,4);
    }

    public BuySell getBuySell()
    {
        return this.buysell;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public double getPrice()
    {
        return this.price;
    }

    public void changeAmount(int new_amount, Boolean is_add)
    {
        if (is_add)
        {
            this.amount += new_amount;
        }
        else
        {
            this.amount -= new_amount;
        }
    }

}
