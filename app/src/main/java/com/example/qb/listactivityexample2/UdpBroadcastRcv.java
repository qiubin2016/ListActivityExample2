package com.example.qb.listactivityexample2;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by qb on 2018/7/8.
 */

public class UdpBroadcastRcv extends Thread {
    boolean mRunning = true;
    int mPort = 12580;
    String mBroadcastAddr = "0.0.0.0";//"255.255.255.255";
    DatagramSocket mSocket;
    int mRcvTimeout = 2000;    //2000ms 接收时间超过此时间会抛出异常
    private RcvCallback mCallback;


    public UdpBroadcastRcv() {
        super();
        Log.d(getClass().getName(), "structure");
    }

    public UdpBroadcastRcv(int port, String InetAddressStr, RcvCallback callback) {
        super();
        mPort = port;
        mBroadcastAddr = mBroadcastAddr;
        mCallback = callback;
    }

    private Handler mHandler = new Handler();
    private Runnable mTask = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
//                mHandler.postDelayed(this,5*1000);//设置循环时间，此处是5秒  //此处不需要循环，故注释
            //需要执行的代码
            mRunning = false;    //退出线程
        }
    };

    @Override
    public void run() {
        mHandler.postDelayed(mTask, mRcvTimeout * 3);    //最迟6s后线程退出
        while (mRunning) {
            DatagramPacket mPacket;
            try {
                mSocket = new DatagramSocket(mPort, InetAddress.getByName(mBroadcastAddr));
                mSocket.setBroadcast(true);
                mSocket.setSoTimeout(mRcvTimeout);     //设置接收超时
                Log.d(getClass().getName(), "Ready to receive broadcast packets!");
                //Receive a packet
                byte[] mRecvBuf = new byte[15000];
                mPacket = new DatagramPacket(mRecvBuf, mRecvBuf.length);
                try {
                    mSocket.receive(mPacket);    //在setSoTimeout()设置的时间内未收到数据则抛出异常
                } catch (SocketTimeoutException e) {  //API说抛出SocketException异常，实应捕捉SocketTimeoutException
                    e.printStackTrace();
                    Log.i(getClass().getName(), "Receive broadcast packets tiemout!");
                    mSocket.close();
                    continue;
                }
                mSocket.close();
                //Packet received
                Log.i(getClass().getName(), ">>>Discovery packet received from: " + mPacket.getAddress().getHostAddress());
//                    final String mCodeString = new String(mRecvBuf, 0, mPacket.getLength());
//                    Log.d(getClass().getName(), ">>>Packet received; data(String): " + mCodeString + "!");
                Log.i(getClass().getName(), ">>>Packet received; data(toHexString): " + DataConversion.toHexString(mRecvBuf, mPacket.getLength()) + "!");
                if (null != mCallback) {
                    mCallback.onRcv(mRecvBuf, mPacket.getLength());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(getClass().getName(), "Receive broadcast packets IOException!");
                break;
            }
        }
        Log.d(getClass().getName(), "Receive broadcast packets thread exit!");
    }

    public void exit() {
        mRunning = false;
        mSocket.close();
    }

    public interface RcvCallback {

        public void onRcv(byte[] data, int length);
    }
}
