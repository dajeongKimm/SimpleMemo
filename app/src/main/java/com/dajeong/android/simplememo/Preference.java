package com.dajeong.android.simplememo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Preference {
    private static final String FILENAME = "simplememo";
    private static final String COUNT = "COUNT";

    //최종 메모 번호 읽기
    public static int getCount(Context context){
        return context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE).getInt(COUNT,0);
    }

    //메모 번호 증가
    public static int increaseCount(Context context){
        SharedPreferences pref = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        //1.현재 메모번호를 가져온다.
        int count = pref.getInt(COUNT,0)+1;
        //2. 현재 메모번호를 저장한다.
        pref.edit().putInt(COUNT,count).commit();
        //3.값 리턴
        return count;
    }

    //메모수정
    public static void modify(String key,String memo, Context context){

        // 현재 날짜 시간을 가져온다. 메모를 쓸때 시간, 키값 같이 가져오도록
        long now = System.currentTimeMillis();
        // 날짜를 구분자로 구분해서 memo에 넣는다.
        memo = memo + DetailActivity.DELIMETER+now; // 메모내용 ::: 현재 시간
        memo = memo + DetailActivity.DELIMETER+key; // 메모내용 ::: 현재 시간 ::: 키값
        SharedPreferences sharedPref = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(key, memo).commit();
    }


    //메모쓰기
    public static void write(String memo, Context context){
        int count = increaseCount(context); //메모번호를 가져오고
        String key = "memo_"+count;
        modify(key, memo, context);
    }

    //메모삭제
    public static void remove(String key, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE); //쉐어드프리퍼런스 가져옴.
        sharedPref.edit().remove(key).commit();
    }



    //메모읽기
    public static String read(String key, Context context){
        //1. 쉐어드프리퍼런스 가져오기
        SharedPreferences pref = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        //2. 키를 이용해서 값 불러오기
        String result = pref.getString(key,"");
        //3. 불러온값 리턴.
        return  result;
    }

    //메모목록을 가져오는 함수
    public static List<String> getList(Context context){
        List<String> result = new ArrayList<>();
        //1.현재 메모 번호를 가져와서, 번호 개수만큼 반복하면서 출력
        int count = Preference.getCount(context);
        for(int i =1; i<=count; i++){
            //result.add(Preference.read("memo_"+i, context));
            //중간에 데이터가 삭제 되었으면 목록에 더하지 않는다.
            String memo = Preference.read("memo_"+i,context);
            if(memo != null && !"".equals(memo)) // "".equals(memo) 하는 이유는 memo가 null일때 nullpointException 안나오게 하려고.
                result.add(memo);
        }

        return result;
    }
















}
