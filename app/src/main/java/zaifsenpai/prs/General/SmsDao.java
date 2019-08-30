package zaifsenpai.prs.General;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SmsDao {
    @Insert
    void InsertSingleSms(Sms sms);

    @Insert
    void InsertMultipleSms(List<Sms> smsList);

    @Query("SELECT * FROM Sms")
    List<Sms> GetAll();

    @Query("SELECT * FROM Sms WHERE IsUploaded = '0' LIMIT :n")
    List<Sms> GetFirstNSmsNotUploaded(int n);

    @Query("SELECT * FROM Sms WHERE Sms_ID = :sms_id")
    Sms GetSmsById(int sms_id);

    @Query("SELECT * FROM Sms WHERE Sms_ID_Source LIKE :sms_id_source")
    Sms GetSmsBySourceId(String sms_id_source);

    @Query("SELECT * FROM Sms WHERE Sms_ID = (SELECT MAX(Sms_ID) FROM Sms)")
    Sms GetLastSms();

    @Query("UPDATE Sms SET IsUploaded = '1' WHERE Sms_ID = :sms_id")
    void MarkSmsAsSendById(int sms_id);

    @Query("SELECT COUNT(*) FROM Sms WHERE IsUploaded = '0';")
    int GetNonUploadedSmsCount();

    @Update
    void updateSms(Sms sms);

    @Delete
    void deleteSms(Sms sms);
}
