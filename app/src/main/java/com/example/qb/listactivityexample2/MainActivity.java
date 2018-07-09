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
            if(!mDevice.contains(device)) {
                mDevice.add(device);
            }
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
