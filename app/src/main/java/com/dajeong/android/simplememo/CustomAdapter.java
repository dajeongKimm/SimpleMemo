package com.dajeong.android.simplememo;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    //4. 아답터에서 사용할 데이터 선언
    List<String> list = new ArrayList<>();
    SimpleDateFormat sdf;
    Callback callback;
    MainActivity mainActivity;

    public CustomAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    public CustomAdapter(Callback callback){//생성자에 날짜 포맷
        this.callback = callback;
        sdf = new SimpleDateFormat("yyyy-MM-dd");


    }

    //8. 아답터에 데이터를 셋팅하고 아답터를 갱신한다.
    public void setDataAndRefresh(List<String> list){
        this.list = list;
        notifyDataSetChanged(); // 변경된 데이터를 화면에 반영
    }


    //6. 목록에서 사용할 아이템 레이아웃.xml을 클래스로 변환하고 instance화해서 메모리에 로드한다.

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //6.1 xml파일을 변환한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        //6.2 변환된 view를 Holder에 담는다
        Holder holder = new Holder(view);
        //6.3 홀더를 리턴한다.
        return holder;
    }

    //7. 생성된 홀더에 데이터 셋팅한다.
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        //7.1 데이터목록에 있는 데이터를 개별로 한개씩 꺼낸다.
        String memo =list.get(position);
        // 7.2 화면에 데이터를 셋팅
        String temp[] = memo.split(DetailActivity.DELIMETER); // 메모 내용 ::: 날짜 ::: 키값 // memo를 구분자로 변경.
        String text =temp[0]; // 메모내용
        long date =Long.parseLong(temp[1]); //long 타입으로
        holder.textMemo.setText(text);
        holder.textDate.setText(sdf.format(date));

        holder.key = temp[2]; //키가 필요한 이유 - 쉐어드 프리퍼런스에 저장된 실제 데이터 삭제.
        holder.position = position;// 포지션도 필요 - 현재 메모리에 로드된 데이터 삭제

        holder.textNo.setText((position+1)+""); //position이 0부터 라서.
    }

    //5.목록에 출력되는 아이템의 개수
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Callback{
        public void goEdit(String key);
    }

    public void goEdit(String memoKey) {
        Intent intent = new Intent(mainActivity.getBaseContext(),DetailActivity.class);
        intent.putExtra(DetailActivity.MODE,DetailActivity.MODE_EDIT);
        intent.putExtra(DetailActivity.KEY, memoKey);
        mainActivity.startActivityForResult(intent, MainActivity.REQ_DETAIL);

    }

    //1. 홀더를 먼저 만든다. - 아이템 레이아웃 관리
    public class Holder extends RecyclerView.ViewHolder{
        //2. 아이템 레이아웃에서 사용하는 위젯을 모두 선언.
        TextView textNo, textMemo, textDate;
        ConstraintLayout item;
        Button btnDelete;
        String key; //홀더에 정보 저장할수도 있음.
        int position; //홀더에 정보 저장할수도 있음.
        public Holder(final View itemView) {
            super(itemView);
            //3. findViewById로 소스코드와 화면을 연결
            textNo = itemView.findViewById(R.id.textNo);
            textMemo = itemView.findViewById(R.id.textMemo);
            textDate = itemView.findViewById(R.id.textDate);
            item = itemView.findViewById(R.id.item);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //삭제처리
                    Preference.remove(key,view.getContext());
                    list.remove(position);
                    Toast.makeText(view.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    //화면 갱신
                    notifyDataSetChanged(); //삭제 후 전체 로드 - 시간이 오래걸림.
                    //notifyItemRemoved(position); //전체로드 아님.

                }
            });

            textMemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goEdit(key);
                    //callback.goEdit(key);
                }
            });




        }
    }
}
