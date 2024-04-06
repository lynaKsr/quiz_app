package com.example.quiz_app.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quiz_app.R;
import com.example.quiz_app.model.CategoryModel;

import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {

    private final List<CategoryModel> categories;

    private final LayoutInflater inflater;

    public CategorySpinnerAdapter(Context context, List<CategoryModel> categories) {
        this.categories = categories;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        CategorySpinnerViewHolder categorySpinnerViewHolder;

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.item_category_spinner, parent, false);
            categorySpinnerViewHolder = new CategorySpinnerViewHolder(view);
            view.setTag(categorySpinnerViewHolder);
        } else {
            view = convertView;
            categorySpinnerViewHolder = (CategorySpinnerViewHolder) view.getTag();
        }

        categorySpinnerViewHolder.name.setText(this.categories.get(position).getName());

        return view;
    }

    private static class CategorySpinnerViewHolder {
        final TextView name;

        CategorySpinnerViewHolder(View row) {
            this.name = row.findViewById(R.id.filterName);
        }
    }

}
