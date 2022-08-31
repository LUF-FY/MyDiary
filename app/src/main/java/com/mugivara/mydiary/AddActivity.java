package com.mugivara.mydiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.Throws;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private Button btSave,btCancel;
    private EditText etTitle,etText;
    private TextView tvDate;
    private Context context;
    private ImageButton ibtDate;
    private long MyRecordID;

    private androidx.appcompat.widget.Toolbar mToolbar;


    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mToolbar = findViewById(R.id.activity_add_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

//            calendar.setTime(new Date(record.getDate()));
            calendar = record.getCalendar();
        }
        else
        {
            calendar = Calendar.getInstance();
            MyRecordID=-1;
        }
        DateConverter.setInitialDate(context, tvDate, calendar);



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
                                        DateConverter.setInitialDate(context, tvDate, calendar);
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        ).show();
//                        Log.d(AddActivity.TAG, context.toString());
                        DateConverter.setInitialDate(context, tvDate, calendar);
                    }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit:
                saveAndExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveAndExit() {
        Record record=new Record(MyRecordID, etTitle.getText().toString(),etText.getText().toString(), calendar.getTimeInMillis());
        Intent intent=getIntent();
        intent.putExtra("Record",record);

        //Toast.makeText(v.getContext(), String.valueOf(record.getDate()), Toast.LENGTH_LONG).show();

        setResult(RESULT_OK,intent);
        finish();
    }
}
