package com.dajeong.android.simplememo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

//public class MainActivity extends AppCompatActivity implements CustomAdapter.Callback {
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. 리사이클리뷰를 연결
        recyclerView = findViewById(R.id.recyclerView);
        //2. 데이터 생성
        List<String> list = Preference.getList(this);
        //3. 아답터 생성
        adapter = new CustomAdapter(this);
        //4. 아답터를 리사이클러뷰에 연결
        recyclerView.setAdapter(adapter);
        //5. 레이아웃 매니저를 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //6. 아답터에 데이터 넣기 & 갱신자동
        adapter.setDataAndRefresh(list);



    }

    @Override
    protected void onResume() { //recycle onCreate호출된 뒤 onResume
        super.onResume();

        List<String> list = Preference.getList(this);
        adapter.setDataAndRefresh(list);

    }

    // 액티비티 이동
    public void goPost(View view){ //버튼에서 바로 함수 호출. view를 파라미터로 꼭 넣어줘야함. 이렇게 안하면 리스너로 하면 됨.
        //1. 인텐트 생성 - 시스템 메시지 클래스.
        Intent intent = new Intent(getBaseContext(),DetailActivity.class); //명시적 인텐트
        //2. 시스템 인텐트 전달
        startActivityForResult(intent,REQ_DETAIL);
        //startActivity(intent);

    }

    public static final int REQ_DETAIL = 999;
    public static final int REQ_EDIT = 998;

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK){
                //Toast.makeText(getApplicationContext(), "result ok", Toast.LENGTH_SHORT).show();
                switch (requestCode){
                    case REQ_DETAIL:
                    case REQ_EDIT:
                        List<String> list = Preference.getList(this);
                        adapter.setDataAndRefresh(list);
                        break;
                }
            }else{

                //cancle 처
            }
    }

//    @Override
//    public void goEdit(String memoKey) {
//        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra(DetailActivity.MODE,DetailActivity.MODE_EDIT);
//        intent.putExtra(DetailActivity.KEY, memoKey);
//        startActivityForResult(intent, REQ_DETAIL);
//
//    }


}
