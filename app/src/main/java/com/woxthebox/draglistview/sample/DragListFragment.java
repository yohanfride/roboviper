/*
  Copyright 2014 Magnus Woxblom
  <p/>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p/>
  http://www.apache.org/licenses/LICENSE-2.0
  <p/>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.woxthebox.draglistview.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;

import visualprogammer.Files;
import visualprogammer.ServiceBluetooth;
import visualprogammer.Var;

import java.util.ArrayList;

import static visualprogammer.Var.hexToByte;

public class DragListFragment extends Fragment {

    private ArrayList<Pair<Long, String>> mItemArray;
    private static DragListView mDragListView;
    private ListSwipeHelper mSwipeHelper;
    private MySwipeRefreshLayout mRefreshLayout;
    public static int lastId=0;
    public static String FileName = "";

    public static DragListFragment newInstance() {
        return new DragListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drag_list_layout2, container, false);
        Var.lastFragment = "Drag List";
        if(Var.fileName.isEmpty())
            FileName = "Roboviper Canvas";
        else
            FileName = Var.fileName;

        mRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setEnabled(false);
        mDragListView = (DragListView) view.findViewById(R.id.drag_list_view);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {

            @Override
            public void onItemClick(int position) {
                mRefreshLayout.setEnabled(false);
                Var.indexModule = position;
                Intent intent = new Intent(getActivity(), PopUp.class);
                startActivity(intent);
            }

            @Override
            public void onItemDragStarted(int position) {
                mRefreshLayout.setEnabled(false);
                //Toast.makeText(mDragListView.getContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                mRefreshLayout.setEnabled(false);
                if (fromPosition != toPosition) {
                    Var.order(fromPosition,toPosition);
                    //Toast.makeText(mDragListView.getContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mItemArray = new ArrayList<>();
        //mItemArray.add(new Pair<>((long) 0, "@drawable/ic_keyboard_arrow_up_black_24dp"));
        lastId = -1;

        for(int i = 0; i<Var.activeBlocks.size(); i++){
            mItemArray.add(new Pair<>((long) ++lastId, Var.activeBlocks.get(i).iconDrag));
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
        setupGridHorizontalRecyclerView();

        Button but1=(Button) view.findViewById(R.id.button_forward);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addList("@drawable/xforwardbox",Var.FORWARD_ID);
            }
        });

        Button but2=(Button) view.findViewById(R.id.button_backward);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xbackwardbox",Var.REVERSE_ID);
            }
        });

        Button but3=(Button) view.findViewById(R.id.button_left);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xturnleftbox",Var.TLEFT_ID);
            }
        });

        Button but4=(Button) view.findViewById(R.id.button_right);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xturnrightbox",Var.TRIGHT_ID);
            }
        });

        Button but5=(Button) view.findViewById(R.id.button_delay);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xdelaybox",Var.DELAY_ID);
            }
        });

        Button but6=(Button) view.findViewById(R.id.button_gripper);
        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xgripperbox",Var.GRIPPER_ID);
            }
        });

        Button but7=(Button) view.findViewById(R.id.button_sound);
        but7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xsoundbox",Var.SOUND_ID);
            }
        });

        Button but8=(Button) view.findViewById(R.id.button_display);
        but8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xdisplaybox",Var.LCD_ID);
            }
        });

        Button but10=(Button) view.findViewById(R.id.button_linefol);
        but10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList("@drawable/xlinefollbox",Var.LFOLLOWER_ID);
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //menu.findItem(R.id.action_disable_drag).setVisible(mDragListView.isDragEnabled());
        //menu.findItem(R.id.action_enable_drag).setVisible(!mDragListView.isDragEnabled());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drag_menu_Code:
                Intent intent = new Intent(getActivity(), PopUpSourceCode.class);
                startActivity(intent);
                return true;
            case R.id.drag_menu_Send:
                Var.isExiting = false;
                if(Var.BlAddress.isEmpty()) {
                    Intent intent2 = new Intent(getActivity(), DeviceListActivity.class);
                    startActivity(intent2);
                } else {
                    deploy();
                }
                return true;
            case R.id.drag_menu_Save_File:
                if(Var.fileName.isEmpty()) {
                    Intent intent3 = new Intent(getActivity(), SaveDialogActivity.class);
                    startActivity(intent3);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                } else {
                    saveFile();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                }
                return true;
            case R.id.drag_menu_New_File:
                if( (Var.isSaved == false)  ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirm");
                    builder.setMessage("Do you want previous save program?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Var.isNew=true;
                            if( (Var.fileName.isEmpty())  ){
                                Intent intent = new Intent(getContext(), SaveDialogActivity.class);
                                startActivity(intent);
                                for(int i=0; i<=lastId; i++)
                                    mDragListView.getAdapter().removeItem(0);
                                lastId = -1;
                                Var.isSaved = true;
                                Var.fileName = "";
                                FileName = "Roboviper Canvas";
                                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                            } else {
                                saveFile();
                                Var.activeBlocks.clear();
                                for(int i=0; i<=lastId+1; i++)
                                    mDragListView.getAdapter().removeItem(0);
                                lastId = -1;
                                Var.isSaved = true;
                                Var.fileName = "";
                                FileName = "Roboviper Canvas";
                                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                            }
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Var.fileName = "";
                            Var.activeBlocks.clear();
                            for(int i=0; i<=lastId+1; i++)
                                mDragListView.getAdapter().removeItem(0);
                            lastId = -1;
                            Var.isSaved = true;
                            FileName = "Roboviper Canvas";
                            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Var.fileName = "";
                    Var.activeBlocks.clear();
                    for(int i=0; i<=lastId+1; i++)
                        mDragListView.getAdapter().removeItem(0);
                    lastId = -1;
                    FileName = "Roboviper Canvas";
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void saveFile(){
        Files f= new Files();
        Modules m=new Modules(0,"");
        String temp = "";
        for(int i=0; i<Var.activeBlocks.size(); i++){
            temp+=m.extractDataSave(Var.activeBlocks.get(i))+";";
        }
        f.saveData(temp, Var.dataPath+Var.fileName+".txt");
        f.saveData(temp, Var.outputPath+Var.fileName+".hex");
        Toast.makeText(mDragListView.getContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    public String compileData(String data){

        String[] programData=data.split(";");
        String hexData="";

        for(int i=0; i<programData.length; i++){
            String[] blockData;
            blockData=programData[i].split(",");

            Modules m=new Modules(0,"");

            if(blockData[0].equalsIgnoreCase(m.fwd1)){
                hexData+="ff"+"000000"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.fwd2)){
                hexData+="ff"+"000001"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.wfollower1)){
                if(blockData[1].equals(m.wfgaris))
                    hexData+="ff"+"040100"+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[3]))+decimalToHex(Integer.parseInt(blockData[4]));
                else
                    hexData+="ff"+"040101"+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[3]))+decimalToHex(Integer.parseInt(blockData[4]));
            }
            else if(blockData[0].equalsIgnoreCase(m.wfollower2)){
                if(blockData[1].equals(m.wfgaris))
                    hexData+="ff"+"040000"+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[3]))+decimalToHex(Integer.parseInt(blockData[4]));
                else
                    hexData+="ff"+"040001"+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[3]))+decimalToHex(Integer.parseInt(blockData[4]));
            }
            else if(blockData[0].equalsIgnoreCase(m.lfollower1)){
                hexData+="ff"+"0502"+blockData[1]+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.lfollower2)){
                hexData+="ff"+"0500"+blockData[1]+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.lfollower3)){
                hexData+="ff"+"0501"+blockData[1]+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.reverse1)){
                hexData+="ff"+"000100"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.reverse2)){
                hexData+="ff"+"000101"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.tright1)){
                hexData+="ff"+"000200"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.tright2)){
                hexData+="ff"+"000201"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.tleft1)){
                hexData+="ff"+"000300"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.tleft2)){
                hexData+="ff"+"000301"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[2]));
            }
            else if(blockData[0].equalsIgnoreCase(m.lcd)){
                int num=Integer.parseInt(blockData[1]);
                String text=blockData[2];

                hexData+="ff"+"08"+decimalToHex(Integer.parseInt(blockData[1]));
                for (int x = 0; x < num; x++) {
                    hexData+= asciiData(text.charAt(x));
                }
            }
            else if(blockData[0].equalsIgnoreCase(m.sMonostable)){
                hexData+="ff"+"07"+"00"+decimalToHex(Integer.parseInt(blockData[1]));
            }
            else if(blockData[0].equalsIgnoreCase(m.sAstable)){
                hexData+="ff"+"07"+"01"+decimalToHex(Integer.parseInt(blockData[1]))+decimalToHex(Integer.parseInt(blockData[2]))+decimalToHex(Integer.parseInt(blockData[3]));
            }
            else if(blockData[0].equalsIgnoreCase(m.sMario)){
                hexData+="ff"+"07"+"02";
            }
            else if(blockData[0].equalsIgnoreCase(m.delay)){
                hexData+="ff"+"09"+decimalToHex(Integer.parseInt(blockData[1]));
            }
            else if(blockData[0].equalsIgnoreCase(m.gripper1)){
                hexData+="ff"+"06"+"02"+"00";
            }
            else if(blockData[0].equalsIgnoreCase(m.gripper2)){
                hexData+="ff"+"06"+"02"+"01";
            }
        }
        return hexData;
    }


    private void deploy(){
        Modules m=new Modules(0,"");
        String temp = "";
        for(int i=0; i<Var.activeBlocks.size(); i++){
            temp+=m.extractDataSave(Var.activeBlocks.get(i))+";";
        }
        String data=compileData(temp);
        try {
            Toast.makeText(mDragListView.getContext(), data, Toast.LENGTH_LONG).show();
            sendMessage1(hexToByte(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupGridHorizontalRecyclerView() {
        mDragListView.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.drag_grid_item, R.id.item_layout, true);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(true);
        mDragListView.setCustomDragItem(null);
    }

    private static class MyDragItem extends DragItem {

        MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            CharSequence text = ((TextView) clickedView.findViewById(R.id.text)).getText();
            ((TextView) dragView.findViewById(R.id.text)).setText(text);
            dragView.findViewById(R.id.item_layout).setBackgroundColor(dragView.getResources().getColor(R.color.list_item_background));
        }
    }

    public void addList(String text, int idMod) {
        Var.isSaved = false;
        if(lastId<0){
            mItemArray.add(new Pair<>((long) ++lastId, text));
            setupGridHorizontalRecyclerView();
        } else {
            mDragListView.getAdapter().addItem(++lastId, new Pair<>((long) lastId, text));
        }
        Var.activeBlocks.add(new Modules(idMod,text));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
        Var.DragItem = mDragListView;
        Var.LastID = lastId;
    }

    public void onResume(){
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
    }

    public static void setup() {
        Var.service_data_io = new ServiceBluetooth(mDragListView.getContext(), mHandler);
        Var.output_string = new StringBuffer("");
    }
    public void connect(){
        Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
        startActivityForResult(serverIntent, Var.REQUEST_CONNECT_DEVICE);
    }
    public void onDestroy() {
        super.onDestroy();
        if (Var.service_data_io != null) Var.service_data_io.stop();
    }
    private void sendMessage1(byte[] message) {
        if (Var.service_data_io.getState() != ServiceBluetooth.STATE_CONNECTED) {
            Toast.makeText(mDragListView.getContext(), "failed to deliver data", Toast.LENGTH_SHORT).show();
            return;
        }
        Var.service_data_io.write(message);
        Var.output_string.setLength(0);
    }
    private void sendMessage(String message) {
        if (Var.service_data_io.getState() != ServiceBluetooth.STATE_CONNECTED) {
            Toast.makeText(mDragListView.getContext(), "failed to deliver data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            Var.service_data_io.write(send);
            Var.output_string.setLength(0);
        }
    }
    private String decimalToHex(int a) {
        String h = Integer.toHexString(a);
        if (h.length() < 2) {
            h = "0" + h;
        }
        return h;
    }
    private String asciiData(char c){
        int a=(int)c;
        if(a>=0 && (a+40) < 255){
            return decimalToHex(a);
        }
        return "";
    }

    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Var.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case ServiceBluetooth.STATE_CONNECTED:
                            //stat.setText("Tersambung ke : "+Var.namadevice_connect);
                            break;
                        case ServiceBluetooth.STATE_CONNECTING:
                            //stat.setText(R.string.title_connecting);
                            break;
                        case ServiceBluetooth.STATE_LISTEN:
                        case ServiceBluetooth.STATE_NONE:
                            //stat.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Var.MESSAGE_DEVICE_NAME:

                    Var.namadevice_connect = msg.getData().getString(Var.DEVICE_NAME);
                    Toast.makeText(mDragListView.getContext(), "Tersambung ke "
                            + Var.namadevice_connect, Toast.LENGTH_SHORT).show();
                    break;

                case Var.MESSAGE_READ:

                    break;

                case Var.MESSAGE_TOAST:
                    Toast.makeText(mDragListView.getContext(), msg.getData().getString(Var.TOAST),
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
                    Toast.makeText(mDragListView.getContext(), R.string.bt_not_enabled+" 2", Toast.LENGTH_SHORT).show();
                }
                break;
            case Var.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(mDragListView.getContext(), R.string.bt_not_enabled+" 1", Toast.LENGTH_SHORT).show();
                    setup();
                } else {
                    Toast.makeText(mDragListView.getContext(), R.string.bt_not_enabled, Toast.LENGTH_SHORT).show();
                    //finish();
                }
        }
    }

}
