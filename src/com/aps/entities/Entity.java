package com.aps.entities;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.aps.main.Game;
import com.aps.world.Camera;

public class Entity {

	public static BufferedImage TRASH_full_EN = Game.spritesheet.getSprite(64,
			192, 64, 64);
	public static BufferedImage TRASH_empty_EN = Game.spritesheet.getSprite(128,
			192, 64, 64);
	public static BufferedImage PLASTIC_BOTTLE_EN = Game.spritesheet
			.getSprite(192, 192, 64, 64);
	public static BufferedImage SODACAN_EN = Game.spritesheet.getSprite(256,
			192, 64, 64);
	public static BufferedImage PAPER_EN = Game.spritesheet.getSprite(320, 192,
			64, 64);
	public static BufferedImage Dog_EN = Game.spritesheet.getSprite(0, 320, 64,
			64);
	// public static BufferedImage TRUCK_EN =
	// Game.spritesheet.getSprite(256,448, 64,
	// 64);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected int speed = 1;
	protected int mask_X, mask_Y, mask_W, mask_H;
	protected int frames, maxFrames, index, maxIndex;

	/*
	 * "true" tornara visivel a caixa de colisao de todas as classes filhas de
	 * Entity
	 */
	protected boolean showMask = false;
	/*
	 * 
	 */

	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;

	private BufferedImage sprite;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

	}

	public void setMasks(int maskx, int masky, int maskw, int maskh) {
		this.mask_X = maskx;
		this.mask_Y = masky;
		this.mask_W = maskw;
		this.mask_H = maskh;
	}
	public void setFrames(int maxFrames) {
		this.frames = 0;
		this.maxFrames = maxFrames;

	}
	public void setIndex(int maxIndex) {
		this.index = 0;
		this.maxIndex = maxIndex;
	}

	public int getMask_X() {
		return (int) this.mask_X;
	}
	public int getMask_Y() {
		return (int) this.mask_Y;
	}
	public int getMask_W() {
		return (int) this.mask_W;
	}
	public int getMask_H() {
		return (int) this.mask_H;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void tick() {
	}

	public static boolean isColliding(Entity en1, Entity en2) {
		Rectangle en1Mask = new Rectangle(en1.getX() + en1.getMask_X(),
				en1.getY() + en1.getMask_Y(), en1.getMask_W(), en1.getMask_H());
		Rectangle en2Mask = new Rectangle(en2.getX() + en2.getMask_X(),
				en2.getY() + en2.getMask_Y(), en2.getMask_W(), en2.getMask_H());

		return en1Mask.intersects(en2Mask);
	}

	public void renderBufferedImage(Graphics g, BufferedImage[] lista,
			int index) {
		g.drawImage(lista[index], this.getX() - Camera.x,
				this.getY() - Camera.y, null);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y,
				null);
	}
}
