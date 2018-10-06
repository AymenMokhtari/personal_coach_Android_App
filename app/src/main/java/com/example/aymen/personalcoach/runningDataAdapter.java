package com.example.aymen.personalcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.RunningData;

import java.util.List;

/**
 * Created by Aymen on 10/28/2017.
 */

public class runningDataAdapter extends ArrayAdapter<RunningData> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;
ImageButton delete ;
DBhelper helper;
    public runningDataAdapter(Context context, int resourceId, List<RunningData> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
helper = new DBhelper( context );
    }


    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView runingDateItem, distanceItemRuning , averageItemRunning ,caloriesItemRunning;
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
        viewHolder.runingDateItem = (TextView) rowView.findViewById(R.id.runingDateItem);
        viewHolder.distanceItemRuning = (TextView) rowView.findViewById(R.id.distanceItemRuning);
        viewHolder.averageItemRunning = (TextView) rowView.findViewById(R.id.averageItemRunning);
        viewHolder.caloriesItemRunning = (TextView) rowView.findViewById(R.id.caloriesItemRunning);

        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        RunningData data = getItem(position);

        holder.runingDateItem.setText(data.getDate());
        holder.distanceItemRuning.setText(String.valueOf( data.getDistance()));
        holder.averageItemRunning.setText(String.valueOf(data.getAvg_speed()));
        holder.caloriesItemRunning.setText(String.valueOf(data.getCalories_burned()));
notifyDataSetChanged();

        return rowView;
    }




}