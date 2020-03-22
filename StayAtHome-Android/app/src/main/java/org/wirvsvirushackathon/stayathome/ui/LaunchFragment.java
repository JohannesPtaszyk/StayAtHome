package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.data.UserPreferencesDataSource;

public class LaunchFragment extends Fragment {

    private UserPreferencesDataSource userPreferencesDataSource;

    public LaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(
            @Nullable Bundle savedInstanceState) {
        userPreferencesDataSource = new UserPreferencesDataSource(requireContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).hideNavbar();
        final View root = inflater.inflate(R.layout.fragment_launch,
                                           container,
                                           false);
        root.findViewById(R.id.btn_start)
            .setOnClickListener(v -> {

                TextInputEditText nameEditText = root.findViewById(R.id.eTxt_Name);
                Editable name = nameEditText.getText();

                if (name != null && name.length() != 0) {
                    String nameString = name.toString();
                    userPreferencesDataSource.setUserName(nameString);
                } else {
                    TextInputLayout tilName = root.findViewById(R.id.til_name);
                    tilName.setError("Bitte gib einen Namen ein :)");
                    return;
                }

                TextInputEditText emailEditText = root.findViewById(R.id.eTxt_mail);
                Editable email = emailEditText.getText();

                if (email != null && email.length() != 0) {
                    String emailString = email.toString();
                    userPreferencesDataSource.setUserEmail(emailString);
                } else {
                    TextInputLayout tilEmail = root.findViewById(R.id.til_mail);
                    tilEmail.setError("Bitte gib eine E-mail ein :)");
                    return;
                }

                Navigation.findNavController(root)
                          .navigate(R.id.action_launchFragment_to_homeScreenFragment);
            });
        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!userPreferencesDataSource.getUserName().isEmpty() && !userPreferencesDataSource.getUserEmail().isEmpty()) {
            Navigation.findNavController(view)
                      .navigate(R.id.action_launchFragment_to_homeScreenFragment);
        }
    }
}
