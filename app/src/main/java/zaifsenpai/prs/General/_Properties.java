package zaifsenpai.prs.General;

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
     * API address of server
     */
    public static String SERVER_API_ADDRESS = "http://192.168.2.2:8000/api/sms/";
}
