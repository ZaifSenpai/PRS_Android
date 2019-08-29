package zaifsenpai.prs.General;

import android.Manifest;
import android.content.Context;

/**
 * General Properties which can be used by any class
 */
public abstract class _Properties {
    /**
     * Minimum length allowed of username
     */
    public static int PASSWORD_MIN_LENGTH = 8;
    /**
     * Minimum length allowed of password
     */
    public static int USERNAME_MIN_LENGTH = 4;
    /**
     * Name of sms database
     */
    public static String DATABASE_NAME = "sms_db.sqlite3";
    /**
     * Get Path of sms database
     */
    public static String GET_DATABASE_PATH(Context context) {
        // usually returns: /data/data/zaifsenpai.prs/files/database/sms_db.sqlite3
        return context.getFilesDir().getAbsoluteFile() + "/database/" + DATABASE_NAME;
    }
    public static String LOG_TAG = "PRS_LOG_TAG";
    /**
     * ip address of server with port
     */
    // use following command to start the django server: py manage.py runserver 192.168.2.2:8000
    // This property is set at start of the app in WelcomeActivity.FindServer()
    public static String DJANGO_SERVER_ADDRESS = "http://0.0.0.0:0";

    /**
     * sms API address of server
     */
    public static String DJANGO_SERVER_Sms_API_ADDRESS = DJANGO_SERVER_ADDRESS + "/api/sms/";
    /**
     * recommendations API address of server
     */
    public static String DJANGO_SERVER_Recommendation_API_ADDRESS = DJANGO_SERVER_ADDRESS + "/api/recommendation/";
    /**
     * ip address of asp.net server with port
     */
    public static String ASP_SERVER_ADDRESS = "http://192.168.2.2:8000";
    /**
     * API address of server
     */
    public static String ASP_SERVER_Login_API_ADDRESS = ASP_SERVER_ADDRESS + "/api/Login";
    /**
     * API address of server
     */
    public static String ASP_SERVER_Signup_API_ADDRESS = ASP_SERVER_ADDRESS + "/api/Users";
    /**
     * Permissions that this app needs. These are permissions of "dangerous" level. Other permissions
     * are granted automatically when app is installed
     */
    public static String[] permissions = new String[]{
            Manifest.permission.READ_SMS
    };

    public static void setDjangoServerAddress(String djangoServerAddress) {
        if (!djangoServerAddress.contains("http"))
            djangoServerAddress = "http://" + djangoServerAddress;
        if (!djangoServerAddress.contains(":"))
            djangoServerAddress = djangoServerAddress + ":8000";
        DJANGO_SERVER_ADDRESS = djangoServerAddress;
    }

    public static void setAspServerAddress(String aspServerAddress) {
        if (!aspServerAddress.contains("http"))
            aspServerAddress = "http://" + aspServerAddress;
        if (!aspServerAddress.contains(":"))
            aspServerAddress = aspServerAddress + ":8000";
        ASP_SERVER_ADDRESS = aspServerAddress;
    }
}
