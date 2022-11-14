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

import java.util.ArrayList;

public class MainView extends View {
    int m_ViewWidth, m_ViewHeight; // 화면의 넓이와 높이
    Bitmap m_Img_btnLeft, m_Img_btnRight;
    int m_BtnLeft_X, m_BtnLeft_Y, m_BtnRight_X, m_BtnRight_Y; // 왼쪽버튼 X,Y좌표 오른쪽버튼 X,Y좌표
    int m_Btn_W, m_Btn_H; // 버튼의 넓이와 높이
    Rect m_RectBtn_Left, m_RectBtn_Right; // 버튼의 터치 영역

    Bitmap m_Img_Paddle; //패들
    int m_Paddle_X, m_Paddle_Y; // 패들의 X,Y좌표
    int m_Paddle_W, m_Paddle_H; // 패들의 넓이, 높이

    Bitmap m_Img_Ball; //공
    int m_Ball_X, m_Ball_Y; //공의 x,y좌표
    int m_Ball_D, m_Ball_R; //공의 지름, 반지름
    int m_Ball_Speed, m_Ball_SpeedX, m_Ball_SpeedY; //공의 속도, 공의 X방향 속도, 공의 Y방향 속도

    Bitmap m_Img_Block; // 블럭 이미지
    ArrayList<Block> m_Arr_BlockList = new ArrayList<Block>();

    boolean m_IsPlay = false; // 게임상태


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
        func_BallMove();
        func_PaddleCheck();
        func_BlockCheck();

        canvas.drawBitmap(m_Img_Ball, m_Ball_X, m_Ball_Y, null);
        canvas.drawBitmap(m_Img_Paddle, m_Paddle_X, m_Paddle_Y, null);
        canvas.drawBitmap(m_Img_btnLeft, m_BtnLeft_X, m_BtnLeft_Y, null);
        canvas.drawBitmap(m_Img_btnRight, m_BtnRight_X, m_BtnRight_Y, null);

