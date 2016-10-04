import lejos.hardware.motor.Motor;

/*
 * based on movement mode, runs the engines
 * movement mode is changed by color thread
 */
public class MovementThread implements Runnable{

	/*
	 * 0 - stop
	 * 1 - forward
	 * 2 - right
	 * 3 - left
	 */
	public int movement = 0;
	public int speedB = 600;
	public int speedC = speedB * 13/20;

	@Override
	public void run() {
		while (!Main.stop) {
			speedB = Main.vehicleSpeed*6;
			speedC = speedB * 16/20;
			switch (Main.movement) {
			case 0:
				Motor.B.setSpeed(speedB);
				Motor.C.setSpeed(speedC);
				Motor.B.forward();
				Motor.C.forward();
				break;
			case 1:
				Motor.B.setSpeed(speedB);
				Motor.C.setSpeed(speedB);
				Motor.B.forward();
				Motor.C.forward();
				break;
			case 2:
				Motor.B.setSpeed(speedB);
				Motor.C.setSpeed(speedC*(3.0f/4.0f));
				Motor.B.forward();
				Motor.C.forward();
				break;
			case 3:
				Motor.B.stop();
				Motor.C.stop();
				break;
			}
		}
	}
}
