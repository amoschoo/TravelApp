package com.example.amos.travelapp.Pages;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amos.travelapp.R;
import com.example.amos.travelapp.Resources.GlobalValue;

public class Budget extends AppCompatActivity {
    EditText budgetET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Budget");
        double budget=((GlobalValue)getApplicationContext()).getBudget();
        budgetET = (EditText)findViewById(R.id.BudgeteditText2);
        budgetET.setText("" + budget);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    public void setBudget(View v){
        Double Budget;
        if (budgetET.getText().toString().equals("")){
            Budget=0.0;
        }else {
            Budget = Double.parseDouble(budgetET.getText().toString());
        }
        ((GlobalValue) getApplicationContext()).setBudget(Budget);
        Toast.makeText(this, "BUDGET SET TO S$" + ((GlobalValue) getApplicationContext()).getBudget(), Toast.LENGTH_SHORT).show();
        hide_keyboard(this);
        this.finish();
    }
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
