package zaifsenpai.prs.General;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class SmsUploadService extends IntentService {
    private SmsDatabase smsDatabase;

    public SmsUploadService() {
        this("SmsUploadService");
    }

    public SmsUploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        smsDatabase = Room.databaseBuilder(SmsUploadService.this,
                SmsDatabase.class,
                _Properties.GET_DATABASE_PATH(SmsUploadService.this))
                .build();
        Log.d(_Properties.LOG_TAG, "Starting uploading... ");
        UploadSMS();
        Log.d(_Properties.LOG_TAG, "Uploading Stopped...");
    }

    private void UploadSMS() {
        int n;

        while ((n = smsDatabase.smsDao().GetNonUploadedSmsCount()) > 0) {
            List<Sms> smsList = smsDatabase.smsDao().GetFirstNSmsNotUploaded(10);

            UploadSmsList(smsList);

            try {
                Thread.sleep(5000);
            } catch (Exception ignored) {
            }
        }
    }

    private void UploadSmsList(List<Sms> smsList) {
        StringBuilder csv_data = new StringBuilder();

        for (Sms sms : smsList) {
            csv_data.append(sms.Sms_ID)
                    .append(",")
                    .append(sms.Sms_ID_Source)
                    .append(",")
                    .append(sms.Address)
                    .append(",\"")
                    .append(sms.Body.replace("\"", "\"\"").trim())
                    .append("\",")
                    .append(sms.Date)
                    .append("\n");
            sms.IsUploaded = true;

            smsDatabase.smsDao().updateSms(sms);
        }

        Log.d(_Properties.LOG_TAG, csv_data.toString());

        // @Todo: Implement http POST request to upload sms to Django server
        //

    }
}
