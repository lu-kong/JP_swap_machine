package src.valueengine;

import src.basics.CustomRand;

public class ValEng {
    public ValEng(){};
    
    public static double price()
    {
        CustomRand rd = new CustomRand();
        return rd.getUniform(94.6, 95.0);
    }
}
