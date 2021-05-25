package com.aps.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aps.entities.Coletaveis;
import com.aps.entities.Enemy;
import com.aps.entities.Entity;
import com.aps.entities.TrashCan;
import com.aps.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGTH;
	public static final int TILE_SIZE = 64;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGTH = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];

			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0,
					map.getWidth());

			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];

					FloorTile upFloor = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_upFLOOR);
					FloorTile upFloorSP = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_upFLOORsp);
					FloorTile midFloor = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_midFLOOR);
					FloorTile downFloor = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_downFLOOR);
					FloorTile downFloorSP = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_downFLOORsp);
					FloorTile upWall = new FloorTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_upWall);
					WallTile downWall = new WallTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_downWall);
					WallTile SkyWall = new WallTile(xx * TILE_SIZE,
							yy * TILE_SIZE, Tile.TILE_Dummy);

					Enemy en = new Enemy(xx * TILE_SIZE, (yy * TILE_SIZE),
							TILE_SIZE, TILE_SIZE, Entity.Dog_EN);

					TrashCan tc_up = new TrashCan(xx * TILE_SIZE,
							yy * (TILE_SIZE + -3), TILE_SIZE, TILE_SIZE,
							Entity.TRASH_full_EN);
					TrashCan tc_down = new TrashCan(xx * TILE_SIZE,
							yy * (TILE_SIZE), TILE_SIZE, TILE_SIZE,
							Entity.TRASH_full_EN);
					Coletaveis clt = new Coletaveis(xx * TILE_SIZE,
							yy * (TILE_SIZE), TILE_SIZE, TILE_SIZE,
							Entity.SODACAN_EN);

					switch (pixelAtual) {
						/*
						 * caso a variavel xx do contador seja multiplo de 5, o
						 * tile sera igual a um tile variante
						 */

						/* Chao Superior */
						case 0xFFFFD800 : {
							if (xx % 5 == 0) {
								tiles[xx + (yy * WIDTH)] = upFloorSP;
								break;
							} else {
								tiles[xx + (yy * WIDTH)] = upFloor;
								break;
							}
						}

						/* Chao Inferior */
						case 0xFFFF6A00 : {
							if (xx % 5 == 0) {
								tiles[xx + (yy * WIDTH)] = downFloorSP;
								break;
							} else {
								tiles[xx + (yy * WIDTH)] = downFloor;
								break;
							}
						}

						/* Rua */
						case 0xFFB6FF00 : {
							tiles[xx + (yy * WIDTH)] = midFloor;
							break;
						}

						/* Parede Superior */
						case 0xFFFFFFFF : {
							tiles[xx + (yy * WIDTH)] = upWall;
							break;
						}
						/* Parede Inferior */
						case 0xFFA0A0A0 : {
							tiles[xx + (yy * WIDTH)] = downWall;
							break;
						}
						/* Player Spawn */
						case 0xFF0094FF : {
							Game.player.setX(xx * TILE_SIZE);
							Game.player.setY(yy * TILE_SIZE);

							if (pixels[(xx - 1) + (yy * WIDTH)] == 0xFFFFD800
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFFD800) {
								tiles[xx + (yy * WIDTH)] = upFloor;
								break;
							} else if (pixels[(xx - 1)
									+ (yy * WIDTH)] == 0xFFFF6A00
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFF6A00) {
								tiles[xx + (yy * WIDTH)] = downFloor;
								break;
							} else {
								tiles[xx + (yy * WIDTH)] = midFloor;
								break;
							}
						}

						/* Inimigo Spawn */
						case 0xFFFF0000 : {

							Game.entities.add(en);
							Game.enemies.add(en);

							if (pixels[(xx - 1) + (yy * WIDTH)] == 0xFFFFD800
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFFD800) {
								tiles[xx + (yy * WIDTH)] = upFloor;
								break;
							} else if (pixels[(xx - 1)
									+ (yy * WIDTH)] == 0xFFFF6A00
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFF6A00) {
								tiles[xx + (yy * WIDTH)] = downFloor;
								break;
							} else {
								tiles[xx + (yy * WIDTH)] = midFloor;
								break;
							}
						}
						/* Leixeira */
						case 0xFF00FF90 : {
							if (pixels[(xx - 1) + (yy * WIDTH)] == 0xFFFFD800
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFFD800) {
								Game.entities.add(tc_up);
								tiles[xx + (yy * WIDTH)] = upFloor;
								break;
							} else if (pixels[(xx - 1)
									+ (yy * WIDTH)] == 0xFFFF6A00
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFF6A00) {
								Game.entities.add(tc_down);
								tiles[xx + (yy * WIDTH)] = downFloor;
								break;
							} else {
								Game.entities.add(tc_down);
								tiles[xx + (yy * WIDTH)] = midFloor;
								break;
							}
						}
						/* Coletaveis */
						case 0xFFFF00FF : {
							Game.entities.add(clt);
							if (pixels[(xx - 1) + (yy * WIDTH)] == 0xFFFFD800
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFFD800) {
								tiles[xx + (yy * WIDTH)] = upFloor;
								break;

							} else if (pixels[(xx - 1)
									+ (yy * WIDTH)] == 0xFFFF6A00
									|| pixels[(xx + 1)
											+ (yy * WIDTH)] == 0xFFFF6A00) {

								tiles[xx + (yy * WIDTH)] = downFloor;
								break;
							} else {

								tiles[xx + (yy * WIDTH)] = midFloor;
								break;
							}
						}
						default :
							tiles[xx + (yy * WIDTH)] = SkyWall;
							break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile
				|| tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile
				|| tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
				|| tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
	}

	public void render(Graphics g) {
		int xstart = (Camera.x >> 5);
		int ystart = (Camera.y >> 5);
		int xfinal = xstart + (Game.WIDTH >> 5);
		int yfinal = ystart + (Game.HEIGHT >> 5);

		for (int xx = 0; xx <= xfinal; xx++) {
			for (int yy = 0; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGTH) {
					continue;
				}
				Tile tile = tiles[(xx) + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
