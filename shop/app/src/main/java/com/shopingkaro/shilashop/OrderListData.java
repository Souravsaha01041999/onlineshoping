package com.shopingkaro.shilashop;

public class OrderListData {
    // PRODUCT ID, PRODUCT NAME, PRICE, QUEN
    private String prdid,prdname,prdrice,prdqun,pimg;
    OrderListData(String prdid,String prdname,String prdrice,String prdqun,String pimg)
    {
        this.prdid=prdid;
        this.prdname=prdname;
        this.prdqun=prdqun;
        this.prdrice=prdrice;
        this.pimg=pimg;
    }
    String getPrdid()
    {
        return this.prdid;
    }
    String getPrdname()
    {
        return this.prdname;
    }
    String getPrdrice()
    {
        return this.prdrice;
    }
    String getPrdqun()
    {
        return this.prdqun;
    }
    void setPrqun(String q)
    {
        this.prdqun=q;
    }
    String getImage()
    {
        return this.pimg;
    }
}
