package com.pubsale.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.pubsale2015.R;
import com.pubsale.dto.AuctionDTO;

/**
 * Created by Nahum on 09/04/2016.
 */
public class AuctionAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    AuctionDTO[] auctions;

    public AuctionAdapter(Context context, AuctionDTO[] auctions) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.auctions = auctions;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return auctions.length;
    }


    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return auctions[position];
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.auction_row, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(auctions[position].getName());
        return vi;
    }
}
