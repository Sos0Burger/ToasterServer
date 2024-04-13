package com.sosoburger.toaster.retrofit;

import com.sosoburger.toaster.dto.rq.NotificationContent;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class FirebaseApiImpl implements FirebaseApi{
    static final FirebaseApi firebaseApi = RetrofitClient.getInstance().create(FirebaseApi.class);
    @Override
    public Call<ResponseBody> sendNotification(NotificationContent notificationContent) {
        return firebaseApi.sendNotification(notificationContent);
    }
}
