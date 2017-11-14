package anandniketan.com.bhadajteacher.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.AsyncTasks.LoginAsyncTask;
import anandniketan.com.bhadajteacher.Models.LoginModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;
import anandniketan.com.bhadajteacher.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginAsyncTask loginAsyncTask = null;
    private Context mContext;
    private ProgressDialog progressDialog;
    private ArrayList<LoginModel> logindetailModels = new ArrayList<>();
    boolean isBoolean_permission_access_network = false,
            isBoolean_permission_internet = false,
            isBoolean_permission_wifi = false,
            isBoolean_permission_read_external = false,
            isBoolean_permission_wirte_external = false;
    public static final int REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE = 1;
    public static final int REQUEST_PERMISSIONS_Internet = 2;
    public static final int REQUEST_PERMISSIONS_ACCESS_WIFI_STATE = 3;
    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE = 4;
    public static final int REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        mContext = this;
        fn_permission_ACCESS_ACCESS_NETWORK_STATE();
        binding.UserNameEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
        checkUnmPwd();
    }

    public void login(View view) {
//        if (isBoolean_permission_internet &&
//                isBoolean_permission_access_network &&
//                isBoolean_permission_wifi &&
//                isBoolean_permission_read_external &&
//                isBoolean_permission_wirte_external) {
            if (Utility.isNetworkConnected(mContext)) {

                if (!binding.UserNameEdt.getText().toString().equalsIgnoreCase("")) {
                    if (!binding.PasswordEdt.getText().toString().equalsIgnoreCase("")) {
                        if (binding.PasswordEdt.getText().toString().length() >= 3 && binding.PasswordEdt.getText().toString().length() <= 13) {
                            progressDialog = new ProgressDialog(mContext);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        HashMap<String, String> params = new HashMap<String, String>();
                                        params.put("UserID", binding.UserNameEdt.getText().toString().trim());
                                        params.put("Password", binding.PasswordEdt.getText().toString().trim());

                                        loginAsyncTask = new LoginAsyncTask(params);
                                        logindetailModels = loginAsyncTask.execute().get();

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                if (logindetailModels.size() > 0) {
//                              TODO: Store result values for future use

                                                    if (binding.RememberChk.isChecked()) {
                                                        saveUserNamePwd(binding.UserNameEdt.getText().toString(), binding.PasswordEdt.getText().toString());
                                                    }
                                                    Utility.setPref(mContext, "StaffID", logindetailModels.get(0).getStaffID());
                                                    Utility.setPref(mContext, "Emp_Code", logindetailModels.get(0).getEmp_Code());
                                                    Utility.setPref(mContext, "Emp_Name", logindetailModels.get(0).getEmp_Name());
                                                    Utility.setPref(mContext, "DepratmentID", logindetailModels.get(0).getDepratmentID());
                                                    Utility.setPref(mContext, "DesignationID", logindetailModels.get(0).getDesignationID());
                                                    Utility.setPref(mContext, "DeviceId", logindetailModels.get(0).getDeviceId());

                                                    Utility.pong(mContext, "Login Successful");
                                                    Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
                                                    startActivity(intentDashboard);

                                                    finish();
                                                } else {
                                                    Utility.pong(mContext, "Invalid Credentials. Please try again...");
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            binding.PasswordEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                            Utility.ping(mContext, "Password must be 3 to 13 character");
                        }
                    } else {
                        binding.PasswordEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                        Utility.ping(mContext, "Enter Password");
                    }
                } else {
                    binding.UserNameEdt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                    Utility.ping(mContext, "Enter Username");
                }
            } else {
                Utility.ping(mContext, "Network not available");
            }
//        }
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

    private void fn_permission_ACCESS_ACCESS_NETWORK_STATE() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_NETWORK_STATE))) {


            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE);

            }
        } else {
            isBoolean_permission_internet = true;
        }
    }

    private void fn_permission_Internet() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.INTERNET))) {

            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSIONS_Internet);

            }
        } else {
            isBoolean_permission_access_network = true;
        }
    }

    private void fn_permission_ACCESS_WIFI_STATE() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_WIFI_STATE))) {


            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_PERMISSIONS_ACCESS_WIFI_STATE);

            }
        } else {
            isBoolean_permission_wifi = true;
        }
    }

    private void fn_permission_READ_EXTERNAL_STORAGE() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))) {


            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);

            }
        } else {
            isBoolean_permission_read_external = true;
        }
    }

    private void fn_permission_ACCESS_WRITE_EXTERNAL_STORAGE() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {


            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

            }
        } else {
            isBoolean_permission_wirte_external = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isBoolean_permission_access_network = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
            case REQUEST_PERMISSIONS_Internet: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isBoolean_permission_internet = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
                fn_permission_Internet();
            }
            case REQUEST_PERMISSIONS_ACCESS_WIFI_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isBoolean_permission_wifi = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
                fn_permission_ACCESS_WIFI_STATE();
            }
            case REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isBoolean_permission_read_external = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
                fn_permission_READ_EXTERNAL_STORAGE();
            }
            case REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isBoolean_permission_wirte_external = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
                fn_permission_ACCESS_WRITE_EXTERNAL_STORAGE();
            }
        }
    }
}
