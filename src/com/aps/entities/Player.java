package com.aps.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.aps.main.Game;
import com.aps.world.Camera;
import com.aps.world.World;

public class Player extends Entity
{

    public boolean right, up, left, down;

    private int std_frames = 0, damageFrames = 0;
    private int std_Index = 0;
    private final int max_std_frames = 20;

    private boolean moved = false;
    public boolean isDamaged = false;


    /*Valores dos atributos do jogador*/
    public double Player_speed = 3.2;/*Velocidade de corrida*/
    public double life = 30, maxlife = 30;/* Vida/Vida Maxima */

    private final BufferedImage[] PlayerStandingRight, PlayerStandingLeft;
    private final BufferedImage[] PlayerMoveRight, PlayerMoveLeft;
    private final BufferedImage[] PlayerDamage;

    public Player(int x, int y, int width, int height, BufferedImage sprite)
    {
        super(x, y, width, height, sprite);

        setIndex(7);/* O maximo de movimentos em uma animacao */
        setFrames(5);/* Intervalo entre as animacoes */
        setMasks(20, 15, 23, 49);/* Mascara de colisao */


        // Animacao de dano recebido
        PlayerDamage = new BufferedImage[4];
        for (int i = 0; i <= 3; i++)
        {
            PlayerDamage[i] = Game.spritesheet.getSprite(256 + (i * 64), 128, 64, 64);
        }

        // Animacao de descanco
        PlayerStandingRight = new BufferedImage[2];
        PlayerStandingLeft = new BufferedImage[2];
        for (int i = 0; i <= 1; i++)
        {
            PlayerStandingRight[i] = Game.spritesheet.getSprite(0 + (i * 64), 0, 64, 64);
            PlayerStandingLeft[i] = Game.spritesheet.getSprite(0 + (i * 64), 64, 64, 64);
        }

        // Animacao de Correr
        PlayerMoveRight = new BufferedImage[7];
        /* Correr Direita */
        PlayerMoveLeft = new BufferedImage[7];
        /* Correr Esquerda */
        for (int i = 0; i < 7; i++)
        {
            PlayerMoveRight[i] = Game.spritesheet.getSprite(128 + (i * 64), 0, 64, 64);
            PlayerMoveLeft[i] = Game.spritesheet.getSprite(128 + (i * 64), 64, 64, 64);
        }
    }

    @Override
    public void tick()
    {

        moved = false;
        if (right && World.isFree((int) (x + Player_speed), this.getY()))
        {
            moved = true;
            dir = right_dir;
            x += Player_speed;
        }
        else if (left && World.isFree((int) (x - Player_speed), this.getY()))
        {
            moved = true;
            dir = left_dir;
            x -= Player_speed;
        }
        if (up && World.isFree(this.getX(), (int) (y - Player_speed)))
        {
            moved = true;
            y -= Player_speed;
        }
        else if (down && World.isFree(this.getX(), (int) (y + Player_speed)))
        {
            moved = true;
            y += Player_speed;

        }
        if (moved)
        /*Contador para a animacao com o personagem correndo*/
        {
            frames++;
            if (frames == maxFrames)
            {
                frames = 0;
                index++;
                if (index == maxIndex)
                {
                    index = 0;
                }
            }
        }
        else
        /*Contador para a animacao com o personagem parado*/
        {
            std_frames++;
            if (std_frames == max_std_frames)
            {
                std_frames = 0;
                std_Index++;
                if (std_Index == 2)
                {
                    std_Index = 0;
                }
            }
        }
        checkCollisionItem();

        /*Contador para a animacao de feedback de dano*/
        if (isDamaged)
        {
            this.damageFrames++;
            if (this.damageFrames == 10)
            {
                this.damageFrames = 0;
                isDamaged = false;
            }
        }

        if (life <= 0)
        {
            /* Implica o estado de NORMAL
            para GAME_OVER*/
            Game.gameState = "GAME_OVER";
        }

        /* Camera seguindo no eixo X */
        Camera.x = Camera.clamp(this.getX() + this.getMask_X() - (Game.WIDTH / 2), 2,
                World.WIDTH * 64 - Game.WIDTH);
        /* Camera seguindo no eixo Y */
        Camera.y = Camera.clamp(this.getY() + this.getMask_Y() - (Game.HEIGHT / 2), 2,
                World.HEIGTH * 64 - Game.HEIGHT);

    }

    /*Caso o player encoste em algum item que se encontre 
    presente na lista Game.coletaveis, vai remover o item
    da lista*/
    public void checkCollisionItem()
    {
        for (int i = 0; i < Game.coletaveis.size(); i++)
        {
            Entity atual = Game.coletaveis.get(i);
            if (atual instanceof Coletaveis)
            {
                if (Entity.isColliding(this, atual))
                {
                    Game.entities.remove(atual);
                }
            }
        }
    }

    @Override
    public void render(Graphics g)
    {

        if (moved)
        {
            /* Animacao andando para direita */
            if (dir == right_dir)
            {
                if (!isDamaged)
                {
                    renderBufferedImage(g, PlayerMoveRight, index);
                    /*Correndo para direita*/
                }
                else
                {
                    renderBufferedImage(g, PlayerDamage, 2);
                    /*Dano enquanto correndo para direita*/
                }
            }
            else
            /* Animacao Correndo para esquerda*/

            {
                if (!isDamaged)
                {
                    renderBufferedImage(g, PlayerMoveLeft, index);
                    /*Correndo para esquerda*/
                }
                else
                {
                    renderBufferedImage(g, PlayerDamage, 3);
                    /*Dano enquanto correndo para esquerda*/
                }
            }
        }

        else
        {

            /* Animacao com o player parado */
            if (dir == right_dir)/*Animacao parado olhando para  direita*/
            {
                if (!isDamaged)
                {
                    renderBufferedImage(g, PlayerStandingRight, std_Index);
                    /*Parado para direita*/
                }
                else
                {
                    renderBufferedImage(g, PlayerDamage, 0);
                    /*Dano enquanto parado para direita*/
                }
            }
            else/* Esquerda */
            {
                if (!isDamaged)
                /*Animacao parado olhando para esquerda*/
                {
                    renderBufferedImage(g, PlayerStandingLeft, std_Index);
                    /*Correndo para esquerda*/
                }
                else
                {
                    renderBufferedImage(g, PlayerDamage, 1);
                    /*Dano enquanto correndo para esquerda*/
                }
            }
        }

        /*
	 * Visualizar a mascara de colisao Funcao para debug
         */
        if (showMask)
        {
            g.setColor(Color.BLUE);
            g.fillRect(this.getX() + mask_X - Camera.x, this.getY() + mask_Y - Camera.y, mask_W, mask_H);
        }
    }

}
