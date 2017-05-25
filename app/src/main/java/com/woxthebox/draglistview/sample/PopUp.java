package com.woxthebox.draglistview.sample;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.EditText;

import visualprogammer.Var;

/**
 * Created by Kaze on 5/15/2017.
 */

public class PopUp extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_pop_up1);
        //EditText  codeText = (EditText) findViewById(R.id.editText);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
        //codeText.setText(Var.activeBlocks.get(Var.indexModule).getCode());
    }
}
