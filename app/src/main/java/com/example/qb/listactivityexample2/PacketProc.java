package com.example.qb.listactivityexample2;

import android.util.Log;

import java.util.Queue;
import java.util.Vector;

/**
 * Created by qb on 2018/7/9.
 */

public class PacketProc extends PacketParse {

    @Override
    public boolean checkValid(Vector container) {
        int i ;
        PacketData packetData;
        for(i = 0; i < container.size(); i++) {
            packetData = (PacketData)container.get(i);
            //计算接收到的机号
            if (0 == packetData.mGroup)
            {
                packetData.mDevNum = packetData.mAddr;
            }
            else
            {
                packetData.mDevNum = (packetData.mGroup - 1) * 255 + packetData.mAddr;
            }
            //判断设备类型
            if ((0 != packetData.mDevType) && (packetData.mFinalDevType != packetData.mDevType)) {
                container.remove(i);
            }
        }
        return (container.isEmpty() ? false : true);
    }

    public YdtDev cmdProc(PacketData packetData) {
        YdtDev ydtDev = null;
        final int cmdGetDeviceInfo = 0x1000;

        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        //命令高字节为0表示对方应答，否则为对方主发命令。
        if (0 == packetData.mCmdH) {
            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "cmdl:" + packetData.mCmdL
            + ",len:" + packetData.mLen + "!");
            //命令低字节为0表示对方执行命令成功并应答，否则对方执行命令出错
            if ((0 == packetData.mCmdL) && (packetData.mLen >= 2)) {
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                int cmd = ((byte)packetData.mBuf.get(0) * 256) + (byte)packetData.mBuf.get(1);
                switch (cmd) {
                    case cmdGetDeviceInfo:     //获取设备信息，对方应答
                    {
                        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        ydtDev = GetDeviceInfo(packetData.mBuf);
                    }
                    break;
                }
            }
        } else {
            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            switch (packetData.mCmd) {
                case cmdGetDeviceInfo:
                {

                }
                break;
            }
        }

        return ydtDev;
    }

    private YdtDev GetDeviceInfo(Vector container) {
        //协议版本(1) + mac(6) + dhcp(1) + ip(4) + 子网掩码(4） + 网关(4) + dns(4) + 一卡通ip(4) + 端口(2) + 协议端口(2) + 项目编号(4)
        int i;
        String mac;
        String dhcp;
        String ip;
        String mask;
        String gateway;
        String dns;
        String yktIp;
        String yktPort;
        String ctrlPort;
        String projectNum;
        StringBuffer strBuffer = new StringBuffer("");
        byte[] mMacByte = new byte[6];

        for (i = 0; i < mMacByte.length; i++) {
            mMacByte[i] = (byte)container.get(i + 3);
        }
        mac = DataConversion.toHexString(mMacByte);
        if (0 == (byte) container.get(9)) {
            dhcp = "否";
        } else {
            dhcp = "是";
        }
        //ip地址
        i = 10;
        strBuffer.setLength(0);     //清空
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        ip = strBuffer.toString();
        //子网掩码
        strBuffer.setLength(0);     //清空
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        strBuffer.append(".");
        strBuffer.append(String.valueOf((byte) container.get(i++)));
        mask = strBuffer.toString();
        //
        container.subList(10, 14).toArray();
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",array:"
                + container.subList(10, 14).toArray() + ",mask:" + mask + ",ip:" + ip);
//        byte[] myData = container.toArray(new byte[container.size()]);
        //(String devProduct, String devVersion, String devProjectNum, String devDhcp, String devIp, String devMask,
        //String devGateway, String devDns, String devSvrIp, String devSvrPort, String devCtrlPort, String devNum,
        //        String devMac)
        return new YdtDev("云电梯", "YDT0110 V1000", "88888888", dhcp, ip, mask, "192.168.123.254", "211.162.78.1", "192.168.123.120",
                "12581", "60202", "88", mac);
    }
}
