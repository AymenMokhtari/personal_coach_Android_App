package com.example.aymen.personalcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link P4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class P4Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    SegmentedGroup activtyLevel;

    private UserInfo user;

    // TODO: Rename and change types of parameters
    private String mParam1;
    String activityLevelValue;
    SendMessage SM;


    private OnFragmentInteractionListener mListener;

    public P4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment P4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static P4Fragment newInstance(String param1) {
        P4Fragment fragment = new P4Fragment();
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
        final View view = inflater.inflate( R.layout.fragment_p4, container, false);
      //  TextView text = (TextView) view.findViewById( R.id.text3);
     //   text.setText(mParam1);
        Button btn = (Button)view.findViewById( R.id.btngo3);
        user = new UserInfo(  );
        user.setActivitylevel( "low Active" );
        activtyLevel = view.findViewById( R.id.activityLevelRegister );
activtyLevel.check( R.id.lowActive );
        activtyLevel.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Toast.makeText( getActivity() , activityLevelValue , Toast.LENGTH_SHORT ).show();
                switch (checkedId) {
                    case R.id.lowActive:
                        activityLevelValue = "low Active";
                        break;
                    case R.id.active:
                        activityLevelValue = "Active";
                        break;
                    case R.id.veryActive:
                        activityLevelValue = "Very Active";
                        break;

                    default:
                }
            }
        } );

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(true);
               user.setActivitylevel(activityLevelValue);
                SM.sendData4(user);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean drag) {
        if (mListener != null) {
            mListener.onFragment3Interaction(drag);
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
        void onFragment3Interaction(boolean drag);
    }
    public interface SendMessage {
        void sendData4(UserInfo user);
    }
    protected void displayReceivedData(UserInfo userInfo)
    {
       // Toast.makeText( getActivity() , userInfo.getLastname() , Toast.LENGTH_SHORT ).show();
        //Toast.makeText( getActivity() , userInfo.getFirstname() , Toast.LENGTH_SHORT ).show();

    }
}
