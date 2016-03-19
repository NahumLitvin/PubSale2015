package com.pubsale.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.pubsale2015.R;

/**
 * Created by Nahum on 05/03/2016.
 */
public class MyAuctionsActivity extends Activity {
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
    }
}