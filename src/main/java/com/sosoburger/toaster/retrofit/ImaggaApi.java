package com.sosoburger.toaster.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface ImaggaApi {

    @POST("uploads")
    @Multipart
    Call<UploadResult> upload(@Part MultipartBody.Part image, @Header("Authorization") String auth);

    @GET("tags?language=ru")
    Call<ResponseTagsDTO> getTags(@Query("image_upload_id")String id, @Header("Authorization") String auth);
}
