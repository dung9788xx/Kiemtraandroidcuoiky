package com.ngaoda.php.QLNhanVien.DanhSach;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ngaoda.php.AsyncResponse;
import com.ngaoda.php.QlMon.Fragment_Edit_Mon;
import com.ngaoda.php.R;
import com.ngaoda.php.TaskConnect;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Add_Nhan_Vien extends Fragment implements AsyncResponse {
    View v;
    EditText hoten,ngaysinh,diachi,sdt,username,password,repassword,luong;
    CheckBox checkBox;
    public boolean isnewdata=false;
    ViewPager viewPager;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      v=inflater.inflate(R.layout.qlnv_fragment_add_nhan_vien,container,false);
        anhxa();
       v. findViewById( R.id.date ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdatepicker();
            }
        } );
        v.findViewById(R.id.ngaysinh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdatepicker();
            }
        });
        v.findViewById( R.id.them ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkformed()){
                    String url="http://dungdemo.000webhostapp.com/index.php?task=addnhanvien";
                    Map<String,String > map=new HashMap<>(  );
                    map.put( "hoten",hoten.getText().toString() );
                    map.put( "ngaysinh",ngaysinh.getText().toString() );
                    map.put( "diachi",diachi.getText().toString() );
                    map.put( "sdt",sdt.getText().toString() );
                    map.put( "username",username.getText().toString() );
                    map.put( "password",password.getText().toString() );
                    map.put( "luong",luong.getText().toString() );
                    if(checkBox.isChecked()){
                        map.put("level","1");
                    }else map.put("level","0");
                    TaskConnect taskConnect=new TaskConnect( Fragment_Add_Nhan_Vien.this,url );
                    taskConnect.setMap( map );
                    taskConnect.execute(  );

                }
            }
        } );
      return v;
    }
    public boolean checkformed(){
        boolean status=true;
        if(hoten.getText().toString().equals( "" )) {status=false;hoten.setError( "Không được bỏ trống" );};
        if(ngaysinh.getText().toString().equals( "" )){status=false;ngaysinh.setError( "Không được bỏ trống" );}
        if(diachi.getText().toString().equals( "" )){status=false;diachi.setError( "Không được bỏ trống" );}
        if(sdt.getText().toString().equals( "" )){status=false;sdt.setError( "Không được bỏ trống" );}
        if(username.getText().toString().equals( "" )){status=false;username.setError( "Không được bỏ trống" );}
        if(password.getText().toString().equals( "" )){status=false;password.setError( "Không được bỏ trống" );}
        if(!username.getText().toString().matches("[a-zA-Z0-9 ]*" )){
            status=false;username.setError( "Không chứa ký tự đặc biệt" );
        }
        if(!password.getText().toString().equals( "" )){
            if(!password.getText().toString().equals( repassword.getText().toString())){
                status=false;
                repassword.setError( "Hai mật khẩu không khớp" );
            }
        }
        if(luong.getText().toString().equals( "" )) {status=false;luong.setError( "Không được bỏ trống" );};
        return status;

    }
    public  void showdatepicker(){
        final Calendar c = Calendar.getInstance();
        int  mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int  mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        ngaysinh.setText(dayOfMonth + "/" + (monthOfYear +1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void anhxa(){
        hoten=(EditText) v.findViewById( R.id.hoten );
        ngaysinh=(EditText) v.findViewById( R.id.ngaysinh );
        diachi=(EditText) v.findViewById( R.id.diachi );
        sdt=(EditText) v.findViewById( R.id.sdt );
        username=(EditText) v.findViewById( R.id.username );
        password=(EditText) v.findViewById( R.id.password );
        repassword=(EditText) v.findViewById( R.id.repassword );
        luong=v.findViewById(R.id.luong);
        checkBox=v.findViewById(R.id.cb);
    }
    @Override
    public void whenfinish(String output) {
        v.findViewById(R.id.progress).setVisibility(View.GONE);
        if(output.contains( "dacouser" )){
            Toast.makeText( getActivity(),"Tài khoản đã tồn tại" ,Toast.LENGTH_LONG).show();
            username.setError( "Tài khoản đã tồn tại" );

        }else
        if(output.contains("themthanhcong"))    {
            isnewdata=true;
            Toast.makeText( getActivity(),"Thêm thành công" ,Toast.LENGTH_LONG).show();
            xoa();
            viewPager.setCurrentItem(0);
        }else {
            Toast.makeText(getActivity(), "Xảy ra lỗi vui lòng thử lại!", Toast.LENGTH_LONG).show();
            v.findViewById(R.id.content).setVisibility(View.VISIBLE);

        }
    }

    private void xoa() {
        hoten.setText("");
        ngaysinh.setText("");
        diachi.setText("");
        sdt.setText("");
        username.setText("");
        password.setText("");
        repassword.setText("");
        luong.setText("");
        checkBox.setChecked(false);
    }
}
