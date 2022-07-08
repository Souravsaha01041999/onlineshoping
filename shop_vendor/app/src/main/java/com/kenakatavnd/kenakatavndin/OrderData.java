package com.kenakatavnd.kenakatavndin;

public class OrderData
{
    private String ordidtab,orderlist,address,phone,pincode,date,status,name;
    OrderData(String ordidtab,String orderlist,String address,String phone,String pincode,String date,String status,String name)
    {
        this.ordidtab=ordidtab;
        this.orderlist=orderlist;
        this.address=address;
        this.phone=phone;
        this.pincode=pincode;
        this.date=date;
        this.status=status;
        this.name=name;
    }
    String getOrderId()
    {
        return this.ordidtab;
    }
    String getOrderlist()
    {
        return this.orderlist;
    }
    String getName()
    {
        return this.name;
    }
    String getAddress()
    {
        return this.address;
    }
    String getPhone()
    {
        return this.phone;
    }
    String getPincode()
    {
        return this.pincode;
    }
    String getDate()
    {
        return this.date;
    }
    String getStatus()
    {
        return this.status;
    }
    void setStatus(String s)
    {
        this.status=s;
    }
}
