import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import lejos.hardware.Sound;
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
	
	float[][] logs;
	
	public Vehicle(int minSpeed, int maxSpeed) {
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		speed = 100;
		logs = new float[1000][2];
		initLogs();
		adaptiveCruiseControl = 
				new AdaptiveCruiseControl("acc.fcl");
	}

	@Override
	public void run() {
		float[] distanceData = new float[distanceMode.sampleSize()];
		int i = 0;
		while (!Main.stop) {
			distanceMode.fetchSample(distanceData, 0);
			distance = distanceData[0]* 100.0f;
			if (distance < 10)
				Main.movement = 3;
			//System.out.println(distance);
			
			if (!changeSpeed) {
				int change = (int)(speed*adaptiveCruiseControl.getNewSpeed(speed, distance, minSpeed, maxSpeed));
				newSpeed = speed + change;
				changeSpeed = true;
				System.out.println("newSpeed: " + newSpeed + " speed: " + speed + " distance: " + distance + " change: " + change);
			}
			else {
				//Sound.beep();
				if (newSpeed > speed)
					speed += 1;
				else if (newSpeed < speed-5)
					speed -= 5;
				else {
					speed = newSpeed;
					changeSpeed = false;
				}
			}

			//gets a percentage of change
			/*int change = (int)(speed*adaptiveCruiseControl.getNewSpeed(speed, distance, minSpeed, maxSpeed));
			newSpeed = speed + change;
			//System.out.println("newSpeed: " + newSpeed + " speed: " + speed + " distance: " + distance + " change: " + change);
			/*
			if (newSpeed > speed)
				speed += 1;
			else if (newSpeed < speed)
				speed -= 1;
				
			speed = newSpeed;
			*/
			Main.vehicleSpeed = speed;
			if (i < 1000) {
				logs[i][0] = speed;
				logs[i][1] = distance;
				i++;
			}
		}
	}
	
	public void saveLogs(){
		PrintWriter writer;
		File file = new File("logs_" + System.currentTimeMillis());
		try {
			writer = new PrintWriter(file, "UTF-8");
			for (int i = 0; i < 1000; i++) {
				writer.println(logs[i][0] + ", " + logs[i][1]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initLogs(){
		for (int i = 0; i < 1000; i++) {
			logs[i][0] = 0.0f;
			logs[i][1] = 0.0f;
		}
	}
}