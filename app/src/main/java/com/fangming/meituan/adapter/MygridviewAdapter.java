package com.fangming.meituan.adapter;

import java.util.List;

import com.fangming.meituan.R;
import com.fangming.meituan.data.TwoLevelMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MygridviewAdapter extends BaseAdapter{

	public List<TwoLevelMenu> data;
	private LayoutInflater _inflater;
	private Context context;

	public MygridviewAdapter(Context context, List<TwoLevelMenu> data) {
		this.data = data;
		this.context = context;
		_inflater = LayoutInflater.from(context);
	}

	public void updateList(List<TwoLevelMenu> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = _inflater.inflate(R.layout.grildview_adapter_item, null);
			holder = new ViewHolder();
			holder.tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);
			holder.iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_menu.setText(data.get(position).getName());
		holder.iv_menu.setImageResource(data.get(position).getId());
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_menu;// ²Ëµ¥ÎÄ×Ö
		private ImageView iv_menu;//²Ëµ¥Í¼±ê

	}
}
