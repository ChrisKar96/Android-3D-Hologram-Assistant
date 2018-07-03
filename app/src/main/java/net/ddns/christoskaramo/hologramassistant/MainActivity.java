package net.ddns.christoskaramo.hologramassistant;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    TextToSpeech tts;
    Button greet , dance , wait , yes , no , victory , defeat , think , run , yawn;

    void tts_speak(int str){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(getString(str), TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            tts.speak(getString(str), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setPitch((float) 1.3);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        tts.setVoice(new Voice("hq-fem-us", Locale.US, 500, 100, false, null));
                    else
                        tts.setLanguage(Locale.US);
                }
            }
        });

        final GifImageView front = findViewById(R.id.front);
        final GifImageView left = findViewById(R.id.left);
        final GifImageView right = findViewById(R.id.right);
        final GifImageView back = findViewById(R.id.back);

        greet = findViewById(R.id.greet);
        dance = findViewById(R.id.dance);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        victory = findViewById(R.id.victory);
        defeat = findViewById(R.id.defeat);
        run = findViewById(R.id.run);
        wait = findViewById(R.id.wait);
        think = findViewById(R.id.think);
        yawn = findViewById(R.id.yawn);

        greet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.wave_front);
                left.setImageResource(R.drawable.wave_left);
                right.setImageResource(R.drawable.wave_right);
                back.setImageResource(R.drawable.wave_back);
                tts_speak(R.string.hello);
            }
        });

        dance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.dance_front);
                left.setImageResource(R.drawable.dance_left);
                right.setImageResource(R.drawable.dance_right);
                back.setImageResource(R.drawable.dance_back);
                tts_speak(R.string.lets_dance);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.yes_front);
                left.setImageResource(R.drawable.yes_left);
                right.setImageResource(R.drawable.yes_right);
                back.setImageResource(R.drawable.yes_back);
                tts_speak(R.string.yes_str);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.no_front);
                left.setImageResource(R.drawable.no_left);
                right.setImageResource(R.drawable.no_right);
                back.setImageResource(R.drawable.no_back);
                tts.setSpeechRate((float) 0.5);
                tts_speak(R.string.no_no_no);
                tts.setSpeechRate((float) 1);
            }
        });

        victory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.victory_front);
                left.setImageResource(R.drawable.victory_left);
                right.setImageResource(R.drawable.victory_right);
                back.setImageResource(R.drawable.victory_back);
                tts_speak(R.string.hooray_victory);
            }
        });

        defeat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.defeated_front);
                left.setImageResource(R.drawable.defeated_left);
                right.setImageResource(R.drawable.defeated_right);
                back.setImageResource(R.drawable.defeated_back);
                tts.setSpeechRate((float) 0.7);
                tts_speak(R.string.oh_no_defeat);
                tts.setSpeechRate((float) 1);
            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.run_front);
                left.setImageResource(R.drawable.run_left);
                right.setImageResource(R.drawable.run_right);
                back.setImageResource(R.drawable.run_back);
                tts_speak(R.string.lets_run);
            }
        });

        wait.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.wait_front);
                left.setImageResource(R.drawable.wait_left);
                right.setImageResource(R.drawable.wait_right);
                back.setImageResource(R.drawable.wait_back);
                tts.setSpeechRate((float) 0.7);
                tts_speak(R.string.ill_wait);
                tts.setSpeechRate((float) 1);
            }
        });

        think.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.thinking_front);
                left.setImageResource(R.drawable.thinking_left);
                right.setImageResource(R.drawable.thinking_right);
                back.setImageResource(R.drawable.thinking_back);
                tts_speak(R.string.let_me_think);
            }
        });

        yawn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                front.setImageResource(R.drawable.yawn_front);
                left.setImageResource(R.drawable.yawn_left);
                right.setImageResource(R.drawable.yawn_right);
                back.setImageResource(R.drawable.yawn_back);
                tts.setSpeechRate((float) 0.5);
                tts_speak(R.string.im_tired);
                tts.setSpeechRate((float) 1);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
