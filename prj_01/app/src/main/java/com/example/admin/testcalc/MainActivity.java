package com.example.admin.testcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double operandLeft, operandRight;
    enum operationType {PLUS, MINUS,MULTICAP,DIVISION };
    operationType operation;
    boolean mustClear = false; // если 1 то очистим поле

    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        text = (EditText) findViewById(R.id.editText);
    }

    // очистка
    public void clearEditText(){
        if(mustClear){
            text.setText("");
        }
        mustClear = false;
    }

    // C
    public void butC_Click(View v){
        mustClear = true;
        clearEditText();
        operandLeft = 0;
        operandRight = 0;

    }

    // author
    public void butAuthor_Click(View v){

        Intent intent = new Intent(this, Author.class);
        startActivity(intent);

    }

    // цифры
    public void butNumber1_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "1");
    }

    public void butNumber2_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "2");
    }
    public void butNumber3_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "3");
    }

    public void butNumber4_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "4");
    }
    public void butNumber5_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "5");
    }
    public void butNumber6_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "6");
    }
    public void butNumber7_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "7");
    }
    public void butNumber8_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "8");
    }
    public void butNumber9_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "9");
    }
    public void butNumber0_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        clearEditText();
        text.setText(text.getText().toString() + "0");
    }


    // Матем операции
    public void butMinus_Click(View v){
        operation = operationType.MINUS;
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        operandLeft = Double.parseDouble(text.getText().toString());
        text.setText("");
    }

    public void butPlus_Click(View v){
        operation = operationType.PLUS;
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        operandLeft = Double.parseDouble(text.getText().toString());
        text.setText("");
    }
    public void butMultiplication_Click(View v){
        operation = operationType.MULTICAP;
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        operandLeft = Double.parseDouble(text.getText().toString());
        text.setText("");
    }
    public void butDivision_Click(View v){
        operation = operationType.DIVISION;
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        operandLeft = Double.parseDouble(text.getText().toString());
        text.setText("");
    }

    // =
    public void butResult_Click(View v){
        //EditText text = (EditText) findViewById(R.id.editText); // text - объект элемента управления
        operandRight = Double.parseDouble(text.getText().toString());
        double result = operandLeft;
        switch (operation) {
            case MINUS:
                result = result - operandRight;
                break;
            case PLUS:
                result = result + operandRight;
                break;
            case MULTICAP:
                result = result * operandRight;
                break;
            case DIVISION:
                result = result / operandRight;
                break;


        }
        text.setText(String.valueOf(result));
        mustClear = true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
