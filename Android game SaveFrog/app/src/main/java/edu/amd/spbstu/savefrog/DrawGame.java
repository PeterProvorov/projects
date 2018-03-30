package edu.amd.spbstu.savefrog;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static edu.amd.spbstu.savefrog.MainActivity.LAYOUT_GAME_OVER;
import static edu.amd.spbstu.savefrog.R.id.timeView;

public class DrawGame extends View {
    MainActivity activity;
    Paint paint;
    Path[] path;

    FrogGame game;
    int level;
    int maxPathCount = 10;
    long score = 0;
    long remainedTime;

    Bitmap bonusBitmap;
    Bitmap finishLeafBitmap;
    Bitmap heroBitmap;

    CountDownTimer timer;

    public DrawGame(final MainActivity activity, int level) {
        super(activity);
        this.activity = activity;
        this.level = level;
        this.score = 0;
        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        path = new Path[maxPathCount];
        for (int i = 0; i < maxPathCount; i++) {
            path[i] = new Path();
        }
        game = new FrogGame();

        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainedTime = millisUntilFinished / 1000;
                String time = "Time: " + remainedTime;
                final TextView timeView = (TextView) activity.findViewById(R.id.timeView);
                timeView.setTextSize(23);
                timeView.setText("" + millisUntilFinished / 1000);
                //timeView.setText("Time: " + (millisUntilFinished));
                //Resources res = getResources();
                //String str_time = String.format(res.getString(R.string.str_time), millisUntilFinished / 1000);
                //timeView.setText(str_time);
            }

            @Override
            public void onFinish() {
                activity.setCurLayout(LAYOUT_GAME_OVER);
            }
        };
        timer.start();
    }

    void builtGame(V2d canvasSize) {
        if (!game.isBuilt) {
            game.BuildGame(canvasSize).createHero(new V2d(0,0)).createLevel(level);
            V2d startHeroPos = new V2d(game.curLevel.leafs[0].getPos().x, game.curLevel.leafs[0].getPos().y);
            game.hero.setPos(startHeroPos);
            heroBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frog);
            switch (level) {
                case 0:
                    finishLeafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_flower_yellow);
                    break;
                case 1:
                    finishLeafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_flower_cian);
                    break;
                case 2:
                    finishLeafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_flower_red);
                    break;
                case 3:
                    finishLeafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_flower_white);
                    break;
            }

            final TextView levelView = (TextView) activity.findViewById(R.id.levelview);
            Resources res = getResources();
            String str_level = String.format(res.getString(R.string.str_level), level + 1);
            levelView.setText(str_level);

            updateScore();
            toastNotification();
        }
    }

    void drawLevel(Canvas canvas) {
        V2d canvasSize = new V2d(canvas.getWidth(), canvas.getHeight());

        builtGame(canvasSize);

        canvas.drawColor(getResources().getColor(R.color.colorWater));
        for (int i = 0; i < game.curLevel.leafCount; i++) {
            paint.setColor(getResources().getColor(R.color.colorGreen));
            if (i > 0 && i < game.curLevel.leafCount - 1) {
                game.curLevel.leafs[i].changeSize();
            }

            path[i].reset();
            int rad = (int) game.curLevel.leafs[i].getSize();
            int x = game.curLevel.leafs[i].getPos().x;
            int y = game.curLevel.leafs[i].getPos().y;

            path[i] = drawLeaf(x, y, rad, canvas, paint);

            if(i == game.curLevel.leafCount - 1)
                canvas.drawBitmap(finishLeafBitmap, x - finishLeafBitmap.getWidth() / 2, y - finishLeafBitmap.getHeight() / 2, null);

            if(game.curLevel.leafs[i].isBonus()) {
                bonusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fly);
                int bonusX = x - rad / 2;
                int bonusY = y - rad / 2;
                canvas.drawBitmap(bonusBitmap, bonusX, bonusY, null);
            }
        }
    }

    void drawHero(Canvas canvas) {
        if (game.hero.isMoving == true) {
            game.hero.moving();
        } else {
            int gameStatus = game.checkEndOfGame();
            if(gameStatus != FrogGame.GAME_STATUS_PLAY) {
                //score += remainedTime;
                drawEndOfGame(gameStatus);
            } else {
                checkBonus();
            }
        }
        int x = game.hero.pos.x;
        int y = game.hero.pos.y;

        canvas.drawBitmap(heroBitmap, x - heroBitmap.getWidth() / 2, y - heroBitmap.getHeight() / 2, null);

//        int left = game.hero.pos.x - heroBitmap.getWidth() / 2;
//        int top = game.hero.pos.y - heroBitmap.getHeight() / 2;
//        int right = game.hero.pos.x + heroBitmap.getWidth() / 2;
//        int bottom = game.hero.pos.y + heroBitmap.getHeight() / 2;
//        paint.setColor(getResources().getColor(R.color.colorAccent));
//        canvas.drawLine(left, top, left, bottom, paint);
//        canvas.drawLine(left, top, right, top, paint);
//        canvas.drawLine(left, bottom, right, bottom, paint);
//        canvas.drawLine(right, top, right, bottom, paint);
//        canvas.drawCircle(x, y, 5, paint);
//        canvas.drawBitmap(game.hero.bitmap, game.hero.pos.x, game.hero.pos.y, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawLevel(canvas);
        drawHero(canvas);

        invalidate();
    }

    Path drawLeaf(int x, int y, int rad, Canvas canvas, Paint paint) {
        Path leaf = new Path();
        leaf.moveTo(x, y);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorLeaf));
        //leaf.addCircle(x, y, rad, Path.Direction.CCW);
        //canvas.drawCircle(x, y, rad * 2 / 3, paint);

