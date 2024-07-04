package com.example.mytask.network;

import com.example.mytask.model.PrayerTimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerTimeService {
    @GET("timingsByCity")
    Call<PrayerTimeResponse> getPrayerTimes(
            @Query("city") String city,
            @Query("country") String country,
            @Query("method") int method
    );
}