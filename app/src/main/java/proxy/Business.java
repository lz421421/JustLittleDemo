package proxy;

/**
 * Created by 39157 on 2017/3/22.
 */

public class Business implements Sell {
    @Override
    public void sell(String sellWhat) {
        System.out.println("---------Business------>" + sellWhat);
    }

    @Override
    public void noSell(String noSellReason) {
        System.out.println("---------Business------>" + noSellReason);
    }
}
