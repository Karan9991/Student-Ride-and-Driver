package com.example.sandhu.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.example.sandhu.Student.DatabaseHelper;
import com.example.sandhu.Student.MainActivity;
import com.example.sandhu.Student.Profile;
import com.example.sandhu.Student.R;
import com.example.sandhu.Student.RideDetails;
import com.example.sandhu.Student.UserModel;
import com.example.sandhu.Student.User_Payment;
import com.example.sandhu.Student.ViewProfile;
import com.example.sandhu.signin.forgot_pas.Forgot_Password;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import javax.microedition.khronos.egl.EGLDisplay;

public class Signin extends AppCompatActivity {
    EditText emailsign, passsign;
    Button btnsignin, btnsignup;
    SwitchCompat aSwitch;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;
    String email, pwd,signin;
    Boolean isValid;
    int a;
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
//
    ProgressBar pbar;
    View button_login, button_label,tvsignup,tvforgotpass,relativelay;
    private DisplayMetrics dm;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
    String pcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailsign = (EditText) findViewById(R.id.editTextsignemail);
        passsign = (EditText) findViewById(R.id.editTextsignpass);
   //     btnsignin = (Button) findViewById(R.id.buttonsign);
        aSwitch = (SwitchCompat) findViewById(R.id.switch1);
        tvsignup = findViewById(R.id.textviewsignup);
        tvforgotpass = findViewById(R.id.tvforgotpass);
        //btnsignup = (Button) findViewById(R.id.buttonsignup);
        db = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        email = sharedPreferences.getString("userEmail",null);
        pwd = sharedPreferences.getString("userPassword",null);
        signin = sharedPreferences.getString("signin",null);
firebaseAuth = FirebaseAuth.getInstance();
firebaseUser = firebaseAuth.getCurrentUser();

        if(email != null) {
            emailsign.setText(email);
        }else {
            emailsign.setText(null);
        }
        if(pwd != null) {
            passsign.setText(pwd);
        }else {
            passsign.setText(null);
        }


//        btnsignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (db.countRecords() >= 1) {
//                    if (emailsign.getText().toString().equals(db.getMoviep(1).getEmail()) && passsign.getText().toString().equals(db.getMoviep(1).getPass())) {
//                        if (db.checkdbsign() == true) {
//                            db.sign(new UserModel("signed"));
//
//                        } else {
//                            db.updatesignin(1, "signed");
//
//                        }
//                        setSwitch();
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    } else {
//
//                        db.sign(new UserModel("notsigned"));
//                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                if (TextUtils.isEmpty(emailsign.getText())) {
//                    emailsign.setError("E-Mail is required!");
//                    isValid = false;
//
//                } else if (TextUtils.isEmpty(passsign.getText())) {
//                    passsign.setError("Password is required!");
//                    isValid = false;
//
//                } else if (!PASSWORD_PATTERN.matcher(passsign.getText().toString().trim()).matches()) {
//                    passsign.setError("Please enter a valid password");
//                    isValid = false;
//                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailsign.getText().toString().trim()).matches()) {
//                    emailsign.setError("Please enter a valid email address");
//                    isValid = false;
//                }
//            }
//        });

//        btnsignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Profile.class));
//            }
//        });


  //Animating signin button
        pbar=(ProgressBar) findViewById(R.id.mainProgressBar1);
      //  button_icon=findViewById(R.id.button_icon);
        button_label=findViewById(R.id.button_label);
        relativelay=findViewById(R.id.relativelay);



        dm=getResources().getDisplayMetrics();
        button_login=findViewById(R.id.button_login);
        button_login.setTag(0);
        pbar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        StatusBarUtil.immersive(this);

        //  frag_login=new LoginFragment();
        //  frag_dashboard=new DashboardFragment();
        //    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, frag_login).commit();
        animateLayout();
        final ValueAnimator va=new ValueAnimator();
        va.setDuration(1300);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator p1) {
                LinearLayout.LayoutParams bt =  (LinearLayout.LayoutParams)button_login.getLayoutParams();
                //  RelativeLayout.LayoutParams button_login_lp= (RelativeLayout.LayoutParams) button_login.getLayoutParams();
                bt.width=Math.round((Float) p1.getAnimatedValue());
                button_login.setLayoutParams(bt);

            }
        });
        button_login.animate().translationX(dm.widthPixels+button_login.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        button_login.animate().translationX(0).setStartDelay(1300).setDuration(1200).setInterpolator(new OvershootInterpolator()).start();

        //click
        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1) {
                if (checkvalidation()) {
                     pcode = sharedPreferences.getString("pcode", null);
                    //Toast.makeText(getApplicationContext(),pcode,Toast.LENGTH_SHORT).show();
                    if(pcode!=null){
                      alertd();
                    }
else {
                        if ((int) button_login.getTag() == 1) {
                            return;
                        } else if ((int) button_login.getTag() == 2) {
                            button_login.animate().x(dm.widthPixels / 2).y(dm.heightPixels / 2).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN)).setListener(null).setDuration(1000).setStartDelay(0).start();
                            button_login.animate().setStartDelay(600).setDuration(1000).scaleX(40).scaleY(40).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN_OUT)).start();
                            //  button_icon.animate().alpha(0).rotation(90).setStartDelay(0).setDuration(800).start();
                            return;
                        }
                        button_login.setTag(1);
                        va.setFloatValues(button_login.getMeasuredWidth(), button_login.getMeasuredHeight());
                        va.start();
                        pbar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                        //1
                        button_label.animate().setStartDelay(100).setDuration(500).alpha(0).start();
                        button_login.animate().setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(4000).setDuration(1000).scaleX(30).scaleY(30).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator p1) {
                                pbar.animate().setStartDelay(0).setDuration(0).alpha(0).start();

                            }

                            @Override
                            public void onAnimationEnd(Animator p1) {
                                 startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }

                            @Override
                            public void onAnimationCancel(Animator p1) {
                                // TODO: Implement this method
                            }

                            @Override
                            public void onAnimationRepeat(Animator p1) {
                                // TODO: Implement this method
                            }
                        }).start();
                    }
                }
            }
        });
