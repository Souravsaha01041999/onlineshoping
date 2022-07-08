package com.shopingkaro.shilashop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FinalOrderAdpt extends RecyclerView.Adapter<FinalOrderAdpt.MyHolder>
{
    interface ClickEvent
    {
        public void onClick(String prdid,String prdname,String prdprice,String prdqun,String img);
    }
    private List<FinalOrderList>lst;
    private Context  context;
    ClickEvent ce;
    boolean visibale;
    FinalOrderAdpt(Context c,boolean v)
    {
        this.visibale=v;
        this.context=c;
        lst=new ArrayList<>();
    }
    void setOnRemoveListener(ClickEvent ce2)
    {
        this.ce=ce2;
    }
    void add(FinalOrderList f)
    {
        this.lst.add(f);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.finalorderlists, parent, false);
        return new FinalOrderAdpt.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.price.setText("Rs: "+lst.get(position).getPrdprice());
        holder.qun.setText(lst.get(position).getPrdqun());
        holder.name.setText(lst.get(position).getPrdname());
        if (visibale) {
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lst.size()>1) {
                        String id = lst.get(position).getPrdid();
                        String name = lst.get(position).getPrdname();
                        String price = lst.get(position).getPrdprice();
                        String qn = lst.get(position).getPrdqun();
                        String img = lst.get(position).getImage();
                        lst.remove(position);
                        ce.onClick(id, name, price, qn, img);
                    }
                    else {
                        Toast.makeText(context,"Can't remove one item\nyou can cancel this order",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    float getTotal()
    {
        float temp=0;
        int i;
        for (i=0;i<lst.size();i++)
        {
            temp=temp+(Float.parseFloat(lst.get(i).getPrdqun())*Float.parseFloat(lst.get(i).getPrdprice()));
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        return lst.size();
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
        TextView name,qun,price;
        Button remove;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.finalorder_prdname);
            qun=itemView.findViewById(R.id.finalorder_prdqun);
            price=itemView.findViewById(R.id.finalorder_price);
            if (!visibale) {
                itemView.findViewById(R.id.finalorder_itemremove).setVisibility(View.GONE);
            }else {
                remove=itemView.findViewById(R.id.finalorder_itemremove);
            }
        }
    }
}
