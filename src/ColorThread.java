import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

/*
 * scans a color below a car and sets movement option
 * reacts if a car goes too far left or right
 * movement thread makes movements
 */
public class ColorThread implements Runnable {
	
	public int colorRight = -1;
	public int colorLeft = -1;
	
	EV3ColorSensor colorSensorRIGHT = new EV3ColorSensor(SensorPort.S2);
	EV3ColorSensor colorSensorLEFT= new EV3ColorSensor(SensorPort.S3);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!Main.stop) { 
			colorRight = colorSensorRIGHT.getColorID();
			colorLeft = colorSensorLEFT.getColorID();
			if (colorRight == Main.WHITE && colorLeft == Main.WHITE)
				Main.movement = 0;
			else if (colorLeft != Main.WHITE)
				Main.movement = 2;
			else if (colorRight != Main.WHITE)
				Main.movement = 1;
			else
				Main.movement = 0;
		}
	}

}
