package com.example.qb.listactivityexample2;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by qb on 2018/7/8.
 */

public class UdpBroadcastSnd extends Thread {
    boolean mRunning = true;
    byte[] mData;
    int mPort = 10086;
    String mBroadcastAddr = "255.255.255.255";

    public UdpBroadcastSnd(byte[] data) {
        mData = data;
    }
    public UdpBroadcastSnd(byte[] data, int port, String InetAddressStr) {
        mData = data;
        mPort = port;
        mBroadcastAddr = InetAddressStr;
    }
    @Override
    public void run() {
        Log.d(getClass().getName(), "run1!");
        int mCount = 3;
        MulticastSocket mMulSocket;
        DatagramPacket mPacket;
        if (0 == mData.length) {
            return;
        }
        Log.d(getClass().getName(), "run2!");
        while (mRunning) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            Log.e(getClass().getName(), "run3!");
            Log.v(getClass().getName(), "run3!");
            Log.i(getClass().getName(), "run3!");
            Log.w(getClass().getName(), "run3!");

            Log.d(getClass().getName(), "run3!");
            if (0 != mCount) {
                Log.d(getClass().getName(), "run4!");
                mCount--;
                try {
                    mMulSocket = new MulticastSocket();
                    mPacket = new DatagramPacket(mData, mData.length, InetAddress.getByName(mBroadcastAddr), mPort);
                    mMulSocket.send(mPacket);
                    mMulSocket.close();
                    Log.d(getClass().getName(), "sended broadcast packets!");
                    try {
                        Log.d(getClass().getName(), "delay some time begin!");
                        Thread.sleep(2000);    //延时2秒
                        Log.d(getClass().getName(), "delay some time end!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(getClass().getName(), "run exit!");
                mRunning = false;
                break;
            }
        }
    }

}
