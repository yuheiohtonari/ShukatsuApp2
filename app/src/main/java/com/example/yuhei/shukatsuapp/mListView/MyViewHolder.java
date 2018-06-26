package com.example.yuhei.shukatsuapp.mListView;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.yuhei.shukatsuapp.R;

public class MyViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener{

    TextView nameTxt;
    MyLongClickListener longClickLictener;

    public MyViewHolder(View view){
        nameTxt = view.findViewById(R.id.nameTxt);

        view.setOnLongClickListener(this);
        view.setOnCreateContextMenuListener(this);

    }


    @Override
    public boolean onLongClick(View view) {
        this.longClickLictener.onItemLongClick();
        return false;
    }
    public void  setLongClickLictener(MyLongClickListener longClickLictener){
        this.longClickLictener = longClickLictener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Action :");
        menu.add(0,0,0,"new");
        menu.add(0,1,0,"edit");
        menu.add(0,2,0,"delete");


    }
}
