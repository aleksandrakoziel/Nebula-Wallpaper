package com.example.spacewallpaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SpaceWallpaper extends WallpaperService {

    private final Handler mHandler = new Handler();
    ArrayList<Nebula> nebulas = new ArrayList<>();

    @Override
    public void onCreate() {
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        nebulas = Utils.availableNebulas(displayMetrics.widthPixels, displayMetrics.heightPixels);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new SpaceEngine();
    }

    class SpaceEngine extends Engine {

        private final Paint paint = new Paint();
        private ArrayList<Stars> cosmos = new ArrayList<>();
        private int current;
        private int nebula;
        private float mTouchX = -1;
        private float mTouchY = -1;
        private int screenX;
        private int screenY;
        private Bitmap myBitmap;
        private long startTime;
        private long mStartTime;
        private float mCenterX;
        private float mCenterY;
        private int FRAMES = 24;
        private int MINUTE_IN_MILLIS = 5 * 60 * 1000;
        private float CIRCLE = 10000;

        private final Runnable mDrawSpace = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        private boolean mVisible;

        SpaceEngine() {
            // Create a Paint to draw the lines for our cube
            final Paint paint = this.paint;
            paint.setColor(0xffffffff);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(2);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);

            mStartTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
            screenX = displayMetrics.widthPixels;
            screenY = displayMetrics.heightPixels;

            for (int i = 0; i < 300; i++) {
                Stars c = new Stars(screenX, screenY);
                cosmos.add(c);
            }

            nebula = nebulas.get(current).getResId();
            startTime = System.currentTimeMillis();
            setTouchEventsEnabled(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mDrawSpace);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                drawFrame();
            } else {
                mHandler.removeCallbacks(mDrawSpace);
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            mCenterX = width / 2.0f;
            mCenterY = height / 2.0f;
            drawFrame();
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible = false;
            mHandler.removeCallbacks(mDrawSpace);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
                                     float yStep, int xPixels, int yPixels) {
            drawFrame();
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mTouchX = event.getX();
                mTouchY = event.getY();
                Log.d("NEBULASWALLPAPER", "Tapped on x: " + mTouchX + " and y: " + mTouchY);
                if (belongsToCenter()) {
                    Log.d("NEBULASWALLPAPER", "Tap belongs to center, go to wiki page.");
                    aboutNebulas();
                } else if (onDiagonal()) {
                    Log.d("NEBULASWALLPAPER", "Tap belongs to diagonal, create message.");
                    composeMessage();
                } else {
                    Log.d("NEBULASWALLPAPER", "Tap outside, check if change possible.");
                    updateNebula();
                }

            } else {
                mTouchX = -1;
                mTouchY = -1;
            }
            super.onTouchEvent(event);
        }


        void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    // draw stars on canvas
                    drawStars(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }


            mHandler.removeCallbacks(mDrawSpace);
            if (mVisible) {
                mHandler.postDelayed(mDrawSpace, 1000 / FRAMES);
            }
        }


        void drawStars(Canvas canvas) {
            canvas.save();
            canvas.translate(0, 0);

            if (myBitmap != null) {
                myBitmap.recycle();
                myBitmap = null;
            }
            Bitmap original = BitmapFactory.decodeResource(getResources(), nebula);
            myBitmap = Bitmap.createScaledBitmap(original, screenX, screenY, false);
            if (original != myBitmap) {
                original.recycle();
            }

            canvas.drawBitmap(myBitmap, 0, 0, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            //drawing all stars
            for (Stars c : cosmos) {
                paint.setColor(getRandomColor(ThreadLocalRandom.current().nextInt(0, 10)));
                paint.setStrokeWidth(c.getStarWidth());
                canvas.drawPoint(c.getX(), c.getY(), paint);
            }
            canvas.restore();
        }

        private int getRandomColor(int random) {
            int color;
            if (random == 0) {
                color = Integer.parseInt("fdc36f", 16);
            } else if (random == 1) {
                color = Integer.parseInt("581f42", 16);
            } else if (random == 2) {
                color = Integer.parseInt("f18db0", 16);
            } else if (random == 3) {
                color = Integer.parseInt("3c65b3", 16);
            } else {
                color = Color.WHITE;
            }
            return color;
        }

        void updateNebula() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > MINUTE_IN_MILLIS) {
                if (current == nebulas.size() - 1) {
                    current = 0;
                } else {
                    current++;
                }
                startTime = System.currentTimeMillis();
                nebula = nebulas.get(current).getResId();
                myBitmap.recycle();

                Log.d("NEBULASWALLPAPER", "Nebulas image was changed to no." + current);
            } else {
                Log.d("NEBULASWALLPAPER", "Change too soon. Waiting...");
            }
        }

        private boolean belongsToCenter() {
            return CIRCLE > (mTouchX - mCenterX) * (mTouchX - mCenterX) + (mTouchY - mCenterY) * (mTouchY - mCenterY);
        }

        void aboutNebulas() {
            String url = "https://en.wikipedia.org/wiki/Nebula";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        boolean onDiagonal() {
            double x = (double) screenX;
            double y = (double) screenY;
            double a = (-1 * y) / x;
            return mTouchY < a * mTouchX + y + 100 && mTouchY > a * mTouchX + y - 100;
        }

        void composeMessage() {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "I love Nebulas!");
            startActivity(intent);
        }
    }
}
