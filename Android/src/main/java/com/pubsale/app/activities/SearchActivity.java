package com.pubsale.app.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.example.pubsale2015.R;
import com.pubsale.app.fragments.AuctionsFragment;
import com.pubsale.app.fragments.CategoriesFragment;
import com.pubsale.dto.GetAuctionsRequestDTO;
import com.pubsale.helpers.Helper;

/**
 * Created by Nahum on 05/03/2016.
 */
public class SearchActivity extends Activity {
    AuctionsFragment auctionsFragment;
    CategoriesFragment categoriesFragment;
    EditText searchAuction;
    GetAuctionsRequestDTO req = new GetAuctionsRequestDTO();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_auction);
        searchAuction = (EditText) findViewById(R.id.search_auction);
        categoriesFragment = (CategoriesFragment) getFragmentManager().findFragmentById(R.id.sp_category);
        req.setBuyerEmail(Helper.GetIsLoggedInRequest(this).getEmail());
        req.setEndDateAfterUnixTime(System.currentTimeMillis() / 1000L);
        searchAuction.setOnFocusChangeListener(new View.OnFocusChangeListener() {


            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Search();
                }
            }
        });

        categoriesFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Search();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Search();
            }
        });


        GetAuctionsRequestDTO request = new GetAuctionsRequestDTO();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        auctionsFragment = AuctionsFragment.newInstance(request, true);
        ft.replace(R.id.auctions_fragment, auctionsFragment);
        ft.commit();
    }

    private void Search() {
        req.setCategory(categoriesFragment.getSelectedCategory());
        req.setFreeText(searchAuction.getText().toString());
        req.setEndDateAfterUnixTime(System.currentTimeMillis() / 1000);
        auctionsFragment.search(req);
    }


}

