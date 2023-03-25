package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Mouse extends Object {
    @Override
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e){
        if (!GameScreen.pause) {
            Rectangle tikus = b.next();
            tikus.x -= 60 * Gdx.graphics.getDeltaTime();

            //kalau keluar dari screen, hapus dari iter
            if (tikus.x - 64 > 1080) {
                b.remove();
            }

            if (tikus.overlaps(c)) {
                if (d >= 3) {
                    d -= 3;
                } else {
                    d = 0;
                }
                e.play();
                tikus.x -= 200;
                b.remove();
            }
        }
        return d;
    }
}
