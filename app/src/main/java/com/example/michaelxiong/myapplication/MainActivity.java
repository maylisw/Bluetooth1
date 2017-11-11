package com.example.michaelxiong.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_ENABLE_BT = 1;
    public final static int REQUEST_ENABLE_DIS = 300;

    private TextView codeDisplay;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeDisplay = (TextView) findViewById(R.id.code_display);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent == ACTION_SCAN_MODE_CHANGED){}
            }
        };
    }

    public void onStartClick(View view){
        setUpBT();
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, REQUEST_ENABLE_DIS);
        startActivityForResult(discoverableIntent, REQUEST_ENABLE_DIS);
    }

    public void onFindClick(View view){
        setUpBT();
    }

    protected void setUpBT(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(this, "Please enable Bluetooth NOW!!!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Yay", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            Log.d("Working:", "Bluetooth enabled");
            Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == REQUEST_ENABLE_DIS){
            Log.d("Working", "Made discoverable");
            Toast.makeText(this, "Made discoverable", Toast.LENGTH_SHORT).show();
            String code = "";
            for(int i = 0; i < 8; i++){
                code = code + (int) (10*Math.random());
            }
            codeDisplay.setText(code);
        }
    }
}
