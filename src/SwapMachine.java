package src;

import java.util.concurrent.Exchanger;
import src.basics.*;
import src.exchangers.*;
//class Quote;

public class SwapMachine 
{
    public static void main( String[] args )
    {
        if ( args.length > 0) {
            System.out.println( "Log path at : \n" + args[0]);
        }

        Exchanger<Quote> quoteEx = new Exchanger<>();;
        BuySidePlayer bp = new BuySidePlayer( quoteEx );
        SellSidePlayer sp = new SellSidePlayer( quoteEx );

        // Let two players join the game
        (new Thread(bp)).start();
        (new Thread(sp)).start();
    }
}
