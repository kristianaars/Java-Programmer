package me.aars.GunsAreFun.graphics;

import me.aars.GunsAreFun.Game;

public class Render3D extends Render {
	
	public double[] zBuffer;

	public Render3D(int W, int H) {
		super(W, H);
		zBuffer = new double[WIDTH * HEIGHT];
	}
	
	private float floorPosition = 8;
	private float cielingPosition = 8;
	private int renderDistance = 8000;
	
	public void floor(Game game) {
		double forward = game.c.z;
		double side = game.c.x;
		
		double rotation = game.c.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		
		for (int y = 0; y < HEIGHT; y++) {
			double cieling = (y - HEIGHT / 2.0) / HEIGHT;
			
			
			double z = (floorPosition / cieling);
			
			if(z < 0) {
				z = cielingPosition / -cieling;
			}
			
			for (int x = 0; x < WIDTH; x++) {
				if(z > renderDistance/2) break;
				double xDepth = (x - WIDTH / 2.0) / HEIGHT ;
				xDepth *= z;
				double xx = xDepth * cosine + z * sine;
				double yy = z * cosine - xDepth * sine;
				int xPix = (int) (xx + side);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * WIDTH] = z;
				pixels[x + y * WIDTH] = Param.param.pixels[(xPix & 7) + (yPix & 7) * 8];
			}
		}
	}
	
	public void renderDistanceLimiter() {
		for(int i = 0; i < WIDTH*HEIGHT; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance * 2 / zBuffer[i]);
			
			if(brightness < 0) brightness = 0;
			if(brightness > 255) brightness = 255;
			
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;
			
			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}
