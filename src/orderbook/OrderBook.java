package src.orderbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import src.basics.CustomRand;

public class OrderBook
{
    private List<Order> BuyOrders;
    private List<Order> SellOrders;
    private static Comparator<Order> compare= new Comparator<Order>() {
        public int compare(Order o1, Order o2)
        {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    };

    public OrderBook()
    {
        this.BuyOrders = new ArrayList<>();
        this.SellOrders = new ArrayList<>();
    }
    
    public static void insert(List<Order> list, Order elem)
    {
        if (list.isEmpty())
            list.add(elem);
        //int index = Collections.binarySearch(list, elem, (elem.getBuySell() == BuySell.BUY)? compare:compare.reversed());
        int index = Collections.binarySearch(list, elem, compare);
        if (index>=0)
        {
            list.get(index).changeAmount(elem.getAmount(),true);
        }
        else
        {
            list.add(-index-1, elem);
        }
    }

    public void appendOrder(Order new_order)
    {
        insert((new_order.getBuySell() == BuySell.BUY) ? BuyOrders:SellOrders, new_order);
    }

    private Order geneRandomOrder()
    {
        CustomRand rd = new CustomRand();
        if (rd.nextDouble()>0.5)
        {
            return new Order(BuySell.BUY,1+rd.nextInt(10),rd.getUniform(94.55, 94.75));
        }
        else
        {
            return new Order(BuySell.SELL,1+rd.nextInt(10),rd.getUniform(94.85, 95.05));
        }
    }
    public void selfRandomGenerate(int num_orders)
    {
        for (int i = 0; i < num_orders; i++) 
        {
            appendOrder(geneRandomOrder());
        }
    }

    public List<Order> getBuyOrders()
    {
        return this.BuyOrders;
    }

    public List<Order> getSellOrders()
    {
        return this.SellOrders;
    }
    //double test = Math.round(0.5231);
}