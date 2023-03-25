package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Grasshopper extends Object {
    @Override
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e){
        if (!GameScreen.pause) {
            Rectangle belalang = b.next();
            belalang.x += 30 * Gdx.graphics.getDeltaTime();

            //kalau keluar dari screen, hapus dari iter
            if (belalang.x + 64 > 1080) {
                b.remove();
            }

            if (belalang.overlaps(c)) {
                if (d >= 2) {
                    d -= 2;
                } else {
                    d = 0;
                }
                e.play();
                belalang.x -= 200;
                b.remove();
            }
        }
        return d;
    }
}
