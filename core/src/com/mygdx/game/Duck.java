package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Duck extends Object {
    @Override
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e) {
        if (!GameScreen.pause) {
            Rectangle bebek = b.next();
            bebek.x += 100 * Gdx.graphics.getDeltaTime();

            //kalau keluar dari screen, hapus dari iter
            if (bebek.x + 64 > 1080) {
                b.remove();
            }
            //kalau framenya character overlap sama bebek
            if (bebek.overlaps(c)) {
                if (d >= 5) {
                    d -= 5;
                } else {
                    d = 0;
                }
                e.play();
                bebek.x -= 200;
                b.remove();
            }
        }
        return d;
    }
}