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

import static com.woxthebox.draglistview.sample.DragListFragment.saveFile;

public class MainActivity extends AppCompatActivity {
    public Texts stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button btnBlok = (Button) findViewById(R.id.main_button_blok);
        btnBlok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Var.activeBlocks.clear();
                Var.fileName="";
                showFragment(DragListFragment.newInstance());
                getSupportActionBar().show();
            }
        });
        Button btnBlue = (Button) findViewById(R.id.main_button_bluetooth);
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DeviceListActivity.class);
                startActivity(intent);
            }
        });
        if (savedInstanceState == null) {
            //showFragment(DragListFragment.newInstance());
        }
        Button btnFile = (Button) findViewById(R.id.main_button_file);
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Var.activeBlocks.clear();
                Intent intent = new Intent(getApplication(), OpenFileActivity.class);
                startActivity(intent);
            }
        });
        Button btnManual = (Button) findViewById(R.id.main_button_manual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Var.activeBlocks.clear();
                Var.fileName="";
                Intent intent = new Intent(getApplication(), ManualActivity.class);
                startActivity(intent);
            }
        });
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
        Var.isExiting = true;
        getSupportActionBar().setTitle(R.string.app_name);
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
        getSupportActionBar().hide();
        Var.isSaved = true;
        Var.lastFragment = "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if( (Var.isSaved == false)  ){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want previous save program?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if( (Var.fileName.isEmpty())  ){
                            Intent intent = new Intent(getApplicationContext(), SaveDialogActivity.class);
                            startActivity(intent);
                        } else {
                            saveFile();
                        }
                        onBackPressed();
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
                getSupportActionBar().hide();
                super.onBackPressed();
                getSupportActionBar().setTitle(R.string.app_name);
                Var.lastFragment = "";
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
