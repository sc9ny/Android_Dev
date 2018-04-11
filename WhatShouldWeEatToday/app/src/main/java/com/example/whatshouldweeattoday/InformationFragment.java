package com.example.whatshouldweeattoday;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InformationFragment extends Fragment {

    ImageView image;
    TextView name;
    TextView closed;
    TextView rating;
    TextView price;
    TextView location;
    TextView url;

    public InformationFragment() {
        // Required empty public constructor
    }

    public static InformationFragment newInstance(String name) {
        InformationFragment myFragment = new InformationFragment();

        Bundle args = new Bundle();
        args.putString(MenuActivity.NAME, name);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        image = (ImageView) view.findViewById(R.id.imageD);
        name = (TextView) view.findViewById(R.id.nameD);
        closed = (TextView) view.findViewById(R.id.isOpenD);
        rating = (TextView) view.findViewById(R.id.ratingD);
        price = (TextView) view.findViewById(R.id.priceD);
        location = (TextView) view.findViewById(R.id.locationD);
        url = (TextView) view.findViewById(R.id.urlD);

        if (getArguments() != null) {
            setText("", getArguments().getString(MenuActivity.NAME, ""), "closed", 5.0, "", "", "");
        }
        return view;
    }

    public void setText(String infoImage, String infoName, String infoClosed, double infoRating, String infoPrice, String infoLocation, String infoUrl) {
        new MenuActivity.DownloadImageTask(image).execute(infoImage);
        name.setText(infoName);
        closed.setText(infoClosed.toUpperCase());
        rating.setText("Rating: " + infoRating);
        price.setText("Price: " + infoPrice);
        location.setText("Location: " + infoLocation);
        url.setText("URL: " + infoUrl);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
