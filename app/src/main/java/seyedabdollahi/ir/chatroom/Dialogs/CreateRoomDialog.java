package seyedabdollahi.ir.chatroom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import seyedabdollahi.ir.chatroom.Listeners.OnClickCreateListener;
import seyedabdollahi.ir.chatroom.R;

public class CreateRoomDialog extends Dialog {

    private OnClickCreateListener listener;
    private int width;
    private int height;
    private EditText editText;
    private Button createBtn;

    public CreateRoomDialog(@NonNull Context context , OnClickCreateListener listener) {
        super(context);
        this.listener = listener;
        setWidthHeight(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_room_dialog);
        findViews();
        configViews();
        getWindow().setLayout(width , height);
    }

    private void setWidthHeight(Context context){
        width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.32);
    }

    private void findViews(){
        editText = findViewById(R.id.dialog_edt);
        createBtn = findViewById(R.id.dialog_btn_create);
    }

    private void configViews(){
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                listener.onClickCreate(name);
            }
        });
    }
}
