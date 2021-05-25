package com.aps.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.aps.main.Game;

public class Tile
{
	
	public static BufferedImage TILE_upFLOOR = Game.spritesheet.getSprite(0, 192, 64, 64);
	 public static BufferedImage TILE_upFLOORsp = Game.spritesheet.getSprite(128, 256, 64, 64);
	 
    public static BufferedImage TILE_downFLOOR = Game.spritesheet.getSprite(0, 256, 64, 64);
    public static BufferedImage TILE_downFLOORsp = Game.spritesheet.getSprite(256, 256, 64, 64);
    
    public static BufferedImage TILE_midFLOOR = Game.spritesheet.getSprite(192, 256, 64, 64);
   
    public static BufferedImage TILE_Dummy = Game.spritesheet.getSprite(64, 256, 64, 64);
    
    public static BufferedImage TILE_upWall = Game.spritesheet.getSprite(320, 256, 64, 64);
    public static BufferedImage TILE_downWall = Game.spritesheet.getSprite(384, 256, 64, 64);
    
    

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite)
    {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g)
    {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
