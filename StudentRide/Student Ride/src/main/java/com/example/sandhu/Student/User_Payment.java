package com.example.sandhu.Student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.sandhu.signin.Signin;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class User_Payment extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
//    final String API_GET_TOKEN = "http://192.168.2.24:80/braintree/main.php";
//final String API_CHECK_OUT = "http://192.168.2.24:80/braintree/checkout.php";

    //fill your device IP address
    final String API_GET_TOKEN = "http://192.xxx.x.xx:80/braintree/main.php";
    final String API_CHECK_OUT = "http://192.xxx.x.xx:80/braintree/checkout.php";

String token,amount;
HashMap<String,String> paramsHash;

Button btn_pay;
EditText edt_amount;
LinearLayout group_waiting, group_payment;

SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__payment);

        group_payment = (LinearLayout)findViewById(R.id.payment_group);
        group_waiting = (LinearLayout)findViewById(R.id.waiting_group);

        edt_amount = (EditText)findViewById(R.id.edt_amount);
        btn_pay = (Button)findViewById(R.id.btn_pay);
        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment");

        ConnectionSpec spec = new
                ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .build();
updateAndroidSecurityProvider(this);

        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        new getToken().execute();

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPayment();
            }
        });

    }
private void submitPayment(){
    DropInRequest dropInRequest = new DropInRequest().clientToken(token);
    startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == REQUEST_CODE){
                if (resultCode == RESULT_OK){
                    DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                    PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                    String strNonce = nonce.getNonce();

                    if (!edt_amount.getText().toString().isEmpty()){
                        amount = edt_amount.getText().toString();
                        paramsHash = new HashMap<>();
                        paramsHash.put("amount",amount);
                        paramsHash.put("nonce",strNonce);

                        sendPayments();
                    }
                    else {
                        Toast.makeText(this,"Please enter valid amount",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(resultCode == RESULT_CANCELED)
                    Toast.makeText(this,"User Cancel",Toast.LENGTH_SHORT).show();
                else {
                    Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                    Log.d("EDMT_Error",error.toString());
                }

            }
    }
private void sendPayments(){
    RequestQueue queue = Volley.newRequestQueue(User_Payment.this);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                     if (response.toString().contains("Successful"))
                         Toast.makeText(User_Payment.this,"Transaction Successful",Toast.LENGTH_SHORT).show();
                      else {
                         Toast.makeText(User_Payment.this,"Transaction failed!",Toast.LENGTH_SHORT).show();
                     }
                    Log.d("EDMT_Log",response.toString());

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("EDMT_Error",error.toString());

        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            if (paramsHash == null)
                return null;
            Map<String,String> params = new HashMap<>();
            for (String key:paramsHash.keySet()){
                params.put(key, paramsHash.get(key));
            }
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type","application/x-www-form-urlencoded");
            return params;
        }
    };

    queue.add(stringRequest);
}
    private class getToken extends AsyncTask{
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        mDialog = new ProgressDialog(User_Payment.this,android.R.style.Theme_DeviceDefault_Dialog);
        mDialog.setCancelable(false);
        mDialog.setMessage("Please wait");
        mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //hide group waiting
                            group_waiting.setVisibility(View.GONE);
                            //show group payment
                            group_payment.setVisibility(View.VISIBLE);
                            //set token
                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("EDMT_Error",exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        mDialog.dismiss();
        }
    }
    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }
    protected void checkTls() {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            try {
                ProviderInstaller.installIfNeededAsync(this, new ProviderInstaller.ProviderInstallListener() {
                    @Override
                    public void onProviderInstalled() {
                        SSLContext sslContext = null;
                        try {
                            sslContext = SSLContext.getInstance("TLSv1.2");
                            sslContext.init(null, null, null);
                            SSLEngine engine = sslContext.createSSLEngine();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (KeyManagementException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onProviderInstallFailed(int i, Intent intent) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        String emailsp = sharedPreferences.getString("signin", "");

        if(emailsp.equals("")){
            startActivity(new Intent(User_Payment.this, Signin.class));

        }
        else  {
            startActivity(new Intent(User_Payment.this,MainActivity.class));
        }
        return super.onSupportNavigateUp();
    }
}
