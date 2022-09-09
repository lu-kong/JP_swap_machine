package src.basics;

public class Quote {
    public final double bid;
    public final double ask;
    public final int amount;

    public Quote(double bid, double ask, int amount)
    {
        this.ask = Utils.round(ask,4);
        this.bid = Utils.round(bid,4);
        this.amount = amount;
    }

    public Quote()
    {
        this.ask = 0;
        this.bid = 0;
        this.amount = -1;
    }
}
