package src.exchangers;

import java.util.concurrent.Exchanger;


import java.util.Random;

import src.basics.*;

public class BuySidePlayer implements Runnable{
    private Exchanger<Quote> exchanger;
    private Quote quotex = new Quote();
    public BuySidePlayer(Exchanger<Quote> ex)
    {
        exchanger = ex;
    }

    private Quote geneRandomRFQ()
    {
        Random rd = new Random();
        double dice = rd.nextDouble();
        int amount = rd.nextInt(15) + 15;
        if (dice < 0.2)
            return new Quote(1.0,-1.0,amount);
        else if (dice > 0.8)
            return new Quote(-1.0,1.0,amount);
        else
            return new Quote(1.0,1.0,amount);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        final int NUM_RFQ = 10;
        int rfqCount = 0;
        CustomRand rand = new CustomRand();

        while( rfqCount < NUM_RFQ )
        {
            System.out.println("\n\n******************************************\nbuy online...");
            while( true )
            {   
                try 
                {   
                    Thread.sleep( 1000 );
                    if ( rand.nextDouble() > 0.9 ) // suppose rfq comes as a jump process
                    {
                        quotex = geneRandomRFQ();
                        exchanger.exchange(quotex);
                        System.out.println("\n\n" + "buyside send rfq");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.print(">");
            }

            try 
            {
                double price = rand.getUniform(94.6,95.0); //ValEng.price();      //Naive buyside pricer
                quotex = exchanger.exchange(quotex);

                //TODO BuySide pricing and decide strategy;
                System.out.format("Buy side got :%n  bid = %f%n  ask = %f%nBuyside price:%n  %f%n", quotex.bid, quotex.ask, price);
                if( quotex.bid>0 & price<(quotex.bid)*0.99995)
                {
                    System.out.println("Buyside realise BID!");
                }
                if (quotex.ask>0 & price>(quotex.ask)*1.00005)
                {
                    System.out.println("Buyside realise ASK!");
                }
                System.out.println("Trade END ...\n**********************");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            rfqCount++;

        }
        try {
            exchanger.exchange(new Quote(0,0,-1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
