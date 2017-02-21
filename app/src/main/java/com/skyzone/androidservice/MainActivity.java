package com.skyzone.androidservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.skyzone.androidservice.Net.ImageLoadActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DemoService.MyBinder mMyBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (DemoService.MyBinder) service;
            mMyBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.bt_start_service).setOnClickListener(this);
        findViewById(R.id.bt_stop_service).setOnClickListener(this);
        findViewById(R.id.bt_bind_service).setOnClickListener(this);
        findViewById(R.id.bt_unbind_service).setOnClickListener(this);
        findViewById(R.id.bt_start_bg_service).setOnClickListener(this);
        findViewById(R.id.bt_start_fore_service).setOnClickListener(this);
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
            startActivity(new Intent(this, ImageLoadActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_service:
                startService(new Intent(this, DemoService.class));
                break;
            case R.id.bt_stop_service:
                stopService(new Intent(this, DemoService.class));
                break;
            case R.id.bt_bind_service:
                bindService(new Intent(this, DemoService.class), mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind_service:
                unbindService(mServiceConnection);
                break;
            case R.id.bt_start_bg_service:
                System.out.println("click bg");
                startService(new Intent(this, DemoIntentService.class));
                break;
            case R.id.bt_start_fore_service:
                startService(new Intent(this, DemoForeService.class));
                break;
        }
    }
}