//        leaf.quadTo(x - rad / 2, y, x - rad / 2, y - rad / 2);
//        leaf.quadTo(x + rad * 3 / 8, y - rad * 3 / 4,x + rad / 2, y);
//        leaf.quadTo(x + rad * 3 / 8, y + rad * 3 / 4,x - rad / 2, y + rad / 2);
//        leaf.quadTo(x - rad / 2, y, x, y);

        leaf.quadTo(x - rad, y, x - rad, y - rad);
        leaf.quadTo(x + rad * 3 / 4, y - rad * 3 / 2,x + rad, y);
        leaf.quadTo(x + rad * 3 / 4, y + rad * 3 / 2,x - rad, y + rad);
        leaf.quadTo(x - rad, y, x, y);

        //leaf.addCircle(x, y, rad, Path.Direction.CCW);
        canvas.drawPath(leaf, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorBlack));
        canvas.drawPath(leaf, paint);
//        if (i < game.curLevel.leafCount - 1) {
//            paint.setColor(getResources().getColor(R.color.colorLeaf));
//        } else {
//            paint.setColor(getResources().getColor(R.color.colorAccent));
//        }
//        leaf.addArc(rectF, 200, 310);
        //paint.setColor(getResources().getColor(R.color.colorLeaf));
        //RectF rectF = new RectF(x - rad, y - rad, x + rad, y + rad);
        //canvas.drawArc(rectF, 200, 310, true, paint);
        return leaf;
    }

    void toastNotification() {
        Toast toast;
        ImageView tipImage = new ImageView(activity);
        tipImage.setImageResource(R.drawable.lamp);
        String tip = new String(getResources().getString(R.string.str_tip_4));
        int xOffset = 0, yOffset = 0;

        switch (this.level) {
            case 0:
                tip = getResources().getString(R.string.str_tip_1);
                xOffset = 0;
                yOffset = - 2 * FrogGame.Leaf.LEAF_SIZE;
                break;
            case 1:
                tip = getResources().getString(R.string.str_tip_2);
                xOffset = 0;
                yOffset = - 2 * FrogGame.Leaf.LEAF_SIZE;
                break;
            case 2:
                tip = getResources().getString(R.string.str_tip_3);
                tipImage.setImageResource(R.drawable.fly);
                xOffset = 0;
                yOffset = 0;
                break;
            case 3:
                break;
        }

        toast = Toast.makeText(activity, tip, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, xOffset, yOffset);
        LinearLayout toastImage = (LinearLayout) toast.getView();
        toastImage.addView(tipImage, 0);
        toast.show();
    }

    private void updateScore() {
        Resources res = getResources();

        final TextView scoreView = (TextView) activity.findViewById(R.id.scoreView);
        String str_score = String.format(res.getString(R.string.str_score), score, this.game.curLevel.maxScore);
        scoreView.setText(str_score);
    }

    private void checkBonus() {
        int i = 0;
        for (Path curPath : path) {
            RectF pathBounds = new RectF();
            curPath.computeBounds(pathBounds, true);
            if (pathBounds.contains(game.hero.pos.x, game.hero.pos.y) && game.curLevel.leafs[i].isBonus() == true) {
                game.curLevel.leafs[i].setBonus(false);
                score += this.game.curLevel.bonusScore;
                updateScore();
                break;
            }
            i++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            for (Path curPath : path) {
                RectF pathBounds = new RectF();
                curPath.computeBounds(pathBounds, true);
                if (pathBounds.contains(x, y)) {
                    if(game.hero.isMoving == false)
                        game.hero.moveTo(new V2d((int) pathBounds.centerX(), (int) pathBounds.centerY()));
                    break;
                }
            }
            invalidate();
        }
        return true;
    }

    public void drawEndOfGame(int gameStatus) {
        Resources res = getResources();
        TextView gameOverView = (TextView) activity.findViewById(R.id.gameWinText);
        String str_game_over_text = "";
        switch (gameStatus) {
            case FrogGame.GAME_STATUS_LOSE:
                timer.cancel();
                activity.setCurLayout(LAYOUT_GAME_OVER);
                gameOverView = (TextView) activity.findViewById(R.id.gameLoseText);
                str_game_over_text = res.getString(R.string.str_lose);
                break;
            case FrogGame.GAME_STATUS_WIN:
                timer.cancel();
                activity.setCurLayout(MainActivity.LAYOUT_GAME_WIN);
                gameOverView = (TextView) activity.findViewById(R.id.gameWinText);
                str_game_over_text = res.getString(R.string.str_win);

                TextView finalSCore = (TextView) activity.findViewById(R.id.finalScore);
                String str_final_score = String.format(res.getString(R.string.str_score), score, this.game.curLevel.maxScore);
                finalSCore.setText(str_final_score + " + remaining time:" + remainedTime + "s = " + (remainedTime + score));
                break;
        }
        gameOverView.setText(str_game_over_text);
    }

    public void exitGame() {
        timer.cancel();
    }

    //public void moveHero()
}
