package com.example.bookingapp.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapp.R;
import com.example.bookingapp.data.database.ShareReferenceHelper;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.PlaceRepository;
import com.example.bookingapp.databinding.FragmentHomeBinding;
import com.example.bookingapp.view.adapter.PlaceAdapter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private User user;
    private ShareReferenceHelper shareReferenceHelper;
    private FragmentHomeBinding binding;

    private PlaceRepository placeRepository;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareReferenceHelper = new ShareReferenceHelper(requireContext());
        user = (User)shareReferenceHelper.get("user",User.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setUser(user);
        binding.address.setText(user.getAddress(getContext()));
        placeRepository = new PlaceRepository(getContext());

        // Set up RecyclerView
        binding.allPackage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.popularPackage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.topPackage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // set up adapter
        PlaceAdapter adapter1 = new PlaceAdapter(getContext(),placeRepository.getPlacesByRatingDesc(),PlaceAdapter.TYPE_THEME_1);
        PlaceAdapter adapter2 = new PlaceAdapter(getContext(),placeRepository.getAllPlaces(),PlaceAdapter.TYPE_THEME_2);
        PlaceAdapter adapter3 = new PlaceAdapter(getContext(),placeRepository.getMostPopularPlaces(10),PlaceAdapter.TYPE_THEME_3);
        binding.allPackage.setAdapter(adapter2);
        binding.popularPackage.setAdapter(adapter3);
        binding.topPackage.setAdapter(adapter1);



        return binding.getRoot();
    }
}