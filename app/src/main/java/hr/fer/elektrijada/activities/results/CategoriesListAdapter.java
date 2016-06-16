package hr.fer.elektrijada.activities.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;

/**
 * Created by Ivica Brebrek on 3.6.2016..
 */
public class CategoriesListAdapter extends BaseAdapter {

    private List<CategoryFromDb> listData;
    private LayoutInflater layoutInflater;

    public CategoriesListAdapter(Context context, List<CategoryFromDb> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_categories_list, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.category_name_row);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(listData.get(position).getName());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
    }
}
