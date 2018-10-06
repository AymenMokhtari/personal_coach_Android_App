package com.example.aymen.personalcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link P5Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class P5Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
SegmentedGroup goalRegisterRadio;
SegmentedGroup roleRegisterRadio;
    SendMessage SM;
    private UserInfo user;

    private OnFragmentInteractionListener mListener;

    public P5Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment P5Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static P5Fragment newInstance(String param1) {
        P5Fragment fragment = new P5Fragment();
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
        final View view = inflater.inflate( R.layout.fragment_p5, container, false);
     ////  TextView text = view.findViewById( R.id.text4);
       // text.setText(mParam1);
        goalRegisterRadio = view.findViewById( R.id.goalRegisterRadio );
        roleRegisterRadio = view.findViewById( R.id.roleRegisterRadio );
        roleRegisterRadio.check( R.id.client );
        Button btn = view.findViewById( R.id.btngo4);
        user = new UserInfo(  );
        user.setRole("Client");
        user.setGoal( "Weight Loss" );
        goalRegisterRadio.check( R.id.weightLoss );
        roleRegisterRadio.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.client:
                        user.setRole("Client" );
                        break;
                    case R.id.trainer:
                        user.setRole( "Trainer" );
                        break;
                    default:
                        // Nothing to do
                }
            }
        } );
        goalRegisterRadio.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.weightLoss:
                    user.setGoal("Weight Loss" );
                        break;
                    case R.id.maintainRegister:
                        user.setGoal( "Maintain Weight" );
                        break;
                    case R.id.gainRegister:
                        user.setGoal( "Gain Weight " );
                        break;
                    default:
                        // Nothing to do
                }
            }
        } );
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(true);
                SM.sendData5(user);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean drag) {
        if (mListener != null) {
            mListener.onFragment4Interaction(drag);
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
        void onFragment4Interaction(boolean drag);
    }
    public interface SendMessage {
        void sendData5(UserInfo user);
    }
    protected void displayReceivedData(UserInfo userInfo)
    {
  //      Toast.makeText( getActivity() , userInfo.getLastname() , Toast.LENGTH_SHORT ).show();
    //    Toast.makeText( getActivity() , userInfo.getFirstname() , Toast.LENGTH_SHORT ).show();

    }
}
