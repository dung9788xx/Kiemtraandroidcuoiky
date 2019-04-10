package com.ngaoda.php.QLNhanVien.DanhSach;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewpageAdapter extends FragmentStatePagerAdapter {
    Fragment_List_Nhan_Vien fragment_list_nhan_vien=new Fragment_List_Nhan_Vien();
    Fragment_Add_Nhan_Vien fragment_add_nhan_vien=new Fragment_Add_Nhan_Vien();
    ViewPager viewPager;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        fragment_add_nhan_vien.setViewPager(viewPager);
    }

    public ViewpageAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment_Add_Nhan_Vien getFragment_add_nhan_vien() {
        return fragment_add_nhan_vien;
    }

    public Fragment_List_Nhan_Vien getFragment_list_nhan_vien() {
        return fragment_list_nhan_vien;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return fragment_list_nhan_vien;
        }
        if(i==1){
            return fragment_add_nhan_vien;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==1){
            return "Danh sách nhân viên";
        }else return "Thêm nhân viên";
    }

}
