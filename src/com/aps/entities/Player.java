package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.aps.graficos.Spritesheet;
import com.aps.main.Game;
import com.aps.world.Camera;
import com.aps.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public double speed = 2.2;
	public int score = 0;
	private int std_frames = 0, max_std_frames = 20, std_Index = 0;
	private boolean moved = false;

	private BufferedImage[] PlayerMoveRight;
	private BufferedImage[] PlayerMoveLeft;
	private BufferedImage[] PlayerStdRight;
	private BufferedImage[] PlayerStdLeft;

	private BufferedImage[] PlayerDamage;
	public boolean isDamaged = false;
	public int damageFrames = 0;

	public double life = 30, maxlife = 30;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		setIndex(7); /* O maximo de movimentos em uma animacao */
		setFrames(5);/* Intervalo entre as animacoes */
		setMasks(20, 15, 23, 49);/* Mascara de colisao */

		// Animacao de dano recebido
		PlayerDamage = new BufferedImage[4];
		for (int i = 0; i <= 3; i++) {
			PlayerDamage[i] = Game.spritesheet.getSprite(256 + (i * 64), 128,
					64, 64);
		}

		// Animacao de descanco
		// Descanco Direita
		PlayerStdRight = new BufferedImage[3];
		PlayerStdRight[0] = Game.spritesheet.getSprite(0, 0, 64, 64);
		PlayerStdRight[1] = Game.spritesheet.getSprite(64, 0, 64, 64);
		PlayerStdRight[2] = PlayerStdRight[0];
		// Descanco Esquerda
		PlayerStdLeft = new BufferedImage[3];
		PlayerStdLeft[0] = Game.spritesheet.getSprite(0, 64, 64, 64);
		PlayerStdLeft[1] = Game.spritesheet.getSprite(64, 64, 64, 64);
		PlayerStdLeft[2] = PlayerStdLeft[0];

		// Animacao de Correr
		/* Correr Direita */
		PlayerMoveRight = new BufferedImage[7];
		for (int i = 0; i < 7; i++) {
			PlayerMoveRight[i] = Game.spritesheet.getSprite(128 + (i * 64), 0,
					64, 64);
		}
		/* Correr Esquerda */
		PlayerMoveLeft = new BufferedImage[7];
		for (int i = 0; i < 7; i++) {
			PlayerMoveLeft[i] = Game.spritesheet.getSprite(128 + (i * 64), 64,
					64, 64);
		}

	}

	@Override
	public void tick() {

		moved = false;

		if (right && World.isFree((int) (x + speed), this.getY())) {

			moved = true;
			dir = right_dir;
			x += speed;

		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			y += speed;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index == maxIndex) {
					index = 0;
				}
			}
		} else {
			std_frames++;
			if (std_frames == max_std_frames) {
				std_frames = 0;
				std_Index++;
				if (std_Index == 2) {
					std_Index = 0;
				}
			}
		}

		checkCollisionItem();
		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		if(life<=0) {
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 64, 64,Game.spritesheet.getSprite(0, 0, 64, 64));
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
			return;
		}
		
		/* Camera seguindo no eixo X */
		Camera.x = Camera.clamp(
				this.getX() + this.getMask_X() - (Game.WIDTH / 2), 2,
				World.WIDTH * 64 - Game.WIDTH);
		/* Camera seguindo no eixo Y */
		Camera.y = Camera.clamp(
				this.getY() + this.getMask_Y() - (Game.HEIGHT / 2) - 30, 2,
				World.HEIGTH * 64 - Game.HEIGHT);

	}

	public void checkCollisionItem() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Coletaveis) {
				if (Entity.isColliding(this, atual)) {
					Game.enemies.remove(atual);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (moved) {
			/* Animacao andando para direita */
			if (dir == right_dir) {
				if (!isDamaged) {
					renderBufferedImage(g, PlayerMoveRight, index);
				} else {
					renderBufferedImage(g, PlayerDamage, 2);
				}
			} else
			/* Animacao andando para esquerda */
			{
				if (!isDamaged) {
					renderBufferedImage(g, PlayerMoveLeft, index);
				} else {
					renderBufferedImage(g, PlayerDamage, 3);
				}
			}
		} else {
			/* Animacao Parado */

			if (dir == right_dir)/* Direita */
			{
				if (!isDamaged) {
					renderBufferedImage(g, PlayerStdRight, std_Index);
				} else {
					renderBufferedImage(g, PlayerDamage, 0);
				}
			} else/* Esquerda */
			{
				if (!isDamaged) {
					renderBufferedImage(g, PlayerStdLeft, std_Index);
				} else {
					renderBufferedImage(g, PlayerDamage, 1);
				}
			}
		}

		/*
		 * Visualizar a mascara de colisao Funcao para debug
		 */
		if (showMask) {
			g.setColor(Color.BLUE);
			g.fillRect(this.getX() + mask_X - Camera.x,
					this.getY() + mask_Y - Camera.y, mask_W, mask_H);
		}
	}

}
