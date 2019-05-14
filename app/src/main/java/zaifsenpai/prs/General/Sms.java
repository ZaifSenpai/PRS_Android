package zaifsenpai.prs.General;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Sms {
    @PrimaryKey(autoGenerate = true)
    public int Sms_ID;
    public String Sms_ID_Source;
    public String Address;
    public String Body;
    public long Date;
    public boolean IsUploaded;
}
