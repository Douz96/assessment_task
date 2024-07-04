package com.example.mytask.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mytask.R;
import com.example.mytask.User;
import com.example.mytask.UserAdapter;
import com.example.mytask.Utils;
import com.example.mytask.databinding.FragmentNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private ListView listView;
    private UserAdapter adapter;
    private List<User> userList;


private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        //                binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        //                View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        listView = view.findViewById(R.id.listView);
        userList = new ArrayList<>();

        String jsonString = Utils.loadJSONFromAsset(getContext(), "users.json");

            if (jsonString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String firstName = jsonObject.getString("firstName");
                        String lastName = jsonObject.getString("lastName");
                        String email = jsonObject.getString("email");
                        String phone = jsonObject.getString("phone");

                        User user = new User(id, firstName, lastName, email, phone);
                        userList.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            adapter = new UserAdapter(getContext(), userList);
            listView.setAdapter(adapter);

            TextView textFN = view.findViewById(R.id.firstName);

            textFN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to UserDetailsFragment
                    NavController navController = NavHostFragment.findNavController(NotificationsFragment.this);
                    navController.navigate(R.id.action_fragmentNotification_to_userDetailsFragment);
                }
            });

            return view;



            //                final TextView textView = binding.textNotifications;
            //                notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            //                return root;


    }


//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Set click listener for the TextView
//        binding.firstName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to UserDetailsFragment using Navigation Component
//                NavHostFragment.findNavController(NotificationsFragment.this)
//                        .navigate(R.id.action_fragmentNotification_to_userDetailsFragment);
//            }
//        });
//    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////

    public void refreshData() {
        // Clear existing data
        userList.clear();

        // Load JSON data from assets
        String jsonString = Utils.loadJSONFromAsset(getContext(), "users.json");

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String firstName = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String email = jsonObject.getString("email");
                    String phone = jsonObject.getString("phone");

                    User user = new User(id, firstName, lastName, email, phone);
                    userList.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }




} // end