        for (Block w_Block : m_Arr_BlockList){
            canvas.drawBitmap(m_Img_Block, w_Block.Block_X, w_Block.Block_Y,null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int w_X = (int) event.getX();
        int w_Y = (int) event.getY();

        int w_KeyAction = event.getAction();

        switch (w_KeyAction) {
            case MotionEvent.ACTION_DOWN:
                if(m_IsPlay) {
                    if (m_RectBtn_Left.contains(w_X, w_Y)) {
                        m_IsTouch = true;
                        m_Handler_BtnLeft(0);

                    } else if (m_RectBtn_Right.contains(w_X, w_Y)) {
                        m_IsTouch = true;
                        m_Handler_BtnRight(0);
                    }
                }else{
                    if(m_RectBtn_Left.contains(w_X, w_Y)){
                        m_IsPlay =true;
                        m_Ball_SpeedX = -m_Ball_Speed;
                        m_Ball_SpeedY = -m_Ball_Speed;
                    }else if(m_RectBtn_Right.contains(w_X, w_Y)){
                        m_IsPlay = true;
                        m_Ball_SpeedX = m_Ball_Speed;
                        m_Ball_SpeedY = -m_Ball_Speed;
                    }


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

        //볼 초기화
        m_Img_Ball = BitmapFactory.decodeResource(getResources(), R.drawable.block_ball);
        m_Ball_D = m_Paddle_H;
        m_Ball_R = m_Ball_D/2;
        m_Ball_X = m_ViewWidth /2 - m_Ball_R;
        m_Ball_Y = m_Paddle_Y - m_Ball_D;
        m_Img_Ball = Bitmap.createScaledBitmap(m_Img_Ball, m_Ball_D, m_Ball_D, false);

        m_Ball_Speed = m_Ball_R;
        m_Ball_SpeedX = 0;
        m_Ball_SpeedY = 0;

        //블럭 생성
        func_MakeBlock();

        m_Handler_ViewReload(30);
    }

    private void func_Reset(){
        m_IsPlay = false;
        m_IsTouch = false;

        m_Ball_SpeedX = 0;
        m_Ball_SpeedY = 0;

        m_Paddle_X = m_ViewWidth / 2 - m_Paddle_W / 2;
        m_Paddle_Y = m_BtnLeft_Y - m_Paddle_H - m_Paddle_H / 2;
        m_Ball_X = m_ViewWidth /2 - m_Ball_R;
        m_Ball_Y = m_Paddle_Y - m_Ball_D;
    }

    //블럭 만들기
    private void func_MakeBlock(){
        int w_Block_W = m_ViewWidth/7;
        int w_Block_H = w_Block_W/3;

        m_Img_Block = BitmapFactory.decodeResource(getResources(), R.drawable.block_block01);
        m_Img_Block = Bitmap.createScaledBitmap(m_Img_Block, w_Block_W, w_Block_H, false);

        m_Arr_BlockList.clear();
        for(int i=0; i < 3; i++){
            int w_Block_Y = w_Block_H*2 + w_Block_H*i;
            for(int j=0; j < 7; j++){
                int w_Block_X = w_Block_W *j;

                Block w_Block = new Block(w_Block_W, w_Block_H, w_Block_X, w_Block_Y);
                m_Arr_BlockList.add(w_Block);

            }
        }
    }

    // 공의 움직임 처리
    private void func_BallMove(){
        if(m_IsPlay){
            m_Ball_X += m_Ball_SpeedX;
            m_Ball_Y += m_Ball_SpeedY;

            if(m_Ball_X <= 0 || m_Ball_X >= m_ViewWidth - m_Ball_D){
                m_Ball_SpeedX *= -1;
            }
            if (m_Ball_Y <=0){
                m_Ball_SpeedY *= -1;
            }
            if (m_Ball_Y>= m_ViewHeight) func_Reset();
        }
    }

    //패들 충돌 확인
    private void func_PaddleCheck(){
        if(m_IsPlay){
            if(m_Paddle_X - m_Ball_R <= m_Ball_X && m_Ball_X <= m_Paddle_X + m_Paddle_W-m_Ball_R
            && m_Paddle_Y - m_Ball_D <= m_Ball_Y && m_Ball_Y <= m_Paddle_Y - m_Ball_R){
                m_Ball_SpeedY *= -1;
                if(m_Paddle_Y - m_Ball_D < m_Ball_Y) m_Ball_Y = m_Paddle_Y - m_Ball_D - (m_Ball_Y - (m_Paddle_Y - m_Ball_D));
            } else if (m_Paddle_Y - m_Ball_R <= m_Ball_Y && m_Ball_Y <= m_Paddle_Y + m_Ball_R
            && m_Paddle_X - m_Ball_D <= m_Ball_X && m_Ball_X <= m_Paddle_X + m_Paddle_W){
                m_Ball_SpeedX *= -1;
                m_Ball_X += m_Ball_SpeedX;
            }
        }
    }

    //블럭 충돌 확인
    private void func_BlockCheck(){
        for (Block w_Block : m_Arr_BlockList){
            int w_BlockCheck = w_Block.IsClash(m_Ball_X, m_Ball_Y, m_Ball_D,m_Ball_R);

            switch (w_BlockCheck){
                case 0:
                    continue;
                case 1:
                case 2:
                    m_Ball_SpeedX *=-1;
                    break;
                case 3:
                case 4:
                    m_Ball_SpeedY *=-1;
                    break;
            }
            m_Arr_BlockList.remove(w_Block);
            break;
        }
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
                m_Paddle_X -= m_Paddle_W / 8;
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
                m_Paddle_X += m_Paddle_W / 8;
                if(m_Paddle_X >= m_ViewWidth - m_Paddle_W){
                    m_Paddle_X = m_ViewWidth - m_Paddle_W;
                    m_IsTouch = false;
                }
                if (m_IsTouch) m_Handler_ViewReload(30);
            }
        }, p_DelayTime);
    }
}
