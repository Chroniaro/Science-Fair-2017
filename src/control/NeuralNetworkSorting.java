package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public abstract class NeuralNetworkSorting {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File logs = new File("outputLogs");
		File[] sessions = logs.listFiles();
		int x = 0;
		if(sessions != null)
			for(x = 0; x < sessions.length; x++)
			{
				if(!("session_" + x).equals(sessions[x].getName()))
					break;
			}
		String pref = "outputLogs/session_" + x;
		logs = new File(pref);
		logs.mkdirs();
		
		NeuroEvolutionSimulator simulation = new NeuroEvolutionSimulator();
		for(int i = 0; i < 20; i++)
		{
			System.setOut(new PrintStream(new FileOutputStream(pref + "/log_" + i + ".log")));
			for(int n = 0; n < 1000; n++)
				simulation.evaluateGeneration(n % 10 == 0);
		}
	}
}