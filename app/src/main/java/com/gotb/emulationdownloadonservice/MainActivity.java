package com.gotb.emulationdownloadonservice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String COMPLETE_DOWNLOAD = "action";
    Button btnDownload;
    TextView tvDownloadComplete;
    BroadcastReceiver broadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = (Button) findViewById(R.id.btnDownload);
        tvDownloadComplete = (TextView) findViewById(R.id.tvDownloadComplete);

    }

    public void onClickDownload(View view) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("receiveDataIntent");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("receiveDataIntent")){
                    tvDownloadComplete.setText(intent.getStringExtra(COMPLETE_DOWNLOAD));
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        startService(new Intent(this, NotificationService.class));
    }

    public void onClickDoSomething(View view) {
        Toast.makeText(MainActivity.this, "Magic!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(broadcastReceiver);
        } catch (RuntimeException e){}

    }
}
