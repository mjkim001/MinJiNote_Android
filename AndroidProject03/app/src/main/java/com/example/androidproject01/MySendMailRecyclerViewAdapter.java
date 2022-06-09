package com.example.androidproject01;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySendMailRecyclerViewAdapter extends RecyclerView.Adapter<MySendMailRecyclerViewAdapter.MyViewHolder>{
    static ArrayList<Mail> arrayList;
    View itemView;
    static NavMailActivity activity;

    public MySendMailRecyclerViewAdapter(ArrayList<Mail> arrayList, NavMailActivity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }
    @NonNull
    @Override
    // 해당 객체 생성시 자동으로 호출됨
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // message_list를 뷰 객체로 생성함
        itemView = inflater.inflate(R.layout.message_list, parent, false);
        // list_item을 뷰로 만든것을 객체값으로 넣어 return해줌
        return new MyViewHolder(itemView);
    }
    @Override
    // 해당 adapter에 값변경시 자동 호출
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mail item = arrayList.get(position);
        // Contact 객체의 형태로 값을 받아와서 setItem을 하여 넣어줌
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView send_id, send_title, send_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            send_id = itemView.findViewById(R.id.send_id);
            send_title = itemView.findViewById(R.id.send_title);
            send_time = itemView.findViewById(R.id.send_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail_sender = arrayList.get(getAdapterPosition()).getSender();
                    String mail_recipient = arrayList.get(getAdapterPosition()).getRecipient();
                    String mail_title = arrayList.get(getAdapterPosition()).getTitle();
                    String mail_content = arrayList.get(getAdapterPosition()).getContent();
                    String mail_time = arrayList.get(getAdapterPosition()).getTime();

                    Intent intent = new Intent(activity, NavMailActivity.class);
                    intent.putExtra("mail_sender",mail_sender);
                    intent.putExtra("mail_recipient",mail_recipient);
                    intent.putExtra("mail_title",mail_title);
                    intent.putExtra("mail_content",mail_content);
                    intent.putExtra("mail_time",mail_time);
                    intent.putExtra("fragment_name","SendMailDetailFragment");
                    activity.startActivity(intent);
                }
            });

        }
        public void setItem(Mail item) {
            send_id.setText(item.getSender());
            send_title.setText(item.getTitle());
            send_time.setText(item.getTime());
        }

    }
}