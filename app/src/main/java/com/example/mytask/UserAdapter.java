package com.example.mytask;

// UserAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_notifications, parent, false);
        }

        ImageView imageViewProfile = convertView.findViewById(R.id.imageViewProfile);
        TextView firstNameTextView = convertView.findViewById(R.id.firstName);
//        TextView lastNameTextView = convertView.findViewById(R.id.lastName);
//        TextView emailTextView = convertView.findViewById(R.id.email);
//        TextView phoneTextView = convertView.findViewById(R.id.phone);



//        imageViewProfile.setImageResource(R.drawable.ic_user_black_24);

        ImageView imageView = (imageViewProfile).findViewById(R.id.imageViewProfile);

        Glide.with(imageView.getContext())
                .load(R.drawable.ic_account_circle_orange_24)
                .placeholder(R.drawable.ic_account_circle_orange_24)
                .error(R.drawable.bg_circle)
                .into(imageViewProfile);

        firstNameTextView.setText(user.getFirstName() + ',' + user.getLastName());
//        lastNameTextView.setText(user.getLastName());
//        emailTextView.setText(user.getEmail());
//        phoneTextView.setText(user.getPhone());

        return convertView;
    }
}
