package anandniketan.com.bhadajteacher.Utility;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

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
    }

    public static String getPref(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, "");
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


    public static final int REQUEST_PERMISSIONS_Internet = 1;
    public static final int REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE = 2;
    public static final int REQUEST_PERMISSIONS_ACCESS_WIFI_STATE = 3;
    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE = 4;
    public static final int REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 5;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.INTERNET) &&
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_NETWORK_STATE) &&
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_WIFI_STATE) &&
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSIONS_Internet);
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE);
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_PERMISSIONS_ACCESS_WIFI_STATE);
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSIONS_Internet);
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_PERMISSIONS_ACCESS_NETWORK_STATE);
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_PERMISSIONS_ACCESS_WIFI_STATE);
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
