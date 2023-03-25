package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Worm extends Object {
    @Override
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e){
        if (!GameScreen.pause) {
            Rectangle cacing = b.next();
            cacing.x -= 50 * Gdx.graphics.getDeltaTime();

            //kalau keluar dari screen, hapus dari iter
            if (cacing.x - 64 > 1080) {
                b.remove();
            }

            if (cacing.overlaps(c)) {
                if (d >= 1) {
                    d -= 1;
                } else {
                    d = 0;
                }
                e.play();
                cacing.x -= 200;
                b.remove();
            }
        }
        return d;
    }
}
