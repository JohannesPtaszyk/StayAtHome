package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import org.wirvsvirushackathon.stayathome.R;

public class UserFragment extends Fragment {



    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View root = inflater.inflate(R.layout.fragment_user, container, false);
        return root;
    }
}
