package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.wirvsvirushackathon.stayathome.R;

public class SelectHomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).hideNavbar();
        final View root = inflater.inflate(R.layout.fragment_select_home,
                container,
                false);
        root.findViewById(R.id.selectHome_confirm)
                .setOnClickListener(v -> {

                    Navigation.findNavController(root)
                            .navigate(R.id.action_selectHomeFragment_to_selectWifiFragment);
                });
        return root;
    }
}
