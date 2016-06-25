package biz.growapp.network.request;

public class CreateContent {
    private final Integer photoId;
    private final String text;

    public CreateContent(String text) {
        this.text = text;
        photoId = null;
    }

    public CreateContent(Integer photoId, String text) {
        this.photoId = photoId;
        this.text = text;
    }
}
