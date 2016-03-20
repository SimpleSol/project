package biz.growapp.baseandroidproject.network;

public interface NetoworkConst {
    String DEV_URL = "http://dev.xxxx.com";
    String PROD_URL = "https://prod.xxxx.com";

    int IMAGE_MAX_SIZE = 1024;
    int IMAGE_COMPRESS_QUALITY = 85;
    String IMAGE_COMPRESSED_NAME = "compressedImage.jpg";

    long CONNECTION_TIMEOUT = 60;
    long READ_TIMEOUT = 60;

    // TODO: 20.03.2016 move this class to upper level
    interface ErrorCodes {
        String TOKEN_EXPIRED = "invalid_token";
        String UNKNOWN_ERROR = "unknown_error";
    }
}
