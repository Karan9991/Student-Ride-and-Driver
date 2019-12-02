package com.example.sandhu.Student;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;

import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.example.sandhu.signin.Signin;
import com.example.sandhu.signin.forgot_pas.Forgot_Password;
import com.example.sandhu.signin.forgot_pas.GmailSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.regex.Pattern;


public class Profile extends AppCompatActivity  {
    EditText fname,lname,sid,uni,email,phone,pass,cpass;
    TextView t1,tvsignin;
    Button save,edit;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");
private boolean isValid;

    private boolean isImageSelected,alertdone;

    private boolean isButton;

    private static final int CAMERA_REQUEST = 200;

    private ImageView pic, rpic;
    private DatabaseHelper db;

    private UserModel userModel;
    private Bitmap bp;
    private byte[] photo;

    TabLayout tabLayout;
    int pStatus = 0;
    private Handler handler = new Handler();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
private boolean pb = true;
    Resources res;
    Drawable drawable;
     ProgressBar mProgress;
     SharedPreferences sharedPreferences;
    Random random = new Random();
    String code,emailv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
      //  final SendEmailTask sendEmailTask = new SendEmailTask();
//set progressbar
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
         res = getResources();
         drawable = res.getDrawable(R.drawable.circular);
         mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        //invisible progressbar
        mProgress.setVisibility(View.GONE);


        tabLayout = (TabLayout)findViewById(R.id.simpleTabLayout);
        if (databaseHelper.countRecords()>=1){
             tabLayout.setVisibility(View.VISIBLE);
        }

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Profile");
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("           View Profile");
        tabLayout.addTab(secondTab);

db = new DatabaseHelper(this);

    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Profile");

        fname = (EditText)findViewById(R.id.firstname);
        lname = (EditText)findViewById(R.id.lastname);
        sid = (EditText)findViewById(R.id.studentid);
        uni = (EditText)findViewById(R.id.university);
        pass = (EditText)findViewById(R.id.passwordedit);
        cpass = (EditText)findViewById(R.id.confrmpasswordedit);

        email = (EditText)findViewById(R.id.emailedit);
        phone = (EditText)findViewById(R.id.phoneedit);
      //  t1 = (TextView) findViewById(R.id.textView3);
        pic = (ImageView)findViewById(R.id.imageView2);

        save = (Button)findViewById(R.id.savebtn);
        edit = (Button)findViewById(R.id.button3);
        tvsignin = (TextView) findViewById(R.id.textviewsignin);

pb = true;
isButton = false;


tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (databaseHelper.countRecords() >=1) {
            if (tab.getPosition() == 1) {
                startActivity(new Intent(getApplicationContext(), ViewProfile.class));
            }
        }
        else {
            TabLayout.Tab tabb = tabLayout.getTabAt(0);
            tabb.select();
            Toast.makeText(getApplicationContext(),"No Record Found for View Profile",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});

//signin click
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Signin.class));
            }
        });
//DATABASE
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       if (db.checkdb() == false){
           Toast.makeText(getApplication(),"No Record Found For Edit Profile",Toast.LENGTH_SHORT).show();
       }
       else {
           fname.setText(db.getMoviep(1).getFname());
           lname.setText(db.getMoviep(1).getLname());
           sid.setText(db.getMoviep(1).getSid());
           uni.setText(db.getMoviep(1).getUni());
           pass.setText(db.getMoviep(1).getPass());
           email.setText(db.getMoviep(1).getEmail());
           phone.setText(db.getMoviep(1).getPhone());
          // save.setText("UPDATE");
           // pic.setImageBitmap(convertToBitmap(db.getMoviep(1).getImage()));
           //isButton = true;
       }
    }
});
        fname.setAllCaps(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb =true;
             // sendemail();
                 savedata();
//firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//    @Override
//    public void onComplete(@NonNull Task<AuthResult> task) {
//        if(task.isSuccessful()){
//            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()){
//                        savedata();
//                        Toast.makeText(Profile.this,"Please check your email for verification", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(Profile.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//        }else {
//            Toast.makeText(Profile.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//});
            }
        });

           }


