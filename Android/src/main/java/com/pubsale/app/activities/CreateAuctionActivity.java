package com.pubsale.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.pubsale2015.R;
import com.pubsale.app.fragments.CategoriesFragment;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.AuctionDTO;
import com.pubsale.dto.CreateAuctionRequestDTO;
import com.pubsale.dto.IsActionSucceededDTO;
import com.pubsale.dto.IsLoggedInRequestDTO;
import com.pubsale.helpers.Helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nahum on 06/03/2016.
 */
public class CreateAuctionActivity extends Activity {

    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 42426;
    private EditText itemName;
    private EditText auctionEnd;
    private EditText startingPrice;
    private CheckBox isImmediateBuy;
    private EditText immediateBuyPrice;
    private EditText description;
    private ImageButton image;
    private DatePickerDialog toDatePickerDialog;
    private Button createAuction;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private Uri outputFileUri;
    private CategoriesFragment categoriesFragment;


    private static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_auction);

        findViewsById();

        setDatePickerOnAuctionEndControl();

        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    openImageIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        createAuction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (itemName.getText().length() < 5) {
                    itemName.setError("at least 5 chars");
                } else
                    itemName.setError(null);
                int starting_price = 0;
                try {
                    starting_price = Integer.parseInt(startingPrice.getText().toString());
                    if (starting_price < 1) {
                        startingPrice.setError("must be a greater than one");
                    }
                    startingPrice.setError(null);
                } catch (NumberFormatException nfe) {
                    startingPrice.setError("must be a number");

                }

                int max_price = 0;
                if (isImmediateBuy.isChecked()) {
                    try {
                        max_price = Integer.parseInt(immediateBuyPrice.getText().toString());
                        if (starting_price >= max_price) {
                            immediateBuyPrice.setError("must be a greater than minimum price");
                        }

                    } catch (NumberFormatException nfe) {
                        immediateBuyPrice.setError("must be a number");
                    }


                }
                if (description.getText().length() < 30) {
                    description.setError("at least 30 chars");
                } else {
                    description.setError(null);
                }

                if (itemName.getError() != null) return;
                if (startingPrice.getError() != null) return;
                if (immediateBuyPrice.getError() != null) return;
                if (description.getError() != null) return;

                final AuctionDTO dto = new AuctionDTO();
                dto.setName(itemName.getText().toString());

                dto.setStartUnixTime(System.currentTimeMillis() / 1000L);
                dto.setEndUnixTime(getDateFromDatePicker(toDatePickerDialog.getDatePicker()).getTime() / 1000L);

                dto.setCurrentPrice(starting_price);
                dto.setEndPrice(max_price);
                Log.e("", (String.valueOf(categoriesFragment == null)));
                Log.e("", (String.valueOf(categoriesFragment.getSelectedCategory() == null)));
                dto.setCategory(categoriesFragment.getSelectedCategory());
                dto.setDescription(description.getText().toString());
                try {
                    Log.i("", outputFileUri.toString());
                    dto.setPhoto(Helper.ByteArrayFromBitmap(((BitmapDrawable) image.getDrawable()).getBitmap()));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                SharedPreferences prefs = getSharedPreferences("cred", MODE_PRIVATE);
                String email = prefs.getString("email", null);
                String session = prefs.getString("session", null);
                final CreateAuctionRequestDTO cdto = new CreateAuctionRequestDTO(dto, new IsLoggedInRequestDTO(session, email));

                new CreateAuctionTask().execute(cdto);

            }
        });


    }

    private void setDatePickerOnAuctionEndControl() {
        auctionEnd.setInputType(InputType.TYPE_NULL);

        auctionEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                auctionEnd.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //set min date to tommrrow
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        toDatePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        auctionEnd.setText(dateFormatter.format(cal.getTime()));
    }

    private void findViewsById() {
        itemName = (EditText) findViewById(R.id.et_item_name);
        auctionEnd = (EditText) findViewById(R.id.et_auction_end);
        startingPrice = (EditText) findViewById(R.id.et_starting_price);
        isImmediateBuy = (CheckBox) findViewById(R.id.cb_immediate_buy);
        immediateBuyPrice = (EditText) findViewById(R.id.et_immediate_buy_price);
        description = (EditText) findViewById(R.id.et_description);
        image = (ImageButton) findViewById(R.id.iw_auction);
        createAuction = (Button) findViewById(R.id.btn_create_auction);
        categoriesFragment = (CategoriesFragment) getFragmentManager().findFragmentById(R.id.sp_category);
    }

    private void openImageIntent() throws IOException {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = File.createTempFile("temp", ".jpg").getAbsolutePath();
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
                outputFileUri = selectedImageUri;
                image.setImageURI(selectedImageUri);
            }
        }
    }


    private class CreateAuctionTask extends AsyncTask<CreateAuctionRequestDTO, Void, IsActionSucceededDTO> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(CreateAuctionActivity.this, "Creating Auction", "Please wait...", true);
        }

        @Override
        protected IsActionSucceededDTO doInBackground(CreateAuctionRequestDTO... request) {
            return PubServiceClient.getInstance().CreateAuction(request[0]);
        }

        @Override
        protected void onPostExecute(IsActionSucceededDTO response) {
            dialog.dismiss();
            if (response == null) {
                Toast.makeText(CreateAuctionActivity.this, "Internal Error!", Toast.LENGTH_LONG).show();
                return;
            }
            if (!response.isSuccess()) {
                Toast.makeText(CreateAuctionActivity.this, response.getFailReason(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateAuctionActivity.this, LoginActivity.class));
            }
            startActivity(new Intent(CreateAuctionActivity.this, MyAuctionsActivity.class));

        }
    }


}