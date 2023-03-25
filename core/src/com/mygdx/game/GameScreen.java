package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends Object implements Screen {
    final Crossy game;

    Texture farmer1, bebekImage, belalangImage, cacingImage, tikusImage, background,
            traktorImage, sawiImage, terongImage, wortelImage, kolImage, lobakImage;
    Sound collect, chew, win, lose; //wav
    Music farmMusic; //mp3
    OrthographicCamera camera;
    Rectangle character, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15,
            s16, s17, s18, s19, s20, s21, s22, s23, s24, s25, s26, s27, s28, s29, s30, s31,
            s32, s33, s34, s35, s36, s37, s38, s39, s40, s41, s42, s43, s44, s45, s46, s47,
            s48, s49, s50, s51, s52, s53, s54, s55, s56, s57, s58, s59, s60, s61, s62, s63,
            s64, s65, s66, s67, s68, s69, s70, s71, s72;
    Array<Rectangle> ducks, worm, grasshopper, mouse, tractor; //untuk menampung hewan" + traktor yang sudah dispawn

    //STATIC variables --> supaya bisa diakses dari class lain
    static long lastDropDuck, lastDropWorm, lastDropMouse, lastDropGH, lastDropTractor; //utk menampung waktu terakhir sejak objek di spawn
    static int cropsCollected; //utk mencatat jumlah crops yg terkumpul
    static int lives; //hidup dari karakter
    static boolean pause, hasBeenRun; //utk mengatur mekanisme pause & end
    long timer; //utk mengatur mekanisme end


    //memanggil class
    Duck clssBebek = new Duck();
    Worm clssCacing = new Worm();
    Mouse clssTikus = new Mouse();
    Grasshopper clssBelalang = new Grasshopper();
    Tractor clssTraktor = new Tractor();

    public GameScreen(final Crossy game) { //constructor to start the game
        this.game = game;
        pause = false;
        hasBeenRun = false;

        //load images pake file handler
        farmer1 = new Texture(Gdx.files.internal("farmer64.png"));
        bebekImage = new Texture(Gdx.files.internal("bebek.png"));
        belalangImage = new Texture(Gdx.files.internal("belalang.png"));
        cacingImage = new Texture(Gdx.files.internal("cacing.png"));
        tikusImage = new Texture(Gdx.files.internal("tikuskanan.png"));
        traktorImage = new Texture(Gdx.files.internal("traktor.png"));
        background = new Texture(Gdx.files.internal("background.png"));
        sawiImage = new Texture(Gdx.files.internal("sawi.png"));
        terongImage = new Texture(Gdx.files.internal("terong.png"));
        wortelImage = new Texture(Gdx.files.internal("wortel.png"));
        kolImage = new Texture(Gdx.files.internal("kol.png"));
        lobakImage = new Texture(Gdx.files.internal("lobak.png"));

        //load audio
        win = Gdx.audio.newSound(Gdx.files.internal("cling.wav"));
        lose = Gdx.audio.newSound(Gdx.files.internal("game-over.wav"));
        chew = Gdx.audio.newSound(Gdx.files.internal("chewy.wav"));
        collect = Gdx.audio.newSound(Gdx.files.internal("bloop.wav"));
        farmMusic = Gdx.audio.newMusic(Gdx.files.internal("farm.mp3"));
        farmMusic.setLooping(true); //loop lagu

        //supaya yg ditampilkan area game itu trs
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1080);

        //instance karakter
        character = new Rectangle();
        character.x = 540 - 32;
        character.y = 20; //20 above the bottom screen
        character.width = 64;
        character.height = 64;

        instanceSayur();

        cropsCollected = 0;
        lives = 1;

        //utk menampung objek yg sudah di spawn -> akan dimasukkan ke iter
        ducks = new Array<Rectangle>();
        worm = new Array<Rectangle>();
        mouse = new Array<Rectangle>();
        grasshopper = new Array<Rectangle>();
        tractor = new Array<Rectangle>();

        //SPAWN OBJECTS
        random(); //dari Class Object
        spawn(objek, koordinatY); //utk hewan
        clssTraktor.spawnTractor(tractor, TimeUtils.nanoTime()); //utk traktor
    }

    private void spawn(ArrayList<Integer> a, ArrayList <Integer> b){ //ngeluarin hewan
        for (int n = 0; n < a.size(); n++) {
            if (a.get(n) == 0) {
                Rectangle bebek = new Rectangle();
                bebek.x = 0; //selalu muncul dari kiri
                bebek.y = b.get(n);
                bebek.width = 64;
                bebek.height = 64;
                if (ducks.size <= 2) {
                    ducks.add(bebek);
                    lastDropDuck = TimeUtils.nanoTime();
                }
                else {
                    //durasi rilis objek
                    if (TimeUtils.nanoTime() - lastDropDuck > Long.parseLong("50000000")) {
                        ducks.add(bebek);
                        lastDropDuck = TimeUtils.nanoTime();
                    }
                }
            } else if (a.get(n) == 1){
                Rectangle belalang = new Rectangle();
                belalang.x = 0; //selalu muncul dari kiri
                belalang.y = b.get(n);
                belalang.width = 64;
                belalang.height = 64;
                if (grasshopper.size <= 2) {
                    grasshopper.add(belalang);
                    lastDropGH = TimeUtils.nanoTime();
                }
                else {
                    //durasi rilis objek
                    if (TimeUtils.nanoTime() - lastDropGH > Long.parseLong("10000000")) {
                        grasshopper.add(belalang);
                        lastDropGH = TimeUtils.nanoTime();
                    }
                }
            } else if (a.get(n) == 2){
                Rectangle tikus = new Rectangle();
                tikus.x = 1080; //selalu muncul dari kanan
                tikus.y = b.get(n);
                tikus.width = 64;
                tikus.height = 64;
                if (mouse.size <= 2) {
                    mouse.add(tikus);
                    lastDropMouse = TimeUtils.nanoTime();
                }
                else {
                    //durasi rilis objek
                    if (TimeUtils.nanoTime() - lastDropDuck > Long.parseLong("10000000")) {
                        mouse.add(tikus);
                        lastDropMouse = TimeUtils.nanoTime();
                    }
                }
            } else {
                Rectangle cacing = new Rectangle();
                cacing.x = 1080; //selalu muncul dari kanan
                cacing.y = b.get(n);
                cacing.width = 64;
                cacing.height = 64;
                if (worm.size <= 2) {
                    worm.add(cacing);
                    lastDropWorm = TimeUtils.nanoTime();
                }
                else {
                    //durasi rilis objek
                    if (TimeUtils.nanoTime() - lastDropWorm > Long.parseLong("7000000")) {
                        worm.add(cacing);
                        lastDropWorm= TimeUtils.nanoTime();
                    }
                }
            }
        }

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //renders characters
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.font.draw(game.batch, "Crops Collected: " + cropsCollected, 50, 1080);
        game.batch.draw(farmer1, character.x, character.y);
        drawSayur(); //men-draw objek sayur di koordinatnya masing-masing

        //selama array masih ada isi, maka batch men-draw gambar objek tersebut
        if (!ducks.isEmpty()) {
            for (Rectangle bebek : ducks) {
                game.batch.draw(bebekImage, bebek.x, bebek.y);
            }
        }
        if (!worm.isEmpty()) {
            for (Rectangle cacing : worm) {
                game.batch.draw(cacingImage, cacing.x, cacing.y);
            }
        }
        if (!mouse.isEmpty()) {
            for (Rectangle tikus : mouse) {
                game.batch.draw(tikusImage, tikus.x, tikus.y);
            }
        }
        if (!grasshopper.isEmpty()) {
            for (Rectangle belalang : grasshopper) {
                game.batch.draw(belalangImage, belalang.x, belalang.y);
            }
        }
        if (!tractor.isEmpty()) {
            for (Rectangle traktor : tractor) {
                game.batch.draw(traktorImage, traktor.x, traktor.y);
            }
        }
        game.batch.end();


        //utk geser pake keyboard
        if (!pause) { //jika PAUSE, karakter tidak bisa bergerak
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) character.x -= 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) character.x += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) character.y += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) character.y -= 100 * Gdx.graphics.getDeltaTime();

            //agar character tetap di dalam screen
            if (character.x < 0) character.x = 0;
            if (character.x > 1080 - 64) character.x = 1080 - 64;
            if (character.y < 20) character.y = 20;
            if (character.y > 1080 - 64) character.y = 1080 - 64;

            //kondisi agar spawn dilakukan kembali jika jumlah objek yang ada terlalu sedikit
            if (ducks.size < 3 || worm.size < 5 || mouse.size < 3 || grasshopper.size < 5)
                spawn(objek, koordinatY);

            if (tractor.size < 2){
                clssTraktor.spawnTractor(tractor, lastDropTractor);
            }
        }
        //supaya binatang jalan dengan kecepatan konstan
        Iterator<Rectangle> iterDuck = ducks.iterator();
        Iterator<Rectangle> iterWorm = worm.iterator();
        Iterator<Rectangle> iterMouse = mouse.iterator();
        Iterator<Rectangle> iterGrasshopper = grasshopper.iterator();
        Iterator<Rectangle> iterTractor = tractor.iterator();


        if (!pause) {
            while(iterDuck.hasNext()){
                cropsCollected = clssBebek.move(iterDuck, character, cropsCollected, chew);
            }

            while(iterWorm.hasNext()){
                cropsCollected = clssCacing.move(iterWorm, character, cropsCollected, chew);
            }

            while(iterMouse.hasNext()){
                cropsCollected = clssTikus.move(iterMouse, character, cropsCollected, chew);
            }

            while(iterGrasshopper.hasNext()){
                cropsCollected = clssBelalang.move(iterGrasshopper, character, cropsCollected, chew);
            }

            while (iterTractor.hasNext()){
                lives = clssTraktor.move(iterTractor, character, lives, lose);
            }

            collectCrops();
        }

        //PAUSE MECHANISM
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                if (!pause) pause();
                else resume();
        }

        //END MECHANISM
        if (character.y >= 1080-80){ //WIN -> ketika sudah mencapai garis finish
            if (!hasBeenRun) {
                timer = TimeUtils.nanoTime();
                farmMusic.setVolume(0.5f);
                win.play();
                hasBeenRun = true;
            }
            gameOver();
        }

        if (lives == 0) { //LOSE -> jika tertabrak oleh traktor
            if (!hasBeenRun) {
                timer = TimeUtils.nanoTime();
                farmMusic.setVolume(0.5f);
                hasBeenRun = true;
            }
            gameOver();
        }
    }

    public void gameOver(){
        pause();
        if (TimeUtils.nanoTime() - timer >= 1500000000f) { //tunggu 1.5s
            game.setScreen(new GameOver(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        //playback starts when the screen is shown
        farmMusic.play();
    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bebekImage.dispose();
        belalangImage.dispose();
        tikusImage.dispose();
        cacingImage.dispose();
        traktorImage.dispose();
        farmer1.dispose();
        collect.dispose();
        chew.dispose();
        farmMusic.dispose();
        win.dispose();
        lose.dispose();
    }

    public void instanceSayur(){
        s1 = new Rectangle();
        s1.x = 160;
        s1.y = 152;
        s2 = new Rectangle();
        s2.x = 210;
        s2.y = 152;
        s3 = new Rectangle();
        s3.x = 260;
        s3.y = 152;
        s4 = new Rectangle();
        s4.x = 160;
        s4.y = 202;
        s5 = new Rectangle();
        s5.x = 210;
        s5.y = 202;
        s6 = new Rectangle();
        s6.x = 260;
        s6.y = 202;
        s7 = new Rectangle();
        s7.x = 480;
        s7.y = 152;
        s8 = new Rectangle();
        s8.x = 530;
        s8.y = 152;
        s9 = new Rectangle();
        s9.x = 580;
        s9.y = 152;
        s10 = new Rectangle();
        s10.x = 480;
        s10.y = 202;
        s11 = new Rectangle();
        s11.x = 530;
        s11.y = 202;
        s12 = new Rectangle();
        s12.x = 580;
        s12.y = 202;
        s13 = new Rectangle();
        s13.x = 800;
        s13.y = 152;
        s14 = new Rectangle();
        s14.x = 850;
        s14.y = 152;
        s15 = new Rectangle();
        s15.x = 900;
        s15.y = 152;
        s16 = new Rectangle();
        s16.x = 800;
        s16.y = 202;
        s17 = new Rectangle();
        s17.x = 850;
        s17.y = 202;
        s18 = new Rectangle();
        s18.x = 900;
        s18.y = 202;

        s19 = new Rectangle();
        s19.x = 90;
        s19.y = 472;
        s20 = new Rectangle();
        s20.x = 140;
        s20.y = 472;
        s21 = new Rectangle();
        s21.x = 190;
        s21.y = 472;
        s22 = new Rectangle();
        s22.x = 90;
        s22.y = 522;
        s23 = new Rectangle();
        s23.x = 140;
        s23.y = 522;
        s24 = new Rectangle();
        s24.x = 190;
        s24.y = 522;

        s25 = new Rectangle();
        s25.x = 90;
        s25.y = 600;
        s26 = new Rectangle();
        s26.x = 140;
        s26.y = 600;
        s27 = new Rectangle();
        s27.x = 190;
        s27.y = 600;
        s28 = new Rectangle();
        s28.x = 90;
        s28.y = 650;
        s29 = new Rectangle();
        s29.x = 140;
        s29.y = 650;
        s30 = new Rectangle();
        s30.x = 190;
        s30.y = 650;

        s31 = new Rectangle();
        s31.x = 345;
        s31.y = 472;
        s32 = new Rectangle();
        s32.x = 395;
        s32.y = 472;
        s33 = new Rectangle();
        s33.x = 445;
        s33.y = 472;
        s34 = new Rectangle();
        s34.x = 345;
        s34.y = 522;
        s35 = new Rectangle();
        s35.x = 395;
        s35.y = 522;
        s36 = new Rectangle();
        s36.x = 445;
        s36.y = 522;

        s37 = new Rectangle();
        s37.x = 345;
        s37.y = 600;
        s38 = new Rectangle();
        s38.x = 395;
        s38.y = 600;
        s39 = new Rectangle();
        s39.x = 445;
        s39.y = 600;
        s40 = new Rectangle();
        s40.x = 345;
        s40.y = 650;
        s41 = new Rectangle();
        s41.x = 395;
        s41.y = 650;
        s42 = new Rectangle();
        s42.x = 445;
        s42.y = 650;

        s43 = new Rectangle();
        s43.x = 600;
        s43.y = 472;
        s44 = new Rectangle();
        s44.x = 650;
        s44.y = 472;
        s45 = new Rectangle();
        s45.x = 700;
        s45.y = 472;
        s46 = new Rectangle();
        s46.x = 600;
        s46.y = 522;
        s47 = new Rectangle();
        s47.x = 650;
        s47.y = 522;
        s48 = new Rectangle();
        s48.x = 700;
        s48.y = 522;

        s49 = new Rectangle();
        s49.x = 600;
        s49.y = 600;
        s50 = new Rectangle();
        s50.x = 650;
        s50.y = 600;
        s51 = new Rectangle();
        s51.x = 700;
        s51.y = 600;
        s52 = new Rectangle();
        s52.x = 600;
        s52.y = 650;
        s53 = new Rectangle();
        s53.x = 650;
        s53.y = 650;
        s54 = new Rectangle();
        s54.x = 700;
        s54.y = 650;

        s55 = new Rectangle();
        s55.x = 855;
        s55.y = 472;
        s56 = new Rectangle();
        s56.x = 905;
        s56.y = 472;
        s57 = new Rectangle();
        s57.x = 955;
        s57.y = 472;
        s58 = new Rectangle();
        s58.x = 855;
        s58.y = 522;
        s59 = new Rectangle();
        s59.x = 905;
        s59.y = 522;
        s60 = new Rectangle();
        s60.x = 955;
        s60.y = 522;

        s61 = new Rectangle();
        s61.x = 855;
        s61.y = 600;
        s62 = new Rectangle();
        s62.x = 905;
        s62.y = 600;
        s63 = new Rectangle();
        s63.x = 955;
        s63.y = 600;
        s64 = new Rectangle();
        s64.x = 855;
        s64.y = 650;
        s65 = new Rectangle();
        s65.x = 905;
        s65.y = 650;
        s66 = new Rectangle();
        s66.x = 955;
        s66.y = 650;

        s67 = new Rectangle();
        s67.x = 480;
        s67.y = 855;
        s68 = new Rectangle();
        s68.x = 530;
        s68.y = 855;
        s69 = new Rectangle();
        s69.x = 580;
        s69.y = 855;
        s70 = new Rectangle();
        s70.x = 480;
        s70.y = 905;
        s71 = new Rectangle();
        s71.x = 530;
        s71.y = 905;
        s72 = new Rectangle();
        s72.x = 580;
        s72.y = 905;
    }

    public void drawSayur(){
        game.batch.draw(sawiImage, s1.x, s1.y);
        game.batch.draw(terongImage, s2.x, s2.y);
        game.batch.draw(kolImage, s3.x, s3.y);
        game.batch.draw(lobakImage, s4.x, s4.y);
        game.batch.draw(wortelImage, s5.x, s5.y);
        game.batch.draw(lobakImage, s6.x, s6.y);

        game.batch.draw(terongImage, s7.x, s7.y);
        game.batch.draw(kolImage, s8.x, s8.y);
        game.batch.draw(lobakImage, s9.x, s9.y);
        game.batch.draw(wortelImage, s10.x, s10.y);
        game.batch.draw(sawiImage, s11.x, s11.y);
        game.batch.draw(terongImage, s12.x, s12.y);

        game.batch.draw(wortelImage, s13.x, s13.y);
        game.batch.draw(kolImage, s14.x, s14.y);
        game.batch.draw(lobakImage, s15.x, s15.y);
        game.batch.draw(sawiImage, s16.x, s16.y);
        game.batch.draw(kolImage, s17.x, s17.y);
        game.batch.draw(sawiImage, s18.x, s18.y);

        game.batch.draw(wortelImage, s19.x, s19.y);
        game.batch.draw(terongImage, s20.x, s20.y);
        game.batch.draw(wortelImage, s21.x, s21.y);
        game.batch.draw(lobakImage, s22.x, s22.y);
        game.batch.draw(wortelImage, s23.x, s23.y);
        game.batch.draw(sawiImage, s24.x, s24.y);

        game.batch.draw(sawiImage, s25.x, s25.y);
        game.batch.draw(wortelImage, s26.x, s26.y);
        game.batch.draw(lobakImage, s27.x, s27.y);
        game.batch.draw(lobakImage, s28.x, s28.y);
        game.batch.draw(kolImage, s29.x, s29.y);
        game.batch.draw(sawiImage, s30.x, s30.y);

        game.batch.draw(wortelImage, s31.x, s31.y);
        game.batch.draw(terongImage, s32.x, s32.y);
        game.batch.draw(sawiImage, s33.x, s33.y);
        game.batch.draw(sawiImage, s34.x, s34.y);
        game.batch.draw(terongImage, s35.x, s35.y);
        game.batch.draw(lobakImage, s36.x, s36.y);

        game.batch.draw(kolImage, s37.x, s37.y);
        game.batch.draw(sawiImage, s38.x, s38.y);
        game.batch.draw(lobakImage, s39.x, s39.y);
        game.batch.draw(wortelImage, s40.x, s40.y);
        game.batch.draw(lobakImage, s41.x, s41.y);
        game.batch.draw(kolImage, s42.x, s42.y);

        game.batch.draw(wortelImage, s43.x, s43.y);
        game.batch.draw(kolImage, s44.x, s44.y);
        game.batch.draw(lobakImage, s45.x, s45.y);
        game.batch.draw(lobakImage, s46.x, s46.y);
        game.batch.draw(wortelImage, s47.x, s47.y);
        game.batch.draw(sawiImage, s48.x, s48.y);

        game.batch.draw(wortelImage, s49.x, s49.y);
        game.batch.draw(terongImage, s50.x, s50.y);
        game.batch.draw(lobakImage, s51.x, s51.y);
        game.batch.draw(terongImage, s52.x, s52.y);
        game.batch.draw(wortelImage, s53.x, s53.y);
        game.batch.draw(sawiImage, s54.x, s54.y);

        game.batch.draw(wortelImage, s55.x, s55.y);
        game.batch.draw(lobakImage, s56.x, s56.y);
        game.batch.draw(sawiImage, s57.x, s57.y);
        game.batch.draw(lobakImage, s58.x, s58.y);
        game.batch.draw(kolImage, s59.x, s59.y);
        game.batch.draw(lobakImage, s60.x, s60.y);

        game.batch.draw(wortelImage, s61.x, s61.y);
        game.batch.draw(terongImage, s62.x, s62.y);
        game.batch.draw(lobakImage, s63.x, s63.y);
        game.batch.draw(lobakImage, s64.x, s64.y);
        game.batch.draw(kolImage, s65.x, s65.y);
        game.batch.draw(sawiImage, s66.x, s66.y);

        game.batch.draw(wortelImage, s67.x, s67.y);
        game.batch.draw(sawiImage, s68.x, s68.y);
        game.batch.draw(wortelImage, s69.x, s69.y);
        game.batch.draw(lobakImage, s70.x, s70.y);
        game.batch.draw(kolImage, s71.x, s71.y);
        game.batch.draw(wortelImage, s72.x, s72.y);
    }

    public void collectCrops() {
        if (s1.overlaps(character)){ cropsCollected += 15; collect.play(); s1.setX(1081); s1.setY(1081); }
        if (s2.overlaps(character)){ cropsCollected += 5; collect.play(); s2.setX(1081); s2.setY(1081); }
        if (s3.overlaps(character)){ cropsCollected += 5; collect.play(); s3.setX(1081); s3.setY(1081); }
        if (s4.overlaps(character)){ cropsCollected += 10; collect.play(); s4.setX(1081); s4.setY(1081); }
        if (s5.overlaps(character)){ cropsCollected += 20; collect.play(); s5.setX(1081); s5.setY(1081); }
        if (s6.overlaps(character)){ cropsCollected += 10; collect.play(); s6.setX(1081); s6.setY(1081); }
        if (s7.overlaps(character)){ cropsCollected += 5; collect.play(); s7.setY(1081); s7.setY(1081); }
        if (s8.overlaps(character)){ cropsCollected += 5; collect.play(); s8.setX(1081); s8.setY(1081); }
        if (s9.overlaps(character)){ cropsCollected += 10; collect.play(); s9.setX(1081); s9.setY(1081); }
        if (s10.overlaps(character)){ cropsCollected += 20; collect.play(); s10.setX(1081); s10.setY(1081); }
        if (s11.overlaps(character)){ cropsCollected += 15; collect.play(); s11.setX(1081); s11.setY(1081); }
        if (s12.overlaps(character)){ cropsCollected += 55; collect.play(); s12.setX(1081); s12.setY(1081); }
        if (s13.overlaps(character)){ cropsCollected += 20; collect.play(); s13.setX(1081); s13.setY(1081); }
        if (s14.overlaps(character)){ cropsCollected += 5; collect.play(); s14.setX(1081); s14.setY(1081); }
        if (s15.overlaps(character)){ cropsCollected += 10; collect.play(); s15.setX(1081); s15.setY(1081); }
        if (s16.overlaps(character)){ cropsCollected += 15; collect.play(); s16.setX(1081); s16.setY(1081); }
        if (s17.overlaps(character)){ cropsCollected += 5; collect.play(); s17.setX(1081); s17.setY(1081); }
        if (s18.overlaps(character)){ cropsCollected += 15; collect.play(); s18.setX(1081); s18.setY(1081); }
        if (s19.overlaps(character)){ cropsCollected += 20; collect.play(); s19.setX(1081); s19.setY(1081); }
        if (s20.overlaps(character)){ cropsCollected += 5; collect.play(); s20.setX(1081); s20.setY(1081); }
        if (s21.overlaps(character)){ cropsCollected += 20; collect.play(); s21.setX(1081); s21.setY(1081); }
        if (s22.overlaps(character)){ cropsCollected += 10; collect.play(); s22.setX(1081); s22.setY(1081); }
        if (s23.overlaps(character)){ cropsCollected += 20; collect.play(); s23.setX(1081); s23.setY(1081); }
        if (s24.overlaps(character)){ cropsCollected += 15; collect.play(); s24.setX(1081); s24.setY(1081); }
        if (s25.overlaps(character)){ cropsCollected += 15; collect.play(); s25.setX(1081); s25.setY(1081); }
        if (s26.overlaps(character)){ cropsCollected += 20; collect.play(); s26.setX(1081); s26.setY(1081); }
        if (s27.overlaps(character)){ cropsCollected += 10; collect.play(); s27.setX(1081); s27.setY(1081); }
        if (s28.overlaps(character)){ cropsCollected += 10; collect.play(); s28.setX(1081); s28.setY(1081); }
        if (s29.overlaps(character)){ cropsCollected += 5; collect.play(); s29.setX(1081); s29.setY(1081); }
        if (s30.overlaps(character)){ cropsCollected += 15; collect.play(); s30.setX(1081); s30.setY(1081); }
        if (s31.overlaps(character)){ cropsCollected += 20; collect.play(); s31.setX(1081); s31.setY(1081); }
        if (s32.overlaps(character)){ cropsCollected += 5; collect.play(); s32.setX(1081); s32.setY(1081); }
        if (s33.overlaps(character)){ cropsCollected += 15; collect.play(); s33.setX(1081); s33.setY(1081); }
        if (s34.overlaps(character)){ cropsCollected += 15; collect.play(); s34.setX(1081); s34.setY(1081); }
        if (s35.overlaps(character)){ cropsCollected += 5; collect.play(); s35.setX(1081); s35.setY(1081); }
        if (s36.overlaps(character)){ cropsCollected += 10; collect.play(); s36.setX(1081); s36.setY(1081); }
        if (s37.overlaps(character)){ cropsCollected += 5; collect.play(); s37.setX(1081); s37.setY(1081); }
        if (s38.overlaps(character)){ cropsCollected += 15; collect.play(); s38.setX(1081); s38.setY(1081); }
        if (s39.overlaps(character)){ cropsCollected += 10; collect.play(); s39.setX(1081); s39.setY(1081); }
        if (s40.overlaps(character)){ cropsCollected += 20; collect.play(); s40.setX(1081); s40.setY(1081); }
        if (s41.overlaps(character)){ cropsCollected += 10; collect.play(); s41.setX(1081); s41.setY(1081); }
        if (s42.overlaps(character)){ cropsCollected += 5; collect.play(); s42.setX(1081); s42.setY(1081); }
        if (s43.overlaps(character)){ cropsCollected += 20; collect.play(); s43.setX(1081); s43.setY(1081); }
        if (s44.overlaps(character)){ cropsCollected += 5; collect.play(); s44.setX(1081); s44.setY(1081); }
        if (s45.overlaps(character)){ cropsCollected += 10; collect.play(); s45.setX(1081); s45.setY(1081); }
        if (s46.overlaps(character)){ cropsCollected += 10; collect.play(); s46.setX(1081); s46.setY(1081); }
        if (s47.overlaps(character)){ cropsCollected += 20; collect.play(); s47.setX(1081); s47.setY(1081); }
        if (s48.overlaps(character)){ cropsCollected += 15; collect.play(); s48.setX(1081); s48.setY(1081); }
        if (s49.overlaps(character)){ cropsCollected += 20; collect.play(); s49.setX(1081); s49.setY(1081); }
        if (s50.overlaps(character)){ cropsCollected += 5; collect.play(); s50.setX(1081); s50.setY(1081); }
        if (s51.overlaps(character)){ cropsCollected += 10; collect.play(); s51.setX(1081); s51.setY(1081); }
        if (s52.overlaps(character)){ cropsCollected += 5; collect.play(); s52.setX(1081); s52.setY(1081); }
        if (s53.overlaps(character)){ cropsCollected += 20; collect.play(); s53.setX(1081); s53.setY(1081); }
        if (s54.overlaps(character)){ cropsCollected += 15; collect.play(); s54.setX(1081); s54.setY(1081); }
        if (s55.overlaps(character)){ cropsCollected += 20; collect.play(); s55.setX(1081); s55.setY(1081); }
        if (s56.overlaps(character)){ cropsCollected += 10; collect.play(); s56.setX(1081); s56.setY(1081); }
        if (s57.overlaps(character)){ cropsCollected += 15; collect.play(); s57.setX(1081); s57.setY(1081); }
        if (s58.overlaps(character)){ cropsCollected += 10; collect.play(); s58.setX(1081); s58.setY(1081); }
        if (s59.overlaps(character)){ cropsCollected += 5; collect.play(); s59.setX(1081); s59.setY(1081); }
        if (s60.overlaps(character)){ cropsCollected += 10; collect.play(); s60.setX(1081); s60.setY(1081); }
        if (s61.overlaps(character)){ cropsCollected += 20; collect.play(); s61.setX(1081); s61.setY(1081); }
        if (s62.overlaps(character)){ cropsCollected += 5; collect.play(); s62.setX(1081); s62.setY(1081); }
        if (s63.overlaps(character)){ cropsCollected += 10; collect.play(); s63.setX(1081); s63.setY(1081); }
        if (s64.overlaps(character)){ cropsCollected += 10; collect.play(); s64.setX(1081); s64.setY(1081); }
        if (s65.overlaps(character)){ cropsCollected += 5; collect.play(); s65.setX(1081); s65.setY(1081); }
        if (s66.overlaps(character)){ cropsCollected += 15; collect.play(); s66.setX(1081); s66.setY(1081); }
        if (s67.overlaps(character)){ cropsCollected += 20; collect.play(); s67.setX(1081); s67.setY(1081); }
        if (s68.overlaps(character)){ cropsCollected += 15; collect.play(); s68.setX(1081); s68.setY(1081); }
        if (s69.overlaps(character)){ cropsCollected += 20; collect.play(); s69.setX(1081); s69.setY(1081); }
        if (s70.overlaps(character)){ cropsCollected += 10; collect.play(); s70.setX(1081); s70.setY(1081); }
        if (s71.overlaps(character)){ cropsCollected += 5; collect.play(); s71.setX(1081); s71.setY(1081); }
        if (s72.overlaps(character)){ cropsCollected += 20; collect.play(); s72.setX(1081); s72.setY(1081); }
    }
}
