package com.woxthebox.draglistview.sample;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import visualprogammer.Var;


/**
 * Created by Kaze on 6/10/2017.
 */

public class PopUpSourceCode extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.85),(int)(height*.8));

        List<String> values = new ArrayList<String>();
        for(int i=0; i<Var.activeBlocks.size(); i++){
            if(i<9)
                values.add("  "+(i+1)+". "+Var.activeBlocks.get(i).getCode()+";");
            else
                values.add((i+1)+". "+Var.activeBlocks.get(i).getCode()+";");
        }
        ListView listView = (ListView) findViewById(R.id.ListCode);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.source_code_list_item,R.id.item_content, values);
        listView.setAdapter(adapter);
    }

}
