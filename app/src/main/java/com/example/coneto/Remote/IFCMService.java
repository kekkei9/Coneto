package com.example.coneto.Remote;

import com.example.coneto.Model.FCMResponse;
import com.example.coneto.Model.FCMSendData;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAVTT0arA:APA91bHaWKss3N4hKSK3XKjHBLoX18XALFn27GhUcowmLTGGBBV5wYbByxrVPctf0nRnnzRe7FPzI_MWPcmOZ8Xxrqk7eNvkMJnCD6nPgZH7JAmSVrGFnzAxLU5ju_-VTAsBB3Ex6LTD"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);
}
