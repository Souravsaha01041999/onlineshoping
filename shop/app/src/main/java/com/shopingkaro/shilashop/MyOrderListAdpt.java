package com.shopingkaro.shilashop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderListAdpt extends RecyclerView.Adapter<MyOrderListAdpt.MyHodler>
{
    interface MyOrderClickEvent
    {
        public void onClick(String orderids,String list,String status,String name,int pos,TextView statusshow);
    }

    private Context context;
    List<MyOrderData>datas;
    private MyOrderClickEvent moce;

    MyOrderListAdpt(Context c,MyOrderClickEvent m)
    {
        this.moce=m;
        this.context=c;
        this.datas= new ArrayList<>();
    }
    void add(MyOrderData mod)
    {
        this.datas.add(mod);
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.myordersview, parent, false);
        return new MyOrderListAdpt.MyHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHodler holder, final int position) {
        holder.setIsRecyclable(false);


                holder.date.setText(datas.get(position).getDate());

                holder.status.setText(datas.get(position).getStatus());
                if (datas.get(position).getStatus().equals("pending"))
                {
                    holder.status.setTextColor(Color.BLACK);
                }
                else if (datas.get(position).getStatus().equals("cancel"))
                {
                    holder.status.setTextColor(Color.RED);
                }
                else if (datas.get(position).getStatus().equals("delivered"))
                {
                    holder.status.setTextColor(Color.GREEN);
                }
                holder.shopname.setText(datas.get(position).getShop_name());
                holder.orderid.setText("Orde ID: "+datas.get(position).getOrder_id());
                holder.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moce.onClick(datas.get(position).getOrder_id(), datas.get(position).getList(), datas.get(position).getStatus(),datas.get(position).getShop_name(),position,holder.status);
                    }
                });
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyHodler extends RecyclerView.ViewHolder
    {
        TextView orderid,shopname,status,date;
        RelativeLayout rl;
        public MyHodler(@NonNull View itemView) {
            super(itemView);
            orderid=itemView.findViewById(R.id.myorderview_orderid);
            shopname=itemView.findViewById(R.id.myorderview_shop_name);
            status=itemView.findViewById(R.id.myorderview_order_status);
            rl=itemView.findViewById(R.id.myorderview_click);
            date=itemView.findViewById(R.id.myorderview_date);
        }
    }
}
