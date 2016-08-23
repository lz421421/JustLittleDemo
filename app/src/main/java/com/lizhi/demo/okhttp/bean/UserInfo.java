package com.lizhi.demo.okhttp.bean;

/**
 * Created by Administrator on 2016/3/25.
 */
public class UserInfo {


    public int Uid;//:247485
    public int ShopID;//:20963,
    public String LoginName;//:lxj123,
    public String Mobile;//:null,
    public String SSUserName;//:山西大同市服务站,
    public String ServiceStationMobile;//:0352-7923888,
    public int Ssid;//:75,
    public boolean IsSend;//:true,
    public int IsHaveBank;//:0,
    public String GhsToken;//:0C26790C0716084B7874F7ADA4ED51E03F558BC107002D23CF34837B1E73CE48


    @Override
    public String toString() {
        return "UserInfo{" +
                "Uid=" + Uid +
                ", ShopID=" + ShopID +
                ", LoginName='" + LoginName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", SSUserName='" + SSUserName + '\'' +
                ", ServiceStationMobile='" + ServiceStationMobile + '\'' +
                ", Ssid=" + Ssid +
                ", IsSend=" + IsSend +
                ", IsHaveBank=" + IsHaveBank +
                ", GhsToken='" + GhsToken + '\'' +
                '}';
    }
}
