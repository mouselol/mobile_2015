package com.example.admin.todosoft;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 23.11.2015.
 */
public class TodoListActivityBase extends ListActivity {

    ListView todoListView;
    ArrayList<TodoTask> taskList;
    TodoAdapter adapter;
    TodoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todoListView = (ListView) findViewById(android.R.id.list);

        dbHelper = new TodoDbHelper(this);
    }



    // заполнение списка
    public void fillTodoList(TodoDbHelper.where_clause where){

        taskList = new ArrayList<TodoTask>();
        Cursor c = dbHelper.fetchAll(where);
        String date, description;
        long id;
        //if (c != null) {
        if (c.moveToFirst()) {
            while(c.moveToNext()) {
                date = c.getString(0);//Integer.toString(c.getInt(0));
                description = c.getString(1);
                id = c.getLong(2);
                taskList.add(new TodoTask(date, description, id));
                //showMessage(date,description);
            }
            c.close();
        }else{
            showMessage("","не нашли задач");
        }
        // Использование собственного шаблона
        adapter = new TodoAdapter(this, R.layout.list_row, taskList);
        setListAdapter(adapter);

    }



/*
    @Override
    protected void onResume() {
        //datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //datasource.close();
        super.onPause();
    }
*/
    public void showMessage(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
