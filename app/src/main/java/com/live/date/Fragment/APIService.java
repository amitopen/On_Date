package com.live.date.Fragment;

import com.live.date.Notifications.MyResponse;
import com.live.date.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "content-Type:application/json",
                    "Authorization:key=AAAAGP7hVM4:APA91bE9428aaTeI4lIIBM-yQwr6fenDORZ2G_5wXz5DMFc2LnyM_Z8XYbqHHygCa1JPBQOhP7NchFY4m-yl-sEq7Ss1udH--sfgGecRPtXtNwzm24-usW_C1C5f8U78-3z84H7TG3RF"

    }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
