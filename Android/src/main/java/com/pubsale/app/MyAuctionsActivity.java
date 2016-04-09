package com.pubsale.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.pubsale2015.R;
import com.pubsale.dto.GetAuctionsRequestDTO;

/**
 * Created by Nahum on 05/03/2016.
 */
public class MyAuctionsActivity extends Activity {
    AuctionsFragment auctionsFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_auctions);

        final Button btnSearchAuctions = (Button) findViewById(R.id.btnCreateAuction);
        btnSearchAuctions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MyAuctionsActivity.this, CreateAuctionActivity.class));

            }
        });

        GetAuctionsRequestDTO request = new GetAuctionsRequestDTO();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        auctionsFragment = AuctionsFragment.newInstance(request, this);
        ft.replace(R.id.auctions_fragment, auctionsFragment);
        ft.commit();



    }


}