

package com.woxthebox.draglistview.sample;

import java.util.Set;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import visualprogammer.Var;


public class DeviceListActivity extends Activity {

    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;
    public TextView titleSearch;
    public Button scanButton;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.bluetooth_list);

        setResult(Activity.RESULT_CANCELED);
        titleSearch=(TextView) findViewById(R.id.title_paired_devices);

        scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                titleSearch.setText(R.string.title_other_devices);
                doDiscovery();
                //v.setVisibility(View.GONE);
            }
        });

        //mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);


        /*ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);*/

        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Var.bluetooth_adapter = BluetoothAdapter.getDefaultAdapter();
        if (Var.bluetooth_adapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }

        /*IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);


        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();


        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();


        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                titleSearch.setText(R.string.title_other_devices);
            }
        } else {
            titleSearch.setText("Tidak ditemukan perangkat baru");
            scanButton.setVisibility(View.VISIBLE);
            //String noDevices = getResources().getText(R.string.none_paired).toString();
            //mPairedDevicesArrayAdapter.add(noDevices);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }


        //this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        //if (D) Log.d(TAG, "doDiscovery()");
        //setProgressBarIndeterminateVisibility(true);
        //setTitle(R.string.scanning);

        //findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        mBtAdapter.startDiscovery();
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //Finding devices
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

    }


    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            mBtAdapter.cancelDiscovery();


            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Var.namadevice_connect = info;
            Var.BlAddress = address;

            //Intent intent = new Intent();
            //intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            //setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    titleSearch.setText(R.string.title_other_devices);
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    titleSearch.setText("Tidak ditemukan perangkat baru");
                    scanButton.setVisibility(View.VISIBLE);
                    //String noDevices = getResources().getText(R.string.none_found).toString();
                    //mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

}
