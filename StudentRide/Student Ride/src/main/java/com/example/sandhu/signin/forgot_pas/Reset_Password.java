package com.example.sandhu.signin.forgot_pas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandhu.Student.DatabaseHelper;
import com.example.sandhu.Student.R;
import com.example.sandhu.Student.UserModel;
import com.example.sandhu.signin.Signin;

import java.util.regex.Pattern;

import javax.microedition.khronos.egl.EGLDisplay;

public class Reset_Password extends AppCompatActivity {
EditText password,cpass;
Button resetbtn;
  DatabaseHelper databaseHelper;
  private boolean isValid;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    password = (EditText)findViewById(R.id.forgotpasswordedit);
        cpass = (EditText)findViewById(R.id.forgotconfrmpasswordedit);
        resetbtn = (Button)findViewById(R.id.forgotpasssubmit);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Password");
    databaseHelper = new DatabaseHelper(getApplicationContext());
    resetbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validate()&&databaseHelper.countRecords() >= 1) {
                databaseHelper.updateUserpassword(1, password.getText().toString());
                Toast.makeText(getApplicationContext(),"Your Password has been Reset",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Reset_Password.this, Signin.class));
            }
        }
    });
    }
public boolean validate(){
    String p = password.getText().toString().trim();
    String cp = cpass.getText().toString().trim();

    if(!PASSWORD_PATTERN.matcher(password.getText().toString().trim()).matches()){
        password.setError( "Password too weak" );
        isValid = false;
    }
            else if(!PASSWORD_PATTERN.matcher(cpass.getText().toString().trim()).matches()){
                cpass.setError( "Password too weak" );
                isValid = false;
            }
    else if (TextUtils.isEmpty(password.getText())){
               password.setError( "Password is required!" );
        isValid = false;
    }
           else if (TextUtils.isEmpty(cpass.getText())){
               cpass.setError( "Confirm Password is required!" );
               isValid = false;
           }
    else if (!p.equals(cp)) {
               password.setError("Passwords Mismatch");
               cpass.setError("Passwords Mismatch");
        isValid = false;
    }
    else {
        isValid = true;
           }
    return isValid;

}
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(Reset_Password.this, Signin.class));
        return super.onSupportNavigateUp();
    }
}
