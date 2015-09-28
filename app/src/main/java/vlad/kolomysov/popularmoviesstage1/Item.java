package vlad.kolomysov.popularmoviesstage1;

import android.graphics.Bitmap;

/**
 * Created by admin on 28.09.15.
 */
public class Item {


    Bitmap bitmap;
    String text;

    Item(Bitmap bitmap,String text){
        this.bitmap = bitmap;
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
