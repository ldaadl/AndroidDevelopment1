package com.example.mywork1_lda.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywork1_lda.R;

import java.util.List;
import java.util.Map;

public class AdapterOfMessage extends RecyclerView.Adapter<AdapterOfMessage.MyViewHolder> {
    private View itemView;
    private Context context;
    private List<Map<String, Object>> data;

    // whichItem == 0 表示我自己
    private int whichItem;

    public AdapterOfMessage(List<Map<String, Object>> data, Context context) {
        this.context = context;
        this.data = data;

        // 第一次设置whichItem
        // 空字符串表示我自己
        if(data.get(0).get("联系人") == "")
            whichItem = 0;
        else
            whichItem = 1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(whichItem==0){
            itemView = LayoutInflater.from(context).inflate(R.layout.message_item2,parent,false);
        }else{
            itemView = LayoutInflater.from(context).inflate(R.layout.message_item1,parent,false);

        }


        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    // 在onbind中设置下一个item是谁
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.headphoto.setImageResource(Integer.parseInt(data.get(position).getOrDefault("头像",R.drawable.person1).toString()));
        holder.message.setText(data.get(position).get("消息").toString());

         if(position+1<data.size()){
            if(data.get(position+1).get("联系人").toString() == "")
                whichItem = 0;
            else
                whichItem = 1;
        }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView headphoto;
        TextView message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if (whichItem==0){
                headphoto = itemView.findViewById(R.id.myImage);
                message = itemView.findViewById(R.id.myMessage);
            }else{
                headphoto = itemView.findViewById(R.id.heImage);
                message = itemView.findViewById(R.id.heMessage);
            }

        }
    }
}
