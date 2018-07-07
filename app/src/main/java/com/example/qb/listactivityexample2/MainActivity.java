package com.example.ListActivityExample2.listactivityexample2;

import android.app.ActionBar;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
        mDevListAdapter.addDevice(new MyDev());
        mDevListAdapter.addDevice(new MyDev("qb","深圳市"));
        mDevListAdapter.addDevice(new MyDev("my_name", "guangzhou市"));
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
    private class DevListAdapter extends BaseAdapter {
        private ArrayList<MyDev> mDevice;
        private LayoutInflater mInflator;

        public DevListAdapter() {
            super();
            mDevice = new ArrayList<MyDev>();
            mInflator = MainActivity.this.getLayoutInflater();
        }

        public void addDevice(MyDev device) {
            if(!mDevice.contains(device)) {
                mDevice.add(device);
            }
        }

        public MyDev getDevice(int position) {
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
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            MyDev device = mDevice.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

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

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }

    public class MyDev{
        public String devName = "MyDeviceName";
        public String devAddress = "MyDeviceAddress";

        public MyDev() {

        }

        public MyDev(String name, String address)
        {
            devName = name;
            devAddress = address;
        }

        public String getName() {
            return devName;
        }

        public String getAddress() {
            return devAddress;
        }
    }
}
