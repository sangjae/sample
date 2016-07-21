package com.example.user.thursday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2016-07-21.
 */
public class List_Activity extends AppCompatActivity{

    //Intent intent = getIntent();
    //String id = intent.getExtras().getString("id");
    //String pw = intent.getExtras().getString("pw");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);




        Button btn_plus = (Button) findViewById(R.id.plus);

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(List_Activity.this, Add_Activity.class);
                startActivity(intent1);
            }
        });


    }




}
