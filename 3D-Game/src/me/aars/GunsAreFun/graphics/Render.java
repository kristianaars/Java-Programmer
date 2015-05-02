package me.aars.GunsAreFun.graphics;

import me.aars.GunsAreFun.Display;

public class Render {
	public final int WIDTH;
	public final int HEIGHT;
	public final int[] pixels;

	public Render(int W, int H) {
		this.WIDTH = W;
		this.HEIGHT = H;
		this.pixels = new int[WIDTH * HEIGHT];
	}

	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.HEIGHT; y++) {
			int yPix = y + yOffset;
			
			//Check Render Bounds
			if (yPix < 0 || yPix >= Display.H) {
				continue;
			}

			for (int x = 0; x < render.WIDTH; x++) {
				int xPix = x + xOffset;
				
				//Check Render Bounds
				if (xPix < 0 || xPix >= Display.W) {
					continue;
				}
				
				
				//pixels[xPix + yPix * WIDTH] = render.pixels[x + y * render.WIDTH];;
				int alpha = render.pixels[x + y * render.WIDTH];
				if (alpha > 0) 
					pixels[xPix + yPix * WIDTH] = alpha;
			}
		}
	}
	
	public int getPixelArraySize() {
		return pixels.length;
	}

}
