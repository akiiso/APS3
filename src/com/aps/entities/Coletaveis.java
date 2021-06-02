package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.aps.main.Game;
import com.aps.main.Sound;
import com.aps.world.Camera;

public class Coletaveis extends Entity
{

    boolean Operating = true;

    private BufferedImage[] Coletaveis;

    public Coletaveis(int x, int y, int width, int height, BufferedImage sprite)
    {
        super(x, y, width, height, sprite);

        /*Gerar um numero aleatorio de 0 a 2*/
 /*Cada numero representa uma sprite diferente*/
        index = Game.rand.nextInt(3);

        switch (index)
        {
            case 0:
            {
                setMasks(12, 30, 30, 16);
                break;
            }
            case 1:
            {
                setMasks(20, 35, 14, 21);
                break;
            }
            case 2:
                setMasks(7, 30, 35, 35);
                {
                    break;
                }

        }
        Coletaveis = new BufferedImage[3];
        Coletaveis[0] = Game.spritesheet.getSprite(192, 192, 64, 64);/*Sprite Garafa*/
        Coletaveis[1] = Game.spritesheet.getSprite(256, 192, 64, 64);/*Sprite lata*/
        Coletaveis[2] = Game.spritesheet.getSprite(320, 192, 64, 64);/*Sprite papel*/

    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(Coletaveis[this.index], this.getX() + -Camera.x, this.getY() - Camera.y, null);

        /*Caso colida com o player o objeto ira
        desaparecer e somar pontos ao player*/
        if (isColliding(this, Game.player))
        {
            Sound.collect.play();
            Game.Score += 10;
            Operating = false;
            Game.coletaveis.remove(this);
        }

        if (showMask)
        {
            g.setColor(Color.pink);
            g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
        }
    }

}
