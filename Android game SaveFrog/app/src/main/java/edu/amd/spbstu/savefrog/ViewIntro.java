package edu.amd.spbstu.savefrog;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;

import android.view.View;

// ****************************************************************
// class RefreshHandler
// ****************************************************************
public class ViewIntro extends View {

    class RedrawHandler extends Handler {
        ViewIntro m_viewIntro;

        public RedrawHandler(ViewIntro v) {
            m_viewIntro = v;
        }

        public void handleMessage(Message msg) {
            m_viewIntro.update();
            m_viewIntro.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    ;

    // CONST
    private static final int UPDATE_TIME_MS = 30;


    // DATA
    MainActivity app;
    RedrawHandler handler;
    long startTime;
    int lineLen;
    boolean active;

    // METHODS
    public ViewIntro(MainActivity app) {
        super(app);
        this.app = app;

        handler = new RedrawHandler(this);
        startTime = 0;
        lineLen = 0;
        active = false;
        setOnTouchListener(app);
    }

    public boolean performClick() {
        boolean b = super.performClick();
        return b;
    }

    public void start() {
        active = true;
        handler.sleep(UPDATE_TIME_MS);
    }

    public void stop() {
        active = false;
        //m_handler.sleep(UPDATE_TIME_MS);
    }

    public void update() {
        if (!active)
            return;
        // send next update to game
        if (active)
            handler.sleep(UPDATE_TIME_MS);
    }

    public boolean onTouch(int x, int y, int evtType) {
        AppIntro appIntro = app.getAppIntro();
        return appIntro.onTouch(x, y, evtType);
    }

    public void onConfigurationChanged(Configuration confNew) {
        AppIntro appIntro = app.getAppIntro();
        if (confNew.orientation == Configuration.ORIENTATION_LANDSCAPE)
            appIntro.onOrientation(AppIntro.APP_ORI_LANDSCAPE);
        if (confNew.orientation == Configuration.ORIENTATION_PORTRAIT)
            appIntro.onOrientation(AppIntro.APP_ORI_PORTRAIT);
    }

    public void onDraw(Canvas canvas) {
        AppIntro appIntro = app.getAppIntro();
        appIntro.drawCanvas(canvas);
    }

}
