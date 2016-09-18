import java.util.Random;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/*
 * having some speed, start ACC and gets new speed if needed
 * sends it to Main
 */
public class Vehicle implements Runnable {
	public int speed;
	int newSpeed;
	boolean changeSpeed = false;
	int maxSpeed;
	int minSpeed;
	public float distance;
	
	EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
	SampleProvider distanceMode = us.getDistanceMode();
	
	AdaptiveCruiseControl adaptiveCruiseControl;

	Random random = new Random();
	
	public Vehicle(int minSpeed, int maxSpeed) {
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		speed = 100;
		
		
		adaptiveCruiseControl = 
				new AdaptiveCruiseControl("acc.fcl");
	}

	@Override
	public void run() {
		float[] distanceData = new float[distanceMode.sampleSize()];
		while (!Main.stop) {
			distanceMode.fetchSample(distanceData, 0);
			distance = distanceData[0]* 100.0f;
			//System.out.println(distance);
			/*
			if (!changeSpeed) {
				AdaptiveCruiseControl adaptiveCruiseControl = 
						new AdaptiveCruiseControl("acc.fcl");
				
				double change = newSpeed*(int)adaptiveCruiseControl.getNewSpeed(speed, distance, minSpeed, maxSpeed)/100.0d;
				newSpeed += (int) change;
			}
			else {
				//Sound.beep();
				System.out.println("newSpeed: " + newSpeed + " speed: " + speed);
				if (newSpeed > speed)
					speed += 1;
				else if (newSpeed < speed)
					speed -= 1;
				else {
					Sound.beep();
					newSpeed = speed;
					changeSpeed = false;
				}
			}
			*/

			//gets a percentage of change
			int change = (int)(speed*adaptiveCruiseControl.getNewSpeed(speed, distance, minSpeed, maxSpeed));
			newSpeed = speed + change;
			System.out.println("newSpeed: " + newSpeed + " speed: " + speed + " distance: " + distance + " change: " + change);
			/*
			if (newSpeed > speed)
				speed += 1;
			else if (newSpeed < speed)
				speed -= 1;
				*/
			speed = newSpeed;
			Main.vehicleSpeed = speed;
		}
	}
}