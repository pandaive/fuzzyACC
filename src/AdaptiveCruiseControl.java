import net.sourceforge.jFuzzyLogic.FIS;

/*
 * it runs fuzzy controller and returns new speed
 */
public class AdaptiveCruiseControl {

	FIS fis;
	
	public AdaptiveCruiseControl(String filename) {
		// TODO Auto-generated constructor stub
		fis = FIS.load(filename);
		if (fis == null) {
			Main.stop = true;
			System.out.println("FCL file cannot be loaded");
		}
	}
	
	public double getNewSpeed(int speed, float distance,
			int minSpeed, int maxSpeed){
		fis.setVariable("speed", speed);
		fis.setVariable("distance", distance);
		fis.evaluate();
		
		double speed_change = fis.getVariable("acceleration").getValue();
		return speed_change/100.0d;
	}
}
