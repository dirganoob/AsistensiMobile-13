package com.example.networking.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.networking.R;
import com.example.networking.data.network.ApiConfig;
import com.example.networking.data.network.ApiService;
import com.example.networking.data.network.NetworkUtil;
import com.example.networking.data.response.User;
import com.example.networking.data.response.UserResponse;
import com.example.networking.databinding.FragmentHomeBinding;
import com.example.networking.ui.UserAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserAdapter userAdapter;
    private int currentPage = 1;
    private boolean isLoading = false;

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        // 1. Buat layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        // 2. Atur agar item "Load More" ambil 2 kolom (full width)
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (userAdapter != null && userAdapter.getItemViewType(position) == 2) {
                    return 2; // full width
                } else {
                    return 1; // masing-masing ambil 1 kolom
                }
            }
        });

        // 3. Set layout manager ke RecyclerView
        binding.userRecyclerView.setLayoutManager(layoutManager);

        // 4. Set adapter
        userAdapter = new UserAdapter(new ArrayList<>());
        binding.userRecyclerView.setAdapter(userAdapter);

        // 5. Set listener klik user ke detail
        userAdapter.setOnUserClickListener(user -> {
            int characterId = user.getId();
            DetailFragment detailFragment = DetailFragment.newInstance(characterId);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // 6. Tombol refresh
        binding.refresh.setOnClickListener(v -> loadUsers(currentPage));

        // 7. Load awal
        loadUsers(currentPage);

        // 8. Load More listener
        userAdapter.setOnLoadMoreClickListener(() -> {
            if (!isLoading) {
                currentPage++;
                loadUsers(currentPage);
            }
        });

        return binding.getRoot();
    }

    private void loadUsers(int page) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showError(true, "Gagal memuat data");
            return;
        }

        showError(false, null);
        isLoading = true;
        binding.progressbar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiConfig.getClient().create(ApiService.class);
        apiService.getCharacterData(page).enqueue(new Callback<com.example.networking.data.response.UserResponse>(){
            @Override
            public void onResponse(Call<UserResponse> call, Response<com.example.networking.data.response.UserResponse> response) {
                isLoading = false;
                binding.progressbar.setVisibility(View.GONE);

                List<User> newUsers = null;
                if (response.isSuccessful() && response.body() != null) {
                    newUsers = response.body().getResults();
                    if (page == 1) userAdapter.clearUsers(); // optional: reset data
                    userAdapter.addMoreUsers(newUsers);
                    userAdapter.setShowLoadMore(!newUsers.isEmpty());
                }
                Log.d("API_DATA", "Jumlah karakter: " + newUsers.size());

            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;
                binding.progressbar.setVisibility(View.GONE);
                showError(true, "Gagal memuat data.\n" + t.getMessage());
            }

        });
    }

    private void showError(boolean show, String message) {
        binding.errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.userRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.message.setText(message != null ? message : "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}