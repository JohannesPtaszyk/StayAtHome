package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;

import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wirvsvirushackathon.stayathome.R;

public class LaunchFragment extends Fragment {

    public LaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).hideNavbar();

        final View root = inflater.inflate(R.layout.fragment_launch, container, false);
       root.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Navigation.findNavController(root).navigate(R.id.action_launchFragment_to_homeScreenFragment);
           }
       });
        return root;
    }
}
