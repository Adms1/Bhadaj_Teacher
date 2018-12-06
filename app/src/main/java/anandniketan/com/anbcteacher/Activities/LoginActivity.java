package anandniketan.com.anbcteacher.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.AsyncTasks.AddDeviceDetailAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.ForgotPasswordAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetTermAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.LoginAsyncTask;
import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Models.LoginModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;
import anandniketan.com.anbcteacher.databinding.ForgotPasswordDialogBinding;

public class LoginActivity extends AppCompatActivity {

    ForgotPasswordDialogBinding forgotPasswordDialogBinding;
    LeaveMainModel forgotResponse;
    Dialog forgotDialog;
    String userNameStr;
    //private ActivityLoginBinding binding;
    private LoginAsyncTask loginAsyncTask = null;
    private Context mContext;
    private ProgressDialog progressDialog;
    private ArrayList<LoginModel> logindetailModels = new ArrayList<>();
    private ForgotPasswordAsyncTask forgotPasswordAsyncTask = null;
    private  String notificationMsg,notificationType,name;
    private EditText UserNameEdt;
    private AppCompatEditText PasswordEdt;
    private AppCompatButton mBtnLogin;
    private AppCompatTextView tvForgotPswd;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.activity_login, null, false);
//        binding = DataBindingUtil.bind(rootView);
//        DataBindingUtil.setContentView(rootView);


        setContentView(R.layout.activity_login);

        UserNameEdt = (EditText)findViewById(R.id.UserName_edt);
        PasswordEdt = (AppCompatEditText) findViewById(R.id.Password_edt);
        mBtnLogin = (AppCompatButton)findViewById(R.id.Login_btn);
        tvForgotPswd = (AppCompatTextView)findViewById(R.id.forgot_txt) ;


        mContext = this;
        UserNameEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_login_user, 0, 0, 0);
        PasswordEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_login_pswd, 0, 0, 0);

        try {
            notificationMsg  = getIntent().getStringExtra("message");
            notificationType = getIntent().getStringExtra("fromNotification");

            if(notificationMsg != null) {
                Utility.setPref(LoginActivity.this, "Push_Notification_message", notificationMsg);
                Utility.setPref(LoginActivity.this,"notification_type",notificationType);
            }
            try {
                name = getIntent().getStringExtra("Name");
                Utility.setPref(LoginActivity.this,"notification_name",name);

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }catch (Exception ex){
            ex.getLocalizedMessage();
        }


        checkUnmPwd();

       PasswordEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i==EditorInfo.IME_ACTION_DONE){
                    login(textView);
                }
                return false;
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

        tvForgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });


    }

    public void login(View view) {
        if (Utility.isNetworkConnected(mContext)) {

            if (!UserNameEdt.getText().toString().equalsIgnoreCase("")) {
                if (!PasswordEdt.getText().toString().equalsIgnoreCase("")) {
                    if (PasswordEdt.getText().toString().length() >= 3 && PasswordEdt.getText().toString().length() <= 13) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("UserID", UserNameEdt.getText().toString().trim());
                                    params.put("Password",PasswordEdt.getText().toString().trim());

                                    loginAsyncTask = new LoginAsyncTask(params);
                                    logindetailModels = loginAsyncTask.execute().get();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            if (logindetailModels.size() > 0) {
//                              TODO: Store result values for future use

                                                // if (binding.RememberChk.isChecked()) {
                                                saveUserNamePwd(UserNameEdt.getText().toString(),PasswordEdt.getText().toString());
                                                //}
                                                Utility.setPref(mContext, "StaffID", logindetailModels.get(0).getStaffID());
                                                Utility.setPref(mContext, "Emp_Code", logindetailModels.get(0).getEmp_Code());
                                                Utility.setPref(mContext, "Emp_Name", logindetailModels.get(0).getEmp_Name());
                                                Utility.setPref(mContext, "DepratmentID", logindetailModels.get(0).getDepratmentID());
                                                Utility.setPref(mContext, "DesignationID", logindetailModels.get(0).getDesignationID());
                                                Utility.setPref(mContext, "DeviceId", logindetailModels.get(0).getDeviceId());

                                                Utility.pong(mContext, "Login Successful");


                                                try {
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put("StaffID",Utility.getPref(getApplicationContext(), "StaffID"));
                                                    hashMap.put("DeviceId",Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
                                                    hashMap.put("TokenId",Utility.getPref(getApplicationContext(), "registration_id"));
                                                    hashMap.put("DeviceType","A");

                                                    AddDeviceDetailAsyncTask addDeviceDetailAsyncTask = new AddDeviceDetailAsyncTask(hashMap);
                                                    boolean result = addDeviceDetailAsyncTask.execute().get();


                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }

                                                Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);

                                                try {
                                                    notificationMsg = Utility.getPref( LoginActivity.this,"Push_Notification_message");
                                                    if(notificationMsg != null){
                                                        intentDashboard.putExtra( "message",notificationMsg);
                                                    }
                                                }catch (Exception ex){
                                                    ex.printStackTrace();
                                                }

                                                startActivity(intentDashboard);
                                                finish();


                                            } else {
                                                Utility.pong(mContext, "Invalid Username/Password");
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        PasswordEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                        Utility.ping(mContext, "Password must be 3 to 13 character");
                    }
                } else {
                    PasswordEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                    Utility.ping(mContext, "Please Enter Password");
                }
            } else {
                UserNameEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                Utility.ping(mContext, "Please Enter Username");
            }
        } else {
            Utility.ping(mContext, "Network not available");
        }
//        }
    }

    public void forgotPassword() {
        forgotPasswordDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.forgot_password_dialog,(ViewGroup)rootView, false);
//
        forgotDialog = new Dialog(mContext);



        Window window = forgotDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        forgotDialog.getWindow().getAttributes().verticalMargin = 0.0f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        forgotDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotDialog.setCancelable(false);
        forgotDialog.setContentView(forgotPasswordDialogBinding.getRoot());

        forgotDialog.show();
    }

    public void closeBtn(View view) {
        forgotDialog.dismiss();
    }

    public void getForgotData(final View view) {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //  progressDialog.show();
            userNameStr = forgotPasswordDialogBinding.edtmobileno.getText().toString();
            if (!userNameStr.equalsIgnoreCase("")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("MobileNo", userNameStr);
                            forgotPasswordAsyncTask = new ForgotPasswordAsyncTask(params);
                            forgotResponse = forgotPasswordAsyncTask.execute().get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if (forgotResponse.getSuccess().equalsIgnoreCase("True")) {

                                        Utility.ping(mContext,forgotResponse.getMessage());

                                        if(forgotDialog != null){
                                            forgotDialog.dismiss();
                                        }

                                    } else {
                                        progressDialog.dismiss();
                                        Utility.ping(mContext, forgotResponse.getMessage());
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Utility.ping(mContext, "Please enter your school communication no");
            }
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void checkUnmPwd() {
        if (!Utility.getPref(mContext, "unm").equalsIgnoreCase("")) {
            Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(intentDashboard);
            finish();
        }
    }

    public void saveUserNamePwd(String unm, String pwd) {
        Utility.setPref(mContext, "unm", unm);
        Utility.setPref(mContext, "pwd", pwd);
    }
}
