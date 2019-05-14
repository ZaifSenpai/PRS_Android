package zaifsenpai.prs.General;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Patterns;

/**
 * General methods which can be used by any class
 */
public abstract class _Methods {

    /**
     * Check if given string is a valid email address or not
     * @param target string to check if it is email or not
     * @return true if target is email, false otherwise
     */
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) &&
                Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Check if given permissions are given to app or not
     *
     * @param context     Context of application
     * @param permissions permissions to check if they are granted or not
     * @return true if target is email, false otherwise
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
