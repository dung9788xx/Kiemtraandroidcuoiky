package com.ngaoda.php.QLNhanVien.DanhSach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

public class Fragment_List_Nhan_Vien extends Fragment implements AsyncResponse {
    View v;
    ListView lv;
    List<ClassNhanVien> listnv = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    TextView error;
    boolean isdelete=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      v=inflater.inflate(R.layout.qlnv_fragment_list_nhan_vien,container,false);

        anhxa();
        loaddata();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (AdminActivity.username.equals("admin")) {
                    if(!listnv.get(position).username.equals("admin")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Xác nhận xóa nhân viên?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String url = "http://dungdemo.000webhostapp.com/index.php?task=xoanhanvien";
                                Map<String, String> map = new HashMap<>();
                                map.put("username", listnv.get(position).getUsername());
                                TaskConnect t = new TaskConnect(Fragment_List_Nhan_Vien.this, url);
                                t.setMap(map);
                                t.execute();
                                loaddata();
                                dialogInterface.dismiss();
                                isdelete=true;
                                v.findViewById(R.id.progress).setVisibility(View.VISIBLE);

                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        Toast.makeText(getActivity(), "Không được phép xóa tài khoản này!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Bạn không có quyền xóa !", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent t = new Intent(getActivity(), Activity_Edit_NhanVien.class);
                t.putExtra("nv", listnv.get(position));
                startActivity(t);
            }
        });

        return v;
    }
    public void anhxa() {
        lv = (ListView) v.findViewById(R.id.lv);
        error = v.findViewById(R.id.connecterror);

    }
    @Override
    public void whenfinish(String output) {
        v.findViewById(R.id.progress).setVisibility(View.GONE);
        if (output.contains("getlistnhanvien")) {
            try {
                JSONArray jsonArray = new JSONArray(output.substring(output.indexOf("|") + 1));
                listnv.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    listnv.add(new ClassNhanVien(object.getString("manv"), object.getString("hoten"), object.getString("ngaysinh")
                            , object.getString("diachi"), object.getString("sdt"), object.getString("username")
                            , object.getString("password"), object.getString("level"), object.getString("luong")));
                }
                arrayAdapter = new ArrayAdapter(getActivity(), R.layout.item_nhanvien, listnv) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = layoutInflater.inflate(R.layout.item_nhanvien, null);
                        TextView hoten = (TextView) view.findViewById(R.id.hoten);
                        TextView username = (TextView) view.findViewById(R.id.username);
                        TextView password = (TextView) view.findViewById(R.id.password);
                        TextView sdt = (TextView) view.findViewById(R.id.sdt);
                        ClassNhanVien n = listnv.get(position);
                        hoten.setText(n.getHoten());
                        sdt.setText(n.getSdt());
                        username.setText("User: " + n.getUsername());
                        password.setText("Password:  " + n.getPassword());
                        return view;
                    }
                };
                lv.setAdapter(arrayAdapter);
                v.findViewById(R.id.content).setVisibility(View.VISIBLE);
            } catch (JSONException e) {

            }
        }
        if (output.contains("using"))
            Toast.makeText(getActivity(), "Tài khoản đang được sử dụng!", Toast.LENGTH_LONG).show();
        if (output.contains("deleted"))
            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_LONG).show();
        if (output.contains("error"))
            Toast.makeText(getActivity(), "Xảy ra lỗi vui lòng thử lại!", Toast.LENGTH_LONG).show();

        if(output.contains("ConnectException")||output.contains("Timeout")){
            if(isdelete){
                Toast.makeText(getActivity(), "Xảy ra lỗi vui lòng thử lại!", Toast.LENGTH_LONG).show();
                isdelete=false;
            }else{
                v.findViewById(R.id.content).setVisibility(View.GONE);
                v.findViewById(R.id.progress).setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            }

        }


    }
    public void loaddata() {
        String url = "http://dungdemo.000webhostapp.com/index.php?task=getlistnhanvien";
        TaskConnect t = new TaskConnect(this, url);
        t.execute();

    }
}
