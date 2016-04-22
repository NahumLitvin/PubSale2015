package com.pubsale.app.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.pubsale2015.R;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.BidRequestDTO;
import com.pubsale.dto.IsActionSucceededDTO;
import com.pubsale.helpers.Helper;

import java.text.SimpleDateFormat;

/**
 * Created by Nahum on 20/04/2016.
 */
public class AuctionViewFragment extends DialogFragment {
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    BidRequestDTO request = new BidRequestDTO();

    public static AuctionViewFragment newInstance(AuctionDTO auction, boolean isBuyMode) {
        AuctionViewFragment f = new AuctionViewFragment();
        Bundle bdl = new Bundle(2);
        bdl.putSerializable("auction", auction);
        bdl.putBoolean("isBuyMode", isBuyMode);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auction_view_fragment, container, false);

        TextView itemName = (TextView) view.findViewById(R.id.tv_item_name);
        TextView auctionEnd = (TextView) view.findViewById(R.id.tv_auction_end);
        TextView startingPrice = (TextView) view.findViewById(R.id.tv_starting_price);
        TextView immediateBuyPrice = (TextView) view.findViewById(R.id.tv_immediate_buy_price);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView category = (TextView) view.findViewById(R.id.tv_category);
        ImageView image = (ImageView) view.findViewById(R.id.iw_auction);
        final EditText etBid = (EditText) view.findViewById(R.id.et_bid);
        Button btnBid = (Button) view.findViewById(R.id.btn_bid);
        Button btnBuyNow = (Button) view.findViewById(R.id.btn_buy_now);

        TextView winnerName = (TextView) view.findViewById(R.id.tv_winner_name);
        TextView winnerPhone = (TextView) view.findViewById(R.id.tv_winner_phone);

        final AuctionDTO auction = (AuctionDTO) getArguments().getSerializable("auction");

        Boolean isBuyMode = getArguments().getBoolean("isBuyMode");
        assert auction != null;
        if (auction.getEndUnixTime() * 1000 < System.currentTimeMillis()) {
            //if auction is over color in red and show seller data
            itemName.setTextColor(0xFFFF0000);
            winnerName.setVisibility(View.VISIBLE);
            winnerPhone.setVisibility(View.VISIBLE);
            winnerName.setText(auction.getTopBidder().getName());
            winnerPhone.setText(auction.getTopBidder().getPhone());
        } else {
            winnerName.setVisibility(View.GONE);
            winnerPhone.setVisibility(View.GONE);
        }
        itemName.setText(auction.getName());
        auctionEnd.setText(myFormat.format(new java.util.Date(auction.getEndUnixTime() * 1000L)));
        startingPrice.setText(String.valueOf(auction.getCurrentPrice()));
        immediateBuyPrice.setText(String.valueOf(auction.getEndPrice()));
        description.setText(auction.getDescription());
        category.setText(auction.getCategory().getName());
        image.setImageBitmap(Helper.ByteArrayToBitmap(auction.getPhoto()));
        if (!isBuyMode) {
            etBid.setVisibility(View.GONE);
            btnBid.setVisibility(View.GONE);
            btnBuyNow.setVisibility(View.GONE);
        } else {
            request.setAuctionId(auction.getId());
            request.setRequest(Helper.GetIsLoggedInRequest(getActivity()));
            if (immediateBuyPrice.getText().length() == 0) {
                btnBuyNow.setVisibility(View.GONE);
                btnBuyNow.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        request.setBidValue(auction.getEndPrice());
                        new BidTask(AuctionViewFragment.this).execute();
                    }
                });
            }
            btnBid.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (etBid.getText().length() > 0) {
                        Integer bid = Integer.parseInt(etBid.getText().toString());
                        if (bid > auction.getCurrentPrice()) {
                            request.setBidValue(bid);
                            new BidTask(AuctionViewFragment.this).execute();
                        } else {
                            Toast.makeText(getActivity(), "Bid Must be Greater Than Current Bid", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });

        }


        return view;
    }

    private class BidTask extends AsyncTask<Void, Void, IsActionSucceededDTO> {

        ProgressDialog dialog;
        DialogFragment t;

        BidTask(DialogFragment t) {
            this.t = t;
        }
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "Bidding", "Please wait...", true);
        }

        @Override
        protected IsActionSucceededDTO doInBackground(Void... voids) {

            return PubServiceClient.getInstance().BidInAuction(request);

        }

        @Override
        protected void onPostExecute(IsActionSucceededDTO response) {
            dialog.dismiss();
            if (response == null) {
                Toast.makeText(getActivity(), "Internal Error!", Toast.LENGTH_LONG).show();
                return;
            }
            if (!response.isSuccess()) {
                Toast.makeText(getActivity(), response.getFailReason(), Toast.LENGTH_LONG).show();
                return;

            }
            Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
            t.dismiss();

        }
    }

}