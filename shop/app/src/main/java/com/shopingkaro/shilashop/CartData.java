package com.shopingkaro.shilashop;

public class CartData
{
    private String prdid,prdname,prdprice,prdqun;
    CartData(String prdid,String prdname,String prdprice,String prdqun)
    {
        this.prdid=prdid;
        this.prdname=prdname;
        this.prdprice=prdprice;
        this.prdqun=prdqun;
    }
    String getPrdid()
    {
        return this.prdid;
    }
    String getPrdname()
    {
        return this.prdname;
    }
    String getPrdprice()
    {
        return this.prdprice;
    }
    String getPrdqun()
    {
        return this.prdqun;
    }
    void setPrqun(String data)
    {
        this.prdqun=data;
    }
}
