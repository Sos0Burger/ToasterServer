package com.sosoburger.toaster.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;


public class ImaggaApiImpl implements ImaggaApi {
    static final ImaggaApi IMAGGA_API = RetrofitClient.getInstance().create(ImaggaApi.class);

    @Override
    public Call<UploadResult> upload(MultipartBody.Part image, String auth) {
        return IMAGGA_API.upload(image, auth);
    }

    @Override
    public Call<ResponseTagsDTO> getTags(String id, String auth) {
        return IMAGGA_API.getTags(id, auth);
    }
}
