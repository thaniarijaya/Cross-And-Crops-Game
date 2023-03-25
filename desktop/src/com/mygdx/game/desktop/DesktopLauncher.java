package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Crossy;

/**
 * Proyek Game PBO
 * Kelompok 10:
 * Grace Natasha - C14200021
 * Josephine Michelle - C14200049
 * Ariella Thania Rijaya - C14200158
 * Kelas: PBO-B
 */

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cross and Crop"; //Judul
		config.width = 1080; //Lebar layar
		config.height = 1080; //Tinggi layar
		new LwjglApplication(new Crossy(), config); //memanggil class Crossy
	}
}
