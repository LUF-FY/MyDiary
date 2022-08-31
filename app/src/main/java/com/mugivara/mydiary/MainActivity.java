package com.mugivara.mydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.mugivara.mydiary.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {


    DBRecords mDBConnector;
    Context mContext;
    ListView mListView;
    Cursor cursor;
    myListAdapter myAdapter;

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
        // Inflate the menu; this adds items to the action bar if it is present.
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
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        if (resultCode == Activity.RESULT_OK) {
            Record record = (Record) data.getExtras().getSerializable("Record");
            if (requestCode == UPDATE_ACTIVITY)
                mDBConnector.update(record);
            else
                mDBConnector.insert(record.getTitle(), record.getText(), record.getDateInMillis());
            updateList();
        }
    }
    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

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

            //Toast.makeText(parent.getContext(), String.valueOf(record.getDate()), Toast.LENGTH_LONG).show();
            DateConverter.setInitialDate(parent.getContext(), vDate, record.getCalendar());

            //Toast.makeText(parent.getContext(), record.getDate() + ", " + String.valueOf(new Date(record.getDate()).getTime()) + ", " + calendar.getTimeInMillis(), Toast.LENGTH_LONG).show();
            return convertView;
        }
    } // end myAdapter

}