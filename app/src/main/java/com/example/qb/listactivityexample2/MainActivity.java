package com.example.ListActivityExample2.listactivityexample2;

import android.app.ActionBar;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.example.qb.listactivityexample2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private DevListAdapter mDevListAdapter;
    private boolean mScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ListActivityExample2", "onCreate");

//        setContentView(R.layout.activity_main);

        ActionBar mActionBar = getActionBar();
        if (null == mActionBar) {
            Log.d("ListActivityExample2", "null");
        } else {
            Log.d("ListActivityExample2", "not null");
            mActionBar.setTitle(R.string.titile_device);    //设置标题 2018
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ListActivityExample2", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ListActivityExample2", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ListActivityExample2", "onResume");
        // Initializes list view adapter.
        mDevListAdapter = new DevListAdapter();
//        mDevListAdapter.addDevice(new MyDev());
//        mDevListAdapter.addDevice(new MyDev("qb","深圳市"));
//        mDevListAdapter.addDevice(new MyDev("my_name", "guangzhou市"));
        mDevListAdapter.addDevice(new YdtDev("yundianti", "YDT0110 V1000", "88888888", "否", "192.168.123.222", "255.255.255.0", "192.168.123.254",
                "211.162.78.1", "192.168.123.118", "12581", "60202", "384"));
        mDevListAdapter.addDevice(new YdtDev("云电梯1", "YDT0110 V1001", "88888887", "否", "192.168.123.221", "255.255.255.1", "192.168.123.252",
                "211.162.78.2", "192.168.123.119", "12582", "60203", "15"));
        setListAdapter(mDevListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ListActivityExample2", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ListActivityExample2", "onRestart");
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
//                scanLeDevice(true);
                break;
            case R.id.menu_stop:
//                scanLeDevice(false);
                break;
        }
        return true;
    }

    //Adapter for holding devices found through scanning.
//    private class DevListAdapter extends BaseAdapter {
//        private ArrayList<MyDev> mDevice;
//        private LayoutInflater mInflator;
//
//        public DevListAdapter() {
//            super();
//            mDevice = new ArrayList<MyDev>();
//            mInflator = MainActivity.this.getLayoutInflater();
//        }
//
//        public void addDevice(MyDev device) {
//            if(!mDevice.contains(device)) {
//                mDevice.add(device);
//            }
//        }
//
//        public MyDev getDevice(int position) {
//            return mDevice.get(position);
//        }
//
//        public void clear() {
//            mDevice.clear();
//        }
//
//        @Override
//        public int getCount() {
//            return mDevice.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return mDevice.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder viewHolder;
//            // General ListView optimization code.
//            if (view == null) {
//                view = mInflator.inflate(R.layout.listitem_device, null);
//                viewHolder = new ViewHolder();
//                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
//                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
//                view.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) view.getTag();
//            }
//
//            MyDev device = mDevice.get(i);
//            final String deviceName = device.getName();
//            if (deviceName != null && deviceName.length() > 0)
//                viewHolder.deviceName.setText(deviceName);
//            else
//                viewHolder.deviceName.setText(R.string.unknown_device);
//            viewHolder.deviceAddress.setText(device.getAddress());
//
//            return view;
//        }
//    }

    // Device scan callback.
//    private BluetoothAdapter.LeScanCallback mLeScanCallback =
//            new BluetoothAdapter.LeScanCallback() {
//
//                @Override
//                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mLeDeviceListAdapter.addDevice(device);
//                            mLeDeviceListAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            };

