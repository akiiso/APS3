package com.aps.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu
	{
		public String[] options =
			{
					"JOGAR", "SAIR"
			};
		public int currentOption = 0;
		public int maxOption = options.length - 1;
		public boolean up, down, enter;
		public boolean pause = false;
		private int X = (Game.WIDTH * Game.SCALE) / 2, Y = (Game.HEIGHT * Game.SCALE) / 2;


		void txtRender(Graphics g, String TXT, int X, int Y)
			{
				g.drawString(TXT, X, Y);
			}

		String menuPAUSE()
			{
				if( pause ) {
					return "RESUMIR";
				}
				else {
					return options[0];
				}
			}

		public void tick()
			{

				if( up ) {
					up = false;
					currentOption--;
					if( currentOption < 0 )
						currentOption = maxOption;
				}
				if( down ) {
					down = false;
					currentOption++;
					if( currentOption > maxOption )
						currentOption = 0;
				}
				if( enter ) {
					enter = false;
					if( options[currentOption] == options[0] ) {
						/* Iniciar Jogo */
						pause = false;
						Game.gameState = "NORMAL";
						Sound.music.loop();

					}

					else if( options[currentOption] == options[1] ) {
						/* Sair */
						System.exit(1);
					}
				}
			}

		public void render(Graphics g)
			{
				Graphics2D g2 = (Graphics2D) g;
				/* Background */
				g2.setColor(new Color(0, 0, 0, 200));
				g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
				/*
				 * 
				 */
				/* Titulo */
				g.setColor(new Color(150, 200, 150));
				g.setFont(new Font("arial", Font.BOLD, 50));
				txtRender(g, "SUPER", X / 2, Y / 3);
				txtRender(g, "LIXEIRINHO", X / 2 - 40, Y / 3 + 50);
				txtRender(g, "RUN", X / 2 + 35, Y / 3 + 100);
				/*
				 * 
				 */
				/* Opcoes */
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.PLAIN, 40));

				txtRender(g, menuPAUSE(), X / 2, Y + 50);
				txtRender(g, options[1], X / 2, Y + 100);

				if( options[currentOption] == options[0] ) {
					g.setColor(Color.GREEN);
					txtRender(g, menuPAUSE(), X / 2, Y + 50);

				}
				else if( options[currentOption] == options[1] ) {
					g.setColor(Color.GREEN);
					txtRender(g, options[1], X / 2, Y + 100);
				}
			}
	}
