package com.kenakatavnd.kenakatavndin;

public class ProductList
{
    private String prid,prdname,price,qun,image;
    ProductList(String prid,String prdname,String price,String qun,String image)
    {
        this.prid=prid;
        this.prdname=prdname;
        this.price=price;
        this.qun=qun;
        this.image=image;
    }

    String getPrid()
    {
        return this.prid;
    }

    String getPrdname()
    {
        return this.prdname;
    }

    String getPrice()
    {
        return this.price;
    }

    String getQun()
    {
        return this.qun;
    }

    String getImage()
    {
        return this.image;
    }

}
