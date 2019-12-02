package com.example.sandhu.Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.sandhu.signin.Signin;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {
ImageView pic;
TextView tname,tsid,tuni,temail,tphone;
DatabaseHelper db;
TabLayout tabLayout;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        //creating back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Profile");

    db = new  DatabaseHelper(this);
    //creating tabs
    tabLayout = (TabLayout)findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Profile");
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("           View Profile");
        tabLayout.addTab(secondTab);

        pic = (ImageView)findViewById(R.id.profile_image);
        tname = (TextView)findViewById(R.id.textviewname);
        tsid = (TextView)findViewById(R.id.textViewstudentidvalue);
        tuni = (TextView)findViewById(R.id.textViewuniversityvalue);
        temail = (TextView)findViewById(R.id.textViewemailvalue);
        tphone = (TextView)findViewById(R.id.textViewphonevalue);
        tname.setText(db.getMoviep(1).getFname()+ " " + db.getMoviep(1).getLname());
        tsid.setText(db.getMoviep(1).getSid());
        tuni.setText(db.getMoviep(1).getUni());
        temail.setText(db.getMoviep(1).getEmail());
        tphone.setText(db.getMoviep(1).getPhone());

        pic.setImageBitmap(convertToBitmap(db.getMoviep(1).getImage()));
        //set tab index as 1
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        //when click on a tab
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //convert image to Bitmp
    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
//back button click
    @Override
    public boolean onSupportNavigateUp() {
        String emailsp = sharedPreferences.getString("signin", "");

        if(emailsp.equals("")){
            startActivity(new Intent(ViewProfile.this, Signin.class));

        }
        else  {
            startActivity(new Intent(ViewProfile.this,MainActivity.class));
        }
        return super.onSupportNavigateUp();
    }
}