tvforgotpass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(Signin.this, Forgot_Password.class));
    }
});
tvsignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Profile.class));
    }
});
    }
public void nextActivity(){
    SharedPreferences.Editor mEditor = sharedPreferences.edit();
    mEditor.putString("signin", "signed");
    mEditor.putString("pcode", null);
    mEditor.apply();
}
public boolean checkvalidation(){
                    if (db.countRecords() >= 1) {
                    if (emailsign.getText().toString().equals(db.getMoviep(1).getEmail()) && passsign.getText().toString().equals(db.getMoviep(1).getPass())) {

                         isValid = true;

                        //                        if (db.checkdbsign() == true) {
//                            db.sign(new UserModel("signed"));
//                             isValid = true;
//                        } else {
//                            db.updatesignin(1, "signed");
//                            isValid = true;
//                        }
                        setSwitch();
                    } else {
                         isValid = false;
                       // db.sign(new UserModel("notsigned"));
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
                    else {
                        isValid = false;
                        // db.sign(new UserModel("notsigned"));
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                if (TextUtils.isEmpty(emailsign.getText())) {
                    emailsign.setError("E-Mail is required!");
                    isValid = false;

                } else if (TextUtils.isEmpty(passsign.getText())) {
                    passsign.setError("Password is required!");
                    isValid = false;

                } else if (!PASSWORD_PATTERN.matcher(passsign.getText().toString().trim()).matches()) {
                    passsign.setError("Please enter a valid password");
                    isValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailsign.getText().toString().trim()).matches()) {
                    emailsign.setError("Please enter a valid email address");
                    isValid = false;
                }

             return isValid;
                //checkemailverified();
}


    @Override
    protected void onResume() {
        setSavedDetails();
        //if authenticated go to mainactivity
        if (signin!=null){
            startActivity(new Intent(Signin.this, MainActivity.class));
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        this.finish();
    }

    public void setSwitch() {
        if (aSwitch.isChecked()) {
            SharedPreferences.Editor mEditor = sharedPreferences.edit();
            mEditor.putString("userEmail", emailsign.getText().toString());
            mEditor.putString("userPassword", passsign.getText().toString());
            mEditor.apply();
        } else {
            SharedPreferences.Editor mEditor = sharedPreferences.edit();
            mEditor.putString("userEmail", null);
            mEditor.putString("userPassword", null);
            mEditor.apply();
        }
    }

    private void setSavedDetails() {
        String email = sharedPreferences.getString("userEmail", null);
        String pwd = sharedPreferences.getString("userPassword", null);
        if (email != null && pwd != null) {
            emailsign.setText(email);
            passsign.setText(pwd);
            aSwitch.setChecked(true);
        }
    }
    public void alertd(){
        final EditText input = new EditText(Signin.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(input)
                .setTitle("Enter E-Mail Verification Code")

                .create();
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (input.length()==4&&input.getText().toString().equals(pcode)){
                    nextActivity();
                    d.dismiss();
                    Toast.makeText(getApplicationContext(),"E-Mail Verified",Toast.LENGTH_SHORT).show();


                }else if (input.length()==4&&input.getText().toString()!=pcode){
                    Toast.makeText(getApplicationContext(),"Incorrect Verification Code",Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }
    public void animateLayout(){
        final ValueAnimator vaa=new ValueAnimator();
        vaa.setDuration(1300);
        vaa.setInterpolator(new DecelerateInterpolator());
        vaa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator p1) {

            }
        });
        relativelay.animate().translationX(dm.widthPixels+relativelay.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        relativelay.animate().translationX(0).setStartDelay(1300).setDuration(1200).setInterpolator(new OvershootInterpolator()).start();

    }
}
