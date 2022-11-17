package com.example.blockgame;

public class Block {
    int Block_W, Block_H; // 블록의 너비와 높이
    int Block_X, Block_Y; // 블록의 좌표(X,Y)

    //생성자
    public Block(int w, int h, int x, int y){
        Block_H = h;
        Block_W = w;
        Block_X = x;
        Block_Y = y;
    }

    //블록에 충돌 확인(0: 충돌하지 않음, 1:왼쪽, 2: 오른쪽, 3:위, 4:아래 )
    public int IsClash(int Ball_X, int Ball_Y, int Ball_D, int Ball_R){
        //블럭 좌측 충돌 확인
        if (Block_X-Ball_D <= Ball_X && Ball_X <= Block_X-Ball_R
                && Block_Y-Ball_R <= Ball_Y && Ball_Y <= Block_Y+Block_H-Ball_R){
            return 1;
        }
        // 블럭 우측 충돌 확인
        if (Block_X+Block_W >= Ball_X && Ball_X >= Block_X+Block_W-Ball_R
                && Block_Y-Ball_R <= Ball_Y && Ball_Y <= Block_Y+Block_H-Ball_R){
            return 2;
        }
        // 블럭 상측 충돌 확인
        if (Block_Y-Ball_D <= Ball_Y && Ball_Y <= Block_Y-Ball_R
                && Block_X-Ball_R <= Ball_X && Ball_X <= Block_X+Block_W-Ball_R){
            return 3;
        }
        // 블럭 하측 충돌 확인
        if (Block_Y+Block_H >= Ball_Y && Ball_Y >= Block_H-Ball_R
                && Block_X-Ball_R <= Ball_X && Ball_X <= Block_X+Block_W-Ball_R){
            return 4;
        }

        return 0;
    }
}
