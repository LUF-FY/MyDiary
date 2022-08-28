package com.mugivara.mydiary;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import static androidx.core.view.ViewGroupKt.setMargins;

public class RecordView extends LinearLayout {

    private static final String TAG = "RecordView";

    private TextView title;
    private TextView text;

    private RecordView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public RecordView(Context context, Record record, int titleSize,  int textSize) {
        this(context);
        setTitle(context, record.getTitle(), titleSize);
        setText(context, record.getText(), textSize);
        this.addView(this.title);
        this.addView(this.text);
        Log.d(TAG, "Создался");
    }

    public RecordView(Context context, String title, String text, int titleSize,  int textSize) {
        this(context);
        setTitle(context, title, titleSize);
        setText(context, text, textSize);
        this.addView(this.title);
        this.addView(this.text);
    }


    public void setTitle(Context context, String title, int titleSize) {
        this.title = new TextView(context);
        this.title.setText(title);
        this.title.setTextSize(titleSize);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 12, 24, 12);
        this.title.setLayoutParams(layoutParams);
    }

    public TextView getTitle() {
        return title;
    }


    public TextView getText() {
        return text;
    }

    public void setText(Context context, String text, int textSize) {
        this.text = new TextView(context);
        this.text.setText(text);
        this.text.setTextSize(textSize);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 12, 24, 12);
        this.text.setLayoutParams(layoutParams);
    }
    
}