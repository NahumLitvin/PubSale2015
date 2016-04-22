package com.pubsale.app.fragments;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.example.pubsale2015.R;
import com.pubsale.app.AuctionAdapter;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.GetAuctionsRequestDTO;

import java.util.List;

/**
 * Created by Nahum on 09/04/2016.
 */
public class AuctionsFragment extends ListFragment implements AdapterView.OnItemClickListener, DialogInterface.OnDismissListener {
    private GetAuctionsRequestDTO auctionsRequest;
    private boolean isBuyMode;

    public static AuctionsFragment newInstance(GetAuctionsRequestDTO auctionsRequest, boolean isBuyMode) {
        AuctionsFragment f = new AuctionsFragment();
        Bundle bdl = new Bundle(2);
        bdl.putSerializable("auctionsRequest", auctionsRequest);
        bdl.putBoolean("isBuyMode", isBuyMode);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auctions_fragment, container, false);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        auctionsRequest = (GetAuctionsRequestDTO) getArguments().getSerializable("auctionsRequest");
        isBuyMode = getArguments().getBoolean("isBuyMode");
        new GetMyAuctionsTask(auctionsRequest).execute();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AuctionDTO auctionDTO = (AuctionDTO) getListView().getItemAtPosition(position);
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("auctionDTO", auctionDTO);
        DialogFragment newFragment = AuctionViewFragment.newInstance(auctionDTO, isBuyMode);
        newFragment.show(getFragmentManager(), "dialog");

    }

    public void search(GetAuctionsRequestDTO getAuctionsRequestDTO) {
        auctionsRequest = getAuctionsRequestDTO;
        new GetMyAuctionsTask(auctionsRequest).execute();
    }

    /*
    * refresh search when dialog is closed
    * */
    public void onDismiss(DialogInterface dialog) {
        new GetMyAuctionsTask(auctionsRequest).execute();
    }


    private class GetMyAuctionsTask extends AsyncTask<Void, Void, List<AuctionDTO>> {
        private GetAuctionsRequestDTO _dto;

        GetMyAuctionsTask(GetAuctionsRequestDTO dto) {
            _dto = dto;
        }

        @Override
        protected List<AuctionDTO> doInBackground(Void... voids) {
            return PubServiceClient.getInstance().GetAuctions(_dto);
        }

        @Override
        protected void onPostExecute(List<AuctionDTO> response) {
            if (response != null) {
                AuctionDTO[] array = new AuctionDTO[response.size()];
                response.toArray(array); // fill the array
                setListAdapter(new AuctionAdapter(getActivity(), array));
            }
        }
    }
}