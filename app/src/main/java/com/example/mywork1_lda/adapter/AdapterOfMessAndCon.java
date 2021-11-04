package com.example.mywork1_lda.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywork1_lda.R;

import java.util.List;
import java.util.Map;


// 这个泛型传入需要回去仔细研究一下
public class AdapterOfMessAndCon extends RecyclerView.Adapter<AdapterOfMessAndCon.MyViewHolder> {

    private View itemView;
    private Context context;
    private List<Map<String, Object>> data;
    // 我觉得从外部传入控件更好，这样更灵活，当然，为了更大的兼容性和灵活性，方法也要更复杂
    private int item;
    private int count;
    private String[] what;

    public AdapterOfMessAndCon(List<Map<String, Object>> data, Context context, int item, String[] what) {
        Log.d("adapter","构造器");
        this.context = context;
        this.data = data;
        this.item = item;
        this.what = what;

    }

    // MyViewHolder是一个视图指针类型，这个方法就是创建了一个视图指针，找到控件的位置并返回
    // 这时的空间已经与数据绑定
    // 返回的对象必然还要被设置调用
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 将item渲染到recycleView的实际过程
        Log.d("adapter","希望是多次调用"+viewType);
        itemView = LayoutInflater.from(context).inflate(this.item, parent, false);
        // 每次渲染一个item就调用一次

        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }



    // 将数据与视图绑定
    // 一般来说list的每一个元素就是形成一组控件的的数据，这里的position是遍历list的一个元素
    // 遍历
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("adapter","绑定");
        holder.message.setText(data.get(position).getOrDefault(what[1],"未知用户").toString());  //
        holder.who.setText(data.get(position).getOrDefault(what[0],"未知消息").toString());  // data.get(position).get(what[1]).toString()
        holder.headphoto.setImageResource(Integer.parseInt(data.get(position).getOrDefault(what[2], R.drawable.person1).toString()));  // Integer.parseInt(data.get(position).get(what[2]).toString())
    }


    // 数据的个数，遍历list会用到
    @Override
    public int getItemCount() {
        Log.d("adapter","计数");
        return data.size();
    }


    // 视图指针类, 负责找到要被使用的控件
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout messageItem;
        ImageView headphoto;
        TextView who;
        TextView message;
        LinearLayout ll_hidden;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("adapter","构造子类");
            headphoto = itemView.findViewById(R.id.headPhoto);
            who = itemView.findViewById(R.id.whoMessage);
            message = itemView.findViewById(R.id.message);
            messageItem = itemView.findViewById(R.id.messageItem);
            ll_hidden = itemView.findViewById(R.id.ll_hidden);
        }
    }
}
