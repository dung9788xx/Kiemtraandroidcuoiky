package com.ngaoda.php.QlMon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

public class Activity_List_Mon extends AppCompatActivity implements truyendata{
    ListView lv;
    ArrayList<ClassMon> listmon = new ArrayList<>();
    ArrayAdapter<ClassMon> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlmon_activity__list__mon);
        setTitle(Html.fromHtml("<h3>Danh sách món</h3>"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Activity_List_Mon.this, AdminActivity.class));
        finishAffinity();

    }

    @Override
    public void data(ClassMon mon) {
        Fragment_Edit_Mon fragmentedit=(Fragment_Edit_Mon) getSupportFragmentManager().findFragmentById(R.id.fragedit);
        if(fragmentedit!=null&&getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            fragmentedit.setData(mon);
        }else{
            Intent t=new Intent(this,Activity_Edit_Mon.class);
            t.putExtra("mon",mon);
            startActivity(t);
        }

    }

}
