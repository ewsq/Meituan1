package com.fangming.meituan.main;

import com.fangming.meituan.R;
import com.fangming.meituan.shangjia.ShangjiaActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private MainActivity _this;
	private Button btn_home;
	private Button btn_sj;
	private Button btn_wd;
	private Button btn_gd;
	private HomeFragment homefragment;
	private StoreFragment storefragment;
	private int index = 1;// 目前展示的是哪个activity
	private FragmentManager fManager;
	private Button[] btn;// tab选项按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_this = this;
		setContentView(R.layout.activity_main);
		initView();
		initTab();
	}

	private void initView() {
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_sj = (Button) findViewById(R.id.btn_sj);
		btn_wd = (Button) findViewById(R.id.btn_wd);
		btn_gd = (Button) findViewById(R.id.btn_gd);
		btn_home.setOnClickListener(_this);
		btn_sj.setOnClickListener(_this);
		btn_wd.setOnClickListener(_this);
		btn_gd.setOnClickListener(_this);
		btn = new Button[4];
		btn[0] = btn_home;
		btn[1] = btn_sj;
		btn[2] = btn_wd;
		btn[3] = btn_gd;
	}

	private void initTab() {
		homefragment = new HomeFragment();
		storefragment = new StoreFragment();
		fManager = getSupportFragmentManager();
		fManager.beginTransaction().replace(R.id.fragment_container, homefragment).show(homefragment).commit();
		index = 0;
		btn[index].setSelected(true);
	}

	private void refreshFragment(int index, int currentindex, Fragment fragment) {
		if (index != currentindex) {
			btn[index].setSelected(false);
			fManager.beginTransaction().replace(R.id.fragment_container, fragment).show(fragment).commit();
			btn[currentindex].setSelected(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			refreshFragment(index, 0, homefragment);
			index = 0;
			break;
		case R.id.btn_sj:
			refreshFragment(index, 1, storefragment);
			index = 1;
			break;
		case R.id.btn_wd:
			refreshFragment(index, 2, storefragment);
			index = 2;
			break;
		case R.id.btn_gd:
			refreshFragment(index, 3, storefragment);
			index = 3;
			break;

		default:
			break;
		}

	}

}
