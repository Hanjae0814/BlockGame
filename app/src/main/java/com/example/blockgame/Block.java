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
}
