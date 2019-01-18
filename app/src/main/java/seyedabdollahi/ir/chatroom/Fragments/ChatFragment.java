package seyedabdollahi.ir.chatroom.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

import java.util.ArrayList;
import java.util.List;

import seyedabdollahi.ir.chatroom.Adapters.ChatAdapter;
import seyedabdollahi.ir.chatroom.BitmapClass;
import seyedabdollahi.ir.chatroom.Data.ChatController;
import seyedabdollahi.ir.chatroom.Data.ChatRoomAPI;
import seyedabdollahi.ir.chatroom.Data.SendMessageController;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Keys.Keys;
import seyedabdollahi.ir.chatroom.Models.Chat;
import seyedabdollahi.ir.chatroom.Models.ChatId;
import seyedabdollahi.ir.chatroom.Models.Message;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;
import seyedabdollahi.ir.chatroom.Responses.MessageResponse;

public class ChatFragment extends android.support.v4.app.Fragment {

    private CoordinatorLayout chatLayout;
    private RecyclerView recyclerView;
    private LinearLayout sendLayout;
    private LinearLayout messagesLayout;
    private ChatAdapter chatAdapter;
    private EditText edtText;
    private ImageView sendBtn;
    private String id;
    private String authorization;
    private String username;
    private ProgressBar progressBar;
    private ChatId chatId;
    private ChatController chatController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue) , PorterDuff.Mode.SRC_IN);
        configViews();
        configBackground();
        id = getArguments().getString("chat_id");
        authorization = MyPreferenceManager.getInstance(getActivity()).getAccessToken();
        username = MyPreferenceManager.getInstance(getActivity()).getUserName();
        getChats();
    }

    private void findViews(View view){
        recyclerView = view.findViewById(R.id.chat_recycler);
        messagesLayout = view.findViewById(R.id.chat_messages_layout);
        sendLayout = view.findViewById(R.id.chat_send_layout);
        edtText = view.findViewById(R.id.chat_edt_message);
        sendBtn = view.findViewById(R.id.chat_send_btn);
        chatLayout = view.findViewById(R.id.chat_layout);
        progressBar = view.findViewById(R.id.chats_progress_bar);
    }

    private void configViews(){
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtText.getText().toString().equals("")){
                    Message message = new Message();
                    message.setRoomId(id);
                    message.setText(edtText.getText().toString());
                    message.setUsername(MyPreferenceManager.getInstance(getActivity()).getUserName());
                    SendMessageController sendMessageController = new SendMessageController(sendMessageCallback);
                    sendMessageController.start(authorization , message);
                }
            }
        });
        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //DoNothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int lines = edtText.getLineCount();
                switch (lines){
                    case 1:
                        setParams(90 , 10);
                        break;
                    case 2:
                        setParams(875 , 125);
                        break;
                    case 3:
                        setParams(85 , 15);
                        break;
                    case 4:
                        setParams(825 , 175);
                        break;
                    default:
                        setParams(80 , 20);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //DoNothing
            }
        });
    }

    private void configBackground(){
        Point point = MyPreferenceManager.getInstance(getActivity()).getRealSize();
        BitmapClass bitmap = new BitmapClass();
        chatLayout.setBackground(bitmap.createBitmapDrawableWithPixels(getResources() , R.drawable.background_wallpaper , point.x , point.y , false));
    }

    private void getChats(){
        progressBar.setVisibility(View.VISIBLE);
        chatId = new ChatId();
        chatId.setRoomId(id);
        chatController = new ChatController(chatCallback);
        chatController.start(authorization , chatId);
    }

    private void showChats(List<Chat> chats){
        chatAdapter = new ChatAdapter(chats , username);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(chats.size()-1);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.CHAT_FRAGMENT_ON_PAUSE));
        super.onPause();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.CHAT_FRAGMENT_ON_RESUME));
        super.onResume();
    }

    private void showSnackBar(String state , String text){
        TSnackbar snackbar = TSnackbar.make(chatLayout, text , TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = view.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.WHITE);
        view.setBackgroundColor(getResources().getColor(R.color.red));
        if (state.equals("getChats")){
            snackbar.setDuration(TSnackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getString(R.string.try_again), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChats();
                }
            });
        }
        snackbar.show();
    }

    private void setParams(int messagesLayoutWeight , int sendLayoutWeight){
        LinearLayout.LayoutParams messagesLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 0 , messagesLayoutWeight);
        LinearLayout.LayoutParams sendLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 0 , sendLayoutWeight);
        messagesLayout.setLayoutParams(messagesLayoutParams);
        sendLayout.setLayoutParams(sendLayoutParams);
    }


    ChatRoomAPI.getChatCallback chatCallback = new ChatRoomAPI.getChatCallback() {
        @Override
        public void onResponse(boolean successful , List<Chat> chats) {
            if (successful){
                if (chats.size() == 0){
                    Chat newChat = new Chat();
                    newChat.setUsername(getString(R.string.app_username));
                    newChat.setText(getString(R.string.app_message));
                    newChat.setDayTime("");
                    chats.add(newChat);
                    showChats(chats);
                }else {
                    new AnalyseChats(chats).execute();
                }
            }else {
                progressBar.setVisibility(View.INVISIBLE);
                showSnackBar("getChats" , getString(R.string.error_unknown));
            }
        }

        @Override
        public void onFailure() {
            progressBar.setVisibility(View.INVISIBLE);
            showSnackBar("getChats" , getString(R.string.error_on_failure));
        }
    };

    ChatRoomAPI.sendMessageCallback sendMessageCallback = new ChatRoomAPI.sendMessageCallback() {
        @Override
        public void onResponse(boolean successful, MessageResponse messageResponse) {
            if(successful){
                edtText.setText("");
                getChats();
            }
            else {
                showSnackBar("sendMessage" , getString(R.string.error_unknown));
            }
        }

        @Override
        public void onFailure() {
            showSnackBar("sendMessage" , getString(R.string.error_on_failure));
        }
    };

    private class AnalyseChats extends AsyncTask<Void , Void , List<Chat>>{
        List<Chat> chats;

        private AnalyseChats(List<Chat> chats) {
            this.chats = chats;
        }

        @Override
        protected void onPostExecute(List<Chat> analysedChats) {
            super.onPostExecute(analysedChats);
            showChats(analysedChats);
        }

        @Override
        protected List<Chat> doInBackground(Void... voids) {
            String previousDayTime = "";
            String time;
            List<Chat> analysedChats = new ArrayList<>();
            int size = chats.size();
            for (int i=0 ; i<size ; i++){
                Chat chat = new Chat();
                time = chats.get(i).getTextTime();
                if (previousDayTime.equals(time.substring(0,10))){
                    chat.setDayTime("");
                }else {
                    chat.setDayTime(time.substring(0 , 10));
                    previousDayTime = chat.getDayTime();
                }
                chat.setTextTime(time.substring(11 , 16));
                chat.setUsername(chats.get(i).getUsername());
                chat.setText(chats.get(i).getText());
                analysedChats.add(i , chat);
            }
            return analysedChats;
        }
    }
}
