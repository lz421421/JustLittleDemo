package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 39157 on 2017/3/22.
 */

public class Test {

    public static void main(String args[]) {
        //静态代理
        Business business = new Business();
        BusinessProxy businessProxy = new BusinessProxy(business);
        businessProxy.sell("你猜我想卖什么？");
        businessProxy.noSell("我什么也不想卖了！！");


        //动态代理
        Business business1 = new Business();
        Sell sell = (Sell) Proxy.newProxyInstance(business1.getClass().getClassLoader(), new Class[]{Sell.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("-----before-----");
                Object object = method.invoke(proxy, args);
                System.out.println("-----end-----");
                return object;
            }
        });


        int a = 99999, b = 10000;
        swap(a, b);
        System.out.println("-------->" + a + "---->" + b);
    }


    public static void swap(int a, int b) {
        int tem;
        tem = a;
        a = b;
        b = tem;
        System.out.println("----swap---->" + a + "---->" + b);
    }
}
