package seyedabdollahi.ir.chatroom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class BitmapClass {

    public BitmapDrawable createBitmapDrawableWithPixels(Resources resources , int drawable , int widthPixel , int heightPixel , boolean filter){
        Bitmap bitmap = BitmapFactory.decodeResource(resources , drawable);
        Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap , widthPixel , heightPixel , filter);
        return new BitmapDrawable(resources , resizeBitmap);
    }
}
