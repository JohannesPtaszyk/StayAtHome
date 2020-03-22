package org.wirvsvirushackathon.stayathome.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.data.User;
import org.wirvsvirushackathon.stayathome.model.RestDBInterface;
import org.wirvsvirushackathon.stayathome.model.ServerCommunicationManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {



    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Fetches the actual User List from the RestAPI
     * TODO: Sort on the server to fetch the Highscores.
     */
    public void getHighscoreListe() {

        RestDBInterface dbInterface = ServerCommunicationManager.getRetrofitInstance().create(RestDBInterface.class);
        Call<List<User>> userGetCall =  dbInterface.getUsers();

        userGetCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(!response.isSuccessful()) {
                    //TODO: Error Handling
                    return;
                }

                List<User> users = response.body();

                for(User _user : users){
                    System.out.println((_user.email));
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //TODO: Error Handling
                System.out.println("RETROFIT_FAIL");
                System.out.println(t.getStackTrace());
            }
        });

    }

    /**
     * Fetching the actual Userhighscores by opening the app.
     */
    @Override
    public void onStart() {

        getHighscoreListe();

        super.onStart();
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
