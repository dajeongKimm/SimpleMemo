package com.dajeong.android.simplememo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    EditText editMemo;
    int mode = 0;
    String key = "";
    public static final String DELIMETER = ":::"; //구분자는 보통 상수로 뺀다.
    public static final String SELECTED_MEMO = "selected_memo";
    public static final String MODE = "mode";
    public static final String KEY = "key";
    public static final int MODE_NEW = 1;
    public static final int MODE_EDIT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        editMemo = findViewById(R.id.editMemo);


        //////////////////////////////
        Intent intent = getIntent();
        mode = intent.getIntExtra(MODE, 1);

        switch (mode) {
            case MODE_EDIT:
                key = intent.getStringExtra(KEY);
                String temp = Preference.read(key, this);
                editMemo.setText(temp.split(DELIMETER)[0]);
        }
    }

    //저장버튼
    public void save(View view) {
        //1.1 화면에서 입력된 글자를 가져오고
        String memo = editMemo.getText().toString();

        switch (mode) {//2.핸드폰에 글자를 저장한다.
            case MODE_NEW:
                Preference.write(memo, this);
                break;
            case MODE_EDIT:
                Preference.modify(key, memo, this);
                break;
        }

        setResult(RESULT_OK);
        finish();

        //3. 현재 쓰기 액티비티를 종료한다.


    }


}


