package com.example.blockgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {
    int m_ViewWidth, m_ViewHeight; // 화면의 넓이와 높이
    Bitmap m_Img_btnLeft, m_Img_btnRight;
    int m_BtnLeft_X, m_BtnLeft_Y, m_BtnRight_X, m_BtnRight_Y; // 왼쪽버튼 X,Y좌표 오른쪽버튼 X,Y좌표
    int m_Btn_W, m_Btn_H; // 버튼의 넓이와 높이
    Rect m_RectBtn_Left, m_RectBtn_Right; // 버튼의 터치 영역

    Bitmap m_Img_Paddle; //패들
    int m_Paddle_X, m_Paddle_Y; // 패들의 X,Y좌표
    int m_Paddle_W, m_Paddle_H; // 패들의 넓이, 높이

    public MainView(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        m_ViewWidth = w;
        m_ViewHeight = h;

        func_Setting();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.drawBitmap(m_Img_Paddle, m_Paddle_X, m_Paddle_Y, null);
        canvas.drawBitmap(m_Img_btnLeft, m_BtnLeft_X, m_BtnLeft_Y, null);
        canvas.drawBitmap(m_Img_btnRight, m_BtnRight_X, m_BtnRight_Y, null);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int w_X = (int) event.getX();
        int w_Y = (int) event.getY();

        int w_KeyAction = event.getAction();

        switch (w_KeyAction) {
            case MotionEvent.ACTION_DOWN:
                if (m_RectBtn_Left.contains(w_X, w_Y)) {

                    m_IsTouch = true;
                    m_Handler_BtnLeft(0);

                } else if (m_RectBtn_Right.contains(w_X, w_Y)){
                    m_IsTouch = true;
                    m_Handler_BtnRight(0);
        }
                break;
            case MotionEvent.ACTION_UP:
                m_IsTouch = false;
                break;
        }

        return true;
    }

    private void func_Setting() {
        //버튼 초기화
        m_Img_btnLeft = BitmapFactory.decodeResource(getResources(), R.drawable.btn_block_left);
        m_Img_btnRight = BitmapFactory.decodeResource(getResources(), R.drawable.btn_block_right);
        m_Btn_W = m_ViewWidth / 8;
        m_Btn_H = m_Btn_W;
        m_BtnLeft_X = 0;
        m_BtnLeft_Y = m_ViewHeight - m_Btn_H;
        m_BtnRight_X = m_ViewWidth - m_Btn_W;
        m_BtnRight_Y = m_BtnLeft_Y;
        m_Img_btnLeft = Bitmap.createScaledBitmap(m_Img_btnLeft, m_Btn_W, m_Btn_H, false);
        m_Img_btnRight = Bitmap.createScaledBitmap(m_Img_btnRight, m_Btn_W, m_Btn_H, false);
        m_RectBtn_Left = new Rect(m_BtnLeft_X, m_BtnLeft_Y, m_BtnLeft_X + m_Btn_W, m_BtnLeft_Y + m_Btn_H);
        m_RectBtn_Right = new Rect(m_BtnRight_X, m_BtnRight_Y, m_BtnRight_X + m_Btn_W, m_BtnRight_Y + m_Btn_H);


        //패들 초기화
        m_Img_Paddle = BitmapFactory.decodeResource(getResources(), R.drawable.block_paddle);
        m_Paddle_W = m_ViewWidth / 5;
        m_Paddle_H = m_Paddle_W / 4;
        m_Paddle_X = m_ViewWidth / 2 - m_Paddle_W / 2;
        m_Paddle_Y = m_BtnLeft_Y - m_Paddle_H - m_Paddle_H / 2;
        m_Img_Paddle = Bitmap.createScaledBitmap(m_Img_Paddle, m_Paddle_W, m_Paddle_H, false);

        m_Handler_ViewReload(30);
    }


    boolean m_IsEnd = true;

    private void m_Handler_ViewReload(long p_DelayTime) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                if (m_IsEnd) m_Handler_ViewReload(30);
            }
        }, p_DelayTime);
    }

    boolean m_IsTouch = false;
    //왼쪽버튼 눌렀을 때
    private void m_Handler_BtnLeft(long p_DelayTime) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                m_Paddle_X -= m_Paddle_W / 20;
                if(m_Paddle_X <= 0){
                    m_Paddle_X = 0;
                    m_IsTouch =false;
                }
                if (m_IsTouch) m_Handler_ViewReload(30);
            }
        }, p_DelayTime);
    }
    //오른쪽버튼 눌렀을 때
    private void m_Handler_BtnRight(long p_DelayTime) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                m_Paddle_X += m_Paddle_W / 20;
                if(m_Paddle_X >= m_ViewWidth - m_Paddle_W){
                    m_Paddle_X = m_ViewWidth - m_Paddle_W;
                    m_IsTouch = false;
                }
                if (m_IsTouch) m_Handler_ViewReload(30);
            }
        }, p_DelayTime);
    }
}
