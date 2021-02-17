package com.fangming.meituan.main;

import java.util.ArrayList;
import java.util.List;

import com.fangming.meituan.R;
import com.fangming.meituan.adapter.MyViewPagerAdapter;
import com.fangming.meituan.adapter.MygridviewAdapter;
import com.fangming.meituan.data.TwoLevelMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

public class HomeFragment extends Fragment {

	private String Tag = "HomeFragment";
	private List<TwoLevelMenu> strs = new ArrayList<TwoLevelMenu>();//vipager第一页
	private List<TwoLevelMenu> strs2 = new ArrayList<TwoLevelMenu>();//vipager第二页
	private MygridviewAdapter mgAdapter1;//vipager第一页适配器
	private MygridviewAdapter mgAdapter2;//vipager第二页适配器
	private GridView gv_one;//vipager第一页gridview
	private GridView gv_two;//vipager第二页gridview
	
	private ViewPager vp_menu;
	private List<View> pagerView;
	private MyViewPagerAdapter pagerAdapter;//vipager适配器
	private ImageView iv_one;
	private ImageView iv_two;
	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.e(Tag, "onAttach");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(Tag, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		InitDate();
		InitView(v,inflater);
		return v;
	}

	private void InitDate() {//初始化数据
		strs.add(new TwoLevelMenu("美食", R.drawable.ic_category_0));
		strs.add(new TwoLevelMenu("电影", R.drawable.ic_category_1));
		strs.add(new TwoLevelMenu("酒店", R.drawable.ic_category_2));
		strs.add(new TwoLevelMenu("KTV", R.drawable.ic_category_3));
		strs.add(new TwoLevelMenu("外卖", R.drawable.travel__icon_hotel_reserve));
		strs.add(new TwoLevelMenu("优惠买单", R.drawable.travel__icon_hotel_reserve));
		strs.add(new TwoLevelMenu("周边游", R.drawable.ic_category_6));
		strs.add(new TwoLevelMenu("火车票机票", R.drawable.ic_category_6));
		strs.add(new TwoLevelMenu("丽人", R.drawable.ic_category_11));
		strs.add(new TwoLevelMenu("休闲娱乐", R.drawable.ic_category_10));
		strs2.add(new TwoLevelMenu("今日新单", R.drawable.ic_category_4));
		strs2.add(new TwoLevelMenu("购物", R.drawable.ic_category_14));
		strs2.add(new TwoLevelMenu("旅游", R.drawable.ic_category_13));
		strs2.add(new TwoLevelMenu("生活服务", R.drawable.ic_category_9));
		strs2.add(new TwoLevelMenu("足疗按摩", R.drawable.ic_category_12));
		strs2.add(new TwoLevelMenu("自助餐", R.drawable.ic_category_10));
		strs2.add(new TwoLevelMenu("甜点饮品", R.drawable.ic_category_8));
		strs2.add(new TwoLevelMenu("小吃快餐", R.drawable.ic_category_7));
		strs2.add(new TwoLevelMenu("景点门票", R.drawable.ic_category_16));
		strs2.add(new TwoLevelMenu("全部分类", R.drawable.ic_category_15));
	}

	private void InitView(View v,LayoutInflater inflater) {
		iv_one=(ImageView) v.findViewById(R.id.iv_one);
		iv_two=(ImageView) v.findViewById(R.id.iv_two);
		
		mgAdapter1=new MygridviewAdapter(getActivity(), strs);
		pagerView=new ArrayList<View>();
		View view1=inflater.inflate(R.layout.viewpager_one, null);
		gv_one=(GridView) view1.findViewById(R.id.gv_one);
		gv_one.setAdapter(mgAdapter1);
		pagerView.add(view1);
		
		mgAdapter2=new MygridviewAdapter(getActivity(), strs2);
		View view2=inflater.inflate(R.layout.viewpager_two, null);
		gv_two=(GridView) view2.findViewById(R.id.gv_two);
		gv_two.setAdapter(mgAdapter2);
		pagerView.add(view2);
		
		
		
		vp_menu=(ViewPager)v.findViewById(R.id.vp_menu);
		pagerAdapter=new MyViewPagerAdapter(getActivity(), pagerView);
		vp_menu.setAdapter(pagerAdapter);
		iv_one.setSelected(true);
		vp_menu.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0==0){
					iv_one.setSelected(true);
					iv_two.setSelected(false);
				}else{
					iv_one.setSelected(false);
					iv_two.setSelected(true);
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(Tag, "onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(Tag, "onActivityCreated");
		
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e(Tag, "onResume");
	}

}
