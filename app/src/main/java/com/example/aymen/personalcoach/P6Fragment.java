package com.example.aymen.personalcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link P6Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class P6Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private UserInfo user;
EditText firstName;
EditText lastname;
EditText email;
EditText password;
    // TODO: Rename and change types of parameters
    private String mParam1;

    SendMessage SM;

    private OnFragmentInteractionListener mListener;

    public P6Fragment() {
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
    public static P6Fragment newInstance(String param1) {
        P6Fragment fragment = new P6Fragment();
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
        final View view = inflater.inflate(R.layout.fragment_p6, container, false);
    //    TextView text =(TextView)view.findViewById(R.id.text2);
        //   text.setText(mParam1);
        Button btn = (Button) view.findViewById(R.id.next5);
      //  SegmentedGroup segmented2 = (SegmentedGroup) view.findViewById(R.id.segmented2);
        //    int redmag = getContext().getResources().getColor(R.color.colorPrimary);
        firstName = view.findViewById( R.id.FirstNameRegisterText );
        lastname = view.findViewById( R.id.lastNameRegisterText );
        email = view.findViewById( R.id.emailRegisterText );
        password  = view.findViewById( R.id.passwordRegisterText );

        user = new UserInfo(  );

        //     segmented2.setTintColor( redmag);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                user.setFirstname( firstName.getText().toString() );
                user.setLastname( firstName.getText().toString() );
                user.setEmail( email.getText().toString() );
                user.setPassword( password.getText().toString() );
                SM.sendData6(user);

        }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean drag) {
        if (mListener != null) {
            mListener.onFragment5Interaction(drag);
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
        void onFragment5Interaction(boolean drag);
    }
    public interface SendMessage {
        void sendData6(UserInfo user);
    }
    protected void displayReceivedData(UserInfo userInfo)
    {
        Toast.makeText( getActivity() , userInfo.toString() , Toast.LENGTH_SHORT ).show();

    }
}
