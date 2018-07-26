package com.example.qb.listactivityexample2;

import android.util.Log;

import java.nio.charset.Charset;
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
                        ydtDev = GetDeviceInfo(packetData.mBuf, packetData);
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

    private YdtDev GetDeviceInfo(Vector container, PacketData packetData) {
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
        String devNum;
        String str;

        i = 3;
        str = "";
        for (int j = 0; j < 6; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%02x", byteTemp & 0xFF);
            if (j < 5) {
                str += ":";
            }
        }
        mac = str;
        if (0 == (byte) container.get(9)) {
            dhcp = "否";
        } else {
            dhcp = "是";
        }
        //ip地址
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",i:"
                + container.get(i) + "," + container.get(i + 1) + ","  + container.get(i + 2)+ ","  + container.get(i + 3));
        i = 10;
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%d", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        ip = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",ip:" + ip);
        //子网掩码
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%d", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        mask = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",mask:" + mask);
        //网关
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%d", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        gateway = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",gateway:" + gateway);
        //dns
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%d", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        dns = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",dns:" + dns);
        //一卡通服务器ip
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%d", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        yktIp = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",yktIp:" + yktIp);
        //一卡通服务器端口
        int intTmp = (int)(byte)container.get(i++) * 256 + (int)(byte)container.get(i++);
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",int:" + intTmp);
//        yktPort = String.format("%d", ((int)(byte)container.get(i++) * 256 + (int)(byte)container.get(i++)));
        yktPort = String.format("%d", intTmp);
        //协议控制器端口
        intTmp = (int)(byte)container.get(i++) * 256 + (int)(byte)container.get(i++);
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",int:" + intTmp);
        ctrlPort = String.format("%d", intTmp);
        //项目编号
        str = "";
        for (int j = 0; j < 4; j++) {
            Byte byteTemp = (byte)container.get(i++);
            str += String.format("%02x", byteTemp & 0xFF);
            if (j < 3) {
                str += ".";
            }
        }
        projectNum = str;
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",projectNum:" + projectNum);
        //设备机号
        devNum = String.format("%d", packetData.mDevNum);

        return new YdtDev("云电梯", "", projectNum, dhcp, ip,
                mask, gateway, dns, yktIp, yktPort, ctrlPort, devNum, mac);
    }
}
