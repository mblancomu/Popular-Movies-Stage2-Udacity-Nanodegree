package com.example.blancomm.popularmoviesstage1.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.utils.AnimationsUtils;
import com.example.blancomm.popularmoviesstage1.utils.Constant;
import com.example.blancomm.popularmoviesstage1.utils.JSONActions;
import com.example.blancomm.popularmoviesstage1.utils.URLUtils;
import com.example.blancomm.popularmoviesstage1.utils.UtilsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements VolleyListeners {

    private String mIdMovie;
    private NetworkImageView mImageDetail, mThumbnail;
    private TextView mVotes, mRates, mPopulrity, mDate;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout mAppBar;
    private String TAG = DetailFragment.class.getSimpleName();
    private String mTitle;
    private CardView mCardHeaderCollapse;
    private NestedScrollView mScrollView;
    private ImageView mIconRate,mIconPopularity, mIconVotes, mIconDate, mFlag;
    private MovieDetailInfo movieDetail;
    private TableRow mRowAdults;
    private LinearLayout mLinearIcons;
    private CardView mCardGenres;
    private LinearLayout mIconsGenres, mMainView;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String id) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.TAG_ID_MOVIE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        mIdMovie = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);

        try {

            VolleyRequest.requestJson(this, URLUtils.getURLMovieDtail(mIdMovie));

            Log.e(TAG,"Videos: " + URLUtils.getURLMovieVideos(mIdMovie));

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        instantiateObjects(rootView);


        return rootView;
    }

    private void instantiateObjects(View view) {

        mImageDetail = (NetworkImageView) view.findViewById(R.id.image_detail);
        mThumbnail = (NetworkImageView)view.findViewById(R.id.thumbnail_film2);
        mAppBar = (AppBarLayout) view.findViewById(R.id.view);
        mDate = (TextView)view.findViewById(R.id.imdb_id);
        mPopulrity = (TextView)view.findViewById(R.id.popularity);
        mRates = (TextView)view.findViewById(R.id.hightest_rate);
        mVotes = (TextView)view.findViewById(R.id.vote_count);
        mScrollView = (NestedScrollView)view.findViewById(R.id.nested);
        mIconDate = (ImageView)view.findViewById(R.id.iv_date);
        mIconVotes= (ImageView)view.findViewById(R.id.iv_votes);
        mIconPopularity = (ImageView)view.findViewById(R.id.iv_popular);
        mIconRate = (ImageView)view.findViewById(R.id.iv_rate);
        mCardHeaderCollapse = (CardView)view.findViewById(R.id.card_header_collapse);
        mRowAdults = (TableRow)view.findViewById(R.id.tr_adults);
        mLinearIcons = (LinearLayout)view.findViewById(R.id.ll_icons_header);
        mFlag = (ImageView)view.findViewById(R.id.iv_flag);
        mCardGenres = (CardView)view.findViewById(R.id.card_genres);
        mIconsGenres = (LinearLayout)view.findViewById(R.id.ll_genres);
        mMainView = (LinearLayout)view.findViewById(R.id.ll_main);


        AnimationsUtils.fadeInAlphaIcons(getActivity(), mIconVotes, R.anim.tween_votes);
        AnimationsUtils.fadeInAlphaIcons(getActivity(),mIconPopularity, R.anim.tween_popularity);
        AnimationsUtils.fadeInAlphaIcons(getActivity(),mIconRate, R.anim.tween_rate);
        AnimationsUtils.fadeInAlphaIcons(getActivity(), mIconDate, R.anim.tween_imdb);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        UtilsView.makeCollapsingToolbarLayoutTypeface(collapsingToolbar, getActivity());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                Log.e(TAG, "Valor de i: " + i);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i >= -418 && i < 0) {

                    mScrollView.animate().translationY(60).setInterpolator(new DecelerateInterpolator(2));
                    mCardHeaderCollapse.setCardBackgroundColor(getResources().getColor(R.color.white));
                    layoutParams.setMargins(0, 0, 0, 0);
                    mLinearIcons.setLayoutParams(layoutParams);
                    mRowAdults.setVisibility(View.VISIBLE);

                } else if (i == 0) {

                    mScrollView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    mCardHeaderCollapse.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutParams.setMargins(0,55,0,0);
                    mLinearIcons.setLayoutParams(layoutParams);
                    mRowAdults.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void onFinishJsonRequest(JSONObject jsonObject) {

        try {
            movieDetail = JSONActions.parseDetail(jsonObject);
            injectData(movieDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void injectData(MovieDetailInfo movieDetailInfo) throws JSONException {

        VolleyRequest.requestImage(Constant.URL_DETAIL_IMAGE + movieDetailInfo.getImageDetail(), mImageDetail);
        VolleyRequest.requestImage(Constant.URL_THUMNAIL_IMAGE + getActivity().getString(R.string.width_image_thumb_detail) + movieDetailInfo.getThumnail(), mThumbnail);
        mTitle = movieDetailInfo.getTitle();
        collapsingToolbar.setTitle(mTitle);

        mDate.setText(movieDetailInfo.getReleaseDate());
        mVotes.setText(movieDetailInfo.getVoteCount());
        mRates.setText(movieDetailInfo.getVoteAverage());
        mPopulrity.setText(movieDetailInfo.getPopularity());
        putIconsGenres(JSONActions.getGenres(movieDetailInfo.getGenreIds()));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setSynopsisCard(inflater);
        setRuntimeCard(inflater);
        setCountryCard(inflater);
        setHomepageCard(inflater);
        setImdbCard(inflater);
        setVideosCard(inflater);

        mFlag.setImageResource(UtilsView.setFlagLanguageDetail(movieDetailInfo.getOriginalLanguage()));

    }

    private void putIconsGenres(List<String> genres){

        int genresSize = genres.size();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (genresSize > 0){
            mCardGenres.setVisibility(View.VISIBLE);
        }else {
            mCardGenres.setVisibility(View.GONE);
        }

        for (int i = 0; i < genresSize; i++){

            View view = inflater.inflate(R.layout.item_card_genres, mIconsGenres, false);

            ImageView imageView = (ImageView)view.findViewById(R.id.iv_genre);
            TextView textView = (TextView)view.findViewById(R.id.tv_genre);
            textView.setText(genres.get(i).toString().replace(" ", " \n"));
            imageView.setImageResource(UtilsView.setIconGenre(genres.get(i).toString()));
            lp.setMargins(15, 15, 15, 15);
            view.setLayoutParams(lp);
            mIconsGenres.addView(view);

        }

    }

    private void setSynopsisCard(LayoutInflater inflater){

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.synopsis));
        ((TextView)view.findViewById(R.id.tv_description)).setText(movieDetail.getDescription());
        mMainView.addView(view);
    }

    private void setRuntimeCard(LayoutInflater inflater){

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.runtime));
        ((TextView)view.findViewById(R.id.tv_description)).setText(movieDetail.getRuntime() + "  min");
        mMainView.addView(view);
    }

    private void setCountryCard(LayoutInflater inflater) throws JSONException {

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);
        int countriesSize = JSONActions.getCountries(movieDetail.getProduction_countries()).size();
        StringBuilder countries = new StringBuilder(countriesSize);

        for (int i = 0; i < countriesSize;i++){

            if (countriesSize > 1) {
                countries.append(", " + JSONActions.getCountries(movieDetail.getProduction_countries()).get(i).toString());
            }

             countries.append(JSONActions.getCountries(movieDetail.getProduction_countries()).get(i).toString());

        }

        ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.country));
        ((TextView)view.findViewById(R.id.tv_description)).setText(countries.toString());
        mMainView.addView(view);

    }

    private void setVideosCard(LayoutInflater inflater){

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.videos));
        ((TextView)view.findViewById(R.id.tv_description)).setText(movieDetail.getProduction_countries());
        mMainView.addView(view);

    }

    private void setImdbCard(LayoutInflater inflater){

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        String urlImdb = "http://www.imdb.com/title/" + movieDetail.getImdb_id() + "/?ref_=inth_ov_tt";
                ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.imdb));
        ((TextView)view.findViewById(R.id.tv_description)).setText(urlImdb);
        mMainView.addView(view);

    }

    private void setHomepageCard(LayoutInflater inflater){

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.homepage));
        ((TextView)view.findViewById(R.id.tv_description)).setText(movieDetail.getHomepage());
        mMainView.addView(view);

    }

}
