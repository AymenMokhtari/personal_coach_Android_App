package com.example.aymen.personalcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatsecret.platform.model.CompactFood;

import java.util.List;

/**
 * Created by Aymen on 10/28/2017.
 */

public class FoodCustomAdapter  extends ArrayAdapter<CompactFood> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;

    public FoodCustomAdapter(Context context, int resourceId, List<CompactFood> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView FoodNameText, FoodDescText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        //Réutiliser les Views
        if (rowView == null) {
            rowView = inflater.inflate(resourceId, parent, false);
        }

        //Configuration du ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.FoodNameText = (TextView) rowView.findViewById(R.id.foodNameItem);
        viewHolder.FoodDescText = (TextView) rowView.findViewById(R.id.foodDescItem);
        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        CompactFood food = getItem(position);

        holder.FoodNameText.setText(food.getName());
        holder.FoodDescText.setText(food.getDescription());
notifyDataSetChanged();

        return rowView;
    }


}