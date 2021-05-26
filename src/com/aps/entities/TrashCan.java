package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.aps.main.Game;
import com.aps.world.Camera;

public class TrashCan extends Entity
	{

		boolean Operating = true;

		private BufferedImage[] TrashCan;

		public TrashCan(int x, int y, int width, int height, BufferedImage sprite)
			{
				super(x, y, width, height, sprite);
				setIndex(1);
				setMasks(13, 10, 33, 55);

				TrashCan = new BufferedImage[2];
				TrashCan[0] = Game.spritesheet.getSprite(64, 192, 64, 64);
				TrashCan[1] = Game.spritesheet.getSprite(128, 192, 64, 64);

			}

		@Override
		public void render(Graphics g)
			{
				if (isColliding(this, Game.player) && Operating) {
					this.index = 1;
					Game.player.score += 50;
					Operating = false;
					Game.coletaveis.remove(this);
				}
				g.drawImage(TrashCan[this.index], this.getX() + -Camera.x, this.getY() - Camera.y, null);

				if (showMask) {
					g.setColor(Color.GREEN);
					g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
				}
			}

	}
