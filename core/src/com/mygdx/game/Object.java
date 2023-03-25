package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

abstract class Object {
    ArrayList<Integer> koordinatY = new ArrayList<>();
    ArrayList<Integer> objek = new ArrayList<>();

    int koordinat = 88;

    Object() {
        this.koordinatY = koordinatY;
        this.objek = objek;
        this.koordinat = koordinat;
    }

    public ArrayList<Integer> getKoordinatY() {
        return koordinatY;
    }

    public void setKoordinatY(ArrayList<Integer> koordinatY) {
        this.koordinatY = koordinatY;
    }

    public ArrayList<Integer> getObjek() {
        return objek;
    }

    public void setObjek(ArrayList<Integer> objek) {
        this.objek = objek;
    }

    public int getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(int koordinat) {
        this.koordinat = koordinat;
    }

    public void random(){
        for (int n = 0; n < 3; n++){
            if (n != 2) {
                for (int j = 0; j < 4; j++) {
                    koordinatY.add(koordinat);
                    objek.add(j);
                    koordinat += 64;
                }

            } else {
                for (int j = 0; j < 3; j++) {
                    koordinatY.add(koordinat);
                    objek.add(j);
                    koordinat += 64;
                }
            }
            koordinat += 128;
        }
        Collections.shuffle(objek);
    }
    public int move(Iterator<Rectangle> b, Rectangle c, int d, Sound e){
        Rectangle unk = b.next();
        unk.x += 0 * Gdx.graphics.getDeltaTime();

        //kalau keluar dari screen, hapus dari iter
        if (unk.x + 64 > 1080) {
            b.remove();
        }
        //kalau frame character overlap dgn objek
        if(unk.overlaps(c)) {
            d--;
            e.play();
            unk.x -= 200;
            b.remove();
        }
        return d;
    }

}
