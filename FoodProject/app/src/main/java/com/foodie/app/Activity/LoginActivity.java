
package com.foodie.app.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.foodie.app.R;
import com.foodie.app.code.Code;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;
import com.foodie.app.util.HttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;


public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    protected static final String TAG="LoginActivity";
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNicknameView;
    private EditText mPasswordAgainView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mSignInButton;
    private Button mSwitchButton;
    int type=0;
    // private objects

    private Boolean isRegiterAction=false;

    private int address=0;

    private RadioGroup mRadioGroup;

    private RadioButton n,c,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String account= sharedPreferences.getString("account","null");
        Log.i("account",account);

        if(account.length()==0|account.equals("")|account.equals("null")){
        }else{
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            LoginActivity.this.finish();
        }
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.et_phone);
        mPasswordView = (EditText) findViewById(R.id.et_password);
        mPasswordAgainView= (EditText) findViewById(R.id.et_password_again);
        mNicknameView= (EditText) findViewById(R.id.et_nickname);
        mRadioGroup=(RadioGroup)findViewById(R.id.manger);
        mRadioGroup.setVisibility(View.INVISIBLE);
        s=(RadioButton)findViewById(R.id.sorth);
        c=(RadioButton)findViewById(R.id.center);
        n=(RadioButton)findViewById(R.id.north);
        n.setChecked(true);
        //three button

        s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                address=3;
            }
        });
        n.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                address=1;
            }
        });
        c.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                address=2;
            }
        });
