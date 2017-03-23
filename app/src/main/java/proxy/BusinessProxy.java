package proxy;

/**
 * Created by 39157 on 2017/3/22.
 */

public class BusinessProxy implements Sell {

    Business business;

    public BusinessProxy(Business business) {
        this.business = business;
    }

    @Override
    public void sell(String sellWhat) {
        business.sell(sellWhat);
    }

    @Override
    public void noSell(String noSellReason) {
        business.noSell(noSellReason);
    }
}
