package com.example.yuhei.shukatsuapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class MemoActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;


    MemoOpenHelper helper = null;
    boolean newFlag = false;
    String id = "";
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

//        editText = (EditText)findViewById(R.id.body);
//        textView = (TextView)findViewById(R.id.count);
//
//        textView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = editText.getText().toString();
//                int symbols = text.length();
//                textView.setText(""+symbols);
//                }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });




        if (helper == null) {
            helper = new MemoOpenHelper(MemoActivity.this);
        }

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");

        if (id.equals("")) {
            newFlag = true;
        } else {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                Cursor c = db.rawQuery("select body from MEMO_TABLE where uuid = '" + id + "'", null);
                boolean next = c.moveToFirst();
                while (next) {
                    String dispBody = c.getString(0);
                    TextInputEditText body = findViewById(R.id.body2);
                    body.setText(dispBody, TextView.BufferType.NORMAL);
                    next = c.moveToNext();
                }
            } finally {
                db.close();
            }
        }

        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText body = findViewById(R.id.body2);
                String bodyStr = body.getText().toString();
                fragmentManager = getSupportFragmentManager();


                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    if (newFlag) {
                        id = UUID.randomUUID().toString();
                        db.execSQL("insert into MEMO_TABLE(uuid,body)VALUES('" + id + "','" + bodyStr + "')");

                    } else {
                        db.execSQL("update MEMO_TABLE set body = '" + bodyStr + "' where uuid = '" + id + "'");

                    }
                } finally {
                    db.close();
                }

                /*fragment = new MemoFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.commit();*/
                Intent intent = new Intent(MemoActivity.this, MainActivity.class);

                startActivity(intent);
            }



        });

        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
