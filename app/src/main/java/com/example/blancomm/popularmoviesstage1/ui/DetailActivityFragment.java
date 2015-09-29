package com.example.blancomm.popularmoviesstage1.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage1.BackButtonListeners;
import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.utils.Constant;
import com.example.blancomm.popularmoviesstage1.utils.JSONActions;
import com.example.blancomm.popularmoviesstage1.utils.UtilsView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements VolleyListeners {

    private String mIdMovie;
    private NetworkImageView mImageDetail;
    private TextView mTextDetail;
    private CollapsingToolbarLayout collapsingToolbar;
    private BackButtonListeners detailsInterface;

    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(String id) {
        DetailActivityFragment fragment = new DetailActivityFragment();
        Bundle args = new Bundle();
        args.putString(Constant.TAG_ID_MOVIE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (!(getActivity() instanceof BackButtonListeners)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            detailsInterface = (BackButtonListeners) getActivity();
        }

        mIdMovie = getArguments().getString(Constant.TAG_ID_MOVIE);
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

    @Override
    public void onStart() {
        super.onStart();

        detailsInterface.setSelectedFragment(this);
    }

    private void instantiateObjects(View view) {

        mImageDetail = (NetworkImageView) view.findViewById(R.id.image_detail);
        mTextDetail = (TextView) view.findViewById(R.id.text_detail);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        UtilsView.makeCollapsingToolbarLayoutTypeface(collapsingToolbar, getActivity());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Snackbar.make(findViewById(R.id.coordinator_detail), "Añadido a favoritos", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailActivity.this, "Pelicula añadida", Toast.LENGTH_LONG).show();
                    }
                }).show();*/
            }
        });
    }

    @Override
    public void onFinishJsonRequest(JSONObject jsonObject) {

        MovieDetailInfo movieDetail;

        try {
            movieDetail = JSONActions.parseDetail(jsonObject);
            injectData(movieDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void injectData(MovieDetailInfo movieDetailInfo) {

        String urlImage = Constant.URL_DETAIL_IMAGE + movieDetailInfo.getImageDetail();
        VolleyRequest.requestImage(urlImage, mImageDetail);
        mTextDetail.setText(movieDetailInfo.getDescription());
        collapsingToolbar.setTitle(movieDetailInfo.getTitle());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
       /* collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);*/
    }

    private void backDismiss() {

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_DETAIL_FRAGMENT);
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getActivity().getSupportFragmentManager().executePendingTransactions();
            getActivity().finish();
        }
    }

    public boolean onBackPressed() {

        backDismiss();
        return true;

    }
}
