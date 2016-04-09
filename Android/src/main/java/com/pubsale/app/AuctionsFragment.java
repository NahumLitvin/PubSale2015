package com.pubsale.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pubsale2015.R;

/**
 * Created by Nahum on 09/04/2016.
 */
public class AuctionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auctions_fragment, container, false);
    }
}