package com.example.aymen.personalcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link P3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class P3Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String DATEPICKER_TAG = "datepicker";
    Date birthday;
    View view;
    boolean filledDate = false;

EditText height;
    // TODO: Rename and change types of parameters
    private String mParam1;
    SendMessage SM;
    private UserInfo user;

    private TextView pickdate;
    private OnFragmentInteractionListener mListener;

    public P3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment P3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static P3Fragment newInstance(String param1) {
        P3Fragment fragment = new P3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate( R.layout.fragment_p3, container, false);
     //   TextView text =(TextView)view.findViewById( R.id.text2);
      //  text.setText(mParam1);
        Button btn = (Button) view.findViewById( R.id.btngo2);
        user = new UserInfo(  );
height = view.findViewById( R.id.heightRegister );
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get( Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filledDate== true){

                onButtonPressed(true);
                try {


                user.setHeight( Float.valueOf(height.getText().toString() ));
            //    user.setActivitylevel("high");
                SM.sendData3(user);
                }catch (Exception e){

                }
                }else {
                    Toast.makeText( getActivity(), "Please select your birthday" ,Toast.LENGTH_SHORT ).show();
                }
            }
        });


pickdate = view.findViewById( R.id.pickdate );

pickdate.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        datePickerDialog.setYearRange(1950, 2028);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
    }
} );

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean drag) {
        if (mListener != null) {
            mListener.onFragment6Interaction(drag);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
       String monthString = new DateFormatSymbols().getMonths()[month-1];
    //    Toast.makeText(getActivity(), "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        filledDate = true;
        pickdate.setText( monthString + "-"+day+"-"+year );
        user.setDateOfBirth( day+"-"+month+"-"+year );

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragment6Interaction(boolean drag);
    }

    protected void displayReceivedData(UserInfo userInfo)
    {
        user.setLastname( userInfo.getLastname() ); ;
       // Toast.makeText( getActivity() , userInfo.getLastname() , Toast.LENGTH_SHORT ).show();
       // Toast.makeText( getActivity() , userInfo.getFirstname() , Toast.LENGTH_SHORT ).show();

    }
    public interface SendMessage {
        void sendData3(UserInfo user);
    }


}
