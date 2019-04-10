package com.ngaoda.php.QlMon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ngaoda.php.AsyncResponse;
import com.ngaoda.php.MoneyType;
import com.ngaoda.php.QlDat.ClassMon;
import com.ngaoda.php.R;
import com.ngaoda.php.TaskConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_List_Mon extends Fragment implements AsyncResponse {
    ListView lv;
    View v;
    ArrayList<ClassMon> listmon = new ArrayList<>();
    ArrayAdapter<ClassMon> arrayAdapter;
    truyendata truyendata;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.qlmon_fragment_list_mon,container,false);
        lv = (ListView)v.findViewById(R.id.lv);
        truyendata=(truyendata)getActivity();
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.addmon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Activity_Add_Mon.class));
            }
        });
        loadmon();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                truyendata.data(listmon.get(position));

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Toàn bộ thông tin về món này sẽ bị xóa ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = "http://dungdemo.000webhostapp.com/index.php?task=xoamon";
                        Map<String,String > map=new HashMap<>();
                        map.put( "mamon",listmon.get( position).getMamon());
                        TaskConnect t = new TaskConnect( Fragment_List_Mon.this, url);
                        t.setMap( map );
                        t.execute();
                        loadmon();
                        dialogInterface.dismiss();

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
                return true;
            }
        });
        return v;
    }


    public void  loadmon() {
        String url = "http://dungdemo.000webhostapp.com/index.php?task=getallmon";
        TaskConnect t = new TaskConnect(this, url);
        t.execute();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void whenfinish(String output) {
        v.findViewById(R.id.progress).setVisibility(View.GONE);
        try{
            if (output.contains("getmon")) {
                listmon.clear();
                JSONArray array = new JSONArray(output.substring(output.indexOf("|") + 1));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    ClassMon m = new ClassMon();
                    m.setMamon(object.getString("mamon"));
                    m.setTenmon(object.getString("tenmon"));
                    m.setGia(object.getInt("gia"));
                    listmon.add(m);

                }
                arrayAdapter=new ArrayAdapter<ClassMon>(getActivity(),R.layout.qlmon_item_mon,listmon){
                    @Override
                    public View getView(int position,  View convertView, ViewGroup parent) {
                        LayoutInflater layoutInflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v=layoutInflater.inflate(R.layout.qlmon_item_mon,null);
                        TextView tenmon=v.findViewById(R.id.tenmon);
                        TextView gia=v.findViewById(R.id.gia);
                        ClassMon m=listmon.get(position);
                        tenmon.setText(m.getTenmon());
                        gia.setText(MoneyType.toMoney(m.getGia()));
                        return v;
                    }
                };
                lv.setAdapter(arrayAdapter);
                v.findViewById(R.id.content).setVisibility(View.VISIBLE);
            }
            if(output.contains("using"))
                Toast.makeText(getActivity(), "Món đang được sử dụng! ", Toast.LENGTH_LONG).show();
            if(output.contains("deletesuccess")){
                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_LONG).show();
            }
            if(output.contains("error"))   Toast.makeText(getActivity(),"Xảy ra lỗi vui lòng thử lại !",Toast.LENGTH_LONG).show();

            if(output.contains("ConnectException")||output.contains("Timeout")){
                v.findViewById(R.id.progress).setVisibility(View.GONE);
                v.findViewById(R.id.error).setVisibility(View.VISIBLE);


            }
        }catch (JSONException e){

        }
    }

}
