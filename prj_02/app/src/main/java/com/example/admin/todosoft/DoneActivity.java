package com.example.admin.todosoft;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by admin on 24.11.2015.
 */
public class DoneActivity extends TodoListActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_activity);
        fillTodoList(TodoDbHelper.where_clause.DONE);
    }

    @Override
    protected void onListItemClick(ListView list, View v, int position, long id) {
        super.onListItemClick(list, v, position, id);
        // delete from db
        TodoTask task = taskList.get(position);
        int r = dbHelper.removeFromDone(task.id);
        showMessage("удалили",String.valueOf(r));
        taskList.remove(position);
        adapter.notifyDataSetChanged(); //Updates adapter to new changes


    }

    public void buttonGotoMainClick(View v){
        onBackPressed();
    }

}
