package app;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.Point;

public class Principal extends JFrame{
	int width=640;
	int height=480;
	Point snake;
	Point manzana;
	
	boolean gameOver=false;
	
	ArrayList<Point> lista=new ArrayList<>();
	
	int widthPoint=10;
	int heightPoint=10;
	
	int direccion=KeyEvent.VK_LEFT;
	
	long frecuencia=60;
	
	ImagenSnake imagenSnake;
	
	public Principal() {
		setTitle("Snake");
		setSize(width,height);
		
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-width/2, dim.height/2-height/2);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		Teclas teclas=new Teclas();
		this.addKeyListener(teclas);
		
		snake=new Point(width/2, height/2);
		
		startGame();
		
		imagenSnake =new ImagenSnake();
		this.getContentPane().add(imagenSnake);
		
		setVisible(true);
		
		Momento momento=new Momento();
		Thread trid=new Thread(momento);
		trid.start();
	}

	public void startGame() {
		manzana=new Point(200,200);
		snake=new Point(width/2, height/2);
		
		lista=new ArrayList<Point>();
		lista.add(snake);
		crearComida();
	}
	
	public void crearComida() {
		Random dado=new Random();
		
		manzana.x=dado.nextInt(width)+5;
		if((manzana.x%5)>0) {
			manzana.x=manzana.x-(manzana.x%5);
		}if(manzana.x<5) {
			manzana.x=manzana.x+10;
		}
		manzana.y=dado.nextInt(height)+5;
		if((manzana.y%5)>0) {
			manzana.y=manzana.y-(manzana.y%5);
		}if(manzana.y<5) {
			manzana.y=manzana.y+10;
		}
	}
	
	public static void main(String[] args) throws Exception{
		Principal snake=new Principal();

	}
	public void actualizar() {
		imagenSnake.repaint();
		
		lista.add(0,new Point(snake.x,snake.y));
		lista.remove(lista.size()-1);
		for(int i=1;i<lista.size()-1;i++) {
			Point punto=lista.get(i);
			if(snake.x==punto.x && snake.y==punto.y) {
				gameOver=true;
			}
		}
		if(snake.x>(manzana.x-10)&&snake.x<(manzana.x+10)&&snake.y>(manzana.y-10)&&snake.y<(manzana.y+10)) {
			lista.add(0,new Point(snake.x,snake.y));
			crearComida();
		}
	}
	
	
	public class ImagenSnake extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(0,0,255));
			g.fillRect(snake.x, snake.y, widthPoint, heightPoint);
			for(int i=0;i<lista.size();i++) {
				Point point=(Point)lista.get(i);
				g.fillRect(point.x,point.y,widthPoint,heightPoint);
			}
	
			
			g.setColor(new Color(255,0,0));
			g.fillRect(manzana.x, manzana.y, widthPoint, heightPoint);
			
			if(gameOver) {
				g.drawString("GAME OVER", 200, 320);
			}
		}
		
	}

	public class Teclas extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}else if(e.getKeyCode()==KeyEvent.VK_UP) {
				if(direccion!=KeyEvent.VK_DOWN) {
					direccion=KeyEvent.VK_UP;
				}
				
				
			}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				if(direccion!=KeyEvent.VK_UP) {
					direccion=KeyEvent.VK_DOWN;
				}
				
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				if(direccion!=KeyEvent.VK_RIGHT) {
					direccion=KeyEvent.VK_LEFT;
				}
				
			}else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(direccion!=KeyEvent.VK_LEFT) {
					direccion=KeyEvent.VK_RIGHT;
				}
				
			}
		}
		
		
	}
	
	public class Momento extends Thread{
		long last=0;
		public void run() {
			while(true) {
				if((java.lang.System.currentTimeMillis()-last)> frecuencia) {
					if(!gameOver) {
					if(direccion==KeyEvent.VK_UP) {
						snake.y=snake.y-heightPoint;
						if(snake.y<0) {
							snake.y=height-heightPoint;
						}
						
						if(snake.y>height) {
							snake.y=0;
						}
					}else if(direccion==KeyEvent.VK_DOWN) {
						snake.y=snake.y+heightPoint;
						if(snake.y<0) {
							snake.y=height-heightPoint;
						}
						
						if(snake.y>height) {
							snake.y=0;
						}
						}else if(direccion==KeyEvent.VK_LEFT) {
							snake.x=snake.x-widthPoint;
							if(snake.y<0) {
								snake.x=width-widthPoint;
							}
							
							if(snake.y>height) {
								snake.x=0;
							}
							}else if(direccion==KeyEvent.VK_RIGHT) {
								snake.x=snake.x+widthPoint;
								if(snake.y<0) {
									snake.x=width-widthPoint;
								}
								
								if(snake.y>height) {
									snake.x=0;
								}
								}
					}
					actualizar();
					last=java.lang.System.currentTimeMillis();
				}
			}
		}
	}
}
