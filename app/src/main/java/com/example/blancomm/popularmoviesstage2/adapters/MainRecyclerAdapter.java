package com.example.blancomm.popularmoviesstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage2.ui.DetailActivity;
import com.example.blancomm.popularmoviesstage2.ui.DetailFragment;
import com.example.blancomm.popularmoviesstage2.ui.MainActivity;
import com.example.blancomm.popularmoviesstage2.utils.Constant;
import com.example.blancomm.popularmoviesstage2.utils.UtilsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private List<MoviesInfo> mItems;
    private Context mContext;
    private String TAG = MainRecyclerAdapter.class.getSimpleName();
    private int mPositionTab;
    private int posSelected = 0;
    private PositionListener mListener;

    public MainRecyclerAdapter(List<MoviesInfo> items, Context context, int positionTab, PositionListener listener) {
        mItems = items;
        mContext = context;
        mPositionTab = positionTab;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_grid_row_gral, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final MoviesInfo item = mItems.get(i);

        String thumbnail = item.getThumnail();
        String year;

        String urlImage = Constant.URL_THUMNAIL_IMAGE + mContext.getString(R.string.width_image_thumb)+ thumbnail;

        if (!thumbnail.equals("null")) {

            Picasso.with(mContext).load(urlImage).placeholder(R.drawable.ic_image).into(viewHolder.mThumbnail);
        }

        float rating = Float.parseFloat(item.getVoteAverage())/2;
        boolean tabletSize = mContext.getResources().getBoolean(R.bool.isTablet);

        if (tabletSize && posSelected == i){
            viewHolder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorBackgroundCardViewHightest));
        } else {

            viewHolder.mCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        if (item.getReleaseDate().length() >= 4) {
             year = item.getReleaseDate().substring(0, 4);
        }else {
            year = "No date";
        }

        viewHolder.mVotes.setText(item.getVoteCount());
        viewHolder.mRatingBar.setRating(rating);
        viewHolder.mTitle.setText(item.getTitle()+ " (" + year + ")");

        viewHolder.mIconLanguage.setImageResource(UtilsView.setFlagLanguageDetail(item.getOriginalLanguage()));
        viewHolder.mCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                boolean tabletSize = mContext.getResources().getBoolean(R.bool.isTablet);
                if (tabletSize) {

                    mListener.onSelecteditem(i);

                    DetailFragment newDetailFragment = DetailFragment.newInstance(item.getId(),mPositionTab);

                    FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager()
                            .beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_placeholder, newDetailFragment);
                    fragmentTransaction.commit();

                } else {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, item.getId());
                    intent.putExtra(Constant.EXTRA_TAB, mPositionTab);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mThumbnail;
        private TextView mTitle,mVotes;
        private ImageView mIconLanguage;
        private RatingBar mRatingBar;
        private CardView mCard;

        ViewHolder(View v) {
            super(v);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail_film);
            mTitle = (TextView)v.findViewById(R.id.title_card);
            mIconLanguage = (ImageView)v.findViewById(R.id.language_icon);
            mRatingBar =  (RatingBar)v.findViewById(R.id.ratingBar);
            mVotes = (TextView)v.findViewById(R.id.votes);
            mCard = (CardView)v.findViewById(R.id.cardview);
        }
    }

    public void updateResults(List<MoviesInfo> results, Context context) {
        this.mItems = results;
        this.mContext = context;
        //Triggers the list update
        notifyDataSetChanged();
    }

    public void updateSelectItem(int position){
        this.posSelected = position;
        notifyDataSetChanged();
    }

}

