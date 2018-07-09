package com.example.qb.listactivityexample2;

import android.util.Log;

import java.util.Vector;

/**
 * Created by qb on 2018/7/8.
 */

public class DevScan {
    private UdpBroadcastRcv mUdpBroadcastRcv;
    private UdpBroadcastSnd mUdpBroadcastSnd;
    private ScanCallback mScanCallback;

    public void startScan(ScanCallback callback) {
        Log.d(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        //
        mScanCallback = callback;
        Log.d(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        //启动udp发送广播
        byte[] sndData = {(byte)0xF2, 0x00, 0x00, 0x21, 0x03, 0x10, 0x00, 0x32, (byte)0xF3};
        mUdpBroadcastSnd = new UdpBroadcastSnd(sndData);
        mUdpBroadcastSnd.start();
        Log.d(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        //启动udp接收
        mUdpBroadcastRcv = new UdpBroadcastRcv(12580, "255.255.255.255", mRcvCallback);
        mUdpBroadcastRcv.start();
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    public void stopScan(ScanCallback callback) {
        mScanCallback = callback;
        //停止udp发送广播
        mUdpBroadcastSnd.exit();
        //停止udp接收广播
        mUdpBroadcastRcv.exit();
    }

    private UdpBroadcastRcv.RcvCallback mRcvCallback =
            new UdpBroadcastRcv.RcvCallback() {
                @Override
                public void onRcv(byte[] data, int length) {
                    YdtDev ydtDev;
                    if (null != mScanCallback) {
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                + ",>>>Packet received; data(toHexString): " + DataConversion.toHexString(data, data.length) + "len:" + data.length + "!");
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        PacketProc packProc = new PacketProc();
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        Vector container = packProc.dataParse(data, length);
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        if (!container.isEmpty()) {
                            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            if (packProc.checkValid(container)) {
                                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                for (int i = 0; i < container.size(); i++) {
                                    PacketData data1 = (PacketData)container.get(0);

                                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                    + "step:" + data1.mStep + ",len:" + data1.mLen + ",cmdl:" + data1.mCmdL);
                                    ydtDev = packProc.cmdProc(((PacketData)container.get(i)));
                                    if (null != ydtDev) {
                                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                        mScanCallback.onScan(ydtDev);

                                    }
                                }
                            }
                        }
                    }
                }
            };

    public interface ScanCallback {

        public void onScan(YdtDev device);
    }
}
