package com.mugivara.mydiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddActivity extends Activity {
    private static final String TAG = "AddActivity";
    private Button btSave,btCancel;
    private EditText etTitle,etText;
    private TextView tvDate;
    private Context context;
    private ImageButton ibtDate;
    private long MyRecordID;


    private Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle=(EditText)findViewById(R.id.editText_Title);
        etText=(EditText)findViewById(R.id.editText_Text);
        tvDate=(TextView)findViewById(R.id.textView_Date);

        ibtDate= (ImageButton) findViewById(R.id.button_Date);
        btSave=(Button)findViewById(R.id.button_Save);
        btCancel=(Button)findViewById(R.id.button_Chanel);


        if(getIntent().hasExtra("Record")){
            Record record=(Record)getIntent().getSerializableExtra("Record");
            etTitle.setText(record.getTitle());
            etText.setText(record.getText());
            //tvDate.setText(DateConverter.fromMilicToDate(record.getDate()));
            MyRecordID=record.getId();

            calendar.setTime(new Date(record.getDate()));

        }
        else
        {
            MyRecordID=-1;
        }
        setInitialDate();



        ibtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        calendar.set(Calendar.YEAR, year);
                                        calendar.set(Calendar.MONTH, monthOfYear);
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        setInitialDate();
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        ).show();
//                        Log.d(AddActivity.TAG, context.toString());
                        setInitialDate();
                    }
        });




        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record record=new Record(MyRecordID, etTitle.getText().toString(),etText.getText().toString(), calendar.getTimeInMillis());
                Intent intent=getIntent();
                intent.putExtra("Record",record);
                setResult(RESULT_OK,intent);
                //Log.d("recordddd", record.getText() + record.getTitle() + String.valueOf(record.getDate()));
                finish();

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void setInitialDate(){
        tvDate.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
}
