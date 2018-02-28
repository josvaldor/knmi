package main.java.org.utn.knmi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import main.java.org.utn.knmi.IO;
import main.java.org.utn.knmi.Mean;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("f",true,"file name");
		options.addOption("y",true,"year range");
		options.addOption("o",true,"output xlsx file");
		options.addOption("a",false,"anomaly");
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			String fileName = null;
			String yearRange = null;
			String outputFileName= null;
			boolean a = false;
			if(cmd.hasOption("f")){
				fileName = cmd.getOptionValue("f");
			}else{
				System.err.println("file name required");
			}
			
			if(cmd.hasOption("y")){
				yearRange = cmd.getOptionValue("y");
			}else{
				System.err.println("year range required");
			}
			
			if(cmd.hasOption("o")){
				outputFileName = cmd.getOptionValue("o");
			}
			
			if(cmd.hasOption("a")){
				a = true;
			}
			
			if(fileName != null && yearRange != null){
				IO file = new IO();
				Mean mean = new Mean();
				TreeMap<String,LinkedList<String>> map = file.read(fileName);
				String[] meanArray = mean.mean(map, yearRange);
				TreeMap<String,LinkedList<String>> anomaly = mean.anomaly(map, meanArray, yearRange);		
				mean.calculate(file.read(fileName), yearRange);
				Object[][] meanObjectArray = mean.meanObjectArray(meanArray, yearRange);
				if(a){
					Object[][] anomalyObjectArray = mean.anomalyObjectArray(anomaly, yearRange);
					file.writeXLS(outputFileName, meanObjectArray, anomalyObjectArray);
				}else{
					file.writeXLS(outputFileName, meanObjectArray,null);
				}
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
