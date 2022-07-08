package com.shopingkaro.shilashop;

public class ShopData
{
    private String id,name,image;
    ShopData(String id,String name,String image)
    {
        this.id=id;
        this.name=name;
        this.image=image;
    }

    String getId()
    {
        return this.id;
    }
    String getName()
    {
        return this.name;
    }
    String getImage()
    {
        return this.image;
    }
}
