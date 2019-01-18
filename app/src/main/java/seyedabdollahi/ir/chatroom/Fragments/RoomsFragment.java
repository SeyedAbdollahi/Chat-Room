package seyedabdollahi.ir.chatroom.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.androidadvance.topsnackbar.TSnackbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seyedabdollahi.ir.chatroom.Adapters.RoomAdapter;
import seyedabdollahi.ir.chatroom.Data.ChatRoomAPI;
import seyedabdollahi.ir.chatroom.Data.CreateChatRoomController;
import seyedabdollahi.ir.chatroom.Data.RoomsController;
import seyedabdollahi.ir.chatroom.Dialogs.CreateRoomDialog;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Keys.Keys;
import seyedabdollahi.ir.chatroom.Listeners.OnClickCreateListener;
import seyedabdollahi.ir.chatroom.Models.ChatRoom;
import seyedabdollahi.ir.chatroom.Models.Room;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;

public class RoomsFragment extends android.support.v4.app.Fragment {

    private CoordinatorLayout roomsLayout;
    private RecyclerView recyclerViewRooms;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private ProgressBar progressBar;
    private ImageView create;
    private CreateRoomDialog createRoomDialog;
    private String authorization;
    private TextView txtErrorDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rooms_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configViews();
        authorization = MyPreferenceManager.getInstance(getActivity()).getAccessToken();
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue) , PorterDuff.Mode.SRC_IN);
        initRoomList();
        getRooms();
    }

    private void findViews(View view){
        recyclerViewRooms = view.findViewById(R.id.recycle_rooms);
        progressBar = view.findViewById(R.id.progress_bar);
        create = view.findViewById(R.id.img_create_room);
        roomsLayout = view.findViewById(R.id.rooms_layout);
    }

    private void configViews(){
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoomDialog = new CreateRoomDialog(getActivity(), new OnClickCreateListener() {
                    @Override
                    public void onClickCreate(String name) {
                        String accessToken = MyPreferenceManager.getInstance(getActivity()).getAccessToken();
                        CreateChatRoomController createChatRoomController = new CreateChatRoomController(createRoomCallback);
                        ChatRoom chatRoom = new ChatRoom();
                        chatRoom.setName(name);
                        createChatRoomController.start(accessToken , chatRoom);
                    }
                });
                createRoomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // for old Api
                createRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                createRoomDialog.show();
                txtErrorDialog = createRoomDialog.findViewById(R.id.create_room_dialog_error_txt);
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getRooms();
        }
    };

    private void getRooms(){
        progressBar.setVisibility(View.VISIBLE);
        RoomsController roomsController = new RoomsController(getRoomsCallback);
        roomsController.start(authorization);
    }

    private void initRoomList(){
        roomAdapter = new RoomAdapter(roomList);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewRooms.setAdapter(roomAdapter);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver , new IntentFilter(Actions.REFRESH_ROOMS));
        super.onResume();
    }

    private void showSnackBar(boolean successful , String text){
        TSnackbar snackbar = TSnackbar.make(roomsLayout, text , TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = view.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.WHITE);
        if(successful){
            view.setBackgroundColor(getResources().getColor(R.color.green));
        }else {
            snackbar.setDuration(TSnackbar.LENGTH_INDEFINITE);
            view.setBackgroundColor(getResources().getColor(R.color.red));
            snackbar.setAction(getString(R.string.try_again), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRooms();
                }
            });
        }
        snackbar.show();
    }

    ChatRoomAPI.getRoomsCallback getRoomsCallback = new ChatRoomAPI.getRoomsCallback() {
        @Override
        public void onResponse(boolean successful , List<Room> inputList) {
            if (successful){
                new SortRooms(inputList).execute();
            }else {
                progressBar.setVisibility(View.INVISIBLE);
                showSnackBar(false ,"UnknownError");
            }
        }

        @Override
        public void onFailure() {
            progressBar.setVisibility(View.INVISIBLE);
            showSnackBar(false , getResources().getString(R.string.error_on_failure));
        }
    };

    ChatRoomAPI.createRoomCallback createRoomCallback = new ChatRoomAPI.createRoomCallback() {
        @Override
        public void onResponse(boolean successful) {
            if (successful){
                progressBar.setVisibility(View.VISIBLE);
                createRoomDialog.dismiss();
                showSnackBar(true , getString(R.string.room_created));
                getRooms();
            }
            else {
                txtErrorDialog.setText(getString(R.string.error_unknown));
            }
        }

        @Override
        public void onFailure() {
            txtErrorDialog.setText(getString(R.string.error_on_failure));
        }
    };

    private class SortRooms extends AsyncTask<Void , Void , Void >{
        List<Room> rooms;

        private SortRooms(List<Room> rooms) {
            this.rooms = rooms;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            roomList.clear();
            roomList.addAll(rooms);
            roomAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Collections.sort(rooms, new Comparator<Room>() {
                @Override
                public int compare(Room x, Room y) {
                    return x.getName().compareTo(y.getName());
                }
            });
            return null;
        }
    }
}
