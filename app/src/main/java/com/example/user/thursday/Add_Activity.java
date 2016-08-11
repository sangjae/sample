package com.example.user.thursday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

    EditText edit_place;
    EditText edit_price;
    TextView select_day;
    TextView memo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);

        Button btn_save = (Button) findViewById(R.id.btn_save);
        edit_place = (EditText) findViewById(R.id.edit_place);
        edit_price = (EditText) findViewById(R.id.edit_price);
        select_day = (TextView) findViewById(R.id.select_day);
        memo = (TextView) findViewById(R.id.edit_memo);
/*
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check_place = edit_place.getText().toString();


                String check_price = edit_price.getText().toString();

                String check_select_day = select_day.getText().toString();

                if (check_place.isEmpty() || check_price.isEmpty() || check_select_day.equals("날짜를 입력해주세요")) {
                    Toast.makeText(Add_Activity.this, "잘 좀 쳐라", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Add_Activity.this, List_Activity.class);
                    startActivity(intent);
                }


            }
        });*/

        select_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickDialogFragment datePickerFragment = new DatePickDialogFragment();
                datePickerFragment.show(getFragmentManager(), "DaySelect");

            }
        });

        getAccountList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean emptyCheck(EditText editText){
        boolean _emptyCheck;

        if(TextUtils.isEmpty(editText.getText())){
            _emptyCheck = true;
        }else{
            _emptyCheck = false;
        }

        return _emptyCheck;
    }

    public void toolbarRightButtonClick(View view){

        Log.d("눌림","눌림");
        Log.d("test",select_day.getTag().toString());
        if(emptyCheck(edit_place) || emptyCheck(edit_price) || select_day.getText().toString().equals("날짜를 입력해주세요")){
            Toast.makeText(this, "에효 잘좀쓰지 좀", Toast.LENGTH_SHORT).show();
            Log.d("눌림", "필수항목누락");
            Log.d("누락항목1", edit_place.getText().toString());
            Log.d("누락항목2", edit_price.getText().toString());
            Log.d("누락항목3", select_day.getText().toString());
            return;
        }

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("PAYMENT_STORE_NM", edit_place.getText().toString());
            requestObject.put("PAYMENT_AMT", edit_price.getText().toString());
            requestObject.put("PAYMENT_DTTM", select_day.getTag().toString());
            requestObject.put("ACCOUNT_TTL_CD", spinnerList.getAccountTitleCd(spinner.getSelectedItemPosition()));
            requestObject.put("SUMMARY", memo.getText().toString());
            requestObject.put("USER_ID", "test_user1");
            Log.d("sj", requestObject.toString());

            CommNetwork network = new CommNetwork(this, this);
            network.requestToServer("EXPENSE_I001", requestObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();

        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);


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


        Log.d("요청성공", "스피너까진오케이");
        //성공시
        Toast.makeText(this, "요청 성공!!", Toast.LENGTH_SHORT).show();
        try {

            if("EXPENSE_I001".equals(api_key)){
                if(!TextUtils.isEmpty(response.getString("EXPENSE_SEQ"))){
                    Intent intent = new Intent(Add_Activity.this, List_Activity.class);
                    intent.putExtra("EXPENSE_SEQ", response.getString("EXPENSE_SEQ"));
                    startActivity(intent);
                    Log.d("요청성공", "잘넘어감");
                    finish();
                }
            }

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
        if("EXPENSE_I001".equals(api_key)){
            Toast.makeText(this, "필수항목 누락", Toast.LENGTH_SHORT).show();
        }
    }


}
