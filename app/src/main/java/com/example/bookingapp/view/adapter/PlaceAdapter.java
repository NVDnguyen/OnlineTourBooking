package com.example.bookingapp.view.adapter;

import static com.example.bookingapp.utils.Tool.convertToAddress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.data.model.Place;
import com.example.bookingapp.databinding.ItemPlace1Binding;
import com.example.bookingapp.databinding.ItemPlace2Binding;
import com.example.bookingapp.databinding.ItemPlace3Binding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_THEME_1 = 0;
    public static final int TYPE_THEME_2 = 1;
    public static final int TYPE_THEME_3 = 2;

    private final List<Place> placeList;
    private final Context context;
    private final int themeType; // Selected theme type

    public PlaceAdapter(Context context, List<Place> placeList, int themeType) {
        this.context = context;
        this.placeList = placeList;
        this.themeType = themeType; // Set the theme
    }

    @Override
    public int getItemViewType(int position) {
        // Use the same theme for all items
        return themeType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case TYPE_THEME_1:
                ItemPlace1Binding binding = ItemPlace1Binding.inflate(inflater, parent, false);
                return new Theme1ViewHolder(binding,context);
            case TYPE_THEME_2:
                ItemPlace2Binding itemPlace2Binding = ItemPlace2Binding.inflate(inflater, parent, false);
                return new Theme2ViewHolder(itemPlace2Binding,context);
            case TYPE_THEME_3:
                ItemPlace3Binding itemPlace3Binding = ItemPlace3Binding.inflate(inflater, parent, false);
                return new Theme3ViewHolder(itemPlace3Binding,context);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Place place = placeList.get(position);

        switch (themeType) {
            case TYPE_THEME_1:
                ((Theme1ViewHolder) holder).bind(place);
                break;
            case TYPE_THEME_2:
                ((Theme2ViewHolder) holder).bind(place);
                break;
            case TYPE_THEME_3:
                ((Theme3ViewHolder) holder).bind(place);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    // Theme 1 ViewHolder
    static class Theme1ViewHolder extends RecyclerView.ViewHolder {
        private ItemPlace1Binding binding;
        private Context context;
        public Theme1ViewHolder(@NonNull ItemPlace1Binding binding,Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        @SuppressLint("DefaultLocale")
        public void bind(Place place) {
            // Bind data for Theme 1
            binding.placeName.setText(place.getName());
            binding.placeCountry.setText(convertToAddress(context,place.getLocation()));
            binding.placeRating.setText(String.format("%.1f",place.getRating()));


            Picasso.get()
                    .load(Uri.parse(place.getImage().get(0)))
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.error_img)
                    .into(binding.placeImage);
        }
    }

    // Theme 2 ViewHolder
    static class Theme2ViewHolder extends RecyclerView.ViewHolder {
        private ItemPlace2Binding binding;
        private Context context;
        public Theme2ViewHolder(@NonNull ItemPlace2Binding binding,Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        @SuppressLint("DefaultLocale")
        public void bind(Place place) {
            // Bind data for Theme 2
            binding.placeName.setText(place.getName());
            binding.placeRating.setText(String.format("%.1f",place.getRating()));
            binding.ratingBar.setRating(place.getRating());

            binding.bookmarkButton.setOnClickListener(v->{
            });

            Picasso.get()
                    .load(Uri.parse(place.getImage().get(0)))
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.error_img)
                    .into(binding.placeImage);
        }
    }



    // Theme 3 ViewHolder
    static class Theme3ViewHolder extends RecyclerView.ViewHolder {
        private ItemPlace3Binding binding;
        private Context context;
        public Theme3ViewHolder(@NonNull ItemPlace3Binding binding,Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        @SuppressLint("DefaultLocale")
        public void bind(Place place) {
            // Bind data for Theme 3
            binding.placeName.setText(place.getName());
            binding.placeRating.setText(String.format("%.1f",place.getRating()));
            binding.ratingBar.setRating(place.getRating());
            binding.placeDetails.setText(place.getOverview());

            Picasso.get()
                    .load(Uri.parse(place.getImage().get(0)))
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.error_img)
                    .into(binding.placeImage);
        }
    }
}
