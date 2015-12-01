package com.example.admin.todosoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by admin on 24.11.2015.
 */
public class MainActivity extends TodoListActivityBase {

    EditText newTasField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        newTasField = (EditText)findViewById(R.id.editTextNewTask);
        fillTodoList(TodoDbHelper.where_clause.ACTIVE);

    }

    public void buttonAddTaskClick(View v) {
        TodoTask newTask = new TodoTask(getCurrentTimeStamp(), newTasField.getText().toString());
        long newId = dbHelper.addTask(newTask);
        if(newId > 0){
            newTask.id = newId;
            taskList.add(newTask);
            adapter.notifyDataSetChanged();
        }else{
            showMessage("Ошибка","Нельзя добавить эту задачу, возможно это дубль.");
        }
    }

    public void buttonGotoDoneClick(View v){
        Intent myIntent = new Intent(this, DoneActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }
    @Override
    protected void onListItemClick(ListView list, View v, int position, long id) {
        //String item = (String) getListAdapter().getItem(position);
        super.onListItemClick(list, v, position, id);
        //update 0 to 1 in db
        TodoTask task = taskList.get(position);

        showMessage("", String.valueOf(task.id));
        showMessage("", task.description);

        int res = dbHelper.moveFromActiveToDone(task.id);
        showMessage("", String.valueOf(res));
        taskList.remove(position);
        adapter.notifyDataSetChanged(); //Updates adapter to new changes


    }

    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }

}
