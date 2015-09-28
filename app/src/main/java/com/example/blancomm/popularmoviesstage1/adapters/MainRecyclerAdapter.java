package com.example.blancomm.popularmoviesstage1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.ui.DetailActivity;
import com.example.blancomm.popularmoviesstage1.utils.Constant;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private List<MoviesInfo> mItems;
    private Context mContext;

    public MainRecyclerAdapter(List<MoviesInfo> items, Context context) {
        mItems = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_grid_row_gral, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final MoviesInfo item = mItems.get(i);

        String thumbnail = item.getThumnail();
        String urlImage = Constant.URL_THUMNAIL_IMAGE + thumbnail;
        VolleyRequest.requestImage(urlImage, viewHolder.mThumbnail);

        Log.e("","Llega: " + item.getOriginalLanguage()+ "--" + item.getVoteAverage()+ "--" +
        item.getVoteCount()+ "--" + item.getReleaseDate());
        float rating = Float.parseFloat(item.getVoteAverage());
        String year = item.getReleaseDate().substring(0,4);

        viewHolder.mVotes.setText(item.getVoteCount() + " votes");
        viewHolder.mRatingBar.setRating(rating/2);
        viewHolder.mTitle.setText(item.getTitle()+ " (" + year + ")");

        int iconFlag =  0;

        if (item.getOriginalLanguage().equals("en")) {
            iconFlag = R.drawable.ic_flag_uk;
        }else {
            iconFlag = R.drawable.ic_flag_spain;
        }

        viewHolder.mIconLanguage.setImageResource(iconFlag);
        //viewHolder.mThumbnail.setImageBitmap(thumbnail);
        viewHolder.mThumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",item.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView mThumbnail;
        private TextView mTitle,mVotes;
        private ImageView mIconLanguage;
        private RatingBar mRatingBar;

        ViewHolder(View v) {
            super(v);
            mThumbnail = (NetworkImageView) v.findViewById(R.id.thumbnail_film);
            mTitle = (TextView)v.findViewById(R.id.title_card);
            mIconLanguage = (ImageView)v.findViewById(R.id.language_icon);
            mRatingBar =  (RatingBar)v.findViewById(R.id.ratingBar);
            mVotes = (TextView)v.findViewById(R.id.votes);
        }
    }

    public void updateResults(List<MoviesInfo> results, Context context) {
        this.mItems = results;
        this.mContext = context;
        //Triggers the list update
        notifyDataSetChanged();
    }

}

