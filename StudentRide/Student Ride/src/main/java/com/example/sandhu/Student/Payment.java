package com.example.sandhu.Student;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;

public class Payment extends AppCompatActivity {
EditText date;
EditText creditcard;
Button save,edit;
  private boolean isValid;
  private boolean isValidd;
    private boolean isButton;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
//set back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment");

save = (Button)findViewById(R.id.savecard);
edit = (Button)findViewById(R.id.edit);
date = (EditText)findViewById(R.id.date);
creditcard = (EditText)findViewById(R.id.creditcarddedit);

isButton = false;
//set Credit Card number format
creditcard.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isValidd = true;

       String w = s.toString();
       if(w.length()==4 && before == 0) {
                       w += "-";
               creditcard.setText(w);
               creditcard.setSelection(w.length());
           }

       else if(w.length()==9 && before == 0){
            w+="-";
            creditcard.setText(w);
            creditcard.setSelection(w.length());
        }
      else if(w.length()==14 && before == 0){
            w+="-";
            creditcard.setText(w);
            creditcard.setSelection(w.length());
        }

        else if (w.length()!=19) {
           isValidd = false;
       }



    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!isValidd) {
            creditcard.setError("Enter a valid Credit Card Number");
        } else {
            creditcard.setError(null);
        }

    }
});

//set Expiry Date format
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               isValid = true;
                String working = s.toString();
                if (working.length()==2 && before ==0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                        isValid = false;
                    } else {
                        working+="/";
                        date.setText(working);
                        date.setSelection(working.length());
                    }
                }
                else if (working.length()==7 && before ==0) {
                    String enteredYear = working.substring(3);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        isValid = false;
                    }
                }
                else if (working.length()!=7) {
                    isValid = false;
                }

                if (!isValid) {
                    date.setError("Enter a valid date: MM/YYYY");
                } else {
                    date.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//Save Button
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(creditcard.getText())){
            creditcard.setError("Credit Card number is required!");
        }
       else if (TextUtils.isEmpty(date.getText())){
            date.setError("Expiry date is required!");
        }
        else {
            if(databaseHelper.countPayRecords() >=1){
                isButton = true;
            }
            if (isValid == true && isValidd == true && isButton == false && databaseHelper.countPayRecords() < 1) {
                databaseHelper.addpay(creditcard.getText().toString(), date.getText().toString());
                if (save.getText().toString() == "SAVE") {
                    Toast.makeText(getApplication(), "Payment Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplication(), "Payment Updated", Toast.LENGTH_SHORT).show();
                }
                isButton = true;
            }
            else if(isButton == true && isValid == true && isValidd == true){
                databaseHelper.updateUserdatapay(1,creditcard.getText().toString(), date.getText().toString());
                if (save.getText().toString() == "UPDATE") {
                    Toast.makeText(getApplication(), "Payment Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplication(), "Payment Saved", Toast.LENGTH_SHORT).show();

                }
                save.setText("SAVE");
                 //creditcard.setText("");
                //date.setText("");
            }
        }

    }

});

edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (databaseHelper.checkdbpay() == false){
            Toast.makeText(getApplication(),"No Record Found for Edit Pay",Toast.LENGTH_SHORT).show();
        }
        else {
            creditcard.setText(databaseHelper.getpay(1).getCreditcard());
            date.setText(databaseHelper.getpay(1).getExpiry());
            save.setText("UPDATE");
            isButton = true;
        }
    }
});

        }


//when Back Button pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
