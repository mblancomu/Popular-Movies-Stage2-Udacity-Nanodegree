package com.example.blancomm.popularmoviesstage2.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.VolleyListeners;
import com.example.blancomm.popularmoviesstage2.app.AppController;
import com.example.blancomm.popularmoviesstage2.db.FavoritesDB;
import com.example.blancomm.popularmoviesstage2.db.SqlHandler;
import com.example.blancomm.popularmoviesstage2.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage2.model.ReviewsInfo;
import com.example.blancomm.popularmoviesstage2.model.VideosInfo;
import com.example.blancomm.popularmoviesstage2.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage2.utils.AnimationsUtilities;
import com.example.blancomm.popularmoviesstage2.utils.Constant;
import com.example.blancomm.popularmoviesstage2.utils.JSONActions;
import com.example.blancomm.popularmoviesstage2.utils.URLUtils;
import com.example.blancomm.popularmoviesstage2.utils.UtilsView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements VolleyListeners {

    private String mIdMovie;
    private ImageView mImageDetail, mThumbnail;
    private TextView mVotes, mRates, mPopulrity, mDate;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout mAppBar;
    private String TAG = DetailFragment.class.getSimpleName();
    private String mTitle;
    private CardView mCardHeaderCollapse;
    private NestedScrollView mScrollView;
    private ImageView mIconRate, mIconPopularity, mIconVotes, mIconDate, mFlag;
    private MovieDetailInfo movieDetail;
    private TableRow mRowAdults;
    private LinearLayout mLinearIcons;
    private CardView mCardGenres;
    private LinearLayout mIconsGenres, mMainView;
    private List<VideosInfo> videos;
    private List<ReviewsInfo> reviews;
    private LayoutInflater inflater;
    private int configDevice;
    private boolean pulsado = false;
    private int mOriginTab;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String id, int mOriginTab) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.ID_MOVIE, id);
        args.putInt(Constant.ID_TAB, mOriginTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        if (savedInstanceState != null) {

            mIdMovie = savedInstanceState.getString(Constant.ID_MOVIE);
            configDevice = savedInstanceState.getInt(Constant.CONFIG_DEVICE);
            mOriginTab = savedInstanceState.getInt(Constant.ID_TAB);

        } else {

            mIdMovie = getArguments().getString(Constant.ID_MOVIE);
            configDevice = getResources().getConfiguration().orientation;
            mOriginTab = getArguments().getInt(Constant.ID_TAB);
        }

        beginRequest(mIdMovie);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int currentOrientation = getResources().getConfiguration().orientation;

        if (configDevice != currentOrientation) {

            beginRequest(mIdMovie);

            configDevice = currentOrientation;

        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_detail);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.button_onoff_indicator_off);


        instantiateObjects(rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constant.ID_MOVIE, mIdMovie);
        outState.putInt(Constant.CONFIG_DEVICE, configDevice);
    }

    /**
     * TODO: This should change in the future, i have very much objects here. Instantiate all objects in view.
     *
     * @param view
     */
    private void instantiateObjects(View view) {

        mImageDetail = (ImageView) view.findViewById(R.id.image_detail);
        mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_film2);
        mAppBar = (AppBarLayout) view.findViewById(R.id.view);
        mDate = (TextView) view.findViewById(R.id.imdb_id);
        mPopulrity = (TextView) view.findViewById(R.id.popularity);
        mRates = (TextView) view.findViewById(R.id.hightest_rate);
        mVotes = (TextView) view.findViewById(R.id.vote_count);
        mScrollView = (NestedScrollView) view.findViewById(R.id.nested);
        mIconDate = (ImageView) view.findViewById(R.id.iv_date);
        mIconVotes = (ImageView) view.findViewById(R.id.iv_votes);
        mIconPopularity = (ImageView) view.findViewById(R.id.iv_popular);
        mIconRate = (ImageView) view.findViewById(R.id.iv_rate);
        mCardHeaderCollapse = (CardView) view.findViewById(R.id.card_header_collapse);
        mRowAdults = (TableRow) view.findViewById(R.id.tr_adults);
        mLinearIcons = (LinearLayout) view.findViewById(R.id.ll_icons_header);
        mFlag = (ImageView) view.findViewById(R.id.iv_flag);
        mCardGenres = (CardView) view.findViewById(R.id.card_genres);
        mIconsGenres = (LinearLayout) view.findViewById(R.id.ll_genres);
        mMainView = (LinearLayout) view.findViewById(R.id.ll_main);

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view.findViewById(R.id.iv_website).setOnClickListener(mLinksClickListener);
        view.findViewById(R.id.iv_imdb).setOnClickListener(mLinksClickListener);

        AnimationsUtilities.fadeInAlphaIcons(getActivity(), mIconVotes, R.anim.tween_votes);
        AnimationsUtilities.fadeInAlphaIcons(getActivity(), mIconPopularity, R.anim.tween_popularity);
        AnimationsUtilities.fadeInAlphaIcons(getActivity(), mIconRate, R.anim.tween_rate);
        AnimationsUtilities.fadeInAlphaIcons(getActivity(), mIconDate, R.anim.tween_imdb);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        UtilsView.makeCollapsingToolbarLayoutTypeface(collapsingToolbar, getActivity());

        /**
         * catch the listeners from AppBarLayout. When is collapse and expanded, do differents actions.
         */
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i >= -418 && i < 0) {

                    //Collapse
                    mScrollView.animate().translationY(getResources().getInteger(R.integer.translation_card_header)).setInterpolator(new DecelerateInterpolator(2));
                    mCardHeaderCollapse.setCardBackgroundColor(getResources().getColor(R.color.white));
                    layoutParams.setMargins(0, 0, 0, 0);
                    mLinearIcons.setLayoutParams(layoutParams);
                    mRowAdults.setVisibility(movieDetail.getAdult().equals("true") ? View.VISIBLE : View.GONE);

                } else if (i == 0) {

                    //Expanded
                    mScrollView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    mCardHeaderCollapse.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutParams.setMargins(0, getResources().getInteger(R.integer.margin_top_card_header), 0, 0);
                    mLinearIcons.setLayoutParams(layoutParams);
                    mRowAdults.setVisibility(View.GONE);

                }
            }
        });
    }

    /**
     * Get the data from json movie request on volleyrequest class.
     *
     * @param jsonObject
     */
    @Override
    public void onFinishJsonMoviesRequest(JSONObject jsonObject) {

        try {
            movieDetail = JSONActions.parseDetail(jsonObject);
            injectData(movieDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get data from json videos for a movie id, request on volleyrequest class.
     *
     * @param jsonObject
     */
    @Override
    public void onFinishJsonVideosRequest(JSONObject jsonObject) {

        try {
            videos = JSONActions.getVideos(jsonObject);
            setVideosCard(inflater);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get data from json reviews for a movie id, request on volleyrequest class.
     *
     * @param jsonObject
     */
    @Override
    public void onFinishJsonReviewsRequest(JSONObject jsonObject) {

        try {
            reviews = JSONActions.getReviews(jsonObject);
            setReviewsCard(inflater);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inject data in the instantiate objects in the view.
     *
     * @param movieDetailInfo
     * @throws JSONException
     */
    private void injectData(MovieDetailInfo movieDetailInfo) throws JSONException {

        Picasso.with(getActivity()).load(Constant.URL_DETAIL_IMAGE + movieDetailInfo.getImageDetail())
                .placeholder(R.drawable.ic_image).into(mImageDetail);

        Picasso.with(getActivity()).load(Constant.URL_THUMNAIL_IMAGE + "w185/" + movieDetailInfo.getThumnail())
                .placeholder(R.drawable.ic_image).into(mThumbnail);

        mTitle = movieDetailInfo.getTitle();
        collapsingToolbar.setTitle(mTitle);

        mDate.setText((movieDetailInfo.getReleaseDate().split("[ \\-]"))[0]);
        mVotes.setText(movieDetailInfo.getVoteCount());
        mRates.setText(movieDetailInfo.getVoteAverage());
        mPopulrity.setText(movieDetailInfo.getPopularity());
        putIconsGenres(JSONActions.getGenres(movieDetailInfo.getGenreIds()));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //Subviews with differents cards.
        setSynopsisCard(inflater);
        setRuntimeCard(inflater);
        setCountryCard(inflater);

        mFlag.setImageResource(UtilsView.setFlagLanguageDetail(movieDetailInfo.getOriginalLanguage()));
        pulsado = false;

        final FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        final boolean existe = SqlHandler.checkidExitsorNot(FavoritesDB.TABLE_FAVORITES, FavoritesDB.COLUMN_IDMOVIE, movieDetail.getId());
        fab.setImageResource(existe ? R.drawable.ic_favorite_select : R.drawable.ic_favorite_normal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pulsado = !pulsado ? false : true;

                //fab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.expand_button));

                SqlHandler.saveFavoriteObject(SqlHandler.saveFavorite(movieDetail), AppController.getmSqlHandler(), fab);

            }
        });


    }

    /**
     * Put icons for genres ids. This insert dinamically a view with imageview and textview.
     *
     * @param genres
     */
    private void putIconsGenres(List<String> genres) {

        int genresSize = genres.size();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mCardGenres.setVisibility(genresSize > 0 ? View.VISIBLE : View.GONE);

        for (int i = 0; i < genresSize; i++) {

            View view = inflater.inflate(R.layout.item_card_genres, mIconsGenres, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.iv_genre);
            TextView textView = (TextView) view.findViewById(R.id.tv_genre);
            textView.setText(genres.get(i).toString().replace(" ", " \n"));
            imageView.setImageResource(UtilsView.setIconGenre(genres.get(i).toString()));
            lp.setMargins(15, 15, 15, 15);
            view.setLayoutParams(lp);
            mIconsGenres.addView(view);

        }

    }

    private void putReviews(List<ReviewsInfo> reviews, LinearLayout viewReview) {

        int genresSize = reviews.size();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < genresSize; i++) {

            View view = inflater.inflate(R.layout.item_review, mIconsGenres, false);

            ((TextView) view.findViewById(R.id.tv_author)).setText(reviews.get(i).getAuthor());
            ((TextView) view.findViewById(R.id.tv_comment)).setText(reviews.get(i).getContent());
            viewReview.addView(view);

        }

    }

    private void putVideos(final List<VideosInfo> videos, LinearLayout viewReview) {

        int videosSize = videos.size();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < videosSize; i++) {

            View view = inflater.inflate(R.layout.item_videos, mIconsGenres, false);
            final String key = videos.get(i).getKey();
            String hd = videos.get(i).getSize();

            ((TextView) view.findViewById(R.id.tv_trailer)).setText(videos.get(i).getName());
            ((ImageView) view.findViewById(R.id.iv_video)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        launchLink(URLUtils.getURLTrailerYouTube(key));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
            ((ImageView) view.findViewById(R.id.iv_hd)).setVisibility(hd.equals("720") ? View.VISIBLE : View.GONE);
            viewReview.addView(view);

        }

    }

    /**
     * Card view for the synopsis of the moview.
     *
     * @param inflater
     */
    private void setSynopsisCard(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.synopsis));
        ((TextView) view.findViewById(R.id.tv_description)).setText(movieDetail.getDescription());
        mMainView.addView(view);
    }

    /**
     * Card view for the runtime of movie.
     *
     * @param inflater
     */
    private void setRuntimeCard(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.runtime));
        ((TextView) view.findViewById(R.id.tv_description)).setText(movieDetail.getRuntime() + "  min");
        mMainView.addView(view);
    }

    /**
     * Card view for the countries production. Get an array with all countries and insert the text.
     *
     * @param inflater
     * @throws JSONException
     */
    private void setCountryCard(LayoutInflater inflater) throws JSONException {

        View view = inflater.inflate(R.layout.card_detail_text_info, mMainView, false);
        int countriesSize = JSONActions.getCountries(movieDetail.getProduction_countries()).size();
        StringBuilder countries = new StringBuilder(countriesSize);

        for (int i = 0; i < countriesSize; i++) {

            countries.append(i == countriesSize - 1 ? "-  " + JSONActions.getCountries(movieDetail.getProduction_countries()).get(i).toString() :
                    "-  " + JSONActions.getCountries(movieDetail.getProduction_countries()).get(i).toString() + " \n");

        }

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.country));
        ((TextView) view.findViewById(R.id.tv_description)).setText(countries.toString());
        mMainView.addView(view);

    }

    /**
     * TODO: Sure, i put here the trailers, now non implementing. Card view for the videos of this movie. This contain the trailers.
     *
     * @param inflater
     */
    private void setVideosCard(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.card_detail_trailers, mMainView, false);

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.videos));
        putVideos(videos, (LinearLayout) view.findViewById(R.id.ll_trailers));
        mMainView.addView(view);

    }

    private void setReviewsCard(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.card_detail_reviews, mMainView, false);

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.reviews));
        int sizeReview = reviews.size();
        view.findViewById(R.id.tv_empty).setVisibility(sizeReview > 0 ? View.GONE : View.VISIBLE);
        putReviews(reviews, ((LinearLayout) view.findViewById(R.id.ll_reviews)));
        mMainView.addView(view);

    }

    /**
     * Common click listeners for the links buttons.
     */
    private View.OnClickListener mLinksClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            showLink(v);
        }
    };

    /**
     * Show a link in function the selected view.
     *
     * @param v
     */
    public void showLink(View v) {
        switch (v.getId()) {
            case R.id.iv_website:
                launchLink(movieDetail.getHomepage());
                break;
            case R.id.iv_imdb:
                launchLink(Constant.URL_IMDB + movieDetail.getImdb_id());
                break;
            default:
                break;
        }
    }

    /**
     * Intent for launch a url on navigatore.
     *
     * @param url
     */
    private void launchLink(String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);

    }

    private void beginRequest(String id) {

        try {

            VolleyRequest.requestJsonMovies(this, URLUtils.getURLMovieDetail(id));
            VolleyRequest.requestJsonVideos(this, URLUtils.getURLMovieVideos(id));
            VolleyRequest.requestJsonReviews(this, URLUtils.getURLMovieReviews(id));

            Log.e(TAG, URLUtils.getURLMovieReviews(id));
            Log.e(TAG, URLUtils.getURLMovieVideos(id));

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, movieDetail.getTitle());
        share.putExtra(Intent.EXTRA_TEXT, movieDetail.getHomepage());

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_share:
                shareTextUrl();
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
