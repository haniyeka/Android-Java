package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class BroadView_TwoPlayerNearby extends View{
    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
    private TictactoeTwoPlayerNearby tictactoeTwoPlayer;
    private TwoplayersNearbyActivity twoplayersActivity;

    public BroadView_TwoPlayerNearby(Context context) {
        super(context);
    }

    public BroadView_TwoPlayerNearby(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        gridPaint.setColor(Color.GRAY);
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.parseColor("#FF4081"));
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.parseColor("#00ACC1"));
    }

    public void setActivity(TwoplayersNearbyActivity activity) {
        twoplayersActivity = activity;
        tictactoeTwoPlayer.gActivity = activity;
    }

    public void setGame(TictactoeTwoPlayerNearby game) {
        tictactoeTwoPlayer = game;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        eltW = (width - LINE_THICK) / 3;
        eltH = (height - LINE_THICK) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas, tictactoeTwoPlayer);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!tictactoeTwoPlayer.isGameOver()  &&  event.getAction() == MotionEvent.ACTION_DOWN) {
            if(tictactoeTwoPlayer.Player.contains("Player " + tictactoeTwoPlayer.currentPlayer)){
                int x = (int) (event.getX() / eltW);
                int y = (int) (event.getY() / eltH);
                char win = tictactoeTwoPlayer.play(x, y);
                invalidate();

                if (win != ' ') {
                    twoplayersActivity.isGameOver(win);
                    Toast.makeText(twoplayersActivity,"game over",Toast.LENGTH_LONG).show();
                }
                else{

                }
            }
            else{
                Toast.makeText(getContext(),"Wait for your friend to play!",Toast.LENGTH_LONG).show();
            }
        }

        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas, TictactoeTwoPlayerNearby gameEngine) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawXO(canvas, this.tictactoeTwoPlayer.getLocation(i, j), i, j);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawXO(Canvas canvas, char c, int x, int y) {
        if (c == 'O') {
            float cx = (eltW * x) + eltW / 2;
            float cy = (eltH * y) + eltH / 2;

            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2, oPaint);

        } else if (c == 'X') {
            float startX = (eltW * x) + ELT_MARGIN;
            float startY = (eltH * y) + ELT_MARGIN;
            float endX = startX + eltW - ELT_MARGIN * 2;
            float endY = startY + eltH - ELT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - ELT_MARGIN;
            float startY2 = (eltH * y) + ELT_MARGIN;
            float endX2 = startX2 - eltW + ELT_MARGIN * 2;
            float endY2 = startY2 + eltH - ELT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }

}
