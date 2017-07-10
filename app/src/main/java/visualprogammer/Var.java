package visualprogammer;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.util.Log;
import android.view.Display;

import java.util.ArrayList;

//import interfaces.Image;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.sample.Modules;

public class Var {
	
	public static String dataPath="/sdcard/RoboViper/data/";
	public static String outputPath="/sdcard/RoboViper/output/";
	public static String fileName="";
	public static String lastFragment = "";
	public static boolean isExiting=false;
	public static boolean isNew=false;
	public static boolean isDeploy=false;
	public static boolean isSaved=true;
	public static boolean forwardActive=false,
			   reverseActive=false,
			   tleftActive=false,
			   trightActive=false,
			   lcdActive=false,
			   delayActive=false,
			   detectActive=false,
			   soundActive=false,
			   gripperActive=false,
			   freeActive=false,
			   loopActive=false,
			   lfollowerActive=false,
			   wfollowerActive=false;
	
	public static Display display;
	public static Point size;
	public static int indexLink;
	public static int indexModule;
	public static int temp_id;
	public static int tempMovingOrder;
	
	public static ArrayList<Image> links= new ArrayList<Image>();
	public static ArrayList<Modules> activeBlocks= new ArrayList<Modules>();
	
	public static boolean onSuccess=false;
	public static boolean fromModul=false;
	public static boolean fromWorksheet=false;
	
	
	public static boolean onDelete=false;
	public static boolean onForward=false;
	public static boolean onReverse=false;
	public static boolean onTright=false;
	public static boolean onTleft=false;
	public static boolean onLCD=false;
	public static boolean onDelay=false;
	public static boolean onSound=false;
	public static boolean onGripper=false;
	public static boolean onLoop=false;
	public static boolean onFollower=false;
	public static boolean onWall=false;

	public static final int HOME_ID = -1;
	public static final int MENU_ID = -2;
	public static final int MODUL_ID = -3;
	public static final int EXIT_ID = -4;
	public static final int ABOUT_ID = -55;
	
	public static final int NEW_ID = -5;
	public static final int OPEN_ID = -6;
	public static final int SAVE_ID = -7;
	public static final int DELETE_ID = -8;
	public static final int INFO_ID = -9;
	public static final int CONNECT_ID = -10;
	public static final int DEPLOY_ID = -11;
	public static final int SYNC_ID = -111;
	
	public static final int LEFT_ID=-12;
	public static final int RIGHT_ID=-13;
	
	public static final int FORWARD_ID=-14;
	public static final int TRIGHT_ID=-15;
	public static final int TLEFT_ID=-16;
	public static final int REVERSE_ID=-17;
	public static final int LCD_ID=-18;
	public static final int DELAY_ID=-19;
	public static final int SOUND_ID=-20;
	public static final int GRIPPER_ID=-22;
	public static final int LFOLLOWER_ID=-23;
	public static final int WFOLLOWER_ID=-24;
	public static final int LOOP_ID=-25;

	public static final int LEFT1_ID=24;
	public static final int RIGHT1_ID=25;
	
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;    
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;  
    public static String namadevice_connect = null;
    public static StringBuffer output_string;
    public static BluetoothAdapter bluetooth_adapter = null;
    public static ServiceBluetooth service_data_io = null;
    public static DragListView DragItem;
	public static int LastID;
	public static String BlAddress="";

	public static void order(int fromPosition, int toPosition){
		ArrayList<Modules> newactiveBlocks= new ArrayList<Modules>();
		for(int i=0; i<activeBlocks.size(); i++){
			if(fromPosition > toPosition) {
				if (toPosition == i) {
					newactiveBlocks.add(activeBlocks.get(fromPosition));
				}
				if (fromPosition == i)
					continue;
				newactiveBlocks.add(activeBlocks.get(i));
			} else {
				if (fromPosition == i)
					continue;
				newactiveBlocks.add(activeBlocks.get(i));
				if (toPosition == i) {
					newactiveBlocks.add(activeBlocks.get(fromPosition));
				}
			}
		}
		activeBlocks = newactiveBlocks;
	}

