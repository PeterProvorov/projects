package edu.amd.spbstu.savefrog;

import android.content.res.Resources;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static final int LAYOUT_INTRO = 0;
    public static final int LAYOUT_ABOUT = 1;
    public static final int LAYOUT_MENU = 2;
    public static final int LAYOUT_LEVEL = 3;
    public static final int LAYOUT_RESULTS = 4;
    public static final int LAYOUT_HELP = 5;
    public static final int LAYOUT_GAME_OVER = 6;
    public static final int LAYOUT_GAME = 7;
    public static final int LAYOUT_GAME_WIN = 8;
    private int curLayout = LAYOUT_INTRO;
    int level = 0;

    DrawGame game;
    private AppIntro appIntro;
    private ViewIntro viewIntro;
    private int scrWidth;
    private int scrHeight;
    private int language;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // No Status bar
        final Window win = getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Application is never sleeps
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        scrWidth = point.x;
        scrHeight = point.y;

        Log.d("VT", "Screen size is " + String.valueOf(scrWidth) + " * " + String.valueOf(scrHeight));

        // Detect language
        String strLang = Locale.getDefault().getDisplayLanguage();
        if (strLang.equalsIgnoreCase("english")) {
            Log.d("VT", "LOCALE: English");
            language = AppIntro.LANGUAGE_ENG;
        } else if (strLang.equalsIgnoreCase("русский")) {
            Log.d("VT", "LOCALE: Russian");
            language = AppIntro.LANGUAGE_RUS;
        } else {
            Log.d("VT", "LOCALE unknown: " + strLang);
            language = AppIntro.LANGUAGE_UNKNOWN;
        }
        appIntro = new AppIntro(this, language);
        viewIntro = new ViewIntro(this);

        curLayout = LAYOUT_INTRO;
        setContentView(viewIntro);
        super.onCreate(savedInstanceState);

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    public AppIntro getAppIntro() {
        return appIntro;
    }

    public void setCurLayout(int newLayout) {
        curLayout = newLayout;
        Resources res = getResources();
        switch (curLayout) {
            case LAYOUT_MENU:
                setContentView(R.layout.menu_layout);

                String str_play = String.format(res.getString(R.string.str_play), 1);
                TextView playView = (TextView) this.findViewById(R.id.playButton);
                playView.setText(str_play);
                break;
            case LAYOUT_LEVEL:
                setContentView(R.layout.levels_layout);

                String str_level = String.format(res.getString(R.string.str_level), 1);
                TextView levelView = (TextView) this.findViewById(R.id.level1);
                levelView.setText(str_level);

                str_level = String.format(res.getString(R.string.str_level), 2);
                levelView = (TextView) this.findViewById(R.id.level2);
                levelView.setText(str_level);

                str_level = String.format(res.getString(R.string.str_level), 3);
                levelView = (TextView) this.findViewById(R.id.level3);
                levelView.setText(str_level);

                str_level = String.format(res.getString(R.string.str_level), 4);
                levelView = (TextView) this.findViewById(R.id.level4);
                levelView.setText(str_level);
                break;
            case LAYOUT_GAME_OVER:
                setContentView(R.layout.game_lose);
                break;
            case LAYOUT_GAME_WIN:
                setContentView(R.layout.game_win);
                break;
            case LAYOUT_GAME:
                setContentView(R.layout.game_layout);
                ViewGroup layout = (ViewGroup) findViewById(R.id.frameLayout);
                game = new DrawGame(this, level);
                game.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.addView(game);

                //View timeView = findViewById(R.id.timeView);

//                .LayoutParams lp = new RelativeLayout.LayoutParams(game.getWidth(), game.getHeight());
//                lp.addRule(RelativeLayout.ABOVE, game.getId());
//                timeView.setLayoutParams(lp);

                //mp = MediaPlayer.create(this, R.raw.pinaKolada);
                //mp.start();
                break;
        }
        layoutAnimation();
    }

    public int getCurLayout() {
        return curLayout;
    }

    public void layoutAnimation() {
        int curLayout = getCurLayout();
        switch (curLayout) {
            case LAYOUT_MENU:
                menuAnimation();
                break;
        }
    }

    public void menuAnimation() {
        ImageView leaf1 = (ImageView) findViewById(R.id.leaf1);
        ImageView leaf7 = (ImageView) findViewById(R.id.leaf7);
        ImageView leaf9 = (ImageView) findViewById(R.id.leaf9);
        Animation rotationRight = AnimationUtils.loadAnimation(this, R.anim.rotationright);
        Animation rotationLeft = AnimationUtils.loadAnimation(this, R.anim.rotationleft);
        leaf1.startAnimation(rotationLeft);
        leaf7.startAnimation(rotationLeft);
        leaf9.startAnimation(rotationRight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent evt) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (curLayout) {
                case LAYOUT_ABOUT:
                    setCurLayout(LAYOUT_MENU);
                    break;
                case LAYOUT_HELP:
                    setCurLayout(LAYOUT_MENU);
                    break;
                case LAYOUT_LEVEL:
                    setCurLayout(LAYOUT_MENU);
                    break;
                case LAYOUT_GAME:
                    game.exitGame();
                    setCurLayout(LAYOUT_LEVEL);
                    break;
                case LAYOUT_GAME_OVER:
                    setCurLayout(LAYOUT_LEVEL);
                    break;
                case LAYOUT_GAME_WIN:
                    setCurLayout(LAYOUT_LEVEL);
                    break;
                case LAYOUT_INTRO:
                    //Do nothing
                    break;
                case LAYOUT_MENU:
                    finish();
                    //Do nothing
                    break;
            }
            Log.d("VT", "Back key pressed");
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();
        int touchType = AppIntro.TOUCH_DOWN;

        //if (evt.getAction() == MotionEvent.ACTION_DOWN)
        //  Log.d("DCT", "Touch pressed (ACTION_DOWN) at (" + String.valueOf(x) + "," + String.valueOf(y) +  ")"  );

        if (evt.getAction() == MotionEvent.ACTION_MOVE)
            touchType = AppIntro.TOUCH_MOVE;
        if (evt.getAction() == MotionEvent.ACTION_UP)
            touchType = AppIntro.TOUCH_UP;

        if (curLayout == LAYOUT_INTRO
                || curLayout == LAYOUT_ABOUT) {
            return viewIntro.onTouch(x, y, touchType);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (curLayout == LAYOUT_INTRO
                || curLayout == LAYOUT_ABOUT) {
            viewIntro.start();
        }
        Log.d("VT", "App onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (curLayout == LAYOUT_INTRO
                || curLayout == LAYOUT_ABOUT) {
            viewIntro.stop();
        }
        Log.d("VT", "App onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("VT", "App onDestroy");
    }

    public void onPlayClick(View view) {
        if (getCurLayout() == LAYOUT_MENU) {
            setCurLayout(LAYOUT_LEVEL);
        }
    }

    public void onLevelButtonClick(View view) {
        switch (view.getId()) {
            case R.id.level1:
                level = 0;
                break;
            case R.id.level2:
                level = 1;
                break;
            case R.id.level3:
                level = 2;
                break;
            case R.id.level4:
                level = 3;
                break;
        }

        setCurLayout(LAYOUT_GAME);
    }

    public void onRestartButtonClick(View view) {
        setCurLayout(LAYOUT_GAME);
    }

    public void onNextButtonClick(View view) {
        level = (level + 1) % 4;
        setCurLayout(LAYOUT_GAME);
    }

//    class Game {
//        public static final int LEVEL_FIRST  = 1;
//        public static final int LEVEL_SECOND = 2;
//        public static final int LEVEL_THIRD  = 3;
//        public static final int LEVEL_FOURTH = 4;
//
//        public static final int HERO_FROG = 1;
//        public static final int HERO_FROG_2 = 2;
//
//        private int cur_level;
//        private int cur_hero;
//
//        ImageView hero;
//        View curView; //bad solution
//
//        public void setCurView(View curView) {
//            this.curView = curView;
//        }
//
//        public void levelAnimation() {
//            switch (cur_level) {
//                case LEVEL_FIRST:
//                    animationFirstLevel();
//                    break;
//                case LEVEL_SECOND:
//                    animationSecondLevel();
//                    break;
//                case LEVEL_THIRD:
//                    animationThirdLevel();
//                    break;
//                case LEVEL_FOURTH:
//                    animationFourthLevel();
//                    break;
//            }
//        }
//
//        private void animationFourthLevel() {
//
//        }
//
//        private void animationThirdLevel() {
//
//        }
//
//        private void animationSecondLevel() {
//
//        }
//
//        private void animationFirstLevel() {
//            ImageView leaf_1 = (ImageView)findViewById(R.id.lvl_1_leaf_1);
//            Animation leaf_motion = AnimationUtils.loadAnimation(MainActivity.this, R.anim.leaf_motion);
//            leaf_1.startAnimation(leaf_motion);
//        }
//
//        private void setCurLevel(int level) {
//            cur_level = level;
//
//            switch (cur_level) {
//                case LEVEL_FIRST:
//                    //setContentView(R.layout.level_1);
//                    hero = (ImageView)findViewById(R.id.hero_lvl_1);
//                    break;
//                case LEVEL_SECOND:
//                    setContentView(R.layout.level_2);
//                    break;
//                case LEVEL_THIRD:
//                    setContentView(R.layout.level_3);
//                    break;
//                case LEVEL_FOURTH:
//                    setContentView(R.layout.level_4);
//                    break;
//            }
//            levelAnimation();
//        }
//
//        public Game() {
//            this.cur_level = LEVEL_FIRST;
//            this.cur_hero = HERO_FROG;
//        }
//
//        public Game built() {
//            return this;
//        }
//
//        public Game setLevel(int level) {
//            cur_level = level;
//            return this;
//        }
//
//        public Game setHero(int current_hero) {
//            cur_hero = current_hero;
//            return this;
//        }
//
//        public ImageView getHero() { return hero; }
//
//        public void jump(View view) {
//            TranslateAnimation anim = new TranslateAnimation(0, curView.getLeft(), 0, curView.getTop());
//            anim.setDuration(2000);
//            anim.setStartOffset(100);
//            anim.setFillAfter(true);
//            //anim.setZAdjustment(Animation.ZORDER_TOP);
//            anim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    hero.setLeft(0);
//                    hero.setTop(0);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            hero.startAnimation(anim);
//        }
//
//        public void run() {
//            setCurLevel(cur_level);
//        }
//    }
}
