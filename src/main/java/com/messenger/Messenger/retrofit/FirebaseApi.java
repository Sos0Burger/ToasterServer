package com.messenger.Messenger.retrofit;

import com.messenger.Messenger.FireBaseApiToken;
import com.messenger.Messenger.dto.rq.NotificationContent;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface FirebaseApi {

    @POST("fcm/send")
    @Headers({
            "Content-Type: application/json",
            "Authorization: key="+FireBaseApiToken.token
    }
    )
    Call<ResponseBody> sendNotification(@Body NotificationContent notificationContent);
}
