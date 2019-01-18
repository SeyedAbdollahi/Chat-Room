package seyedabdollahi.ir.chatroom.Adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import seyedabdollahi.ir.chatroom.Models.Chat;
import seyedabdollahi.ir.chatroom.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private List<Chat> items;
    private String username;

    public ChatAdapter(List<Chat> items , String username) {
        this.items = items;
        this.username = username;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_chat , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        try{
            if (items.get(position).getUsername().equals(username)){
                holder.username.setVisibility(View.GONE);
                holder.chatLayout.setBackground(holder.resources.getDrawable(R.drawable.my_chat_background));
                holder.textTime.setTextColor(holder.resources.getColor(R.color.light_blue));
            }
        }catch (Exception e){
            //Do Nothing
        }
        if (items.get(position).getDayTime().equals("")){
            holder.dayTime.setVisibility(View.GONE);
        } else {
            holder.dayTime.setText(items.get(position).getDayTime());
        }
        holder.username.setText(items.get(position).getUsername());
        holder.text.setText(items.get(position).getText());
        holder.textTime.setText(items.get(position).getTextTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private Resources resources;
        private TextView dayTime;
        private TextView username;
        private TextView text;
        private TextView textTime;
        private ConstraintLayout chatLayout;

        private ViewHolder(View itemView){
            super(itemView);
            resources = itemView.getContext().getResources();
            dayTime = itemView.findViewById(R.id.template_chat_day_time);
            username = itemView.findViewById(R.id.template_chat_username);
            text = itemView.findViewById(R.id.template_chat_text);
            textTime = itemView.findViewById(R.id.template_chat_text_time);
            chatLayout = itemView.findViewById(R.id.template_chat_layout);
        }
    }
}
