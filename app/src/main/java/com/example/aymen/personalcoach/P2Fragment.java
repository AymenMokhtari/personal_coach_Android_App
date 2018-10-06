package com.example.aymen.personalcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link P2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class P2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
private UserInfo user;

    // TODO: Rename and change types of parameters
    private String mParam1;
  SendMessage SM;
    SegmentedGroup gender;
    EditText weight ;
    String  genderValue = "male";;

    private OnFragmentInteractionListener mListener;

    public P2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment P2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static P2Fragment newInstance(String param1) {
        P2Fragment fragment = new P2Fragment();
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
        final View view = inflater.inflate( R.layout.fragment_p2, container, false);
     //   TextView text =(TextView)view.findViewById( R.id.text2);
      //  text.setText(mParam1);

         gender = view.findViewById( R.id.genderRegisterText );
        gender.check( R.id.maleRegisterRadio );
        gender.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.maleRegisterRadio:
                        genderValue = "male";
                        Toast.makeText(getActivity(), "male", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.femaleRegisterRadio:
                        Toast.makeText(getActivity(), "female", Toast.LENGTH_SHORT).show();
                        genderValue= "female";
                        break;

                    default:
                        // Nothing to do
                }
            }
        } );
         weight =view.findViewById( R.id.weightRegiterText);
        Button btn = (Button) view.findViewById( R.id.btngo2);
user = new UserInfo();
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                onButtonPressed(true);
                user.setGender(genderValue);
                try {
                    user.setCurrentWheight( Float.valueOf(  weight.getText().toString() ));

                }catch (Exception e){

                }
                SM.sendData2(user);

            } });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean drag) {
        if (mListener != null) {
            mListener.onFragment2Interaction(drag);
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
        void onFragment2Interaction(boolean drag);
    }

    protected void displayReceivedData(UserInfo userInfo)
    {
       // Toast.makeText( getActivity() , userInfo.getLastname() , Toast.LENGTH_SHORT ).show();
        //Toast.makeText( getActivity() , userInfo.getFirstname() , Toast.LENGTH_SHORT ).show();

    }
    public interface SendMessage {
        void sendData2(UserInfo user);
    }
}
