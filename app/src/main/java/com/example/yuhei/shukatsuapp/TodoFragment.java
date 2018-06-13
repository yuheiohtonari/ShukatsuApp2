package com.example.yuhei.shukatsuapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private ListView lvItems;
    private Button button;


  /* private void readItems() {
        File fileDir = getFilesDira();
                File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.todo, null);


        lvItems = view.findViewById(R.id.todoList);
        items = new ArrayList<>();

        itemAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemAdapter);
        items.add("First item");
        items.add("Second Items");

        button = view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.editText);
                String itemText = editText.getText().toString();

               // DatabaseHelper helper = new DatabaseHelper(TodoFragment.this);
               /* SQLiteDatabase db = helper.getWritableDatabase();
                try{
                    String sqlDelete = "DELETE FROM tododatabase WHERE _id = ?";
                    SQLiteStatement stmt = db.compileStatement(sqlDelete);

                    Cursor cursor = db.query(sql)
                }*/
                itemAdapter.add(itemText);
                //writeItems();
                editText.setText("");
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemAdapter.notifyDataSetChanged();
                //writeItems();
                return true;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //readItems();
    }
}

    /*private void setupListViewListener() {
        lvItems.setOnLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    }*/



