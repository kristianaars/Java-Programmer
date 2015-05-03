
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class Component extends JComponent implements Runnable{
	
	private boolean isRunning = false;
	
	public Color color;
	
	private int WIDTH = 1920;
	private int HEIGHT = 1080;
	
	private float R = 255;
	private float G = 0;
	private float B = 0;
	
	private Image drawImg;
	private Graphics gr;

	private boolean animate;
	
	public Component(int width, int height, Color color) {
		WIDTH = width;
		HEIGHT = height;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		start();
		setColor(color);
	}
	
	public void start() {
		if(!isRunning) {	
			Thread t = new Thread(this);
			t.start();
			isRunning = true;
		}
	}
	
	void stop() {
		if(isRunning) isRunning = false;
	}
	
	void paintToScreen() {
		Graphics g;
		try {
			g = getGraphics();
			if(g!=null&&drawImg!=null) 
				g.drawImage(drawImg, 0, 0, null);
			g.dispose();
		} catch(Exception e) {
			
		}
	}
	
	private final static float animationSpeed = (float) 5;
	private final static float dragSpeed = (float)0.1F;
	
	void screenRender() {
		while(drawImg==null) {
			drawImg = createImage(WIDTH, HEIGHT);
		}
		
		gr = drawImg.getGraphics();
		
		if(!animate) {
			gr.setColor(color);
			gr.fillRect(0, 0, WIDTH, HEIGHT);
			return;
		}
		
		float R = this.R;
		float G = this.G;
		float B = this.B;
		
		for(int i = WIDTH; i<WIDTH; i+=1) {
			
			if (R == 255 && G < 255 && B == 0)
				G = G + dragSpeed;
			if (R > 0 && G == 255 && B == 0)
				R = R - dragSpeed;
			if (R == 0 && G == 255 && B < 255)
				B = B + dragSpeed;
			if (R == 0 && G > 0 && B == 255)
				G = G - dragSpeed;
			if (R < 255 && G == 0 && B == 255)
				R = R + dragSpeed;
			if (R == 255 && G == 0 && B > 0)
				B = B - dragSpeed;
			if (R == 255 && G < 255 && B == 0)
				G = G + dragSpeed;
			
			if(R>255) R = 255; else if(R<0) R = 0;
			if(G>255) G = 255; else if(G<0) G = 0;
			if(B>255) B = 255; else if(B<0) B = 0; 
			
			
			//System.out.println("R: "+R+" G: "+G+" B: "+B+" I: "+i);
			
			gr.setColor(new Color((int)R, (int)G, (int)B));
			gr.drawRect(i, 0, 2, HEIGHT);
		}
		
		R = this.R;
		G = this.G;
		B = this.B;
		
		for (int i = WIDTH; i >= 0; i-=1) {

			if (R == 255 && G < 255 && B == 0)
				G = G + dragSpeed;
			if (R > 0 && G == 255 && B == 0)
				R = R - dragSpeed;
			if (R == 0 && G == 255 && B < 255)
				B = B + dragSpeed;
			if (R == 0 && G > 0 && B == 255)
				G = G - dragSpeed;
			if (R < 255 && G == 0 && B == 255)
				R = R + dragSpeed;
			if (R == 255 && G == 0 && B > 0)
				B = B - dragSpeed;
			if (R == 255 && G < 255 && B == 0)
				G = G + dragSpeed;

			if (R > 255)R = 255;else if (R < 0)	R = 0;
			if (G > 255)G = 255;else if (G < 0)G = 0;
			if (B > 255)B = 255;else if (B < 0)B = 0;
			
			gr.setColor(new Color((int) R, (int) G, (int) B));
			gr.drawRect(i, 0, 2, HEIGHT);
		}
	}
	
	void update() {
		if (R == 255 && G < 255 && B == 0)
			G = G + animationSpeed;
		if (R > 0 && G == 255 && B == 0)
			R = R - animationSpeed;
		if (R == 0 && G == 255 && B < 255)
			B = B + animationSpeed;
		if (R == 0 && G > 0 && B == 255)
			G = G - animationSpeed;
		if (R < 255 && G == 0 && B == 255)
			R = R + animationSpeed;
		if (R == 255 && G == 0 && B > 0)
			B = B - animationSpeed;
		if (R == 255 && G < 255 && B == 0)
			G = G + animationSpeed;
		
		if (R > 255)R = 255;else if (R < 0)	R = 0;
		if (G > 255)G = 255;else if (G < 0)G = 0;
		if (B > 255)B = 255;else if (B < 0)B = 0;

	}
	
	@Override
	public void run() {
		while(isRunning) {	
			update();
			screenRender();
			paintToScreen();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setColor(Color c) {
		if(c.getRGB()==-15584170) animate = true;
		else color = c;
	}

}
