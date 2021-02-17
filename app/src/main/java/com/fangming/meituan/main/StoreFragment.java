package com.fangming.meituan.main;


import java.util.ArrayList;
import java.util.List;

import com.fangming.meituan.R;
import com.fangming.meituan.adapter.SjAdapter;
import com.fangming.meituan.data.T_SjData;
import com.fangming.meituan.shangjia.ShangjiaActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StoreFragment extends Fragment{
	private ListView listview_sj;
	private SjAdapter myAdapter;
	private List<T_SjData> mData=new ArrayList<T_SjData>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_store, container, false);
		InitView(v);
		return v;
	}

	private void InitView(View v) {
		listview_sj=(ListView)	v.findViewById(R.id.listview_sj);	
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myAdapter=new SjAdapter(getActivity(), mData);
		listview_sj.setAdapter(myAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

}
