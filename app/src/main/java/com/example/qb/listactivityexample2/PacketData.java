package com.example.qb.listactivityexample2;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by qb on 2018/7/8.
 */

public final class PacketData {
    public final byte mSOI = (byte) 0xF2;       //起始字节
    public byte mGroup;     //组号
    public byte mAddr;      //地址
    public byte mDevType;    //设备类型
    public final byte mFinalDevType = (byte) 0x21;   //设备类型
    public byte mSnr;       //流水号
    public byte mCmdH;      //命令高字节
    public byte mCmdL;      //命令低字节
    public int mCmd;        //命令
    public byte mChk;       //异或校验字节
    public int mLen;       //包的传输数据长度
    public int mStep = 0;      //步长
    public final byte mEOI = (byte) 0xF3;       //结束字节
    public boolean mModify = false; //标志是否需要调整
    public int mDevNum;    //设备机号
        public  Vector mBuf = new Vector();
//    public ArrayList<Character> mBuf = new ArrayList<char>();

    public final int mPacketLimitStep = 8;
    public final int mDataLimitStep = 7;
    public final byte mCfgModifyByte = (byte) 0xF0;   //>=0xF0字节需要合并/拆分
    public final int mCfgDataBufSize = 1000000;

//    public final  int mCmdGetDeviceInfo = 0x1000;

    public PacketData() {
        reset();
    }

    public void reset() {
        mGroup = 0;
        mAddr = 0;
        mDevType = 0;
        mSnr = 0;
        mCmdH = 0;
        mCmdL = 0;
        mLen = 0;
        mStep = 0;
        mModify = false;
        mDevNum = 0;
    }
}
