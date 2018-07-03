package com.example.yuhei.shukatsuapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuhei.shukatsuapp.todoDatabase.HelpTodo;
import com.example.yuhei.shukatsuapp.todoDatabase.TodoDatabaseAccess;

import java.util.List;

public class TodoFragment extends Fragment {

    private ListView lv;
    private Button btnadd;
    private TodoDatabaseAccess databaseAccess2;
    private List<HelpTodo> todos;





//    EditText nameEditText;
//    Button saveBtn, retrieveBtn;
//    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
//    CustomAdapter adapter;
//    final Boolean forUpdate = true;
//    //MenuItem menuItem;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo, container, false);

        this.databaseAccess2 = TodoDatabaseAccess.getInstance(getActivity());
        this.lv = view.findViewById(R.id.todoListView);
        this.btnadd = view.findViewById(R.id.btnAdd);
        this.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelpTodo todo = todos.get(position);
                TextView txtTodo = (TextView) view.findViewById(R.id.txtTodo);
                if (todo.isFullDisplayed()) {
                    txtTodo.setText(todo.getShortText());
                    todo.setFullDisplayed(false);
                } else {
                    txtTodo.setText(todo.getText());
                    todo.setFullDisplayed(true);
                }
            }
        });




        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        databaseAccess2.open();
        this.todos = databaseAccess2.getAllTodos();
        databaseAccess2.close();
        TodoAdapter adapter = new TodoAdapter(getActivity(),todos);
        this.lv.setAdapter(adapter);
    }

    public void onAddClicked(){
        Intent intent = new Intent(getActivity(),TodoEditActivity.class);
        startActivity(intent);
    }
    public void onDeleteClicked(HelpTodo todo) {
        databaseAccess2.open();
        databaseAccess2.delete(todo);
        databaseAccess2.close();

        ArrayAdapter<HelpTodo> adapter = (ArrayAdapter<HelpTodo>) lv.getAdapter();
        adapter.remove(todo);
        adapter.notifyDataSetChanged();
    }
    public void onEditClicked(HelpTodo todo){
        Intent intent = new Intent(getActivity(),TodoEditActivity.class);
        intent.putExtra("TODO",todo);
        startActivity(intent);
    }

    private class TodoAdapter extends ArrayAdapter<HelpTodo>{

        public TodoAdapter(Context context, List<HelpTodo> objects) {
            super(context,0,objects);
        }


        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView =getLayoutInflater().inflate(R.layout.activity_detail,parent,false);

            }
            ImageView btnEdit = convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete =  convertView.findViewById(R.id.btnDelete);
            //Button detailButton = convertView.findViewById(R.id.detailButton);
            TextView txtDate = convertView.findViewById(R.id.txtDate);
            TextView txtTodo = convertView.findViewById(R.id.txtTodo);

            final HelpTodo todo = todos.get(position);
            todo.setFullDisplayed(false);
            txtDate.setText(todo.getDate());
            txtTodo.setText(todo.getShortText());


//            detailButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DetailAlert detailAlert = new DetailAlert();
//                    detailAlert.show(getFragmentManager(),"Detail Alert");
//
//                }
//            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(todo);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(todo);
                }
            });

            return convertView;
        }
    }
}






