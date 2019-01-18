package seyedabdollahi.ir.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import seyedabdollahi.ir.chatroom.Fragments.LoginFragment;
import seyedabdollahi.ir.chatroom.Fragments.RegisterFragment;
import seyedabdollahi.ir.chatroom.Fragments.ViewPagerFragment;
import seyedabdollahi.ir.chatroom.Fragments.WelcomeFragment;
import seyedabdollahi.ir.chatroom.Keys.Actions;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private FrameLayout smallFrameLayout;
    private Point point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        configViews();
        getDisplayRealSize();
        configImages();
        if(MyPreferenceManager.getInstance(MainActivity.this).getAccessToken() == null){
            openWelcomeFragment();
        }else {
            openRoomFragment();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Actions.ON_CLICK_BTN_LOGIN)){
                openLoginFragment();
                return;
            }
            if (action.equals(Actions.ON_CLICK_BTN_REGISTER)){
                openRegisterFragment();
                return;
            }
            if (action.equals(Actions.ON_CLICK_BTN_LOGOUT)){
                finish();
                return;
            }
            if (action.equals(Actions.LOGIN_STATUS)){
                if (intent.getBooleanExtra("successful" , false)){
                    getFragmentManager().popBackStack();
                    openRoomFragment();
                }else {
                    Snackbar snackbar = Snackbar.make(mainLayout , intent.getStringExtra("error") , Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver , new IntentFilter(Actions.LOGIN_STATUS));
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver , new IntentFilter(Actions.ON_CLICK_BTN_LOGIN));
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver , new IntentFilter(Actions.ON_CLICK_BTN_REGISTER));
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver , new IntentFilter(Actions.ON_CLICK_BTN_LOGOUT));
    }

    private void findViews(){
        mainLayout = findViewById(R.id.main_layout);
        smallFrameLayout = findViewById(R.id.small_frame);
    }

    private void configViews(){
        smallFrameLayout.setClipToOutline(true);
    }

    private void getDisplayRealSize(){
        point = MyPreferenceManager.getInstance(MainActivity.this).getRealSize();
        if (point.x == 1) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getRealSize(point);
            MyPreferenceManager.getInstance(MainActivity.this).putRealSize(point);
            MyPreferenceManager.getInstance(MainActivity.this).putScaledDensity(getResources().getDisplayMetrics().scaledDensity);
        }
    }

    private void configImages(){
        BitmapClass bitmap = new BitmapClass();
        mainLayout.setBackground(bitmap.createBitmapDrawableWithPixels(getResources() , R.drawable.background_wallpaper , point.x , point.y , false));
    }

    private void openRegisterFragment(){
        RegisterFragment registerFragment = new RegisterFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.small_frame, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openRoomFragment(){
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.full_size_frame, viewPagerFragment)
                .commit();
    }

    private void openWelcomeFragment(){
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.small_frame, welcomeFragment)
                .commit();
    }

    private void openLoginFragment(){
        LoginFragment loginFragment = new LoginFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.small_frame, loginFragment)
                .addToBackStack(null)
                .commit();
    }
}
