package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Tractor extends Object {
    public void spawnTractor(Array<Rectangle> a, long b) {
        ArrayList<Integer> angka = new ArrayList<>();
        angka.add(1);
        angka.add(2);
        Collections.shuffle(angka);

        Rectangle traktor = new Rectangle();
        traktor.x = 0; //selalu muncul dari kiri
        if (angka.get(0) == 1) {
            traktor.y = 376;
        } else {
            traktor.y = 760;
        }
        traktor.width = 64;
        traktor.height = 64;
        if (a.size <= 2) {
            a.add(traktor);
            GameScreen.lastDropTractor = TimeUtils.nanoTime();
        } else {
            if (TimeUtils.nanoTime() - b > Long.parseLong("1000000000000000")) {
                a.add(traktor);
                GameScreen.lastDropTractor = TimeUtils.nanoTime();
            }
        }


    }
    @Override
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e){
        if (!GameScreen.pause) {
            Rectangle traktor = b.next();
            traktor.x += 100 * Gdx.graphics.getDeltaTime();

            //kalau keluar dari screen, hapus dari iter
            if (traktor.x + 64 > 1080) {
                b.remove();
            }
            //kalau framenya traktor overlap dgn character
            if (traktor.overlaps(c)) {
                d--;
                e.play();
                traktor.x -= 200;
                b.remove();
            }
        }
        return d;
    }
}
