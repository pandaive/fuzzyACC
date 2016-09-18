
import java.io.IOException;
import lejos.hardware.Button;

/*
 * starts threads and holds movement and vehicle speed values
 */
public class Main {
	
	static final int WHITE = 6;
	static final int BLACK = 7;

	static ColorThread colorThread;
	static MovementThread movementThread;
	static Vehicle vehicle;

	/*
	 * movement
	 * 0 - straight
	 * 1 - left
	 * 2 - right
	 */
	public static int movement = 0;
	public static boolean stop = false;
	public static int vehicleSpeed = 70;
	
    public static void main(String[] args) throws InterruptedException, IOException
    {
    	FCLBuilder.createFCLFile();
    	colorThread = new ColorThread();
    	movementThread = new MovementThread();
    	vehicle = new Vehicle(0, 100);
    	Thread colorThr = new Thread(colorThread);
    	Thread movementThr = new Thread(movementThread);
    	Thread vehicleThr = new Thread(vehicle);
    	colorThr.start();
        movementThr.start();
        vehicleThr.start();
        
        while (Button.ESCAPE.isUp()) {//do nothing
        }
        stop = true;
        
      //float[] sample = new float[distanceMode.sampleSize()];
 	   //distanceMode.fetchSample(sample, 0);
 	   //System.out.printf("%.5f m\n", sample[0]);
 	   //System.out.println(sample[0]);
    	
        
    }
}
