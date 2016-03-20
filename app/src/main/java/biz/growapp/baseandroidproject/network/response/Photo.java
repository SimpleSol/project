package biz.growapp.baseandroidproject.network.response;

/**
 "photo": {
 "id": 324,
 "userId": null,
 "hash": "d300ffbb7da4fcdf44d22ffc9110f69cec62cd19ea56378890f750c7b39b48f6",
 "size": "143.3K",
 "width": 320,
 "height": 480,
 "ts": 1455012390,
 "url": "http://static.dev.babylinkapp.com/photo/d300ffbb7da4fcdf44d22ffc9110f69cec62cd19ea56378890f750c7b39b48f6"
 },
 */
public class Photo {
    public static final int NO_ID = -1;

    public int id;
    public int userId;
    public String hash;
    public int width;
    public int height;
    public long ts;
    public String url;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getHash() {
        return hash;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getTimestamp() {
        return ts;
    }

    public String getUrl() {
        return url;
    }
}
