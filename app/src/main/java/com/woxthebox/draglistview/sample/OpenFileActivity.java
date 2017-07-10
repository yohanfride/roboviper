package com.woxthebox.draglistview.sample;

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
public class OpenFileActivity extends AppCompatActivity {

    private ListView listview;
    final ArrayList<String> list = new ArrayList<String>();
    private ArrayList<Pair<Long, String>> mItemArray;
    private DragListView mDragListView;
    private ListSwipeHelper mSwipeHelper;
    //private MySwipeRefreshLayout mRefreshLayout;
    private int lastId = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//setContentView(R.layout.files_list);

        setContentView(R.layout.file_list_layout);
		listview = (ListView) findViewById(R.id.file_list_item);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_color)));
        getSupportActionBar().hide();
        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        mItemArray = new ArrayList<>();

		try {
			File dir = new File(Var.dataPath);
			File[] filelist = dir.listFiles();

			if (filelist.length == 0) {
				finish();
				Toast.makeText(getApplicationContext(), "File kosong, silahkan buat file baru", Toast.LENGTH_SHORT).show();
			}

			final String[] name = new String[filelist.length];
            mItemArray = new ArrayList<>();
            for (int i = 0; i < filelist.length; i++) {
				name[i] = filelist[i].getName().replace(".txt", "");
				Date lastModified = new Date(filelist[i].lastModified());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String formattedDateString = formatter.format(lastModified);
                //list.add(0, name[i]);
                mItemArray.add(new Pair<>((long) i, name[i]+"~" + formattedDateString));
			}

			/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.source_file_list, R.id.nameText, list);

			//final StableArrayAdapter adapter = new StableArrayAdapter(this,R.layout.open_layout,R.id.textView_open, list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
										int position, long id) {
					final String item = (String) parent.getItemAtPosition(position);
					String saveData = "";

					try {
						FileReader fstream = new FileReader(Var.dataPath + item + ".txt");
						BufferedReader read = new BufferedReader(fstream);
						saveData = read.readLine();
						read.close();
					} catch (Exception e) {
					}
					Var.activeBlocks.clear();
					Var.generateActiveBlocks(saveData);
					Var.fileName = item;
					showFragment(DragListFragment.newInstance());
                    getSupportActionBar().show();
				}
			});*/
            final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            mDragListView.setSwipeListener(new ListSwipeHelper.OnSwipeListenerAdapter() {
                @Override
                public void onItemSwipeStarted(ListSwipeItem item) {
                }

                @Override
                public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
                    // Swipe to delete on left
                    Pair<Long, String> adapterItem = (Pair<Long, String>) item.getTag();
                    final int pos = mDragListView.getAdapter().getPositionForItem(adapterItem);
                    final String items = name[pos];
                    if (swipedDirection == ListSwipeItem.SwipeDirection.LEFT) {
                        builder2.setTitle("Confirm");
                        builder2.setMessage("Do you want to delete this file?");
                        builder2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mDragListView.getAdapter().removeItem(pos);
                                Files f= new Files();
                                f.deleteFile(items);
                            }
                        });
                        builder2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder2.create();
                        alert.show();
                    }
                    if (swipedDirection == ListSwipeItem.SwipeDirection.RIGHT) {
                        String saveData = "";
                        try {
                            FileReader fstream = new FileReader(Var.dataPath + items + ".txt");
                            BufferedReader read = new BufferedReader(fstream);
                            saveData = read.readLine();
                            read.close();
                        } catch (Exception e) {
                        }
                        Var.activeBlocks.clear();
                        Var.generateActiveBlocks(saveData);
                        Var.fileName = items;
                        showFragment(DragListFragment.newInstance());
                        getSupportActionBar().show();
                    }
                }
            });

            setupListRecyclerView();
		} catch (Exception e) {
			finish();
			Toast.makeText(getApplicationContext(), "File Gagal diambil, silahkan coba lagi", Toast.LENGTH_SHORT).show();
		}




        /*mRefreshLayout.setScrollingView(mDragListView.getRecyclerView());
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.app_color));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });*/
    }

    private void setupListRecyclerView() {
        mDragListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FileAdapter listAdapter = new FileAdapter(mItemArray, R.layout.source_list_item, R.id.image1, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(getApplicationContext(), R.layout.source_list_item));
    }

    @Override
    public void onBackPressed() {
        Var.isExiting = true;
        int count = getFragmentManager().getBackStackEntryCount();
        mDragListView.getAdapter().notifyDataSetChanged();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
        getSupportActionBar().hide();
        Var.lastFragment = "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((Var.isSaved == false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want previous save program?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveFile();
                        onBackPressed();
                        Var.isSaved = true;
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onBackPressed();
                        Var.isSaved = true;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                super.onBackPressed();
                getSupportActionBar().hide();
                Var.lastFragment = "";
                mDragListView.getAdapter().notifyDataSetChanged();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("fragment");
        transaction.add(R.id.container2, fragment);
        transaction.commit();
        //transaction.replace(R.id.container, fragment, "fragment").commit();
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

}