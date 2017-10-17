package com.example.bhaum.soachat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class chathome extends AppCompatActivity {
EditText msg;
    Webservices ws1;
    Handler handler;
    ListView lv;

    private ArrayAdapter<String> listAdapter ;
    ArrayList<String> msgs = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chathome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=(ListView)findViewById(R.id.lv);
        msg=(EditText)findViewById(R.id.msg);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
                ws1=new Webservices();
        ws1.setUrl("http://dditmychat.co.nf/getmsg.php");
        ws1.addParam(Myapp.uid,"email");
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, msgs);
    lv.setAdapter(listAdapter);

        Timer timer = new Timer();
        handler= new Handler() ;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String ss = "";

                            getUrldataOld();
                        } catch (Exception e) {

                        }

                    }
                });


            }
        };
        timer.schedule(task, 0, 15000);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String message=msg.getText().toString();
                if(!message.equals(""))
                {
                    if (android.os.Build.VERSION.SDK_INT > 9)
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    Webservices ws=new Webservices();
                    ws.setUrl("http://dditmychat.co.nf/sendmsg.php");
                    ws.addParam(message,"msg");
                    ws.addParam(Myapp.uid,"email");



                    ws.connect();
                    String recmsg=ws.getData();

                    msgs.add("Me : "+message);
                    listAdapter.notifyDataSetChanged();

                    msg.setText("");
                  //  Toast.makeText(getApplicationContext(),recmsg,Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Enter message",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUrldataOld() {

        class GetImage extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String st) {
                super.onPostExecute(st);
                try
                {
                    msgs.add("Friend : "+st);
                    listAdapter.notifyDataSetChanged();

                   // Toast.makeText(getApplicationContext(),"Msg : "+st,Toast.LENGTH_LONG).show();

                }
                catch(Exception e)
                {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                String data2;

                ws1.connect();
                data2=ws1.getData();


                return data2;
            }
        }

        GetImage gi = new GetImage();
        gi.execute();

    }

}
