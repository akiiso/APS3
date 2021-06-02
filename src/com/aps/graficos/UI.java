package com.aps.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.aps.main.Game;

public class UI
{

    private int X = 10, Y = 10, W = 100, H = 10;

    public void render(Graphics g, int level)
    {
        /* UI Vida */
 /* Barra de vida */
        g.setColor(Color.RED);/*Vida Atual*/
        g.fillRect(X, Y, W, H);
        g.setColor(Color.GREEN);/*Vida maxima*/
        g.fillRect(X, Y, (int) ((Game.player.life / Game.player.maxlife) * W), H);
        /* Numero vida */
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 10));
        g.drawString((int) Game.player.life + "/" + (int) Game.player.maxlife, X + (W / 4), Y * 2 - 1);

        /* indicador Pontos */
        g.setColor(Color.black);
        g.fillRect(X, Y * 2, W, H);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 11));
        g.drawString("Pontos: " + Game.Score, X + 2, Y * 3 - 1);

        /* Indicador level */
        g.setColor(Color.black);
        g.fillRect(X, Y * 4 - 9, 50, H + 2);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 11));
        g.drawString("MAPA: " + level, X + 2, Y * 4);

    }

}
