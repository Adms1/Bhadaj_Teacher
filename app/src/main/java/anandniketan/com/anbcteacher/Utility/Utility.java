package anandniketan.com.anbcteacher.Utility;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.AsyncTasks.DeleteDeviceDetailTsk;
import anandniketan.com.anbcteacher.R;

/**
 * Created by Megha on 15-Sep-16.
 */
public class Utility {
    public static final String MyPREFERENCES = "MyPrefs";
    public static SharedPreferences sharedpreferences;
    public static String parentFolderName = "Skool 360 Teacher";
    public static String childAnnouncementFolderName = "Pdf";
    public static String childCircularFolderName = "Word";
    private static final int MEGABYTE = 1024 * 1024;

    public static boolean isNetworkConnected(Context ctxt) {
        ConnectivityManager cm = (ConnectivityManager) ctxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static boolean isFileExists(String fileName, String moduleName) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        if (moduleName.equalsIgnoreCase("announcement"))
            return new File(extStorageDirectory, parentFolderName + "/" + childAnnouncementFolderName + "/" + fileName).isFile();
        else
            return new File(extStorageDirectory, parentFolderName + "/" + childCircularFolderName + "/" + fileName).isFile();
    }

    public static void ping(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void pong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setPref(Context context, String key, String value) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    public static String getPref(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key,"");
        return value;
    }
    public static String getStringPref(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, "N/A");
        return value;
    }

//        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();


    public static String getTodaysDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        String mDAY, mMONTH, mYEAR;

        mDAY = Integer.toString(dd);
        mMONTH = Integer.toString(mm);
        mYEAR = Integer.toString(yy);

        if (dd < 10) {
            mDAY = "0" + mDAY;
        }
        if (mm < 10) {
            mMONTH = "0" + mMONTH;
        }

        return mDAY + "/" + mMONTH + "/" + mYEAR;
    }

    public static void downloadFile(String fileUrl, String fileName, String moduleName) {
        try {

            File directoryPath = createFile(fileName, moduleName);

            fileUrl = fileUrl.replace(" ", "%20");
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directoryPath);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFile(String fileName, String moduleName) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = null;

        if (moduleName.equalsIgnoreCase("announcement"))
            folder = new File(extStorageDirectory, parentFolderName + "/" + childAnnouncementFolderName);
        else
            folder = new File(extStorageDirectory, parentFolderName + "/" + childCircularFolderName);

        folder.mkdirs();

        File pdfFile = new File(folder, fileName);

        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }

    static SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
    public static boolean CheckDates(String startdate,String enddate)   {
        boolean b = true;
        try {
            if(dfDate.parse(enddate).before(dfDate.parse(startdate)))
            {
                b = false;//If start date is before end date
            }
            else if(dfDate.parse(enddate).equals(dfDate.parse(startdate)))
            {
                b = true;//If two dates are equal
            }
            else
            {
                b = true; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static boolean checkBirthdayOfUser(int month,int  date){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH) + 1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        if((month == monthOfYear) && (date == dayOfMonth)){
            return true;
        }
        return false;
    }

    public static void showUserBirthdayWish(Context context,String Bday){

        try {
            if (Utility.getPref(context, "user_birthday_wish").equalsIgnoreCase("0")) {
                if (Utility.getPref(context, "user_birthday") != null) {
                    if (Utility.getPref(context, "user_birthday").equals("")) {

                            Utility.setPref(context, "user_birthday", Bday);

                            String Bday_local = Utility.getPref(context, "user_birthday");

                            String[] BdayArray = Bday_local.split("/");
                            String date = BdayArray[0];
                            String month = BdayArray[1];

                            if (Utility.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {

                                String flag = Utility.getPref(context, "user_birthday_wish");
                                if (flag != null && !flag.equalsIgnoreCase("")) {
                                    if (flag.equalsIgnoreCase("0")) {
                                        Utility.setPref(context, "user_birthday_wish", "1");
                                        Utility.setPref(context, "user_birthday", "N/A");
                                        DialogUtils.showGIFDialog(context, "Anand Niketan Bhadaj");

                                    }
                                }
                            }
                        }else {

//                            Utility.setPref(context, "user_birthday", Bday);
//
//                            String Bday_local = Utility.getPref(context, "user_birthday");
//
//                            String[] BdayArray = Bday_local.split("/");
//                            String date = BdayArray[0];
//                            String month = BdayArray[1];
//
//                            if (Utility.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {
//                                String flag = Utility.getPref(context, "user_birthday_wish");
//                                if (flag != null && !flag.equalsIgnoreCase("")) {
//                                    if (flag.equalsIgnoreCase("0")) {
//                                        Utility.setPref(context, "user_birthday_wish", "1");
//
//                                        Utility.setPref(context, "user_birthday", "N/A");
//                                        DialogUtils.showGIFDialog(context, "Anand Niketan Bhadaj");
//
//                                    }
//                                }
//                            }
                         }
                    }
                 }
                else {
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void doLogout(final Context context) {
        new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(context, R.style.AppTheme))
                .setCancelable(false)
                .setTitle("Logout")
                .setIcon(context.getResources().getDrawable(R.drawable.ic_launcher))
                .setMessage("Are you sure you want to logout? ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("StaffID", getPref(context.getApplicationContext(), "StaffID"));
                            hashMap.put("DeviceId", Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));

                            DeleteDeviceDetailTsk deleteDeviceDetailTsk = new DeleteDeviceDetailTsk(hashMap);
                            boolean result = deleteDeviceDetailTsk.execute().get();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Utility.setPref(context, "StaffID", "");
                        Utility.setPref(context, "Emp_Code", "");
                        Utility.setPref(context, "Emp_Name", "");
                        Utility.setPref(context, "DepratmentID", "");
                        Utility.setPref(context, "DesignationID", "");
                        Utility.setPref(context, "DeviceId", "");
                        Utility.setPref(context, "unm", "");
                        Utility.setPref(context, "pwd", "");
                        Utility.setPref(context, "user_birthday", "");
                        Intent i = new Intent(context, LoginActivity.class);
                        context.startActivity(i);
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.ic_launcher)
                .show();
    }


}
