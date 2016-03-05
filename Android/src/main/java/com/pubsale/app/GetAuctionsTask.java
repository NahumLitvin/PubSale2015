package com.pubsale.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.GetAuctionsRequestDTO;

import java.util.List;

public class GetAuctionsTask extends AsyncTask<GetAuctionsRequestDTO, Void, List<AuctionDTO>> {

    Context context;
    ProgressDialog dialog;

    public GetAuctionsTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
    }


    @Override
    protected List<AuctionDTO> doInBackground(GetAuctionsRequestDTO... request) {

        return PubServiceClient.getInstance().GetAuctions(request[0]);
    }

    @Override
    protected void onPostExecute(List<AuctionDTO> response) {
        dialog.dismiss();
            /*if(loginResponseDTO==null) {
                Toast.makeText(context, "Internal Error!", Toast.LENGTH_LONG).show();
                return;
            }
            if(loginResponseDTO.getSession()==null){
                Toast.makeText(context, "Bad Credentials!", Toast.LENGTH_LONG).show();
                return;
            }
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));*/

    }
}
