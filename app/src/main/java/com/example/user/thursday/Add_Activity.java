package com.example.user.thursday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 2016-07-21.
 */
public class Add_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);

        TextView select_day = (TextView) findViewById(R.id.select_day);
        Button btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_place = (EditText) findViewById(R.id.edit_place);
                String check_place = edit_place.getText().toString();

                EditText edit_price = (EditText) findViewById(R.id.edit_price);
                String check_price = edit_price.getText().toString();

                TextView select_day = (TextView) findViewById(R.id.select_day);
                String check_select_day = select_day.getText().toString();

                if(check_place.isEmpty() || check_price.isEmpty() || check_select_day.equals("날짜를 입력해주세요")){
                    Toast.makeText(Add_Activity.this, "잘 좀 쳐라", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent intent = new Intent(Add_Activity.this, List_Activity.class);
                    startActivity(intent);
                }
            }
        });

        select_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickDialogFragment datePickerFragment = new DatePickDialogFragment();
                datePickerFragment.show(getFragmentManager(),"DaySelect");

            }
        });



    }

}
