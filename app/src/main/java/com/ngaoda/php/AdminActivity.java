package com.ngaoda.php;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ngaoda.php.QLNhanVien.Activity_QLNhanVien;
import com.ngaoda.php.QlBan.Activity_List_Ban;
import com.ngaoda.php.QlDat.Activity_Table_State;
import com.ngaoda.php.QlMon.Activity_List_Mon;
import com.ngaoda.php.ThongKe.Activity_List_Thongke;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public  static String url="http://dungdemo.000webhostapp.com/index.php";
    TextView tvusername;
    public static String username;
    int checkexit=0;
    int checkexit1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý quán Cafe");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvusername=(TextView) findViewById( R.id.username );
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            username=bundle.getString( "username" );
        }
        tvusername.setText(username );

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            checkexit++;
            if(checkexit==1){
                Toast.makeText(AdminActivity.this,"Nhấn lần nữa để đăng xuất",Toast.LENGTH_LONG).show();
            }
            if(checkexit==2){
                startActivity(new Intent(this,MainActivity.class));
            }
        }
    }
    public void click(View v){
        int id=v.getId();
        switch (id){
            case R.id.qlnv:
                startActivity( new Intent( AdminActivity.this, Activity_QLNhanVien.class ) );

                break;
            case R.id.dat:
                Intent t=new Intent(  AdminActivity.this, Activity_Table_State.class );
                t.putExtra("isadmin",1);
                t.putExtra( "username",username );
                startActivity( t );
                break;
            case R.id.qlb:
                startActivity(new Intent(AdminActivity.this, Activity_List_Ban.class));
                break;
            case R.id.thongke:
                startActivity(new Intent(AdminActivity.this, Activity_List_Thongke.class));
                break;
            case R.id.qlmon:
                Intent t1=new Intent(  AdminActivity.this, Activity_List_Mon.class );
                startActivity( t1 );
                break;
            case R.id.exit:
                checkexit1++;
                if(checkexit1>=2){
                    startActivity(new Intent(this,MainActivity.class));
                }else   Toast.makeText(AdminActivity.this,"Nhấn lần nữa để đăng xuất",Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.gt) {
           startActivity(new Intent(this,Activity_Gioi_Thieu.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
