package skripsigame.skripsi.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.RatingBar;
import android.widget.TextView;

import skripsigame.skripsi.R;

public class AddReviewActivity extends AppCompatActivity {
    float ratingAkhir;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        initrating();
    }

    private void initrating(){
        ratingBar = (AppCompatRatingBar)findViewById(R.id.addreview_rating);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                String ratedValue = String.valueOf(rating*2);
                float nilaiRating = rating*2;
                // Get Number of stars
                int numStars = 10;
                ratingAkhir = nilaiRating;
                TextView text = (TextView)findViewById(R.id.myrating);
                text.setText(ratingAkhir + "/" + numStars + " Bintang");

            }
        });
    }
}
