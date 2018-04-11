package com.example.seungleechoi.presenterhelper1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link controlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class controlFragment extends Fragment implements View.OnClickListener {
    Button start, lap, reset, next;
    TextView time;

    private OnFragmentInteractionListener mListener;

    public controlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        start = (Button)view.findViewById(R.id.start);
        start.setText("Start");
        lap = (Button) view.findViewById(R.id.lap);
        reset= (Button) view.findViewById(R.id.reset);
        next = (Button) view.findViewById(R.id.next);
        time = (TextView) view.findViewById(R.id.time);


        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);
        next.setOnClickListener(this);
        //setRetainInstance(true);
        return view;
        //return inflater.inflate(R.layout.fragment_control, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private static String t = "";



    @Override
    public void onClick(View view) {
        if (view.getId() == start.getId())
        {

            mListener.onButtonClicked(0);
        }
        if (view.getId() == next.getId())
        {
            mListener.onButtonClicked(1);
        }
        if (view.getId() == lap.getId())
        {
            mListener.onButtonClicked(2);
        }
        if (view.getId() == reset.getId())
        {
            mListener.onButtonClicked(3);
        }
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
        void onFragmentInteraction(Uri uri);
        void onButtonClicked(int id);
    }
}
