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
    /**
     * Get Path of sms database
     */
    public static String LOG_TAG = "PRS_LOG_TAG";
    /**
     * ip address of server with port
     */
    // use following command to start the django server: py manage.py runserver 192.168.2.2:8000
    // Make sure that this ip address is in "ALLOWED_HOSTS" list in settings.py
    public static String SERVER_ADDRESS = "http://192.168.2.2:8000";
    /**
     * API address of server
     */
    public static String SERVER_Sms_API_ADDRESS = SERVER_ADDRESS + "/api/sms/";
    /**
     * API address of server
     */
    public static String SERVER_Recommendation_API_ADDRESS = SERVER_ADDRESS + "/api/recommendation/";
    /**
     * Permissions that this app needs
     */
    public static String[] permissions = new String[]{
            Manifest.permission.READ_SMS,
            Manifest.permission.INTERNET
    };
}
