package com.example.yuhei.shukatsuapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Dialog;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.yuhei.shukatsuapp.mDataBase.DBAdapter;

import com.example.yuhei.shukatsuapp.mDataObject.Spacecraft;
import com.example.yuhei.shukatsuapp.mListView.CustomAdapter;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    ListView lv;
    EditText nameEditText;
    Button saveBtn, retrieveBtn;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
    CustomAdapter adapter;
    final Boolean forUpdate = true;
    //MenuItem menuItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo, container, false);

        //Toolbar toolbar = view.findViewById(R.id.toolbar) ;

        lv = view.findViewById(R.id.todoList);
        adapter = new CustomAdapter(getContext(), spacecrafts);

        this.getSpacecrafts();
        lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(false);
            }
        });
        //setUpListViewListener();



//        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                delete();
//                return false;
//            }
//        });
//
//
//
        return view;

    }

    private void displayDialog(Boolean forUpdate) {
        Dialog d = new Dialog(getContext());
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.tododialog);

        nameEditText = (EditText) d.findViewById(R.id.todoEditTxt);
        saveBtn = (Button) d.findViewById(R.id.addButton);
        retrieveBtn= (Button)d.findViewById(R.id.retrieveBtn);


        if (!forUpdate) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save(nameEditText.getText().toString());
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts();
                }
            });

        } else {

            //SET SELECTED TEXT
            nameEditText.setText(adapter.getSelectedItemName());

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(nameEditText.getText().toString());
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts();
                }
            });
        }

        d.show();

    }

    //SAVE
    private void save(String name) {
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        boolean saved = db.add(name);

        if (saved) {
            nameEditText.setText("");
            getSpacecrafts();
        } else {
            Toast.makeText(getContext(), "Unable To Save", Toast.LENGTH_SHORT).show();
        }
    }

    //RETRIEVE OR GETSPACECRAFTS
    private void getSpacecrafts() {
        spacecrafts.clear();
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve();
        Spacecraft spacecraft = null;

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);

            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);

            spacecrafts.add(spacecraft);
        }

        db.closeDB();
        lv.setAdapter(adapter);
    }

    //UPDATE OR EDIT
    private void update(String newName) {
        //GET ID OF SPACECRAFT
        int id = adapter.getSelectedItemID();

        //UPDATE IN DB
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        boolean updated = db.update(newName, id);
        db.closeDB();

        if (updated) {
            nameEditText.setText(newName);
            getSpacecrafts();
        } else {
            Toast.makeText(getContext(), "Unable To Update", Toast.LENGTH_SHORT).show();
        }

    }

    private void delete() {
        //GET ID
        int id = adapter.getSelectedItemID();

        //DELETE FROM DB
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        boolean deleted = db.delete(id);
        db.closeDB();

        if (deleted) {
            getSpacecrafts();
        } else {
            Toast.makeText(getContext(), "Unable To Delete", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                delete();
                return false;
            }
        });
        CharSequence title = item.getTitle();
        if (title == "new") {
            displayDialog(!forUpdate);

        } else if (title == "edit") {
            displayDialog(forUpdate);

        } else if (title == "delete") {
            delete();
        }

        return super.onContextItemSelected(item);
    }

    private void setUpListViewListener(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                spacecrafts.remove(position);

                return true;
            }
        });
    }
}




