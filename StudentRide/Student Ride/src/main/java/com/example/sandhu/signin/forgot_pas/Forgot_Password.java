package com.example.sandhu.signin.forgot_pas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandhu.Student.DatabaseHelper;
import com.example.sandhu.Student.MainActivity;
import com.example.sandhu.Student.Profile;
import com.example.sandhu.Student.R;
import com.example.sandhu.Student.ViewProfile;
import com.example.sandhu.signin.Signin;

import java.util.Random;

import javax.activation.DataHandler;

public class Forgot_Password extends AppCompatActivity implements Code_Fragment.OnFragmentInteractionListener {
View submit;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
EditText etemail;
    String email;
//    String code= "1234";
    Random random = new Random();
    String code;

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
   etemail = (EditText)findViewById(R.id.forgotpassedittextemail);
   submit = findViewById(R.id.forgotpassemailsubmit);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Password");
databaseHelper = new DatabaseHelper(getApplicationContext());
   //
     etemail.setText("karan74406@gmail.com");
        //
        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
       code = String.format("%04d", random.nextInt(10000));
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putString("code", code);
        mEditor.apply();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.myContainer, new Fragment());
        fragmentTransaction.commit();
        final SendEmailTask sendEmailTask = new SendEmailTask();

        submit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {


           if(databaseHelper.countRecords()>=1&& databaseHelper.getMoviep(1).getEmail().equals(etemail.getText().toString())){
               fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.myContainer, new Code_Fragment());
               fragmentTransaction.commit();

               sendEmailTask.execute();
               Toast.makeText(Forgot_Password.this, "Verification code sent" +
                       " Please check your E-Mail",Toast.LENGTH_SHORT).show();
               etemail.setVisibility(View.INVISIBLE);
               submit.setVisibility(View.INVISIBLE);
           }
           else {
               if (TextUtils.isEmpty(etemail.getText())) {
                   etemail.setError("E-Mail is required!");
               } else {
                   AlertDialog alertDialog = new AlertDialog.Builder(Forgot_Password.this).create();
                   alertDialog.setTitle("This E-Mail does not exist");
                   alertDialog.setMessage("Please Sign UP or enter the correct E-Mail");
                   alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           });
                   alertDialog.show();
                   // Toast.makeText(getApplicationContext(),"This E-Mail does not exist" +
                   // "Please Sign Up",Toast.LENGTH_SHORT).show();
               }
           }


       }
   });
    }
    class SendEmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Email sending", "sending start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                email = etemail.getText().toString();
//Fill your own credential
                GmailSender sender = new GmailSender("000111karan@gmail.com", "xxxxxxxx");
                //subject, body, sender, to
                sender.sendMail("E-Mail Verification Code",
                        code,
                        "000111karan@gmail.com",
                        email);

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public boolean onSupportNavigateUp() {
            startActivity(new Intent(Forgot_Password.this, Signin.class));
               return super.onSupportNavigateUp();
    }
}
