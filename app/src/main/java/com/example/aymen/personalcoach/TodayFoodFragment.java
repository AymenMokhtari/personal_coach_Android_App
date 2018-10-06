package com.example.aymen.personalcoach;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.Session;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class TodayFoodFragment extends Fragment {
    final String TAG  = "todayFood";
  private   ListView listView;
  DBhelper helper;
    Session session ;
    int idSP;
    SharedPreferences prefs;
      @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_today_food , container, false);
            helper = new DBhelper( getActivity() );
        session = new Session( getActivity() );
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

        idSP =    prefs.getInt( "id" ,0);
listView = view.findViewById( R.id.listViewTodayHistory );
helper.toDayEatenFood(idSP, new DBhelper.VolleyCallbackList() {
    @Override
    public void onSuccessResponse(ArrayList<FoodHistory> result) {

        listView.setAdapter( new FoodHistoryCustomAdapter( getActivity(), R.layout.one_food_item, result ) );

    }

    @Override
    public void onFail(String msg) {

    }
} );




         return view;
      }
    }
      
    
