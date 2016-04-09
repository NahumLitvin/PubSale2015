package com.pubsale.app;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.pubsale2015.R;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.GetAuctionsRequestDTO;

import java.util.List;

/**
 * Created by Nahum on 09/04/2016.
 */
public class AuctionsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private GetAuctionsRequestDTO auctionsRequest;
    private Class activityToOpenOnClick;

    public static final AuctionsFragment newInstance(GetAuctionsRequestDTO auctionsRequest, Activity activityToOpenOnClick) {
        AuctionsFragment f = new AuctionsFragment();
        Bundle bdl = new Bundle(2);
        bdl.putSerializable("auctionsRequest", auctionsRequest);
        bdl.putSerializable("class", activityToOpenOnClick.getClass());
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        auctionsRequest = (GetAuctionsRequestDTO) getArguments().getSerializable("auctionsRequest");
        activityToOpenOnClick = (Class) getArguments().getSerializable("class");
        new GetMyAuctionsTask(auctionsRequest).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auctions_fragment, container, false);

        return view;
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
        startActivity(new Intent(getActivity(), activityToOpenOnClick.getClass()), bundle);
    }

    public void search(GetAuctionsRequestDTO getAuctionsRequestDTO) {
        auctionsRequest = getAuctionsRequestDTO;
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
                setListAdapter(new ArrayAdapter<AuctionDTO>(getActivity(),
                        android.R.layout.simple_list_item_1, response));
            }
        }
    }
}