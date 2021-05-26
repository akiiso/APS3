package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.aps.main.Game;
import com.aps.main.Sound;
import com.aps.world.Camera;
import com.aps.world.World;

public class Enemy extends Entity
	{

		private BufferedImage[] EnemyMoveRight;
		private BufferedImage[] EnemyMoveLeft;


		public Enemy(int x, int y, int width, int height, BufferedImage sprite)
			{
				super(x, y, width, height, null);

				setFrames(5);
				setIndex(5);
				setMasks(15, 20, 28, 19);
				speed = 2;

				EnemyMoveRight = new BufferedImage[5];
				for (int i = 0; i < maxIndex; i++) {
					EnemyMoveRight[i] = Game.spritesheet.getSprite(i * 64, 320, 64, 64);
				}

				EnemyMoveLeft = new BufferedImage[5];
				for (int i = 0; i < maxIndex; i++) {
					EnemyMoveLeft[i] = Game.spritesheet.getSprite(i * 64, 384, 64, 64);
				}
			}

		public void tick()
			{
				if( this.isCollidingWithPlayer() == false ) {
					if( (Game.player.getX() + 128 > this.getX()) && (Game.player.getX() - 128 < this.getX()) ) {
						if( Game.rand.nextInt(100) < 80 ) {
							if( (int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
									&& !isColliding((int) (x + speed), this.getY()) )
							{
								dir = right_dir;
								x += speed;
							}
							else if( (int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
									&& !isColliding((int) (x - speed), this.getY()) )
							{
								dir = left_dir;
								x -= speed;
							}
							if( (int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
									&& !isColliding(this.getX(), (int) (y + speed)) )
							{
								y += speed;
							}
							else if( (int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
									&& !isColliding(this.getX(), (int) (y - speed)) )
							{
								y -= speed;
							}

							frames++;
							if( frames == maxFrames ) {
								frames = 0;
								index++;
								if( index == maxIndex )
									index = 0;
							}
						}
					}
				}
				else {
					if( Game.rand.nextInt(100) < 10 ) {
						Game.player.life -= 2;
						Game.player.isDamaged = true;
						Sound.hit.play();

					}
				}

			}

		/*
		 * Verificar se Enemy esta colidindo com Player
		 */
		public boolean isCollidingWithPlayer()
			{
				Rectangle enemyCurrent = new Rectangle(this.getX() + mask_X, this.getY() + mask_Y, mask_W, mask_H);

				Rectangle player = new Rectangle(Game.player.getX() + Game.player.mask_X,
						Game.player.getY() + Game.player.mask_Y, Game.player.mask_W, Game.player.mask_H);

				return enemyCurrent.intersects(player);
			}

		/*
		 * Verificar se Enemy esta colidindo com outro Enemy
		 */
		public boolean isColliding(int xnext, int ynext)
			{
				Rectangle enemyCurrent = new Rectangle(xnext + mask_X, ynext + mask_Y, mask_W, mask_H);

				for (int i = 0; i < Game.enemies.size(); i++) {
					Enemy e = Game.enemies.get(i);
					if( e == this )
						continue;
					Rectangle targetEnemy = new Rectangle(e.getX() + mask_X, e.getY() + mask_Y, mask_W, mask_H);
					if( enemyCurrent.intersects(targetEnemy) ) {
						return true;
					}
				}
				return false;
			}

		public void render(Graphics g)
			{

				/* Animacao andando para direita */
				if( dir == right_dir ) {
					g.drawImage(EnemyMoveRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				/* Animacao andando para esquerda */
				else

				{
					g.drawImage(EnemyMoveLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}

				/*
				 * Visualizar a mascara de colisao para estar podendo ajustar mesma
				 */

				if( showMask ) {
					g.setColor(Color.RED);
					g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
				}
			}
	}
