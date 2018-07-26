package com.example.qb.listactivityexample2;

import android.app.ActionBar;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qb.listactivityexample2.DevScan;
import com.example.qb.listactivityexample2.R;
import com.example.qb.listactivityexample2.YdtDev;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private DevListAdapter mDevListAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private DevScan mDevScan;

    // Stops scanning after 6 seconds.
    private static final long SCAN_PERIOD = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getName(), "onCreate");
        Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber());

//        setContentView(R.layout.activity_main);

        ActionBar mActionBar = getActionBar();
        if (null == mActionBar) {
            Log.i(getClass().getName(), "null");
        } else {
            Log.i(getClass().getName(), "not null");
            mActionBar.setTitle(R.string.titile_device);    //设置标题 2018
        }

        mHandler = new Handler();
        mDevScan = new DevScan();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getName(), "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getName(), "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getName(), "onResume");
        // Initializes list view adapter.
        mDevListAdapter = new DevListAdapter();
//        mDevListAdapter.addDevice(new YdtDev("yundianti", "YDT0110 V1000", "88888888", "否", "192.168.123.222", "255.255.255.0", "192.168.123.254",
//                "211.162.78.1", "192.168.123.118", "12581", "60202", "384"));
//        mDevListAdapter.addDevice(new YdtDev("云电梯1", "YDT0110 V1001", "88888887", "否", "192.168.123.221", "255.255.255.1", "192.168.123.252",
//                "211.162.78.2", "192.168.123.119", "12582", "60203", "15"));
        setListAdapter(mDevListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getName(), "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(getClass().getName(), "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mDevListAdapter.clear();
                scanDevice(true);
                break;
            case R.id.menu_stop:
                scanDevice(false);
                break;
        }
        return true;
    }

    private void scanDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    //停止扫描时，调用回调方法
                    mDevScan.stopScan(mScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            //开始扫描
            mDevScan.startScan(mScanCallback);
        } else {
            mScanning = false;
            //停止扫描
            mDevScan.stopScan(mScanCallback);
        }
        invalidateOptionsMenu();
    }

    private class DevListAdapter extends BaseAdapter {
        private ArrayList<YdtDev> mDevice;
        private LayoutInflater mInflator;

        public DevListAdapter() {
            super();
            mDevice = new ArrayList<YdtDev>();
            mInflator = MainActivity.this.getLayoutInflater();
        }

        public void addDevice(YdtDev device) {
            /*
             public String product = "产品:  ";
    public String devProduct = null;
    public String version = "版本:  ";
    public String devVersion = null;
    public String projectNum = "项目编号:  ";
    public String devProjectNum = null;
    public String dhcp = "DHCP:  ";
    public String devDhcp = null;
    public String ip = "IP地址:  ";
    public String devIp = null;
    public String mask = "子网掩码:  ";
    public String devMask = null;
    public String gateway = "网关:  ";
    public String devGateway = null;
    public String dns = "DNS:  ";
    public String devDns = null;
    public String svrIp = "一卡通IP:  ";
    public String devSvrIp = null;
    public String svrPort = "端口:  ";
    public String devSvrPort = null;
    public String ctrlPort = "协议端口:  ";
    public String devCtrlPort = null;
    public String num = "机号:  ";
    public String devNum = null;
    public String mac = "MAC:  ";
    public String devMac = null;
             */
            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "====================");
            for (YdtDev dev : mDevice){
                if (dev.product != device.product)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",product not equal");
                if (dev.devProduct != device.devProduct)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devProduct not equal");
                if (dev.version != device.version)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",version not equal");
                if (dev.devVersion != device.devVersion)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devVersion not equal");
                if (dev.projectNum != device.projectNum)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",projectNum not equal");
                if (!dev.devProjectNum.equals(device.devProjectNum))
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devProjectNum not equal");
                if (dev.devProjectNum != device.devProjectNum)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devProjectNum not equal");
                if (dev.dhcp != device.dhcp)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",dhcp not equal");
                if (dev.devDhcp != device.devDhcp)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devDhcp not equal");
                if (!dev.ip.equals(device.ip))
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",ip not equal");
                if (dev.ip != device.ip)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",ip not equal");
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devIp:"
                        + dev.devIp + ",devIp:" + device.devIp);
                if (dev.devIp != device.devIp)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devIp not equal");
                if (dev.mask != device.mask)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",mask not equal");
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devmask:"
                        + dev.devMask + ",len:" + dev.devMask.length() + "!");
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devmask:"
                        + device.devMask + ",len:" + device.devMask.length() + "!");
                if (dev.devMask != device.devMask)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMask not equal");
                else
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMask equal");
                if (dev.gateway != device.gateway)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",gateway not equal");
                if (dev.devGateway != device.devGateway)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devGateway not equal");
                if (dev.dns != device.dns)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",dns not equal");
                if (dev.devDns != device.devDns)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devDns not equal");
                if (dev.svrIp != device.svrIp)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",svrIp not equal");
                if (dev.devSvrIp != device.devSvrIp)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devSvrIp not equal");
                if (dev.svrPort != device.svrPort)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",svrPort not equal");
                if (dev.devSvrPort != device.devSvrPort)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devSvrPort not equal");
                if (dev.ctrlPort != device.ctrlPort)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",ctrlPort not equal");
                if (dev.devCtrlPort != device.devCtrlPort)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devCtrlPort not equal");
                if (dev.num != device.num)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",num not equal");
                if (dev.devNum != device.devNum)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devNum not equal");
                if (dev.mac != device.mac)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",mac not equal");
                if (dev.devMac != device.devMac)
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac not equal");
                else
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac equal");
                if (dev.devMac.equals(device.devMac))
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac equal");
                else
                    Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac not equal");
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac:"
                        + dev.devMac + ",len:" + dev.devMac.length() + "!");
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",devMac:"
                        + device.devMac + ",len:" + device.devMac.length() + "!");
            }
            if(mDevice.contains(device)){
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",contains");
            }
            else{
                Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",not contains");
            }
            if(!mDevice.contains(device)) {
                mDevice.add(device);
            }
            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "====================");
        }

        public YdtDev getDevice(int position) {
            return mDevice.get(position);
        }

        public void clear() {
            mDevice.clear();
        }

        @Override
        public int getCount() {
            return mDevice.size();
        }

        @Override
        public Object getItem(int i) {
            return mDevice.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_ydt, null);
                viewHolder = new ViewHolder();
                viewHolder.product = (TextView) view.findViewById(R.id.product);
                viewHolder.devProduct = (TextView) view.findViewById(R.id.dev_product);
                viewHolder.version = (TextView) view.findViewById(R.id.version);
                viewHolder.devVersion = (TextView) view.findViewById(R.id.dev_version);
                viewHolder.projectNum = (TextView) view.findViewById(R.id.project_num);
                viewHolder.devProjectNum = (TextView) view.findViewById(R.id.dev_project_num);
                viewHolder.dhcp = (TextView) view.findViewById(R.id.dhcp);
                viewHolder.devDhcp = (TextView) view.findViewById(R.id.dev_dhcp);
                viewHolder.ip = (TextView) view.findViewById(R.id.ip);
                viewHolder.devIp = (TextView) view.findViewById(R.id.dev_ip);
                viewHolder.mask = (TextView) view.findViewById(R.id.mask);
                viewHolder.devMask = (TextView) view.findViewById(R.id.dev_mask);
                viewHolder.gateway = (TextView) view.findViewById(R.id.gateway);
                viewHolder.devGateway = (TextView) view.findViewById(R.id.dev_gateway);
                viewHolder.dns = (TextView) view.findViewById(R.id.dns);
                viewHolder.devDns = (TextView) view.findViewById(R.id.dev_dns);
                viewHolder.svrIp = (TextView) view.findViewById(R.id.svr_ip);
                viewHolder.devSvrIp = (TextView) view.findViewById(R.id.dev_svr_ip);
                viewHolder.devSvrPort = (TextView) view.findViewById(R.id.dev_svr_port);
                viewHolder.svrPort = (TextView) view.findViewById(R.id.svr_port);
                viewHolder.devSvrPort = (TextView) view.findViewById(R.id.dev_svr_port);
                viewHolder.ctrlPort = (TextView) view.findViewById(R.id.ctrl_port);
                viewHolder.devCtrlPort = (TextView) view.findViewById(R.id.dev_ctrl_port);
                viewHolder.num = (TextView) view.findViewById(R.id.num);
                viewHolder.devNum = (TextView) view.findViewById(R.id.dev_num);
                viewHolder.mac = (TextView) view.findViewById(R.id.mac);
                viewHolder.devMac = (TextView) view.findViewById(R.id.dev_mac);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            YdtDev device = mDevice.get(i);
