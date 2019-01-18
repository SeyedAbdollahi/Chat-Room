package seyedabdollahi.ir.chatroom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Models.Room;
import seyedabdollahi.ir.chatroom.R;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> items;

    public RoomAdapter(List<Room> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_room , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(items.get(position).getName());

        holder.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent();
                intent.setAction(Actions.ROOM_ON_CLICK);
                intent.putExtra("chat_name" , items.get(position).getName());
                intent.putExtra("chat_id" , items.get(position).getId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private LinearLayout roomLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_name);
            roomLayout = itemView.findViewById(R.id.template_room_layout);
        }
    }
}
