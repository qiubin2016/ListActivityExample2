package com.example.qb.listactivityexample2;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Created by qb on 2018/7/8.
 */

public abstract class PacketParse {
    public static PacketData mPacketData = new PacketData();

    public Vector dataParse(byte[] data, int length) {
        int i;
        Vector container = new Vector();

        if (data.length < length) {
            return container;
        }

        for (i = 0; i < length; i++) {
            byteParse(container, mPacketData, data[i]);
        }
        
        return container;
    }

    private static void byteParse(Vector container, PacketData packetData, byte data) {
        //起始字节
        if (packetData.mSOI == data) {
            packetData.reset();
            packetData.mStep = 1;
            Log.i("PacketParse", "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",SOI!");
        } else {
            if (0 == packetData.mStep) {
                return;
            }

            if (packetData.mEOI == data) {
                Log.i("PacketParse", "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",EOI,"
                + "step:" + packetData.mStep + ",chk:" + packetData.mChk);
                if ((packetData.mStep >= packetData.mPacketLimitStep) && (0 == packetData.mChk)) {
                    packetData.mLen = packetData.mStep - packetData.mPacketLimitStep;
                    Log.i("PacketParse", "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",EOI,"
                            + "len:" + packetData.mLen);
                    //解析出一条完整命令
                    PacketData packetDataBak = packetData;
                    container.addElement(packetDataBak);
                    packetData = new PacketData();
                    PacketData myPacketData = (PacketData)container.get(0);
                    Log.i("PacketParse", "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",parse success!"
                    + "len:" + myPacketData.mLen + "cmdh:" + myPacketData.mCmdH + "cmdl:" + myPacketData.mCmdL + "step:" + myPacketData.mStep);
                } else {
                    packetData.mStep = 0;
                }
            }
            else if (packetData.mCfgModifyByte == data)    //遇到需要合并的字节
            {
                packetData.mModify = true;
            }
            else
            {
                if (packetData.mModify)     //判断上个字节是否收到调整标识字节
                {
                    data |= packetData.mCfgModifyByte;
                    packetData.mModify = false;
                }
                packetData.mChk ^= data;
                if (packetData.mStep >= packetData.mDataLimitStep)
                {
                    if ((packetData.mStep - packetData.mDataLimitStep) < packetData.mCfgDataBufSize)
                    {
                        packetData.mBuf.add(data);
                        packetData.mStep++;
                        Log.i("PacketParse", "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "byte:" + data);
                    }
                    else
                    {
                        packetData.mStep = 0;
                    }
                }
                else
                {
                    switch(packetData.mStep)
                    {
                        case 1:    //设备组号
                        {
                            //收到广播/符合本机的设备组号
                            packetData.mGroup = data;
                            packetData.mStep++;
                            break;
                        }
                        case 2:    //设备地址
                        {
                            packetData.mAddr = data;
                            packetData.mStep++;
                            break;
                        }
                        case 3:    //设备类型
                        {
                            packetData.mDevType = data;
                            packetData.mStep++;
                            break;
                        }
                        case 4:    //流水号
                        {
                            packetData.mSnr = data;
                            packetData.mStep++;
                            break;
                        }
                        case 5:    //命令高字节
                        {
                            packetData.mCmdH = data;
                            packetData.mStep++;
                            break;
                        }
                        case 6:
                        {
                            packetData.mCmdL = data;
                            packetData.mCmd = (packetData.mCmdH << 8) + packetData.mCmdL;
                            packetData.mStep++;
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }    //End of switch
                }    //End of else
            }    //End of else
        }
    }

    public abstract boolean checkValid(Vector container);

//    public abstract byte[] cmdProc(PacketData packetData);
}
