package com.williamchow.customedittext;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{

    private CleanableEditText mUserNameCleanableEditText;
    private CleanableAmountEditText mAmountCleanableEditText;
//    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mUserNameCleanableEditText = (CleanableEditText) findViewById(R.id.userNameEditText);
        mAmountCleanableEditText = (CleanableAmountEditText) findViewById(R.id.amountEditText);

        mAmountCleanableEditText.addTextChangedListener(new AmountTextChangeListener(mAmountCleanableEditText));


//        mLoginButton = (Button) findViewById(R.id.loginButton);
//        mLoginButton.setOnClickListener(new OnClickListenerImpl());

//        mAmountCleanableEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("William", "not come in");
//                if(!mAmountCleanableEditText.getText().toString().contains("MYR")){
//                    Log.i("William", "Come in here");
//                    String currency = "MYR " + mAmountCleanableEditText.getText().toString();
//                    mAmountCleanableEditText.setText(currency);
//                }
//                return false;
//            }
//        });
    }

//    private class OnClickListenerImpl implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            if (TextUtils.isEmpty(mUserNameCleanableEditText.getText())) {
//                mUserNameCleanableEditText.setShakeAnimation();
//                Toast.makeText(MainActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
//            }
//            else if(!TextUtils.isEmpty(mUserNameCleanableEditText.getText())){
//                if(mUserNameCleanableEditText.getText().toString().contains("MYR ")){
//
//                }else{
//                    String currency = "MYR " + mUserNameCleanableEditText.getText().toString();
//                    mUserNameCleanableEditText.setText(currency);
//                }
//            }

//            if (TextUtils.isEmpty(mAmountCleanableEditText.getText())) {
//                mAmountCleanableEditText.setShakeAnimation();
//                Toast.makeText(MainActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
