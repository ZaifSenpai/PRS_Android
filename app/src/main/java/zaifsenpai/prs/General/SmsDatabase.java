package zaifsenpai.prs.General;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Sms.class}, version = 1, exportSchema = false)
public abstract class SmsDatabase extends RoomDatabase {
    public abstract SmsDao smsDao();
}
