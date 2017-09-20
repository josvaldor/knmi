package org.utn.antarctica.mean;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
public class Mean {
	
	public void calculate(HashMap<String,LinkedList<String>> map,String yearRange){
		String[] yearRangeArray = yearRange.split("-");
		String yearOneString = yearRangeArray[0];
		String yearTwoString = yearRangeArray[1];
		int yearOne = Integer.parseInt(yearOneString);
		int yearTwo = Integer.parseInt(yearTwoString);
		int yearDifference = yearTwo-yearOne+1;
		LinkedList<String> meanList;
		double value;
		double[] sum = new double[12];
		int[] count = new int[12];
		for(int i=yearOne;i<=yearTwo;i++){
			meanList = map.get(i+"");
			for(int j=0;j<12;j++){
				if(!meanList.get(j).equals("-999.9")){
					value = Double.parseDouble(meanList.get(j));
					count[j]++;
					sum[j]+=value;
				}
			}
		}
		double v;
		String output=yearOne+"-"+yearTwo+" "+yearDifference+" Year Mean Monthly Tempurature January-December:";
		DecimalFormat df = new DecimalFormat("#0.00");
		for(int j=0;j<12;j++){		
			v=sum[j]/count[j];
			if(count[j] != yearDifference)
				output+=" "+df.format(v)+"("+count[j]+") ";
			else
				output+=" "+df.format(v);
		}
		System.out.println(output);
	}
	
	/*
	 * Returns array of Strings 
	 */
	public String[] mean(HashMap<String,LinkedList<String>> map,String yearRange){
		String[] yearRangeArray = yearRange.split("-");
		String yearOneString = yearRangeArray[0];
		String yearTwoString = yearRangeArray[1];
		int yearOne = Integer.parseInt(yearOneString);
		int yearTwo = Integer.parseInt(yearTwoString);
		int yearDifference = yearTwo-yearOne+1;
		LinkedList<String> meanList;
		double value;
		double[] sum = new double[12];
		int[] count = new int[12];
		for(int i=yearOne;i<=yearTwo;i++){
			meanList = map.get(i+"");
			for(int j=0;j<12;j++){
				if(!meanList.get(j).equals("-999.9")){
					value = Double.parseDouble(meanList.get(j));
					count[j]++;
					sum[j]+=value;
				}
			}
		}
		String[] valueArray = new String[12];
		DecimalFormat df = new DecimalFormat("#0.00");
		double v;
		for(int j=0;j<12;j++){		
			v=sum[j]/count[j];
			valueArray[j]=df.format(v);
			if(count[j] != yearDifference)
				valueArray[j]+=","+count[j];
		}
		return valueArray;
	}
	
	public HashMap<String,LinkedList<String>> anomaly(HashMap<String,LinkedList<String>> map,String[] meanArray,String yearRange){
		String[] yearRangeArray = yearRange.split("-");
		String yearOneString = yearRangeArray[0];
		String yearTwoString = yearRangeArray[1];
		int yearOne = Integer.parseInt(yearOneString);
		int yearTwo = Integer.parseInt(yearTwoString);
		LinkedList<String> meanList;
		double value;
		double mean;
		String meanString;
		for(int i=yearOne;i<=yearTwo;i++){
			meanList = map.get(i+"");
			for(int j=0;j<12;j++){
				if(!meanList.get(j).equals("-999.9")){
					value = Double.parseDouble(meanList.get(j));
					meanString = meanArray[j];
					if(!meanString.contains(",")){
						mean = Double.parseDouble(meanArray[j]);
					}else{
						mean = Double.parseDouble(meanArray[j].split(",")[0]);
					}
					value = value - mean;
					meanList.set(j, value+"");
				}
			}
			map.put(i+"", meanList);
		}
		return map;
	}
	
	public Object[][] meanObjectArray(String[] meanArray, String yearRange){
		Object[][] data = new Object[2][13];
		data[0][0]="Year Range";
		data[0][1]="January";
		data[0][2]="February";
		data[0][3]="March";
		data[0][4]="April";
		data[0][5]="May";
		data[0][6]="June";
		data[0][7]="July";
		data[0][8]="August";
		data[0][9]="September";
		data[0][10]="October";
		data[0][11]="November";
		data[0][12]="December";
		data[1][0]=yearRange;

		for(int i = 0; i < meanArray.length;i++){
			if(meanArray[i].contains(","))
				data[1][i+1]=meanArray[i].split(",")[0]+"("+meanArray[i].split(",")[1]+")";
			else
				data[1][i+1]=meanArray[i];
		}
		return data;
	}
	
	public Object[][] anomalyObjectArray(HashMap<String,LinkedList<String>> map, String yearRange){
		String[] yearRangeArray = yearRange.split("-");
		String yearOneString = yearRangeArray[0];
		String yearTwoString = yearRangeArray[1];
		int yearOne = Integer.parseInt(yearOneString);
		int yearTwo = Integer.parseInt(yearTwoString);
		int yearDifference = yearTwo-yearOne+1;
		Object[][] data = new Object[yearDifference+1][13];
		data[0][0]="Year";
		data[0][1]="January";
		data[0][2]="February";
		data[0][3]="March";
		data[0][4]="April";
		data[0][5]="May";
		data[0][6]="June";
		data[0][7]="July";
		data[0][8]="August";
		data[0][9]="September";
		data[0][10]="October";
		data[0][11]="November";
		data[0][12]="December";
		LinkedList<String> meanList;
		double value;
		int count = 1;
		DecimalFormat df = new DecimalFormat("#0.00");
		for(int i=yearOne;i<=yearTwo;i++){
			meanList = map.get(i+"");
			data[count][0]=i;
			for(int j=0;j<12;j++){
				
				if(!meanList.get(j).equals("-999.9")){
					value = Double.parseDouble(meanList.get(j));
					data[count][j+1]=df.format(value);
				}else{
					data[count][j+1]="-";
				}
			}
			count++;
		}
		return data;
	}
}
