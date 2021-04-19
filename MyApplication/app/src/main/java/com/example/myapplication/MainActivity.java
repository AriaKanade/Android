package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    int ActiveButtonId;

    LinearLayout Mes;
    LinearLayout Fra;
    LinearLayout Setting;
    LinearLayout Address;

    FrameLayout MainFrameLayout;

    FragmentManager fm;
    FragmentTransaction ft;

    Fragment MesFragment = new MesFragment();
    Fragment FraFragment = new FraFragment();
    Fragment AddressFragment = new AddressFragment();
    Fragment SettingFragment = new SettingsFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveButtonId = R.id.MesButton;
        Mes = findViewById(R.id.Mes);
        Fra = findViewById(R.id.Fra);
        Setting = findViewById(R.id.Setting);
        Address = findViewById(R.id.Address);


        MainFrameLayout = findViewById(R.id.MainFragment);
        initFragment();

        Mes.setOnClickListener(this);
        Fra.setOnClickListener(this);
        Setting.setOnClickListener(this);
        Address.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.Mes:
                    replaceFragment(MesFragment);
                    changeButtonToInactive();
                    changeButtonToActive(R.id.MesButton);
                    ActiveButtonId = R.id.MesButton;
                    break;
                case R.id.Fra:
                    replaceFragment(FraFragment);
                    changeButtonToInactive();
                    changeButtonToActive(R.id.FraButton);
                    ActiveButtonId = R.id.FraButton;
                    break;
                case R.id.Setting:
                    replaceFragment(SettingFragment);
                    changeButtonToInactive();
                    changeButtonToActive(R.id.SettingButton);
                    ActiveButtonId = R.id.SettingButton;
                    break;
                case R.id.Address:
                    replaceFragment(AddressFragment);
                    changeButtonToInactive();
                    changeButtonToActive(R.id.AddressButton);
                    ActiveButtonId = R.id.AddressButton;
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void replaceFragment(Fragment fragment){
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        ft.replace(R.id.MainFragment,fragment).commit();
    }

    public void changeButtonToActive(int ButtonId){
       ImageButton button =  findViewById(ButtonId);
       switch (ButtonId){
           case R.id.MesButton:
               button.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed,null));
               break;
           case R.id.FraButton:
               button.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed,null));
               break;
           case R.id.SettingButton:
               button.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed,null));
               break;
           case R.id.AddressButton:
               button.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed,null));
               break;
           default:
               break;
       }

    }

    public void changeButtonToInactive(){
        ImageButton button = findViewById(ActiveButtonId);

        switch (ActiveButtonId){
            case R.id.MesButton:
                button.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal,null));
                break;
            case R.id.FraButton:
                button.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal,null));
                break;
            case R.id.SettingButton:
                button.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal,null));
                break;
            case R.id.AddressButton:
                button.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal,null));
                break;
            default:
                break;
        }

    }

    public void initFragment(){
        replaceFragment(MesFragment);
        changeButtonToActive(R.id.MesButton);
    }
}