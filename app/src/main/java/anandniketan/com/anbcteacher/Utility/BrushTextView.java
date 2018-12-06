package anandniketan.com.anbcteacher.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class BrushTextView extends TextView {

    public BrushTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BrushTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrushTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/brush_script_mt_kursiv.ttf");
        setTypeface(tf ,Typeface.NORMAL);
    }
}
