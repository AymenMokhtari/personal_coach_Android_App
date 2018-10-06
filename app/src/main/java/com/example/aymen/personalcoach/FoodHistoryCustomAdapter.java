package com.example.aymen.personalcoach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aymen.personalcoach.data.DBhelper;

import java.util.List;

/**
 * Created by Aymen on 10/28/2017.
 */

public class FoodHistoryCustomAdapter extends ArrayAdapter<FoodHistory> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;
ImageButton delete ;
DBhelper helper;
    public FoodHistoryCustomAdapter(Context context, int resourceId, List<FoodHistory> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
helper = new DBhelper( context );
    }


    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView FoodNameHistory, FoodQteHistory , FoodCaloriesHistory;
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
        viewHolder.FoodNameHistory = (TextView) rowView.findViewById(R.id.foodNameItemh);
        viewHolder.FoodQteHistory = (TextView) rowView.findViewById(R.id.qtetxtItem);
        viewHolder.FoodCaloriesHistory = (TextView) rowView.findViewById(R.id.caloriestxtItem);
        delete = rowView.findViewById( R.id.deleteFoodHistory );
      delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FoodHistory foodHistory = (FoodHistory)getItem( position );

                AlertDialog diaBox = AskOption(foodHistory.getId() , position );

                diaBox.show();

            }
        } );

        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        FoodHistory food = getItem(position);

        holder.FoodNameHistory.setText(food.getFoodname());
        holder.FoodQteHistory.setText(String.valueOf( food.getQte()));
        holder.FoodCaloriesHistory.setText(String.valueOf(food.getCalories()));
notifyDataSetChanged();

        return rowView;
    }


    private AlertDialog AskOption(int id , int position)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        helper.deleteFoodHistory( id, new DBhelper.VolleyCallbackDeleteFood() {
                            @Override
                            public void onSuccessResponse() {
                                remove( getItem( position ) );
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String msg) {
                                Toast.makeText( getContext() ,msg , Toast.LENGTH_SHORT ).show();
                            }
                        } );




                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}