package com.example.yuhei.shukatsuapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoFragment extends Fragment {

    MemoOpenHelper helper = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memo, container, false);

        if (helper == null) {
            helper = new MemoOpenHelper(getContext());
        }
        final ArrayList<HashMap<String, String>> memolist = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("select uuid, body from MEMO_TABLE order by id", null);
            boolean next = c.moveToFirst();

            while (next) {
                HashMap<String, String> data = new HashMap<>();

                String uuid = c.getString(0);
                String body = c.getString(1);
                if (body.length() > 10) {
                    body = body.substring(0, 11) + "...";
                }
                data.put("body", body);
                data.put("id", uuid);
                memolist.add(data);

                next = c.moveToNext();
            }
        } finally {
            db.close();
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), memolist,
                android.R.layout.simple_list_item_2,
                new String[]{"body", "id"},
                new int[]{android.R.id.text1, android.R.id.text2});

        ListView listView = view.findViewById(R.id.memoList);
        listView.setAdapter(simpleAdapter);


         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(getActivity(), MemoActivity.class);

                 TwoLineListItem two = (TwoLineListItem) view;


                 TextView idTextView = two.getText2();
                 String idStr = (String) idTextView.getText();

                 intent.putExtra("id", idStr);
                 startActivity(intent);

             }
         });




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TwoLineListItem two = (TwoLineListItem) view;
                TextView idTextView = (TextView) two.getText2();
                String idStr = (String) idTextView.getText();

                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    db.execSQL("DELETE FROM MEMO_TABLE WHERE uuid = '" + idStr + "'");

                } finally {
                    db.close();
                }
                memolist.remove(position);
                simpleAdapter.notifyDataSetChanged();
                return true;
            }
        });

        Button newButton = view.findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MemoActivity.class);
                intent.putExtra("id", "");
                startActivity(intent);
            }
        });

        return view;


    }
}
