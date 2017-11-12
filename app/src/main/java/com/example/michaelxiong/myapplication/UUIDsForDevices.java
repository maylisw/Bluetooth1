package com.example.michaelxiong.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by princ on 11/11/2017.
 */

public class UUIDsForDevices {
    //This should be moved into the main class later
    ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // discovery has finished, give a call to fetchUuidsWithSdp on first device in list.
                if (!mDeviceList.isEmpty()) {
                    BluetoothDevice device = mDeviceList.remove(0);
                    boolean result = device.fetchUuidsWithSdp();
                }
            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
                // This is when we can be assured that fetchUuidsWithSdp has completed.
                // So get the uuids and call fetchUuidsWithSdp on another device in list

                BluetoothDevice deviceExtra = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                System.out.println("DeviceExtra address - " + deviceExtra.getAddress());
                if (uuidExtra != null) {
                    for (Parcelable p : uuidExtra) {
                        System.out.println("uuidExtra - " + p);
                    }
                } else {
                    System.out.println("uuidExtra is still null");
                }
                if (!mDeviceList.isEmpty()) {
                    BluetoothDevice device = mDeviceList.remove(0);
                    boolean result = device.fetchUuidsWithSdp();
                }
            }
        }
    };
}