// three campus south,north,central, different campus have different number
        mSignInButton = (Button) findViewById(R.id.btn_login_register);
        // find button
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRegiterAction){
                    attemptRegister();
                }else{
                    attemptLogin();
                }
            }
        });
        // two buttons login and register, click different button have different functions
        mSwitchButton= (Button) findViewById(R.id.switch_button);
        // switch button

        mSwitchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRegiterAction==false){

                    isRegiterAction=true;
                    mSwitchButton.setText("existing account?");
                    mSignInButton.setText("register");
                    clearText();
                    mNicknameView.setVisibility(View.VISIBLE);
                    mPasswordAgainView.setVisibility(View.VISIBLE);
                    // setich button and sign in button
                }else{
                    clearText();
                    mSignInButton.setText("Login");
                    mSwitchButton.setText("Register?");
                    isRegiterAction=false;
                    mNicknameView.setVisibility(View.GONE);
                    mPasswordAgainView.setVisibility(View.GONE);
                    // setich button and sign in button

                }
            }
        });
        //setOnClickListener Mmethod
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        // use the buttons

        RadioButton customer=(RadioButton)findViewById(R.id.user);
        RadioButton admin=(RadioButton)findViewById(R.id.admin);
        // two button one is user and another is admin
        customer.setChecked(true);
        customer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type=0;
                mRadioGroup.setVisibility(View.INVISIBLE);
                address=0;
                // student and facuity type is 0
            }
        });
        admin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                type=1;
                mRadioGroup.setVisibility(View.VISIBLE);
                address=1;
                // admin type is 1
            }
        });
        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                showProgress(false);
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(LoginActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                    // if request fail
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(LoginActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                    // if request no response
                }else if(msg.obj.equals("4")){
                    Toast.makeText(LoginActivity.this,"The user already exists",Toast.LENGTH_SHORT).show();
                    // if already exists
                }else if(msg.obj.equals("insertFail")){
                    Toast.makeText(LoginActivity.this,"register failed",Toast.LENGTH_SHORT).show();
                    //if register fail
                }else if(msg.obj.equals("insertSuccess")){
                    String nickname=mNicknameView.getText().toString();
                    saveUser(nickname);
                }else if(msg.obj.equals("5")){
                    Toast.makeText(LoginActivity.this,"The user does not exist！",Toast.LENGTH_SHORT).show();
                    // if the user exist
                }else if(msg.obj.equals("6")){
                    Toast.makeText(LoginActivity.this,"Password mistake！",Toast.LENGTH_SHORT).show();
                    // if the password is correct
                }else{
                    saveUser(msg.obj.toString());
                }
                super.handleMessage(msg);
            }
        };
    }
    //hander method

    private void saveUser(String nickname){
        String email = mEmailView.getText().toString();
        Log.i(TAG,"nickname："+nickname+"email:"+email);
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account", email);
        editor.putString("nickname", nickname);
        editor.putString("userType", Integer.toString(type));
        editor.putString("address", Integer.toString(address));
        editor.commit();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        LoginActivity.this.finish();
        Toast.makeText(LoginActivity.this,nickname+",Welcome to login！",Toast.LENGTH_SHORT).show();
        // after register save the user

    }
    private void clearText() {
        mPasswordAgainView.setText("");
        mNicknameView.setText("");
        mPasswordView.setText("");
        // clear text method
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel){
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);


            if (HttpUtils.isNetworkConnected(LoginActivity.this)) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                NameValuePair pair1 = new BasicNameValuePair("email", email);
                NameValuePair pair2 = new BasicNameValuePair("password", password);
                NameValuePair pair4 = new BasicNameValuePair("type", Integer.toString(type));
                NameValuePair pair5 = new BasicNameValuePair("address", Integer.toString(address));
                params.add(pair4);
                params.add(pair2);
                params.add(pair1);
                params.add(pair5);
                HttpRequest.goPost(params, Constant.login,true);
                //get the data from databse make sure if they are correct or not
            } else {
                Toast.makeText(LoginActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNicknameView.setError(null);
        mPasswordAgainView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        final String nickname=mNicknameView.getText().toString();
        String passwordAgain=mPasswordAgainView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(nickname)) {
            mNicknameView.setError("Nicknames cannot be empty");
            focusView = mNicknameView;
            cancel = true;
        } else if (!isNicknameValid(nickname)) {
            mNicknameView.setError("Nickname is too short");
            focusView = mNicknameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(passwordAgain)) {
            mPasswordAgainView.setError("Duplicate passwords cannot be empty");
            focusView = mPasswordAgainView;
            cancel = true;
        } else if (!isPasswordAgainValid(passwordAgain,password)) {
            mPasswordAgainView.setError("The two passwords don't match");
            focusView = mPasswordAgainView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.


            showProgress(true);
            if (HttpUtils.isNetworkConnected(LoginActivity.this)) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                NameValuePair pair1 = new BasicNameValuePair("email", email);
                NameValuePair pair2 = new BasicNameValuePair("password", password);
                NameValuePair pair3 = new BasicNameValuePair("username", nickname);
                NameValuePair pair4 = new BasicNameValuePair("type", Integer.toString(type));
                NameValuePair pair5 = new BasicNameValuePair("address", Integer.toString(address));
                if (type == 0) {
                    params.add(pair2);
                    params.add(pair1);
                    params.add(pair3);
                    params.add(pair4);
                    params.add(pair5);
                    HttpRequest.goPost(params, Constant.register, true);
                    // register method to pass the data to database

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender("yw4dhr@gmail.com","");
                                // user and password
                                sender.sendMail("Regiester is Successful", "Thank you for regiesting SalemState Universal online Ordering App",
                                        "yw4dhr@gmail.com",email);
                                //information of email
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }


                    }).start();
                }
                if(type == 1)
                {
                    Toast.makeText(LoginActivity.this, "Can not register as admin!", Toast.LENGTH_LONG).show();
                    // can not register as admin
                }


                } else {

                    Toast.makeText(LoginActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
                    // make sure if has internet connection
                }
            }

    }

    // use the send email method to send email automaticlly

    private boolean isNicknameValid(String nickname) {
        //TODO: Replace this with your own logic
        return nickname.length()>2;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    private boolean isPasswordAgainValid(String passwordAgain,String password) {
        //TODO: Replace this with your own logic
        return passwordAgain.equals(password);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }






}