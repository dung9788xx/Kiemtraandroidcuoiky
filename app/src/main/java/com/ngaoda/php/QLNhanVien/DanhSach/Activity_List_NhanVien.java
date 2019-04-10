package com.ngaoda.php.QLNhanVien.DanhSach;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ngaoda.php.AdminActivity;
import com.ngaoda.php.AsyncResponse;
import com.ngaoda.php.QLNhanVien.Activity_QLNhanVien;
import com.ngaoda.php.QLNhanVien.ClassNhanVien;
import com.ngaoda.php.R;
import com.ngaoda.php.TaskConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_List_NhanVien extends AppCompatActivity {
    ListView lv;
    List<ClassNhanVien> listnv = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    TextView error;
    boolean isdelete=false;
    ViewPager viewPager;
    ViewpageAdapter viewpageAdapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlnv_activity_nhan_vien);
        setTitle(Html.fromHtml("<h3>Danh sách nhân viên</h3>"));
        viewpageAdapter=new ViewpageAdapter(getSupportFragmentManager());
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.page);
        tabLayout.setupWithViewPager(viewPager);
        viewpageAdapter.setViewPager(viewPager);
        viewPager.setAdapter(viewpageAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
              if(i==0&&viewpageAdapter.getFragment_add_nhan_vien().isnewdata){
                  viewpageAdapter.getFragment_list_nhan_vien().loaddata();
                  viewpageAdapter.getFragment_add_nhan_vien().isnewdata=false;
              }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(this, Activity_QLNhanVien.class));
    }


}
