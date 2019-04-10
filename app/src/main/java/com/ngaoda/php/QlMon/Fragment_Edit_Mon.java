package com.ngaoda.php.QlMon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ngaoda.php.AsyncResponse;
import com.ngaoda.php.QlDat.ClassMon;
import com.ngaoda.php.R;
import com.ngaoda.php.TaskConnect;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Edit_Mon extends Fragment implements AsyncResponse {
    EditText tenmon,gia;
    ClassMon mon;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.qlmon_fragment_edit_mon,container,false);
        anhxa();

        v.findViewById(R.id.capnhat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tenmon.getText().toString().equals("")){
                    if(!gia.getText().toString().equals("")){
                        String url = "http://dungdemo.000webhostapp.com/index.php?task=editmon";
                        Map<String,String > map=new HashMap<>();
                        map.put("mamon",mon.getMamon());
                        map.put( "tenmon",tenmon.getText().toString());
                        map.put("gia",gia.getText().toString());
                        TaskConnect t = new TaskConnect( Fragment_Edit_Mon.this, url);
                        t.setMap( map );
                        t.execute();
                    }else gia.setError("Không được bỏ trống!");
                }else tenmon.setError("Không được bỏ trống!");
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void anhxa() {
        tenmon=v.findViewById(R.id.tenmon);
        gia=v.findViewById(R.id.gia);
    }

    @Override
    public void whenfinish(String output) {
        if(output.contains("edited")){
            Toast.makeText(getActivity(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
            Fragment_List_Mon fragmentlist=(Fragment_List_Mon) getActivity().getSupportFragmentManager().findFragmentById(R.id.fraglist);
        if(fragmentlist!=null){
            fragmentlist.loadmon();
        }
        }
        if(output.contains("error")){
            Toast.makeText(getActivity(),"Xảy ra lỗi vui lòng thử lại!"+output,Toast.LENGTH_LONG).show();
        }
        if(output.contains("ConnectException")||output.contains("Timeout")){
            Toast.makeText(getActivity(), "Kiểm tra lại kết nối !", Toast.LENGTH_SHORT).show();


        }
    }
    public void setData(ClassMon mon){
        this.mon=mon;
        tenmon.setText(mon.getTenmon());
        gia.setText(mon.getGia()+"");
        v.findViewById(R.id.content).setVisibility(View.VISIBLE);
    }
}
