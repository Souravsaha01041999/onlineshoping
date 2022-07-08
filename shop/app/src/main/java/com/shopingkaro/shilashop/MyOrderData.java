package com.shopingkaro.shilashop;

public class MyOrderData {
    private String order_id,shop_name,status,list,date;
    MyOrderData(String order_id,String shop_name,String status,String list,String date)
    {
        this.list=list;
        this.order_id=order_id;
        this.shop_name=shop_name;
        this.status=status;
        this.date=date;
    }
    String getOrder_id()
    {
        return this.order_id;
    }
    String getDate()
    {
        return this.date;
    }
    String getShop_name()
    {
        return this.shop_name;
    }
    String getStatus()
    {
        return this.status;
    }
    String getList()
    {
        return this.list;
    }
    void updateStatus(String s)
    {
        this.status=s;
    }
}
