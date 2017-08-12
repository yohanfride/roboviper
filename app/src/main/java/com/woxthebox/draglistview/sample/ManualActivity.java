package com.woxthebox.draglistview.sample;

/**
 * Created by Kaze on 8/11/2017.
 */

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import visualprogammer.Files;
import visualprogammer.Var;

import static com.woxthebox.draglistview.sample.DragListFragment.saveFile;

@SuppressLint("NewApi")
public class ManualActivity extends AppCompatActivity {
    LinearLayout Help1,Help2,Help3,Help4,Help5;
    int help;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.files_list);

        setContentView(R.layout.manual);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_color)));
        getSupportActionBar().hide();

        Help1 = (LinearLayout) findViewById (R.id.Help1);
        Help2 = (LinearLayout) findViewById (R.id.Help2);
        Help3 = (LinearLayout) findViewById (R.id.Help3);
        Help4 = (LinearLayout) findViewById (R.id.Help4);
        Help5 = (LinearLayout) findViewById (R.id.Help5);
        LinearLayout THelp1 = (LinearLayout) findViewById (R.id.THelp1);
        LinearLayout THelp2 = (LinearLayout) findViewById (R.id.THelp2);
        LinearLayout THelp3 = (LinearLayout) findViewById (R.id.THelp3);
        LinearLayout THelp4 = (LinearLayout) findViewById (R.id.THelp4);
        LinearLayout THelp5 = (LinearLayout) findViewById (R.id.THelp5);
        refresh();
        THelp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                if(help == 1){
                    help = 0;
                } else {
                    Help1.setVisibility(LinearLayout.VISIBLE);
                    help=1;
                }
            }
        });
        THelp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                if(help == 2){
                    help = 0;
                } else {
                    Help2.setVisibility(LinearLayout.VISIBLE);
                    help=2;
                }
            }
        });
        THelp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                if(help == 3){
                    help = 0;
                } else {
                    Help3.setVisibility(LinearLayout.VISIBLE);
                    help=3;
                }
            }
        });
        THelp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                if(help == 4){
                    help = 0;
                } else {
                    Help4.setVisibility(LinearLayout.VISIBLE);
                    help=4;
                }
            }
        });
        THelp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                if(help == 5){
                    help = 0;
                } else {
                    Help5.setVisibility(LinearLayout.VISIBLE);
                    help = 5;
                }
            }
        });
        Button backs = (Button) findViewById(R.id.buttonback);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void refresh(){
        Help1.setVisibility(LinearLayout.GONE);
        Help2.setVisibility(LinearLayout.GONE);
        Help3.setVisibility(LinearLayout.GONE);
        Help4.setVisibility(LinearLayout.GONE);
        Help5.setVisibility(LinearLayout.GONE);

    }
}
