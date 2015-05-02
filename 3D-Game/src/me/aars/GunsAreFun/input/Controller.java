package me.aars.GunsAreFun.input;

public class Controller {

	public double x, z, rotation, xa, za, xMove, zMove, rotationa = 0;
	
	private double movementSpeed = 1;

	public boolean turnRight;
	public boolean turnLeft;

	public void tick(boolean forward, boolean backward, boolean right, boolean left, boolean shift) {
		double rotationSpeed = 0.01;
		xMove = 0;
		zMove = 0;
		movementSpeed = 1;
		
		if(shift) {
			movementSpeed = 1.5;
		}
		
		if(forward) {
			zMove += movementSpeed;
		}
		
		if(backward) {
			zMove -= movementSpeed;
		}
		
		if(left) {
			xMove -= movementSpeed;
		}
		
		if(right) {
			xMove += movementSpeed;
		}
		
		if(turnLeft) {
			rotationa -= rotationSpeed;
		}
		
		if(turnRight) {
			rotationa += rotationSpeed;
		}
		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation));
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation));
		
		x += xa;
		z += za;
		
		xa *= 0.1;
		za *= 0.1;
		
		rotation += rotationa;
		rotationa *= 0.8;
	}
}
