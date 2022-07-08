package com.shopingkaro.shilashop;

public class FinalOrderList
{
    private String prdid,prdname,prdprice,prdqun,image;
    FinalOrderList(String prdid,String prdname,String prdprice,String prdqun,String img)
    {
        this.prdid=prdid;
        this.prdname=prdname;
        this.prdprice=prdprice;
        this.prdqun=prdqun;
        this.image=img;
    }
    String getPrdid()
    {
        return this.prdid;
    }
    String getImage()
    {
        return this.image;
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
}
