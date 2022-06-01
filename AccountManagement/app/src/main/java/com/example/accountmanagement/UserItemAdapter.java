package com.example.accountmanagement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.MyViewHolder> {
    private Context context;
    private List<User> saveddUserList,searchedUserList;
    private Activity parentActivity;

    public UserItemAdapter(Context context, List<User> saveddUserList, List<User> searchedUserList, Activity parentActivity) {
        this.context = context;
        this.saveddUserList = saveddUserList;
        this.searchedUserList = searchedUserList;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User item = searchedUserList.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return searchedUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewPassword, textViewName, textViewAge;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewId);
            textViewPassword = itemView.findViewById(R.id.textViewPassword);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            Button deleteButton = itemView.findViewById(R.id.buttonDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View button) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean isSuccess = jsonResponse.getBoolean("success");
                                if (isSuccess) {
                                    for(int i = 0; i<saveddUserList.size(); i++){
                                        if(saveddUserList.get(i).getUserID().equals(searchedUserList.get(getAdapterPosition()).getUserID())){
                                            saveddUserList.remove(i);
                                            break;
                                        }
                                    }
                                    searchedUserList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }else {
                                    Log.e("UserItemAdapter", "Delete Failed.");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DeleteRequest deleteRequest = new DeleteRequest(searchedUserList.get(getAdapterPosition()).getUserID(),responseListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(parentActivity);
                    requestQueue.add(deleteRequest);
                }
            });

        }

        public void setItem(User user) {
            textViewID.setText(user.getUserID());
            textViewPassword.setText(user.getUserPassword());
            textViewName.setText(user.getUserName());
            textViewAge.setText(user.getUserAge());
        }
    }
}
