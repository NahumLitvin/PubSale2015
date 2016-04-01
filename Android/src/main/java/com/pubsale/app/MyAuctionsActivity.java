package com.pubsale.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.pubsale2015.R;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.GetAuctionsRequestDTO;

import java.util.List;

/**
 * Created by Nahum on 05/03/2016.
 */
public class MyAuctionsActivity extends Activity {
    ListView auctions;
    ArrayAdapter<AuctionDTO> auctionsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_auctions);
        final Button btnSearchAuctions = (Button) findViewById(R.id.btnCreateAuction);
        auctions = (ListView) findViewById(R.id.lv_my_auctions);
        if (auctions == null) {
            Log.e("wtf?", "wtf?");
        }
        btnSearchAuctions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MyAuctionsActivity.this, CreateAuctionActivity.class));
            }
        });

        new GetMyAuctionsTask().execute();
    }

    private class GetMyAuctionsTask extends AsyncTask<Void, Void, List<AuctionDTO>> {

        @Override
        protected List<AuctionDTO> doInBackground(Void... voids) {
            return PubServiceClient.getInstance().GetAuctions(new GetAuctionsRequestDTO());
        }

        @Override
        protected void onPostExecute(List<AuctionDTO> response) {
            Log.e("response size", String.valueOf(response.size()));
            auctionsAdapter = new ArrayAdapter<AuctionDTO>(MyAuctionsActivity.this,
                    android.R.layout.simple_list_item_1, response);
            Log.e("auctions", auctions.toString());
            auctions.setAdapter(auctionsAdapter);
        }
    }




}