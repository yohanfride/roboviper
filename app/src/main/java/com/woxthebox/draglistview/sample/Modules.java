package com.woxthebox.draglistview.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import visualprogammer.Var;

public class Modules {
	public boolean mEmpty = true;
    public int order;
    
	public EditText values;

	public int idModules;
    private String dataSave="";
    public String tipeData="";
    public static String temptipeData="";
    
    public String blockHeader="ff";
    
    public String looping="ulang";
    public String lvalue="3";
    public static String templvalue;
    
    public String fwd1="maju_waktu";
    public String fwd2="maju_jarak";
    public String fvalue="10", fspeed="10";
    public static String tempfvalue="", tempfspeed="";
    
    public String reverse1="mundur_waktu";
    public String reverse2="mundur_jarak";
    public String rvalue="10", rspeed="10";
    public static String temprvalue="", temprspeed="";
    
    public String tleft1="belok_kiri_waktu";
    public String tleft2="belok_kiri_derajat";
    public String tlvalue="90", tlspeed="10";
    public static String temptlvalue="", temptlspeed="";
    
    public String tright1="belok_kanan_waktu";
    public String tright2="belok_kanan_derajat";
    public String trvalue="90", trspeed="10";
    public static String temptrvalue="", temptrspeed="";
    
    public String lcd="lcd",lcdChar="Hello World";
    public static String templcdChar="";
    
    public String sMonostable="buzzer_nyala";
    public String monosOn="3";
    public static String tempmonosOn="";
    public String sAstable="buzzer_nyala_mati";
    public String sMario="buzzer_mario";
    public String astaOn="1",astaOff="1",astaLoop="4";
    public static String tempastaOn="",tempastaOff="",tempastaLoop="";
    
    public String delay="delay",dvalue="3"; //jeda waktu
    public static String tempdvalue="";
    
    public String gripper1="gripper_ambil",
    			gripper2="gripper_taruh";
    public static String tempgvalue="";
    
    public String lfollower1="line_follower_perempatan",
    		lfollower2="line_follower_pertigaan_kanan",
    		lfollower3="line_follower_pertigaan_kiri";
    public String lfvalue="02",lfspeed="10";
    public static String templfvalue="",templfspeed="";
    
    public String wfollower1="wall_follower_kanan",
    		wfollower2="wall_follower_kiri";
    public String wfpar2="waktu",wfspeed="10",wrange="5",wfvalue="5";
    public String wfwaktu="waktu",wfgaris="garis";
    public static String tempwfspeed="",tempwrange="",tempwfpar="",tempwfvalue="";

	public Modules(int id){
		this.idModules = id;
		if(idModules ==Var.FORWARD_ID){
			tipeData = fwd2;
		}
		if(idModules==Var.REVERSE_ID){
			tipeData = reverse1;
		}
		if(idModules==Var.TLEFT_ID){
			tipeData = tleft2;
		}
		if(idModules==Var.TRIGHT_ID){
			tipeData = tright2;
		}
		if(idModules==Var.LCD_ID){
			tipeData = lcd;
		}
		if(idModules==Var.DELAY_ID){
			tipeData = delay;
		}
		if(idModules==Var.SOUND_ID){
			tipeData = sMonostable;
		}
		if(idModules==Var.GRIPPER_ID){
			tipeData = gripper1;
		}
		if(idModules==Var.LFOLLOWER_ID){
			tipeData = lfollower1;
		}
		if(idModules==Var.LOOP_ID){
			tipeData = lfollower1;
		}
		if(idModules==Var.WFOLLOWER_ID){
			tipeData = wfollower1;
		}

	}

	public String extractDataSave(Modules a){
		if(a.idModules ==Var.FORWARD_ID){
			dataSave=a.tipeData+","+a.fvalue+","+a.fspeed+","+a.fspeed;
		}
		if(a.idModules==Var.REVERSE_ID){
			dataSave=a.tipeData+","+a.rvalue+","+a.rspeed+","+a.rspeed;
		}
		if(a.idModules==Var.TLEFT_ID){
			dataSave=a.tipeData+","+a.tlvalue+","+a.tlspeed+","+a.tlspeed;
		}
		if(a.idModules==Var.TRIGHT_ID){
			dataSave=a.tipeData+","+a.trvalue+","+a.trspeed+","+a.trspeed;
		}
		if(a.idModules==Var.LCD_ID){
			dataSave=a.tipeData+","+String.valueOf(a.lcdChar.length())+","+a.lcdChar;
		}
		if(a.idModules==Var.DELAY_ID){
			dataSave=a.tipeData+","+a.dvalue;
		}
		if(a.idModules==Var.SOUND_ID){
			if(a.tipeData.equals(sMonostable)){
				dataSave=a.tipeData+","+a.monosOn;
			}
			else if(a.tipeData.equals(sAstable)){
				dataSave=a.tipeData+","+a.astaOn+","+a.astaOff+","+a.astaLoop;
			}
			else if(a.tipeData.equals(sMario)) dataSave=a.tipeData;
		}
		if(a.idModules==Var.GRIPPER_ID){
			dataSave=a.tipeData;
		}
		if(a.idModules==Var.LFOLLOWER_ID){
			dataSave=a.tipeData+","+a.lfvalue+","+a.lfspeed;
		}
		if(a.idModules==Var.LOOP_ID){
			dataSave=a.tipeData+","+a.lvalue;
		}
		if(a.idModules==Var.WFOLLOWER_ID){
			dataSave=a.tipeData+","+a.wfpar2+","+a.wfvalue+","+a.wrange+","+a.wfspeed;
		}
		return dataSave;
	}

	public String getCode(){
		return extractDataSave(this);
	}
}
