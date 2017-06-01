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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import visualprogammer.Var;

import java.util.ArrayList;

public class DragListFragment extends Fragment {

    private ArrayList<Pair<Long, String>> mItemArray;
    private DragListView mDragListView;
    private ListSwipeHelper mSwipeHelper;
    private MySwipeRefreshLayout mRefreshLayout;
    public static int lastId=0;
    public static String FileName = "Roboviper Canvas";

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
        mRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
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
                mRefreshLayout.setEnabled(true);
                if (fromPosition != toPosition) {
                    Var.order(fromPosition,toPosition);
                    //Toast.makeText(mDragListView.getContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mItemArray = new ArrayList<>();
        //mItemArray.add(new Pair<>((long) 0, "@drawable/ic_keyboard_arrow_up_black_24dp"));
        lastId = -1;
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
        menu.findItem(R.id.action_disable_drag).setVisible(mDragListView.isDragEnabled());
        menu.findItem(R.id.action_enable_drag).setVisible(!mDragListView.isDragEnabled());
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
        if(lastId<0){
            mItemArray.add(new Pair<>((long) ++lastId, text));
            setupGridHorizontalRecyclerView();
        } else {
            mDragListView.getAdapter().addItem(++lastId, new Pair<>((long) lastId, text));
        }
        Var.activeBlocks.add(new Modules(idMod));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
        Var.DragItem = mDragListView;
        Var.LastID = lastId;
    }

    public void onResume(){
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(FileName+" ("+(lastId+1)+"/64)");
    }
}
