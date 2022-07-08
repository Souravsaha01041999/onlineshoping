package com.kenakatavnd.kenakatavndin;

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

public class GetOrderPageAdpt extends RecyclerView.Adapter<GetOrderPageAdpt.MyHolder>
{
    interface OrderClickEvent
    {
        public void onClick(OrderData orderData,TextView status);
    }

    private List<OrderData> datas;
    private Context context;
    private OrderClickEvent olv;
    GetOrderPageAdpt(Context c,OrderClickEvent ocv)
    {
        this.context=c;
        this.olv=ocv;
        datas=new ArrayList<>();
    }
    void add(OrderData od)
    {
        this.datas.add(od);
    }
    void clear()
    {
        this.datas.clear();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_page_view, parent, false);
        return new GetOrderPageAdpt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.cast_phone.setText(datas.get(position).getPhone());
        holder.cast_name.setText(datas.get(position).getName());
        holder.status.setText(datas.get(position).getStatus());
        if (datas.get(position).getStatus().equalsIgnoreCase("pending"))
        {
            holder.status.setTextColor(Color.BLACK);
        }
        else if(datas.get(position).getStatus().equalsIgnoreCase("cancel"))
        {
            holder.status.setTextColor(Color.RED);
        }
        else if (datas.get(position).getStatus().equalsIgnoreCase("delivered"))
        {
            holder.status.setTextColor(Color.GREEN);
        }
        holder.orderid.setText(datas.get(position).getOrderId());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olv.onClick(datas.get(position),holder.status);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout click;
        TextView orderid,status,cast_name,cast_phone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            click=itemView.findViewById(R.id.order_click);
            orderid=itemView.findViewById(R.id.order_id);
            status=itemView.findViewById(R.id.order_status);
            cast_name=itemView.findViewById(R.id.order_cast_name);
            cast_phone=itemView.findViewById(R.id.order_cast_phone);
        }
    }
}
