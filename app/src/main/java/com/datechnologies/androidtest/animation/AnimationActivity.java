package com.datechnologies.androidtest.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

import java.io.IOException;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 * */

public class AnimationActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    Button buttonFader;
    ImageView logo;
    //Variables for Logo Drag
    float xDown=0, yDown=0;

    //songPlayer
    MediaPlayer song;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        //Music
        song = MediaPlayer.create(AnimationActivity.this,R.raw.retail_life);
        song.setLooping(true);
        song.start();

        // Button fade and logo
        buttonFader= findViewById(R.id.btn_fader);
        logo = findViewById(R.id.DnATechnologies_image);


        //Background Animation
        RelativeLayout relativeLayout = findViewById(R.id.animationLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        //--------------------------------------------------


        buttonFader.setOnClickListener(view -> {
            Animation fadeOut=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
            logo.startAnimation(fadeOut);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation fadeIn=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    logo.startAnimation(fadeIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

            });

        });

        //Move Logo following touch
        logo.setOnTouchListener((view, event) -> {

            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getX();
                    yDown = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    view.performClick();
                    float moveX, moveY;
                    moveX= event.getX();
                    moveY = event.getY();

                    float distanceX=moveX-xDown;
                    float distanceY=moveY-yDown;

                    logo.setX(logo.getX()+distanceX);
                    logo.setY(logo.getY()+distanceY);
                    break;
            }
            return true;
        });

    }
    //Navigate back to Home from menu button
    public boolean onOptionsItemSelected(MenuItem item){
        song.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        song.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
