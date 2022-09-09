package src.exchangers;

import java.util.concurrent.Exchanger;
import java.util.ArrayList;
import java.util.List;

import src.basics.*;
import src.orderbook.*;

public class SellSidePlayer implements Runnable{
    private Exchanger<Quote> exchanger;
    private final double PRICE_DIFF = 1e-4;
    //private OrderBook book;

    public SellSidePlayer(Exchanger<Quote> ex)
    {
        exchanger = ex;
    }

    @Override
    public void run() {
        while (true)
        {
            OrderBook book = new OrderBook();
            book.selfRandomGenerate(20);
            
            Quote quotex = new Quote();
            try {
                System.out.println("sell waiting");
                quotex = exchanger.exchange(quotex);


                int amount = quotex.amount;
                if(amount==-1000)
                {
                    System.out.println("Sell receive note: Buy quit...\nGAME OVER...");
                    return;
                }
                //System.out.println("sell got rfq\n");

                System.out.format("sell got :%n  bid = %f%n  ask = %f%n  amount = %d%n", quotex.bid, quotex.ask, amount);

                if (amount<=0)
                    throw new Exception("negative amount");
                boolean requireBid = quotex.bid > 0;
                boolean requierAsk = quotex.ask > 0;
                double bid_price = -1, ask_price = -1;
                if (requireBid)
                    bid_price = impliedPriceFromOrderBook(book,true, amount);
                if (requierAsk)
                    ask_price = impliedPriceFromOrderBook(book,false,amount);
                quotex = new Quote(bid_price, ask_price, amount);
                //System.out.format("A:%f, B:%f%n", ask_price, bid_price);
                System.out.println("sell send quote");
                exchanger.exchange(quotex);

                Thread.sleep(500);
            } catch ( Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static Quote fooQuote()
    {
        CustomRand rd = new CustomRand();
        double bid = rd.getUniform(94.3,94.8);
        double ask = rd.getUniform(94.8,95.3);
        return new Quote(ask,bid,1);
    }

    private double impliedPriceFromOrderBook(OrderBook book, boolean isBid, int amount)
    {   
        double res = 0;
        int count = amount;
        List<Order> l = new ArrayList<>();
        if ( isBid )
        {
            List<Order> sList = book.getBuyOrders();
            for (int i = sList.size() - 1; i >=0 ; i--) {
                l.add(sList.get(i));
            }
            //l = List.copyOf(book.getBuyOrders());
            //Collections.reverse(l);
        }
        else
        {
            l = List.copyOf(book.getSellOrders());
        }

        if (l.size()==0)
        {
            return -1;
        }

        for (Order order : l) {
            int am = order.getAmount();
            if (count > am) {
                res += order.getPrice()*am;
                count -= am;
            } else {
                res += order.getPrice()*count;
                count = 0;
                break;
            }
        }

        if (count > 0)
            return -1;
        res /= amount;
        return (isBid)? res - PRICE_DIFF : res + PRICE_DIFF;
    }
}
