package me.aars.GunsAreFun.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Param {
	
	public static Render param = loadBitmap("/me/aars/GunsAreFun/ui/unicorn.jpg/");

	private static Render loadBitmap(String string) {
		BufferedImage i;
		try {
			i = ImageIO.read(Param.class.getResource(string));
			int width = i.getWidth();
			int height = i.getHeight();
			Render result = new Render(width, height);
			i.getRGB(0, 0, width, height, result.pixels, 0, width);
			for(int c = 0; c<result.getPixelArraySize(); c++) {
				result.pixels[c] = Color.BLUE.getRGB();
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
