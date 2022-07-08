package com.kenakatavnd.kenakatavndin;

public class ProductData
{
    private String prd_id,prd_name,price,image,avl,offer,qun;
    ProductData(String prd_id,String prd_name,String price,String image,String avl,String offer,String qun)
    {
        this.prd_id=prd_id;
        this.prd_name=prd_name;
        this.price=price;
        this.image=image;
        this.avl=avl;
        this.offer=offer;
        this.qun=qun;
    }

    String getPrd_id()
    {
        return this.prd_id;
    }
    String getPrd_name()
    {
        return this.prd_name;
    }
    String getPrice()
    {
        return this.price;
    }
    String getImage()
    {
        return this.image;
    }
    String getAvl()
    {
        return this.avl;
    }
    String getOffer()
    {
        return this.offer;
    }
    String getQun()
    {
        return this.qun;
    }

}
