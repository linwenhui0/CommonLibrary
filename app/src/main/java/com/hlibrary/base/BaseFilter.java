package com.hlibrary.base;

import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 林文辉
 * @param <T>
 *            过滤的实体
 * @version v1.0.0
 * @since 2015-01-27
 * 
 */
public abstract class BaseFilter<T> extends Filter {

	protected List<T> mData;
	private ArrayList<T> mFilterData;
	private BaseAdapter mAdapter;

	/**
	 * @param data
	 *            BaseAdapter中的数据集
	 */
	public BaseFilter(List<T> data, BaseAdapter mAdapter) {
		mData = data;
		this.mAdapter = mAdapter;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		if (mFilterData == null) {
			mFilterData = new ArrayList<T>(mData);
		}

		if (prefix == null || prefix.length() == 0) {
			ArrayList<T> list = mFilterData;
			results.values = list;
			results.count = list.size();
		} else {

			ArrayList<T> unfilteredValues = mFilterData;
			int count = unfilteredValues.size();

			ArrayList<T> newValues = new ArrayList<T>(count);

			for (int i = 0; i < count; i++) {
				T h = unfilteredValues.get(i);
				final boolean isInclude = isInclude(h, prefix);
				if (isInclude) {
					newValues.add(h);
					continue;
				}
			}

			results.values = newValues;
			results.count = newValues.size();
		}
		return results;
	}

	/**
	 * @param model
	 * @param prefix
	 *            过虑的关键字
	 * @return 过滤条件
	 */
	public abstract boolean isInclude(T model, CharSequence prefix);

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		mData = (List<T>) results.values;
		if (results.count > 0) {
			mAdapter.notifyDataSetChanged();
		} else {
			mAdapter.notifyDataSetInvalidated();
		}
	}

}
