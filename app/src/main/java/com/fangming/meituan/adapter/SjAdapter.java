package com.fangming.meituan.adapter;

import java.util.List;

import com.fangming.meituan.R;
import com.fangming.meituan.data.T_SjData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author fangming 商家adapterF
 */
public class SjAdapter extends BaseAdapter {

	public List<T_SjData> T_SjDataList;
	private LayoutInflater _inflater;
	private Context context;

	public SjAdapter(Context context, List<T_SjData> T_SjDataList) {
		this.T_SjDataList = T_SjDataList;
		this.context = context;
		_inflater = LayoutInflater.from(context);
	}

	public void updateList(List<T_SjData> T_SjDataList) {
		this.T_SjDataList = T_SjDataList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return T_SjDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return T_SjDataList.get(position);
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
			convertView = _inflater.inflate(R.layout.listview_item_activity_sj, null);
			holder = new ViewHolder();
			holder.img_url = (ImageView) convertView.findViewById(R.id.img_url);
			holder.tv_storename = (TextView) convertView.findViewById(R.id.tv_storename);
			holder.tv_pjnum = (TextView) convertView.findViewById(R.id.tv_pjnum);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// holder.img_url.setBackgroundResource(T_SjDataList.get(position).type);
		holder.tv_storename.setText(T_SjDataList.get(position).getStoreName());
		holder.tv_pjnum
				.setText(T_SjDataList.get(position).getEltNum() + context.getResources().getString(R.string.pingjia));
		holder.tv_type.setText(T_SjDataList.get(position).getType());
		holder.tv_add.setText(T_SjDataList.get(position).getAdd());
		return convertView;
	}

	private class ViewHolder {
		public ImageView img_url;// 图片
		public TextView tv_storename;// 店名
		public TextView tv_pjnum;// 评价数
		public TextView tv_type;// 类别
		public TextView tv_add;// 地址

	}
}
