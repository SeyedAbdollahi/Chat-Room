package seyedabdollahi.ir.chatroom.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import seyedabdollahi.ir.chatroom.Adapters.FragmentPagerAdappter;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;

public class ViewPagerFragment extends android.support.v4.app.Fragment {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView txtToolbar;
    private String chatName;
    private String chatId;
    private float px;
    private float scaledDensity;
    private float sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        fragmentPagerAdapter = new FragmentPagerAdappter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    private BroadcastReceiver roomBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Actions.ROOM_ON_CLICK)) {
                chatName = intent.getStringExtra("chat_name");
                chatId = intent.getStringExtra("chat_id");
                openChatFragment();
                return;
            }
            if(action.equals(Actions.CHAT_FRAGMENT_ON_PAUSE)){
                Log.d("TAG" , "chat on pause");
                txtToolbar.setText(getString(R.string.app_name));
                txtToolbar.setTextSize(25);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.REFRESH_ROOMS));
                return;
            }
            if(action.equals(Actions.CHAT_FRAGMENT_ON_RESUME)){
                Log.d("TAG" , "chat on resume");
                txtToolbar.setText(chatName);
                txtConfigToOneLine(txtToolbar);
                return;
            }
        }
    };

    private void findViews(View view){
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.table_layout);
        txtToolbar = view.findViewById(R.id.view_pager_txt_toolbar);
    }

    private void txtConfigToOneLine(final TextView textView){
        textView.setTextSize(20);
        if (textView.getText().length() > 31){
            textView.setText(textView.getText().subSequence(0 , 30));
            textView.setText(textView.getText().toString() + "...");
        }
        textView.post(new Runnable() {
            @Override
            public void run() {
                px = textView.getTextSize();
                scaledDensity = MyPreferenceManager.getInstance(getActivity()).getScaledDensity();
                sp = px / scaledDensity;
                while(textView.getLineCount()>1){
                    textView.setTextSize(sp - 5);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(roomBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(roomBroadcastReceiver , new IntentFilter(Actions.ROOM_ON_CLICK));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(roomBroadcastReceiver , new IntentFilter(Actions.CHAT_FRAGMENT_ON_PAUSE));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(roomBroadcastReceiver , new IntentFilter(Actions.CHAT_FRAGMENT_ON_RESUME));
    }

    private void openChatFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("chat_id" , chatId);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .add(R.id.viewpager_frame , chatFragment)
                .addToBackStack(null)
                .commit();
    }
}
