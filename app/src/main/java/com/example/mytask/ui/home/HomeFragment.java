package com.example.mytask.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytask.databinding.FragmentHomeBinding;
import com.example.mytask.model.PrayerTimeResponse;
import com.example.mytask.network.PrayerTimeService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
private static final String BASE_URL = "https://api.aladhan.com/v1/";

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCurrentDateTime();

        getPrayerTimes("Selangor", "Malaysia", 11);
    }


    private void getPrayerTimes(String city, String country, int method) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PrayerTimeService prayerTimeService = retrofit.create(PrayerTimeService.class);
        Call<PrayerTimeResponse> call = prayerTimeService.getPrayerTimes(city, country, method);

        call.enqueue(new Callback<PrayerTimeResponse>() {

            @Override
            public void onResponse(Call<PrayerTimeResponse> call, Response<PrayerTimeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PrayerTimeResponse prayerTimeResponse = response.body();
                    updateUI(prayerTimeResponse);
                } else {
                    Toast.makeText(getActivity(), "Failed to get prayer times", Toast.LENGTH_SHORT).show();
                    System.out.println("Response code: " + response.code());
                    System.out.println("Response message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PrayerTimeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "Network failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Conversion issue! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                retryPrayerTimes(city, country, method);
            }
        });
    }

    private void retryPrayerTimes(String city, String country, int method) {
        new android.os.Handler().postDelayed(() -> getPrayerTimes(city, country, method), 5000);
    }

    private void updateUI(PrayerTimeResponse prayerTimeResponse) {
        PrayerTimeResponse.Data data = prayerTimeResponse.getData();
        if (data != null) {
            PrayerTimeResponse.Timings timings = data.getTimings();

            if (timings != null) {
                binding.textViewFajr.setText(String.format(timings.getFajr()));
                binding.textViewDhuhr.setText(String.format(timings.getDhuhr()));
                binding.textViewAsr.setText(String.format(timings.getAsr()));
                binding.textViewMaghrib.setText(String.format(timings.getMaghrib()));
                binding.textViewIsha.setText(String.format(timings.getIsha()));
            }
        }
    }



    private void displayCurrentDateTime() {

        ZonedDateTime nowCurrent = LocalDateTime.now().atZone(ZoneId.of("Asia/Kuala_Lumpur"));
        String dateString = nowCurrent.format(DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy"));
        String timeString = nowCurrent.format(DateTimeFormatter.ofPattern("h:mm a"));
        String zoneString = nowCurrent.format(DateTimeFormatter.ofPattern("zzzz"));

        binding.currentDateTime.setText(dateString);
        binding.time.setText(timeString);
        binding.timezone.setText(zoneString);
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}