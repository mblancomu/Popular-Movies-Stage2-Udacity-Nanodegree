package com.example.blancomm.popularmoviesstage1.ui;

import android.annotation.TargetApi;
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
import com.example.blancomm.popularmoviesstage1.utils.UtilsView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements VolleyListeners {

    private String mIdMovie;
    private NetworkImageView mImageDetail, mThumbnail;
    private TextView mTextDetail,mVotes, mRates, mPopulrity, mDate;
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

        mIdMovie = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        String urlDetail = Constant.URL_DETAIL_MOVIE + mIdMovie + "?" + Constant.API_KEY;

        VolleyRequest.requestJson(this, urlDetail);

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
        mTextDetail = (TextView) view.findViewById(R.id.text_detail);
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

    private void injectData(MovieDetailInfo movieDetailInfo) {

        VolleyRequest.requestImage(Constant.URL_DETAIL_IMAGE + movieDetailInfo.getImageDetail(), mImageDetail);
        VolleyRequest.requestImage(Constant.URL_THUMNAIL_IMAGE + getActivity().getString(R.string.width_image_thumb_detail) + movieDetailInfo.getThumnail(), mThumbnail);
        mTextDetail.setText(movieDetailInfo.getDescription());
        mTitle = movieDetailInfo.getTitle();
        collapsingToolbar.setTitle(mTitle);

        mDate.setText(movieDetailInfo.getReleaseDate());
        mVotes.setText(movieDetailInfo.getVoteCount());
        mRates.setText(movieDetailInfo.getVoteAverage());
        mPopulrity.setText(movieDetailInfo.getPopularity());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        mFlag.setImageResource(UtilsView.setFlagLanguageDetail(movieDetailInfo.getOriginalLanguage()));

    }

}
