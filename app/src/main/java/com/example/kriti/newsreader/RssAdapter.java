package com.example.kriti.newsreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.kriti.newsreader.R;

public class RssAdapter extends BaseAdapter implements Filterable {

	private List<RssItem> items;
	private List<RssItem> originalData = null;
	private final Context context;
	private ItemFilter mFilter = new ItemFilter();

	public RssAdapter(Context context, List<RssItem> items) {
		this.originalData = items;
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public int getViewTypeCount() {

		return 2;
	}

	@Override
	public int getItemViewType(int position) {

		if(position==0)
			return 0;
		else
			return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			if(position==0)
			{
				convertView = View.inflate(context, R.layout.first_item, null);
				holder = new ViewHolder();
				holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
				convertView.setTag(holder);

			}
			else {
				convertView = View.inflate(context, R.layout.rss_item, null);
				holder = new ViewHolder();
				holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.itemTitle.setText(items.get(position).getTitle());
		return convertView;
	}

	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase();



			FilterResults results = new FilterResults();

			final List<RssItem> list = originalData;

			int count = list.size();
			final ArrayList<RssItem> nlist = new ArrayList<RssItem>(count);

			RssItem filterableString ;

			for (int i = 0; i < count; i++) {
				filterableString = list.get(i);
				if (filterableString.getTitle().toString().toLowerCase().contains(filterString)) {
					nlist.add(filterableString);
				}
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			items = (ArrayList<RssItem>) results.values;
			notifyDataSetChanged();
		}

	}


	static class ViewHolder {
		TextView itemTitle;
	}
}