public void sendemail() {
        if (databaseHelper.countRecords()>=1&& !databaseHelper.getMoviep(1).getEmail().toString().equals(email.getText().toString())) {
            try {
                String vemail = email.getText().toString();
                code = String.format("%04d", random.nextInt(10000));
                SharedPreferences.Editor mEditor = sharedPreferences.edit();
                mEditor.putString("pcode", code);
                mEditor.apply();
                GmailSender sender = new GmailSender("000111karan@gmail.com", "xxxxxxxx");
                //subject, body, sender, to
                sender.sendMail("E-Mail Verification Code",
                        code,
                        "000111karan@gmail.com",
                        vemail);
//                SharedPreferences.Editor mEditor = sharedPreferences.edit();
//                mEditor.putString("vemail", vemail);
//                mEditor.apply();
                Toast.makeText(getApplicationContext(),"veee"+vemail,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"eee"+email.getText().toString(),Toast.LENGTH_SHORT).show();

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
        }
   else if (databaseHelper.countRecords()<1) {
            try {
                String vvemail = email.getText().toString();
                code = String.format("%04d", random.nextInt(10000));
                SharedPreferences.Editor mEditor = sharedPreferences.edit();
                mEditor.putString("pcode", code);
                mEditor.apply();
                GmailSender sender = new GmailSender("000111karan@gmail.com", "xxxxxxxx");
                //subject, body, sender, to
                sender.sendMail("E-Mail Verification Code",
                        code,
                        "000111karan@gmail.com",
                        vvemail);
//                SharedPreferences.Editor mEditor = sharedPreferences.edit();
//                mEditor.putString("vemail", vemail);
//                mEditor.apply();
                Toast.makeText(getApplicationContext(),"00veee"+vvemail,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"00eee"+email.getText().toString(),Toast.LENGTH_SHORT).show();

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
    }
}

    //saving profile
public void savedata(){
    String p = pass.getText().toString().trim();
    String cp = cpass.getText().toString().trim();

    if( TextUtils.isEmpty(fname.getText())){
        fname.setError( "First Name is required!" );
        isValid = false;

    }
    else if(TextUtils.isEmpty(lname.getText())){
        lname.setError( "Last Name is required!" );
        isValid = false;
    }
    else if (TextUtils.isEmpty(sid.getText())){
        sid.setError( "Student ID is required!" );
        isValid = false;
    }
    else if (TextUtils.isEmpty(uni.getText())){
        uni.setError( "University Name is required!" );
        isValid = false;
    }
    else if(TextUtils.isEmpty(pass.getText())){
        pass.setError( "Password is required!" );
        isValid = false;
    }
    else if(TextUtils.isEmpty(cpass.getText())){
        cpass.setError( "Confirm Password is required!" );
        isValid = false;
    }
    else if(!PASSWORD_PATTERN.matcher(pass.getText().toString().trim()).matches()){
        pass.setError( "Password too weak" );
        isValid = false;
    }
    else if (TextUtils.isEmpty(email.getText())){
        email.setError( "E-Mail is required!" );
        isValid = false;
    }
    else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
        email.setError( "Please enter a valid email address");
        isValid = false;
    }
    else if (TextUtils.isEmpty(phone.getText())){
        phone.setError( "Phone is required!" );
        isValid = false;
    }

    else if (!p.equals(cp)) {
        pass.setError("Passwords Mismatch");
        cpass.setError("Passwords Mismatch");
        isValid = false;
    }

    else{
        pass.setError(null);
        isValid = true;
    }

    if (isValid == true && isImageSelected ==true) {
        if (databaseHelper.countRecords() <1) {
            save();
            sendemail();
            progressbar();
            fname.setText("");
            lname.setText("");
            sid.setText("");
            uni.setText("");
            pass.setText("");
            cpass.setText("");
            email.setText("");
            phone.setText("");
           // Toast.makeText(getApplicationContext(),"ffffff"+fname.getText().toString(),Toast.LENGTH_SHORT).show();


        }


    }

     if(isValid == true && isImageSelected == true){
        update();

     }
    else if (isImageSelected == false){

        AlertDialog alertDialog = new AlertDialog.Builder(Profile.this).create();
       // alertDialog.setTitle("Alert");
        alertDialog.setMessage("Please Select Profile Image");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
public void update(){
    sendemail();

    databaseHelper.updateUserdatap(1, fname.getText().toString(), lname.getText().toString(), sid.getText().toString(), uni.getText().toString(), pass.getText().toString(), email.getText().toString(), phone.getText().toString(), profileImage(bp));
  //  db.updatesignin(1, "signed");
    progressbar();
    fname.setText("");
    lname.setText("");
    sid.setText("");
    uni.setText("");
    pass.setText("");
    cpass.setText("");
    email.setText("");
    phone.setText("");
    //save.setText("SAVE");
    Toast.makeText(getApplication(),"Updated",Toast.LENGTH_SHORT).show();
  //  isButton = false;
    pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
  //  startActivity(new Intent(Profile.this, MainActivity.class));


}

    public void selectImage(View view){
        new AlertDialog.Builder(Profile.this)
                .setTitle("Make Your Selection")
                // .setMessage("Do you think Mike is awesome?")
                // .setIcon(R.drawable.ninja)
                .setPositiveButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 2);
                                isImageSelected = true;
                                //  dialog.cancel();
                            }
                        })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(intent.resolveActivity(getPackageManager()) != null){
                            startActivityForResult(intent,CAMERA_REQUEST);
                            isImageSelected = true;
                        }
                       // dialog.cancel();
                    }
                }).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                       //pic.setImageBitmap(bp);
                    }
                }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Uri choosenImage = data.getData();

            if(choosenImage !=null){

                bp=decodeUri(choosenImage, 400);
              //  pic.setImageBitmap(bp);
            }

        }
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    private void getValues(){
    photo = profileImage(bp);
}

    //Insert data to the database
    private void addContact(){
        getValues();

          db.addProfile(new UserModel(fname.getText().toString(), lname.getText().toString(), sid.getText().toString(), uni.getText().toString(), pass.getText().toString(), email.getText().toString(), phone.getText().toString(), photo));
       // db.sign(new UserModel("signed"));
      //  startActivity(new Intent(Profile.this, MainActivity.class));

        Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_LONG).show();
    }

    public void save() {

            addContact();

    }
//back button click
    @Override
    public boolean onSupportNavigateUp() {
        String emailsp = sharedPreferences.getString("signin", "");

         if(emailsp.equals("")){
            startActivity(new Intent(Profile.this,Signin.class));

        }
        else  {
            startActivity(new Intent(Profile.this,MainActivity.class));
        }
        return super.onSupportNavigateUp();
    }


    public void progressbar(){
    mProgress.setProgress(0);   // Main Progress
    mProgress.setSecondaryProgress(100); // Secondary Progress
    mProgress.setMax(100); // Maximum Progress
    mProgress.setProgressDrawable(drawable);
        mProgress.setVisibility(View.VISIBLE);

    new Thread(new Runnable() {

        @Override
        public void run() {
            if (pb == true) {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            if (pStatus == 100) {
                                mProgress.setVisibility(View.GONE);
                            }
                            //tv.setText(pStatus + "%");

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(16); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //    handler.postDelayed(this,1000);

            }
        }
    }).start();
}


}
