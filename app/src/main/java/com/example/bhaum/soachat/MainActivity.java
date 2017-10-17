package com.example.bhaum.soachat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login,submit;
    EditText name,email,college,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login=(Button) findViewById(R.id.login);
        submit=(Button) findViewById(R.id.submit);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        college=(EditText) findViewById(R.id.college);
        password=(EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nm=name.getText().toString();
                String em=email.getText().toString();
                String clg=college.getText().toString();
                String pass=password.getText().toString();

                if (android.os.Build.VERSION.SDK_INT > 9)
                    {
                             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                             StrictMode.setThreadPolicy(policy);
                        }

                Webservices ws=new Webservices();
                ws.setUrl("http://dditmychat.co.nf/signup.php");
                ws.addParam(nm,"name");
                ws.addParam(em,"email");
                ws.addParam(clg,"college");
                ws.addParam(pass,"pass");



                ws.connect();
                String recmsg=ws.getData();
                Toast.makeText(getApplicationContext(),recmsg,Toast.LENGTH_SHORT).show();


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
