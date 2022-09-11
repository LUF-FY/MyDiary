package com.mugivara.mydiary;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.mugivara.mydiary.databinding.ActivityMainBinding;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class MainActivity extends AppCompatActivity {


    DBRecords mDBConnector;
    Context mContext;
    ListView mListView;
    Cursor cursor;
    myListAdapter myAdapter;

    androidx.appcompat.widget.Toolbar mToolbar;

    int ADD_ACTIVITY = 0;
    int UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        mDBConnector=new DBRecords(this);
        mListView=(ListView)findViewById(R.id.list);
        myAdapter=new myListAdapter(mContext,mDBConnector.selectAll());
        mListView.setAdapter(myAdapter);
        registerForContextMenu(mListView);

        mToolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, AddActivity.class);
                startActivityForResult (i, ADD_ACTIVITY);
                updateList();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                mDBConnector.deleteAll();
                updateList();
                return true;
            case R.id.uploadInFile:
                uploadInFile(mDBConnector.selectAll());
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private static final String TAG = "MyActivity";


    private void uploadInFile (ArrayList<Record> listRecords) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s ="";
                for (Record r : listRecords) {
                    s += r.toString() + '\n';
                }
                try {
                    File f = new File(getApplicationContext().getFilesDir().getPath() + "/MyDiaryRecords.txt");
                    FileWriter writer = new FileWriter(f, false);
                    writer.write(s);
                    writer.flush();
                    Log.i(TAG, "uploadInFile : " + f.toString());
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }

            }
        }).start();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(mContext, AddActivity.class);
                Record record = mDBConnector.select(info.id);
                i.putExtra("Record", record);
                startActivityForResult(i, UPDATE_ACTIVITY);
                updateList();
                return true;
            case R.id.delete:
                mDBConnector.delete (info.id);
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void updateList () {
        myAdapter.setArrayMyData(mDBConnector.selectAll());
        myAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Record record = (Record) data.getExtras().getSerializable("Record");
            if (requestCode == UPDATE_ACTIVITY)
                mDBConnector.update(record);
            else
                mDBConnector.insert(record.getTitle(), record.getText(), record.getDateInMillis());
            updateList();
        }
    }

    class myListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private ArrayList<Record> arrayMyRecords;
    
        public myListAdapter (Context ctx, ArrayList<Record> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }
    
        public ArrayList<Record> getArrayMyData() {
            return arrayMyRecords;
        }
    
        public void setArrayMyData(ArrayList<Record> arrayMyData) {
            this.arrayMyRecords = arrayMyData;
        }
    
        public int getCount () {
            return arrayMyRecords.size();
        }
    
        public Object getItem (int position) {
    
            return position;
        }
    
        public long getItemId (int position) {
            Record record = arrayMyRecords.get(position);
            if (record != null) {
                return record.getId();
            }
            return 0;
        }
    
        public View getView(int position, View convertView, ViewGroup parent) {
    
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);
    
            TextView vTitle= (TextView)convertView.findViewById(R.id.title);
            TextView vText = (TextView)convertView.findViewById(R.id.text);
            TextView vDate=(TextView)convertView.findViewById(R.id.date);
    
    
            Record record = arrayMyRecords.get(position);
            vTitle.setText(record.getTitle());
            vText.setText(record.getText());

            DateConverter.setInitialDate(parent.getContext(), vDate, record.getCalendar());
            return convertView;
        }
    } // end myAdapter

}