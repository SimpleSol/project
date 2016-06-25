package biz.growapp.network.service;

import biz.growapp.network.request.CreateContent;
import biz.growapp.network.response.base.EmptyResponse;
import biz.growapp.network.response.base.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StreamService {

    @POST("j/m/content/plain")
    Call<ServerResponse<EmptyResponse>> createPost(@Body CreateContent request);

    @POST("j/m/like/content/contentId/{postId}")
    Call<ServerResponse<EmptyResponse>> likePost(@Path("postId") int postId);


}
