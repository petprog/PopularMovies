package com.example.eziketobenna.popularmovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eziketobenna.popularmovies.Model.Movie;
import com.example.eziketobenna.popularmovies.NetworkUtils.ApiConstants;
import com.example.eziketobenna.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    final static String EXTRA_VALUE = "extraMovie";
    Movie movie;

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.release_date)
    TextView dateTv;
    @BindView(R.id.overview)
    TextView overviewTv;
    @BindView(R.id.main_backdrop)
    ImageView backdropIv;
    @BindView(R.id.poster)
    ImageView posterIv;
    @BindView(R.id.movie_rating)
    RatingBar ratingBar;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    String title, date, overview, backdrop, poster;
    double rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        if (intent.hasExtra(DetailActivity.EXTRA_VALUE)) {
            movie = getIntent().getParcelableExtra(DetailActivity.EXTRA_VALUE);
            populateUI(movie);
        }
    }

    void populateUI(Movie movie) {
        title = movie.getOriginalTitle();
        date = movie.getReleaseDate();
        overview = movie.getOverview();
        poster = movie.getMovieImagePath();
        backdrop = movie.getBackdropPath();
        rating = movie.getVoteAverage();

        //set movie detail on respective views
        titleTv.setText(title);
        dateTv.setText(date);
        overviewTv.setText(overview);
        //Load image
        String posterUrl = ApiConstants.MOVIES_DETAIL_BASE_URL;
        String backdropUrl = ApiConstants.MOVIES_BACKDROP_BASE_URL;
        loadImage(posterIv, poster, posterUrl);
        loadImage(backdropIv, backdrop, backdropUrl);
        /* set movie rating
         * divide the vote average by 2 to fit 5 star rating bar
         * since vote average is /10
         */
        float rated = (((float) rating) / 2);
        ratingBar.setRating(rated);
        setTitle("");
        Log.d(LOG_TAG, "vote average:" + rated);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_data_unavailable, Toast.LENGTH_SHORT).show();
    }

    private void loadImage(ImageView imageView, String imageUrl, String posterUrl) {
        Picasso.get()
                .load(posterUrl + imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}