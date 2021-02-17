package com.fangming.meituan.shangjia;

import java.util.ArrayList;
import java.util.List;

import com.fangming.meituan.R;
import com.fangming.meituan.adapter.SjAdapter;
import com.fangming.meituan.data.T_SjData;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author fangming
 * ÉÌÆ·Ê×Ò³activity
 */
public class ShangjiaActivity extends Activity {
	
	private ShangjiaActivity _this;
	private ListView listview_sj;
	private SjAdapter myAdapter;
	private List<T_SjData> mData=new ArrayList<T_SjData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangjia);
		_this=this;
		InitView();
	}

	private void InitView() {
		listview_sj=(ListView)	findViewById(R.id.listview_sj);	
		myAdapter=new SjAdapter(_this, mData);
		listview_sj.setAdapter(myAdapter);
	}
	
	

}
