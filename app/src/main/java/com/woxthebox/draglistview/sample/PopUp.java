package com.woxthebox.draglistview.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.List;

import visualprogammer.Var;


/**
 * Created by Kaze on 5/15/2017.
 */

public class PopUp extends Activity{
    Spinner spinner;
    int TipeNow;
    TextView text1,text2;
    Modules BlockNow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BlockNow = Var.activeBlocks.get(Var.indexModule);
        TipeNow = BlockNow.idModules;
        if(TipeNow == Var.FORWARD_ID){
            forward();
        } else if(TipeNow == Var.REVERSE_ID){
            backward();
        } else if(TipeNow == Var.TLEFT_ID){
            turnLeft();
        } else if(TipeNow == Var.TRIGHT_ID){
            turnRight();
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.85),(int)(height*.8));
    }

    private void forward(){
        setContentView(R.layout.drag_pop_up_forward);
        text1 = (TextView) findViewById(R.id.textViewAtas);
        text2 = (TextView) findViewById(R.id.textViewBawah);
        TextView blocks_text = (TextView) findViewById(R.id.Blocks);
        blocks_text.setText("Block number: "+(Var.indexModule+1));
        ImageButton btnDelete = (ImageButton) findViewById(R.id.deleteButton);
        //Spinner
        addItemsOnSpinner_forward();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(PopUp.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Var.DragItem.getAdapter().removeItem(Var.indexModule);
                        DragListFragment.lastId--;
                        Var.removeBlock(Var.indexModule);
                        finish();
                    }});
                adb.show();

            }

        });
        //End Spinner
        EditText value1 = (EditText) findViewById(R.id.Value1);
        value1.setText(BlockNow.fvalue);
        value1.addTextChangedListener(new  TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                BlockNow.fvalue = s.toString();
            }
        });
        //End Edit
        SeekBar velocity = (SeekBar) findViewById(R.id.VeloseekBar);
        velocity.setProgress(new Integer(BlockNow.fspeed));
        velocity.refreshDrawableState();
        velocity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        String test = seekBar.getProgress()+"";
                        BlockNow.fspeed = test;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser){
                    }

                }
        );
        //End SeekBar
    }

    public void addItemsOnSpinner_forward() {

        spinner = (Spinner) findViewById(R.id.change_mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 0){
                    text1.setText("cm");
                    text2.setText("(5-100)");
                    BlockNow.tipeData = BlockNow.fwd2;
                }else {
                    text1.setText("sec");
                    text2.setText("(1-100)");
                    BlockNow.tipeData = BlockNow.fwd1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Distance");
        list.add("Time Limit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        int a;
        if(BlockNow.tipeData == BlockNow.fwd2) a = 0;
        else a = 1;
        spinner.setSelection(a);
    }

    private void backward(){
        setContentView(R.layout.drag_pop_up_backward);
        text1 = (TextView) findViewById(R.id.textViewAtas);
        text2 = (TextView) findViewById(R.id.textViewBawah);
        TextView blocks_text = (TextView) findViewById(R.id.Blocks);
        blocks_text.setText("Block number: "+(Var.indexModule+1));
        ImageButton btnDelete = (ImageButton) findViewById(R.id.deleteButton);
        //Spinner
        addItemsOnSpinner_backward();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(PopUp.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Var.DragItem.getAdapter().removeItem(Var.indexModule);
                        DragListFragment.lastId--;
                        Var.removeBlock(Var.indexModule);
                        finish();
                    }});
                adb.show();

            }

        });
        //End Spinner
        EditText value1 = (EditText) findViewById(R.id.Value1);
        value1.setText(BlockNow.rvalue);
        value1.addTextChangedListener(new  TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                BlockNow.rvalue = s.toString();
            }
        });
        //End Edit
        SeekBar velocity = (SeekBar) findViewById(R.id.VeloseekBar);
        velocity.setProgress(new Integer(BlockNow.rspeed));
        velocity.refreshDrawableState();
        velocity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        String test = seekBar.getProgress()+"";
                        BlockNow.rspeed = test;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser){
                    }

                }
        );
        //End SeekBar
    }

    public void addItemsOnSpinner_backward() {

        spinner = (Spinner) findViewById(R.id.change_mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 0){
                    text1.setText("cm");
                    text2.setText("(5-100)");
                    BlockNow.tipeData = BlockNow.reverse2;
                }else {
                    text1.setText("sec");
                    text2.setText("(1-100)");
                    BlockNow.tipeData = BlockNow.reverse1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Distance");
        list.add("Time Limit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        int a;
        if(BlockNow.tipeData == BlockNow.reverse2) a = 0;
        else a = 1;
        spinner.setSelection(a);
    }

    private void turnLeft(){
        setContentView(R.layout.drag_pop_up_left);
        text1 = (TextView) findViewById(R.id.textViewAtas);
        text2 = (TextView) findViewById(R.id.textViewBawah);
        TextView blocks_text = (TextView) findViewById(R.id.Blocks);
        blocks_text.setText("Block number: "+(Var.indexModule+1));
        ImageButton btnDelete = (ImageButton) findViewById(R.id.deleteButton);
        //Spinner
        addItemsOnSpinner_left();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(PopUp.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Var.DragItem.getAdapter().removeItem(Var.indexModule);
                        DragListFragment.lastId--;
                        Var.removeBlock(Var.indexModule);
                        finish();
                    }});
                adb.show();

            }

        });
        //End Spinner
        EditText value1 = (EditText) findViewById(R.id.Value1);
        value1.setText(BlockNow.tlvalue);
        value1.addTextChangedListener(new  TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                BlockNow.tlvalue = s.toString();
            }
        });
        //End Edit
        SeekBar velocity = (SeekBar) findViewById(R.id.VeloseekBar);
        velocity.setProgress(new Integer(BlockNow.tlspeed));
        velocity.refreshDrawableState();
        velocity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        String test = seekBar.getProgress()+"";
                        BlockNow.tlspeed = test;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser){
                    }

                }
        );
        //End SeekBar
    }

    public void addItemsOnSpinner_left() {

        spinner = (Spinner) findViewById(R.id.change_mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 0){
                    text1.setText("dgr");
                    text2.setText("(0-180)");
                    BlockNow.tipeData = BlockNow.tleft2;
                }else {
                    text1.setText("sec");
                    text2.setText("(1-180)");
                    BlockNow.tipeData = BlockNow.tleft1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Degree");
        list.add("Time Limit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        int a;
        if(BlockNow.tipeData == BlockNow.tleft2) a = 0;
        else a = 1;
        spinner.setSelection(a);
    }

    private void turnRight(){
        setContentView(R.layout.drag_pop_up_right);
        text1 = (TextView) findViewById(R.id.textViewAtas);
        text2 = (TextView) findViewById(R.id.textViewBawah);
        TextView blocks_text = (TextView) findViewById(R.id.Blocks);
        blocks_text.setText("Block number: "+(Var.indexModule+1));
        ImageButton btnDelete = (ImageButton) findViewById(R.id.deleteButton);
        //Spinner
        addItemsOnSpinner_right();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(PopUp.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Var.DragItem.getAdapter().removeItem(Var.indexModule);
                        DragListFragment.lastId--;
                        Var.removeBlock(Var.indexModule);
                        finish();
                    }});
                adb.show();

            }

        });
        //End Spinner
        EditText value1 = (EditText) findViewById(R.id.Value1);
        value1.setText(BlockNow.trvalue);
        value1.addTextChangedListener(new  TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                BlockNow.trvalue = s.toString();
            }
        });
        //End Edit
        SeekBar velocity = (SeekBar) findViewById(R.id.VeloseekBar);
        velocity.setProgress(new Integer(BlockNow.trspeed));
        velocity.refreshDrawableState();
        velocity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        String test = seekBar.getProgress()+"";
                        BlockNow.trspeed = test;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser){
                    }

                }
        );
        //End SeekBar
    }

    public void addItemsOnSpinner_right() {

        spinner = (Spinner) findViewById(R.id.change_mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 0){
                    text1.setText("dgr");
                    text2.setText("(0-180)");
                    BlockNow.tipeData = BlockNow.tright2;
                }else {
                    text1.setText("sec");
                    text2.setText("(1-180)");
                    BlockNow.tipeData = BlockNow.tright2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Degree");
        list.add("Time Limit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        int a;
        if(BlockNow.tipeData == BlockNow.tright2) a = 0;
        else a = 1;
        spinner.setSelection(a);
    }

}

