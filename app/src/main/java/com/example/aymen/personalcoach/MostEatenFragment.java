package com.example.aymen.personalcoach;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.Session;

import java.util.ArrayList;

/**
 */
public class MostEatenFragment extends Fragment {
    ArrayList<FoodHistory> mostEatenList;
ListView mosteatenlv;
DBhelper helper;
    Session session ;
    int idSP;
    SharedPreferences prefs;
    @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_most_eaten , container, false);
         RequestQueue queue = Volley.newRequestQueue(getActivity());
        mosteatenlv  = view.findViewById( R.id.mostEatenListview );
        session = new Session( getActivity() );
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

         idSP =    prefs.getInt( "id" ,0);



helper = new DBhelper( getActivity() );

helper.getMostEatenFood(idSP , new DBhelper.VolleyCallbackList() {
    @Override
    public void onSuccessResponse(ArrayList<FoodHistory> result) {
        mosteatenlv.setAdapter( new FoodHistoryCustomAdapter( getActivity() , R.layout.one_food_item, result ) );
    }

    @Override
    public void onFail(String msg) {

    }
} );


        return view;
     }
   }
     
   