//    static class ViewHolder {
//        TextView deviceName;
//        TextView deviceAddress;
//    }
//
//    public class MyDev{
//        public String devName = "MyDeviceName";
//        public String devAddress = "MyDeviceAddress";
//
//        public MyDev() {
//
//        }
//
//        public MyDev(String name, String address)
//        {
//            devName = name;
//            devAddress = address;
//        }
//
//        public String getName() {
//            return devName;
//        }
//
//        public String getAddress() {
//            return devAddress;
//        }
//    }

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
            device.getRandomNum();
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

        public static void setTextParam(TextView view, CharSequence text, @ColorInt int color, Typeface tf){
            view.setText(text);
            view.setTextColor(color);    //红色字体
            view.setTypeface(tf);    //加粗
        }
    }
    public class YdtDev{
        public String product = "产品:  ";
        public String devProduct;
        public String version = "版本:  ";
        public String devVersion;
        public int randomNum = (int) ((Math.random() * 9 + 1) * 100000);
        public String projectNum = "项目编号:  ";
        public String devProjectNum;
        public String dhcp = "DHCP:  ";
        public String devDhcp;
        public String ip = "IP地址:  ";
        public String devIp;
        public String mask = "子网掩码:  ";
        public String devMask;
        public String gateway = "网关:  ";
        public String devGateway;
        public String dns = "DNS:  ";
        public String devDns;
        public String svrIp = "一卡通IP:  ";
        public String devSvrIp;
        public String svrPort = "端口:  ";
        public String devSvrPort;
        public String ctrlPort = "协议端口:  ";
        public String devCtrlPort;
        public String num = "机号:  ";
        public String devNum;

        public YdtDev(){

        }

        public YdtDev(String devProduct, String devVersion, String devProjectNum, String devDhcp, String devIp, String devMask,
                      String devGateway, String devDns, String devSvrIp, String devSvrPort, String devCtrlPort, String devNum) {
            this.devProduct = devProduct;
            this.devVersion = devVersion;
            this.devProjectNum = devProjectNum;
            this.devDhcp = devDhcp;
            this.devIp = devIp;
            this.devMask = devMask;
            this.devGateway = devGateway;
            this.devDns = devDns;
            this.devSvrIp = devSvrIp;
            this.devSvrPort =devSvrPort;
            this.devCtrlPort = devCtrlPort;
            this.devNum = devNum;
            Log.d("ListActivityExample2", "randomNum:" + randomNum);
            Log.d("ListActivityExample2", "product:" + product);
            Log.d("ListActivityExample2", "devProduct:" + devProduct);
            Log.d("ListActivityExample2", "devProduct:" + devProduct);
            Log.d("ListActivityExample2", "devVersion:" + devVersion);
        }

        public int getRandomNum() {
            Log.d("ListActivityExample2", "randomNum:" + randomNum);
            return randomNum;
        }
        public String getProduct() {
            Log.d("ListActivityExample2", "product:" + product);
            return product;
        }

        public String getDevProduct() {
            Log.d("ListActivityExample2", "devProduct:" + devProduct);
            return devProduct;
        }

        public String getVersion() {
            Log.d("ListActivityExample2", "version:" + version);
            return version;
        }
        public String getDevVersion() {
            Log.d("ListActivityExample2", "devVersion:" + devVersion);
            return devVersion;
        }

        public String getProjectNum() {
            return projectNum;
        }

        public String getDevProjectNum() {
            return devProjectNum;
        }

        public String getDhcp() {
            return dhcp;
        }

        public String getDevDhcp() {
            return devDhcp;
        }

        public String getIp() {
            return ip;
        }

        public String getDevIp() {
            return devIp;
        }

        public String getMask() {
            return mask;
        }

        public String getDevMask() {
            return devMask;
        }

        public String getGateway() {
            return gateway;
        }

        public String getDevGateway() {
            return devGateway;
        }

        public String getDns() {
            return dns;
        }

        public String getDevDns() {
            return devDns;
        }

        public String getSvrIp() {
            return svrIp;
        }

        public String getDevSvrIp() {
            return devSvrIp;
        }

        public String getSvrPort() {
            return svrPort;
        }

        public String getDevSvrPort() {
            return devSvrPort;
        }

        public String getCtrlPort() {
            return ctrlPort;
        }

        public String getDevCtrlPort() {
            return devCtrlPort;
        }

        public String getNum() {
            return num;
        }

        public String getDevNum() {
            return devNum;
        }
    }
}
