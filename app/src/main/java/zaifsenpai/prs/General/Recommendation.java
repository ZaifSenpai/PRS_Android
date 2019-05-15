package zaifsenpai.prs.General;

public class Recommendation {
    public int RecoId;
    public String Name;
    public String Image;
    public String Url;

    public Recommendation() {
    }

    public Recommendation(int recoId, String name, String image, String url) {
        RecoId = recoId;
        Name = name;
        Image = image;
        Url = url;
    }
}
