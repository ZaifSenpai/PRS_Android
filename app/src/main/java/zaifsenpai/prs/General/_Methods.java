package zaifsenpai.prs.General;

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
}
