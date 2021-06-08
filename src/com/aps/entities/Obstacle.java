package com.aps.entities;

import com.aps.main.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.aps.world.Camera;

public class Obstacle extends Entity
{

    public boolean BlockingPlayer;
    private final BufferedImage[] Obstacles;

    public Obstacle(int x, int y, int width, int height, BufferedImage sprite)
    {
        super(x, y, width, height, sprite);
        setMasks(21, 15, 24, 42);
        //setMasks(10, -10, 46, 80);

        Obstacles = new BufferedImage[1];
        Obstacles[0] = Game.spritesheet.getSprite(512, 256, 64, 64);

    }

    @Override
    public void tick()
    {
        blocking();
    }

    public void blocking()
    {
        if (isColliding(this, Game.player))
        {
            if (Game.player.getX() < this.getX())
            {
                Game.player.canMove = false;
                Game.player.x -= Game.player.Player_speed;
                Game.player.canMove = true;
            }
            else if (Game.player.getX() > this.getX())
            {
                Game.player.canMove = false;
                Game.player.x += Game.player.Player_speed;
                Game.player.canMove = true;

            }
            if (Game.player.getY() < this.getY())
            {
                Game.player.canMove = false;
                Game.player.y -= Game.player.Player_speed;
                Game.player.canMove = true;
            }
            else if (Game.player.getY() > this.getY())
            {
                Game.player.canMove = false;
                Game.player.y += Game.player.Player_speed;
                Game.player.canMove = true;
            }

        }

    }

    @Override
    public void render(Graphics g)
    {

        renderBufferedImage(g, Obstacles, 0);
        if (showMask)
        {
            g.setColor(Color.orange);
            g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
        }
    }
}