	public static void removeBlock(int fromPosition){
		ArrayList<Modules> newactiveBlocks= new ArrayList<Modules>();
		for(int i=0; i<activeBlocks.size(); i++){
			if (fromPosition == i)
				continue;
			newactiveBlocks.add(activeBlocks.get(i));
		}
		activeBlocks = newactiveBlocks;
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
	public static byte[] hexToByte(String s) throws Exception {
		if ("0x".equals(s.substring(0, 2))) {
			s = s.substring(2);
		}
		Log.d("MonitorActivity",s);
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
				Log.d("MonitorActivity",baKeyword[i]+",");
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return baKeyword;
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

	public static void generateActiveBlocks(String data){

		String[] programData=data.split(";");

		for(int i=0; i<programData.length; i++){
			String[] blockData;
			blockData=programData[i].split(",");

			Modules dropzone = new Modules(1," ") ;

			if(blockData[0].equalsIgnoreCase(dropzone.fwd1) || blockData[0].equalsIgnoreCase(dropzone.fwd2)){
				dropzone= new Modules(Var.FORWARD_ID,"@drawable/xforwardbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.fvalue=blockData[1];
				dropzone.fspeed=blockData[2];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.reverse1) || blockData[0].equalsIgnoreCase(dropzone.reverse2)){
				dropzone= new Modules(Var.REVERSE_ID,"@drawable/xbackwardbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.rvalue=blockData[1];
				dropzone.rspeed=blockData[2];

				//if(dropzone.tipeData.equalsIgnoreCase(dropzone.reverse1)) dropzone.setImageResource(R.drawable.mundur_waktu);
				//else dropzone.setImageResource(R.drawable.mundur_jarak);
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.tleft1) || blockData[0].equalsIgnoreCase(dropzone.tleft2)){
				dropzone= new Modules(Var.TLEFT_ID,"@drawable/xturnleftbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.tlvalue=blockData[1];
				dropzone.tlspeed=blockData[2];

				//if(dropzone.tipeData.equalsIgnoreCase(dropzone.tleft1)) dropzone.setImageResource(R.drawable.belki_waktu);
				//else dropzone.setImageResource(R.drawable.belki_sudut);
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.tright1) || blockData[0].equalsIgnoreCase(dropzone.tright2)){
				dropzone= new Modules(Var.TRIGHT_ID,"@drawable/xturnrightbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.tlvalue=blockData[1];
				dropzone.tlspeed=blockData[2];

				//if(dropzone.tipeData.equalsIgnoreCase(dropzone.tright1)) dropzone.setImageResource(R.drawable.belka_waktu);
				//else dropzone.setImageResource(R.drawable.belka_sudut);
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.lcd)){
				dropzone= new Modules(Var.LCD_ID,"@drawable/xdisplaybox") ;
				dropzone.tipeData=blockData[0];
				dropzone.lcdChar=blockData[2];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.sMonostable)){
				dropzone= new Modules(Var.SOUND_ID,"@drawable/xsoundbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.monosOn=blockData[1];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.sAstable)){
				dropzone= new Modules(Var.SOUND_ID,"@drawable/xsoundbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.astaOn=blockData[1];
				dropzone.astaOff=blockData[2];
				dropzone.astaLoop=blockData[3];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.sMario)){
				dropzone= new Modules(Var.SOUND_ID,"@drawable/xsoundbox") ;
				dropzone.tipeData=blockData[0];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.delay)){
				dropzone= new Modules(Var.DELAY_ID,"@drawable/xdelaybox") ;
				dropzone.tipeData=blockData[0];
				dropzone.dvalue=blockData[1];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.gripper1) || blockData[0].equalsIgnoreCase(dropzone.gripper2)){
				dropzone= new Modules(Var.GRIPPER_ID,"@drawable/xgripperbox") ;
				dropzone.tipeData=blockData[0];
			}
			else if(blockData[0].equalsIgnoreCase(dropzone.lfollower1) ||
					blockData[0].equalsIgnoreCase(dropzone.lfollower2) ||
					blockData[0].equalsIgnoreCase(dropzone.lfollower3)){
				dropzone= new Modules(Var.LFOLLOWER_ID,"@drawable/xlinefollbox") ;
				dropzone.tipeData=blockData[0];
				dropzone.lfvalue=blockData[1];
				dropzone.lfspeed=blockData[2];
			}
			dropzone.mEmpty=false;
			activeBlocks.add(dropzone);
		}
	}
}
