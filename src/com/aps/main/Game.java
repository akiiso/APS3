package com.aps.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.aps.entities.Enemy;
import com.aps.entities.Entity;
import com.aps.entities.Player;

import com.aps.graficos.Spritesheet;
import com.aps.graficos.UI;
import com.aps.world.World;

public class Game extends Canvas implements Runnable, KeyListener
	{
		private static final long serialVersionUID = 1L;
		public static JFrame frame;
		private Thread thread;
		private boolean isRunning = true;
		public static int WIDTH = 200;
		public static int HEIGHT = 320;
		private final int SCALE = 2;

		private int CUR_LEVEL = 1, MAX_LEVEL = 3;
		private BufferedImage image;

		public static List<Entity> entities;
		public static List<Enemy> enemies;
		public static List<Entity> coletaveis;
		public static Spritesheet spritesheet;

		public static World world;
		public static Player player;
		public static Random rand;
		public UI ui;


		public Game()
			{
				rand = new Random();
				addKeyListener(this);
				setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
				initFrame();

				// Inicializando objetos.
				ui = new UI();
				image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				entities = new ArrayList<Entity>();
				enemies = new ArrayList<Enemy>();
				coletaveis = new ArrayList<Entity>();
				spritesheet = new Spritesheet("/spritesheet.png");
				player = new Player(0, 0, 64, 64, spritesheet.getSprite(0, 0, 64, 64));
				entities.add(player);
				world = new World("/level1.png");

			}

		public void initFrame()
			{
				frame = new JFrame("SuperGariRun");
				frame.add(this);
				frame.setResizable(false);
				frame.pack();
				frame.setAutoRequestFocus(isRunning);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}

		public synchronized void start()
			{
				thread = new Thread(this);
				isRunning = true;
				thread.start();
			}

		public synchronized void stop()
			{
				isRunning = false;
				try {
					thread.join();

				} catch (Exception e) {

				}

			}

		public static void main(String args[])
			{
				Game game = new Game();
				game.start();
			}

		public void tick()
			{
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();

				}
				if( coletaveis.size() == 0 ) {/* Avancar de level */
					CUR_LEVEL++;
					if( CUR_LEVEL > MAX_LEVEL ) {
						CUR_LEVEL = 1;
					}
					String newWorld = "level"+CUR_LEVEL +".png";
					World.restartGame(newWorld);
				}
			}

		public void render()
			{
				BufferStrategy bs = this.getBufferStrategy();
				if( bs == null ) {
					this.createBufferStrategy(3);
					return;
				}
				Graphics g = image.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, WIDTH, HEIGHT);

				/* Renderização do jogo */

				/* Mundo */
				world.render(g);

				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.render(g);
				}
				for (int i = 0; i < coletaveis.size(); i++) {
					Entity e = coletaveis.get(i);
					e.render(g);
				}

				ui.render(g,CUR_LEVEL);
				/***/
				g.dispose();
				g = bs.getDrawGraphics();
				g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
				bs.show();
			}

		public void run()
			{
				long LastTime = System.nanoTime();
				double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks;
				double delta = 0;
				int frames = 0;
				double timer = System.currentTimeMillis();
				requestFocus();
				while (isRunning) {
					Long now = System.nanoTime();
					delta += (now - LastTime) / ns;
					LastTime = now;
					if( delta >= 1 ) {
						tick();
						render();
						frames++;
						delta--;
					}

					if( System.currentTimeMillis() - timer >= 1000 ) {
						System.out.println("FPS: " + frames);
						frames = 0;
						timer += 1000;

					}
				}

				stop();
			}

		@Override
		public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void keyPressed(KeyEvent e)
			{
				if( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ) {
					// player vai para direita
					player.right = true;
				}
				else if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ) {
					// player vai para esquerda
					player.left = true;
				}
				if( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ) {
					// player vai pular
					player.up = true;
				}
				else if( e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ) {
					// player vai agachar
					player.down = true;
				}
			}

		@Override
		public void keyReleased(KeyEvent e)
			{
				if( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ) {

					player.right = false;
				}
				else if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ) {

					player.left = false;
				}
				if( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ) {

					player.up = false;
				}
				else if( e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ) {

					player.down = false;
				}

			}
	}
