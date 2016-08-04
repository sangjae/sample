package com.example.user.thursday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 2016-07-21.
 */
public class Add_Activity extends AppCompatActivity implements onNetworkResponseListener {

    Spinner spinner;
    AccountTitleSpinnerList spinnerList;

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

                if (check_place.isEmpty() || check_price.isEmpty() || check_select_day.equals("날짜를 입력해주세요")) {
                    Toast.makeText(Add_Activity.this, "잘 좀 쳐라", Toast.LENGTH_SHORT).show();
                } else {
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

        getAccountList();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    //스피너 클릭시
    public void getAccountList() {

        JSONObject req_data = new JSONObject();
        try {
            req_data.put("USER_ID", "test_user1");

            CommNetwork commNetwork = new CommNetwork(this, this);
            commNetwork.requestToServer("ACCOUNT_L001", req_data);


        } catch (Exception e) {
            ErrorUtils.AlertException(this, "오류가 발생하였습니다.", e);

        }
    }

    @Override
    public void onSuccess(String api_key, JSONObject response) {


        //성공시
        Toast.makeText(this, "요청 성공!!", Toast.LENGTH_SHORT).show();
        try {
            JSONArray array = response.getJSONArray("REC");

            spinnerList = new AccountTitleSpinnerList(array);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList.getArrayList());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner = (Spinner) findViewById(R.id.add_spinner);
            spinner.setAdapter(adapter);
        } catch (Exception e) {
            ErrorUtils.AlertException(this, "오류가 발생하였습니다!!", e);
        }


    }

    @Override
    public void onFailure(String api_key, String error_cd, String error_msg) {
        //실패시
        Toast.makeText(this, "요청 실패..", Toast.LENGTH_SHORT).show();
    }


}
