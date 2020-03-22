package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.data.User;
import org.wirvsvirushackathon.stayathome.data.UserPreferencesDataSource;
import org.wirvsvirushackathon.stayathome.model.RestDBInterface;
import org.wirvsvirushackathon.stayathome.model.ServerCommunicationManager;
import org.wirvsvirushackathon.stayathome.model.UserManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                //TODO: validate for email regex
                if (email != null && email.length() != 0) {
                    String emailString = email.toString();
                    userPreferencesDataSource.setUserEmail(emailString);
                } else {
                    TextInputLayout tilEmail = root.findViewById(R.id.til_mail);
                    tilEmail.setError("Bitte gib eine E-mail ein :)");
                    return;
                }

                // Next step : Check if this email is already registered
                /*
                //TODO: not necassary in prototype
                Call<List<User>>  getUser = ServerCommunicationManager.getDbInterface().getUserByMail(email.toString());
                Callback<List<User>> callback = new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        if(!response.isSuccessful()){
                            Log.e(this.getClass().getName().toString(),response.raw().toString());
                        }



                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.e(this.getClass().getName().toString(),t.getMessage());
                    }
                    getUser.enqueue(callback);

                //TODO: wait for enqueue and check resultlist (Empty List == This user does not exist)
                };*/



                // Create User object as parameter for retrofit2 callback
                User temp = new User(userPreferencesDataSource.getUserEmail(),userPreferencesDataSource.getUserName());

                    Call<User> userCreateCall = ServerCommunicationManager.getDbInterface().CreateUser(temp);
                    userCreateCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (!response.isSuccessful()) {
                                Log.e(this.getClass().getName().toString(), response.message());
                                //TODO: Error handling on failed user creation
                            }

                            Log.d(this.getClass().getName(), "User created successfully!");
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });



                if(UserManager.user == null)
                    UserManager.user = new User(userPreferencesDataSource.getUserEmail(),userPreferencesDataSource.getUserName());

                // Switch to next View
                Navigation.findNavController(root)
                          .navigate(R.id.action_launchFragment_to_homeScreenFragment);
            });


        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // if user is already existing => skip this fragment
        if(!userPreferencesDataSource.getUserName().isEmpty() && !userPreferencesDataSource.getUserEmail().isEmpty()) {

            if(UserManager.user == null)
                UserManager.user = new User(userPreferencesDataSource.getUserEmail(),userPreferencesDataSource.getUserName());

            Navigation.findNavController(view)
                      .navigate(R.id.action_launchFragment_to_homeScreenFragment);

        }




    }
}
