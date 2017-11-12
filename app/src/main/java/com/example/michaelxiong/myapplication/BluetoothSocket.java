package com.example.michaelxiong.myapplication;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
//import android.bluetooth.IBluetoothCallback;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by princ on 11/11/2017.
 */

public class BluetoothSocket extends Object implements Closeable {
    private static final UUID DEFAULT_UUID = UUID.fromString("0000110E-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "BluetoothSocket";
    public static final int MAX_RFCOMM_CHANNEL = 30;
    /** Keep TYPE_ fields in sync with BluetoothSocket.cpp */
    /*package*/ static final int TYPE_RFCOMM = 1;
    /*package*/ static final int TYPE_SCO = 2;
    /*package*/ static final int TYPE_L2CAP = 3;

    /*package*/ static final int EBADFD = 77;
    /*package*/ static final int EADDRINUSE = 98;

    private final int mType;  /* one of TYPE_RFCOMM etc */
    private final BluetoothDevice mDevice;    /* remote device */
    private final String mAddress;    /* remote address */
    private final boolean mAuth;
    private final boolean mEncrypt;
    private final BluetoothInputStream mInputStream;
    private final BluetoothOutputStream mOutputStream;
    private final SdpHelper mSdp;
    private int mPort;  /* RFCOMM channel or L2CAP psm */

    /** prevents all native calls after destroyNative() */
    private boolean mClosed;

    /** protects mClosed */
    private final ReentrantReadWriteLock mLock;

    /** used by native code only */
    private int mSocketData;

    /**
     * Construct a BluetoothSocket.
     * @param type    type of socket
     * @param fd      fd to use for connected socket, or -1 for a new socket
     * @param auth    require the remote device to be authenticated
     * @param encrypt require the connection to be encrypted
     * @param device  remote device that this socket can connect to
     * @param port    remote port
     * @param uuid    SDP uuid
     * @throws IOException On error, for example Bluetooth not available, or
     *                     insufficient priveleges
     */
    /*package*/ BluetoothSocket(int type, int fd, boolean auth, boolean encrypt,
                                BluetoothDevice device, int port, ParcelUuid uuid) throws IOException {
        if (type == BluetoothSocket.TYPE_RFCOMM && uuid == null && fd == -1) {
            if (port < 1 || port > MAX_RFCOMM_CHANNEL) {
                throw new IOException("Invalid RFCOMM channel: " + port);
            }
        }
        if (uuid == null) {
            mPort = port;
            mSdp = null;
        } else {
            mSdp = new SdpHelper(device, uuid);
            mPort = -1;
        }
        mType = type;
        mAuth = auth;
        mEncrypt = encrypt;
        mDevice = device;
        if (device == null) {
            mAddress = null;
        } else {
            mAddress = device.getAddress();
        }
        if (fd == -1) {
            initSocketNative();
        } else {
            initSocketFromFdNative(fd);
        }
        mInputStream = new BluetoothInputStream(this);
        mOutputStream = new BluetoothOutputStream(this);
        mClosed = false;
        mLock = new ReentrantReadWriteLock();
    }



    /* Methods needed
    Create BS for connecting to KNOWN device use: BluetoothDevice.createRfcommSocketToServiceRecord()
    .connect() for connection to remote device
    Then create BlSocket (host)
     */
    public BluetoothSocket CreateRfcommSocketToServiceRecord(UUID uuid){
        BluetoothSocket bluetoothSocket = new BluetoothSocket();

        return bluetoothSocket;
    };

    @Override
    public void close() throws IOException {

    }
}
