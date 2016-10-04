
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * creates a fcl file for ACC to use
 */
public class FCLBuilder {

	private FCLBuilder() {
		// TODO Auto-generated constructor stub
		//createFCLFile();
	}
	
	public static void createFCLFile(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("acc.fcl", "UTF-8");
			writer.println("FUNCTION_BLOCK acc");
			writer.println();
			
			writer.println("VAR_INPUT");
			writer.println("    speed : REAL;");
			writer.println("    distance : REAL;");
			writer.println("END_VAR");
			writer.println();
			
			writer.println("VAR_OUTPUT");
			writer.println("    acceleration : REAL;");
			writer.println("END_VAR");
			writer.println();
			
			writer.println("FUZZIFY speed");
			writer.println("    TERM low := (-10, 1) (30, 0) ;");
			writer.println("    TERM medium := (0, 0) (50, 1) (80, 0) ;");
			writer.println("    TERM high := (50, 0) (100,1) ;");
			writer.println("END_FUZZIFY");
			writer.println();
			
			writer.println("FUZZIFY distance");
			writer.println("    TERM small := (0, 1) (10, 1) (20, 0) ;");
			writer.println("    TERM medium := (10, 0) (25, 1) (45, 0) ;");
			writer.println("    TERM big := (35, 0) (70,1) ;");
			writer.println("END_FUZZIFY");
			writer.println();
			
			writer.println("DEFUZZIFY acceleration");
			writer.println("    TERM slowDown := (-80,1) (0,0);");
			writer.println("    TERM keep := (-5,0) (0,1) (5,0);");
			writer.println("    TERM accelerate := (0,0) (80,1);");
			writer.println("    METHOD : COG;");
			writer.println("    DEFAULT := 0;");
			writer.println("END_DEFUZZIFY");
			writer.println();
			
			writer.println("RULEBLOCK No1");
			writer.println("    AND : MIN;");
			writer.println("	ACT : MIN;");
			writer.println("    ACCU : MAX;");
			writer.println();
			
			writer.println("    RULE 1 : IF distance IS small AND speed IS low THEN acceleration IS keep;");
			writer.println("    RULE 2 : IF distance IS small AND speed IS medium THEN acceleration IS slowDown;");
			writer.println("    RULE 3 : IF distance IS small AND speed IS high THEN acceleration IS slowDown;");
			writer.println("    RULE 4 : IF distance IS medium AND speed IS low THEN acceleration IS accelerate;");
			writer.println("    RULE 5 : IF distance IS medium AND speed IS medium THEN acceleration IS keep;");
			writer.println("    RULE 6 : IF distance IS medium AND speed IS high THEN acceleration IS slowDown;");
			writer.println("    RULE 7 : IF distance IS big AND speed IS low THEN acceleration IS accelerate;");
			writer.println("    RULE 8 : IF distance IS big AND speed IS medium THEN acceleration IS accelerate;");
			writer.println("    RULE 9 : IF distance IS big AND speed IS high THEN acceleration IS keep;");
			writer.println("END_RULEBLOCK");
			writer.println();
			
			writer.println("END_FUNCTION_BLOCK");
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Main.stop = true;
			System.out.println("FCL file not found");
			e.printStackTrace();
		}
	}
}
