package com.pubsale.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pubsale2015.R;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.RegisterRequestDTO;
import com.pubsale.dto.RegisterResponseDTO;


/**
 * Created by Nahum on 04/03/2016.
 */
public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final Button register = (Button) findViewById(R.id.btnRegister);

        final EditText email = (EditText) findViewById(R.id.txtEmail);
        final EditText password = (EditText) findViewById(R.id.txtPassword);
        final EditText name = (EditText) findViewById(R.id.txtName);
        final EditText phone = (EditText) findViewById(R.id.txtPhone);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
                registerRequestDTO.setEmail(email.getText().toString());
                registerRequestDTO.setName(name.getText().toString());
                registerRequestDTO.setPassword(password.getText().toString());
                registerRequestDTO.setPhone(phone.getText().toString());
                new RegisterTask().execute(registerRequestDTO);
            }
        });
    }

    private class RegisterTask extends AsyncTask<RegisterRequestDTO, Void, RegisterResponseDTO> {

        ProgressDialog dialog;

        @Override
        protected RegisterResponseDTO doInBackground(RegisterRequestDTO... request) {
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog = ProgressDialog.show(RegisterActivity.this, "Loading", "Please wait...", true);
                }
            });

            return PubServiceClient.getInstance().Register(request[0]);
        }

        @Override
        protected void onPostExecute(RegisterResponseDTO response) {
            dialog.dismiss();


            if (response == null) {
                Toast.makeText(RegisterActivity.this, "Connection Error Error!", Toast.LENGTH_LONG).show();
                return;
            }

            switch (response.getStatus()) {

                case OK:
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    break;
                case UserExists:
                    Toast.makeText(RegisterActivity.this, "Email Already Exists.", Toast.LENGTH_LONG).show();
                    break;
                case Exception:
                    Toast.makeText(RegisterActivity.this, "Internal Server Error", Toast.LENGTH_LONG).show();
                    break;
            }


        }
    }
}