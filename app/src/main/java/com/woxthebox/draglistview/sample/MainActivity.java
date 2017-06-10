/**
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woxthebox.draglistview.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import visualprogammer.*;

public class MainActivity extends AppCompatActivity {
    public Texts stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnBlok = (Button) findViewById(R.id.main_button_blok);
        btnBlok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Var.activeBlocks.clear();
                showFragment(DragListFragment.newInstance());
            }
        });
        if (savedInstanceState == null) {
            //showFragment(DragListFragment.newInstance());
        }
        Var.lastFragment = "";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_color)));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("frament");
        transaction.add(R.id.container, fragment);
        transaction.commit();
        //transaction.replace(R.id.container, fragment, "fragment").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //boolean listFragment = getSupportFragmentManager().findFragmentByTag("fragment") instanceof DragListFragment;
        menu.findItem(R.id.action_lists).setVisible(false);
        menu.findItem(R.id.action_board).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lists:
                showFragment(DragListFragment.newInstance());
                return true;
            case R.id.action_board:
                showFragment(DragListFragment.newInstance());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
        Var.lastFragment = "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(Var.isSaved == false){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want previous save program?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onBackPressed();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                super.onBackPressed();
                Var.lastFragment = "";
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setup() {
        Var.service_data_io = new ServiceBluetooth(this, mHandler);
        Var.output_string = new StringBuffer("");
    }
    public void connect(){
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, Var.REQUEST_CONNECT_DEVICE);
    }
    public void onDestroy() {
        super.onDestroy();
        if (Var.service_data_io != null) Var.service_data_io.stop();
    }
    private void sendMessage1(byte[] message) {
        if (Var.service_data_io.getState() != ServiceBluetooth.STATE_CONNECTED) {
            Toast.makeText(this, "Pengiriman data gagal", Toast.LENGTH_SHORT).show();
            return;
        }
        Var.service_data_io.write(message);
        Var.output_string.setLength(0);
    }
    private void sendMessage(String message) {
        if (Var.service_data_io.getState() != ServiceBluetooth.STATE_CONNECTED) {
            Toast.makeText(this, "Pengiriman data gagal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            Var.service_data_io.write(send);
            Var.output_string.setLength(0);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Var.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case ServiceBluetooth.STATE_CONNECTED:
                            stat.setText("Tersambung ke : "+Var.namadevice_connect);
                            break;
                        case ServiceBluetooth.STATE_CONNECTING:
                            stat.setText(R.string.title_connecting);
                            break;
                        case ServiceBluetooth.STATE_LISTEN:
                        case ServiceBluetooth.STATE_NONE:
                            stat.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Var.MESSAGE_DEVICE_NAME:

                    Var.namadevice_connect = msg.getData().getString(Var.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Tersambung ke "
                            + Var.namadevice_connect, Toast.LENGTH_SHORT).show();
                    break;

                case Var.MESSAGE_READ:

                    break;

                case Var.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(Var.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Var.REQUEST_CONNECT_DEVICE:

                if (resultCode == Activity.RESULT_OK) {

                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    ///Konek Bluetooth
                    BluetoothDevice device = Var.bluetooth_adapter.getRemoteDevice(address);
                    Var.service_data_io.connect(device);
                }
                break;
            case Var.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setup();
                } else {
                    Toast.makeText(this, R.string.bt_not_enabled, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}
