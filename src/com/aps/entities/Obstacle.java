package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.aps.world.Camera;

public class Obstacle extends Entity
	{
		private BufferedImage[] Obstacles;

		public Obstacle(int x, int y, int width, int height, BufferedImage sprite)
			{
				super(x, y, width, height, sprite);
				setMasks(8, 10, 47, 55);

				Obstacles = new BufferedImage[1];



			}
		@Override
		public void render(Graphics g)
			{

				renderBufferedImage(g, Obstacles, 0);
				if (showMask) {
					g.setColor(Color.orange);
					g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
				}
			}
	}
