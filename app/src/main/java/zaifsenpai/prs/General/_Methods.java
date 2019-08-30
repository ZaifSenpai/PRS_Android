package zaifsenpai.prs.General;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * General methods which can be used by any class
 */
public class _Methods {

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
     * Check if given date is a valid DOB. Date should be in format yyyy-MM-dd and shows that user is older than 10 years and younger than 80 years
     *
     * @param dob Date of Birth in format yyyy-MM-dd
     * @return true if dob is valid
     */
    public static boolean isValidDoB(CharSequence dob) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dob.toString()), dateBefore10Years, dateBefore80Years;
            Calendar cal = Calendar.getInstance();

            cal.setTime(new Date());
            cal.add(Calendar.YEAR, -10);
            dateBefore10Years = cal.getTime();

            cal.setTime(new Date());
            cal.add(Calendar.YEAR, -80);
            dateBefore80Years = cal.getTime();

            if (date.after(dateBefore80Years) && date.before(dateBefore10Years))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
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
