package com.shopingkaro.shilashop;

public class ProductDet {
    private String prid,prname,prprice,primage,pravl,qun;   //THIS QUN FOR AVAILABEL IN SHOP
    ProductDet(String prid,String prname,String prprice,String primage,String pravl,String qun)
    {
        this.prid=prid;
        this.prname=prname;
        this.pravl=pravl;
        this.primage=primage;
        this.prprice=prprice;
        this.qun=qun;
    }
    String getPrid()
    {
        return this.prid;
    }
    String getPrname()
    {
        return this.prname;
    }
    String getPrprice()
    {
        return this.prprice;
    }
    String getPrimage()
    {
        return this.primage;
    }
    String getPravl()
    {
        return this.pravl;
    }
    String getQun()
    {
        return this.qun;
    }
}