//            final String deviceName = device.getName();
//            if (deviceName != null && deviceName.length() > 0)
//                viewHolder.deviceName.setText(deviceName);
//            else
//                viewHolder.deviceName.setText(R.string.unknown_device);
//            viewHolder.deviceAddress.setText(device.getAddress());

            final String product = device.getProduct();
            if (product != null && product.length() > 0)
                viewHolder.setTextParam(viewHolder.product, product, Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            else{
//                viewHolder.setTextParam(viewHolder.product, R.string.unknown_device, Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
                viewHolder.product.setText(product);
                viewHolder.product.setTextColor(Color.RED);    //红色字体
                viewHolder.product.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));    //加粗
            }

//            viewHolder.product.setTextColor(Color.RED);    //红色字体
//            viewHolder.product.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));    //加粗
            viewHolder.devProduct.setText(device.getDevProduct());
//            viewHolder.version.setText(device.getVersion());
            viewHolder.setTextParam(viewHolder.version, device.getVersion(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devVersion.setText(device.getDevVersion());
//            viewHolder.projectNum.setText(device.getProjectNum());
            viewHolder.setTextParam(viewHolder.projectNum, device.getProjectNum(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devProjectNum.setText(device.getDevProjectNum());
            viewHolder.dhcp.setText(device.getDhcp());
            viewHolder.setTextParam(viewHolder.dhcp, device.getDhcp(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devDhcp.setText(device.getDevDhcp());
//            viewHolder.ip.setText(device.getIp());
            viewHolder.setTextParam(viewHolder.ip, device.getIp(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devIp.setText(device.getDevIp());
//            viewHolder.mask.setText(device.getMask());
            viewHolder.setTextParam(viewHolder.mask, device.getMask(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devMask.setText(device.getDevMask());
//            viewHolder.gateway.setText(device.getGateway());
            viewHolder.setTextParam(viewHolder.gateway, device.getGateway(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devGateway.setText(device.getDevGateway());
//            viewHolder.dns.setText(device.getDns());
            viewHolder.setTextParam(viewHolder.dns, device.getDns(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devDns.setText(device.getDevDns());
//            viewHolder.svrIp.setText(device.getSvrIp());
            viewHolder.setTextParam(viewHolder.svrIp, device.getSvrIp(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devSvrIp.setText(device.getDevSvrIp());
//            viewHolder.svrPort.setText(device.getSvrPort());
            viewHolder.setTextParam(viewHolder.svrPort, device.getSvrPort(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devSvrPort.setText(device.getDevSvrPort());
//            viewHolder.ctrlPort.setText(device.getCtrlPort());
            viewHolder.setTextParam(viewHolder.ctrlPort, device.getCtrlPort(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devCtrlPort.setText(device.getDevCtrlPort());
//            viewHolder.num.setText(device.getNum());
            viewHolder.setTextParam(viewHolder.num, device.getNum(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devNum.setText(device.getDevNum());
            viewHolder.setTextParam(viewHolder.mac, device.getMac(), Color.RED, Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.devMac.setText(device.getDevMac());
            return view;
        }
    }

    static class ViewHolder {
        TextView product;
        TextView devProduct;
        TextView version;
        TextView devVersion;
        TextView projectNum;
        TextView devProjectNum;
        TextView dhcp;
        TextView devDhcp;
        TextView ip;
        TextView devIp;
        TextView mask;
        TextView devMask;
        TextView gateway;
        TextView devGateway;
        TextView dns;
        TextView devDns;
        TextView svrIp;
        TextView devSvrIp;
        TextView svrPort;
        TextView devSvrPort;
        TextView ctrlPort;
        TextView devCtrlPort;
        TextView num;
        TextView devNum;
        TextView mac;
        TextView devMac;

        public static void setTextParam(TextView view, CharSequence text, @ColorInt int color, Typeface tf){
            view.setText(text);
            view.setTextColor(color);    //红色字体
            view.setTypeface(tf);    //加粗
        }
    }

    // Device scan callback.
    private DevScan.ScanCallback mScanCallback =
            new DevScan.ScanCallback() {

                @Override
                public void onScan(final YdtDev device) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(getClass().getName(), "line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ",onScan!");
                            mDevListAdapter.addDevice(device);
                            mDevListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
}
