package assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class DirectMappedCache {
	
	private int[] tags;
	//determines the number of bits for each part of address
	private int tagLength;
	private int indexLength;
	
	public DirectMappedCache(int totalSizeInKB, int bytesPerCacheLine){
		int totalSize = 1024 * totalSizeInKB;
		int numLines = totalSize/bytesPerCacheLine;
		tags = new int[numLines];
		
		indexLength = (int) (Math.log(numLines)/Math.log(2));
		int offsetLength = (int) (Math.log(bytesPerCacheLine)/Math.log(2));
		tagLength = 32 - indexLength - offsetLength;
		
		//creates randomly generated tags for each index of the cache
		for(int i = 0; i < tags.length; i++){
			int range = (int) Math.pow(2, tagLength);
			int tag = (int) (Math.random() * (range));
			tags[i] = tag;
		}
	}
	
	public boolean applyAddress(long address){
		String bits = Long.toBinaryString(address);
		String indexBits = bits.substring(tagLength, tagLength + indexLength);
		int index = Integer.parseInt(indexBits, 2);
		String tagBits = bits.substring(0, tagLength);
		int tag = Integer.parseInt(tagBits, 2);
		if(tag == tags[index]){
			return true;
		}
		else{
			tags[index] = tag;
			return false;
		}
	}
	
	public void printHits(){
		PrintWriter fileOut;
		String fileName;
		String fileInName;
		
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the name of the file containing the addresses you want to check.");
			fileInName = scanner.nextLine();
			System.out.println("Please enter the name of the file you want to save the results to.");
			fileName = scanner.nextLine();
			
			fileOut = new PrintWriter(fileName);
			
			File fileDir = new File(fileInName);

	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	        
	        String line;
	        
			//checks the address on each line for a hit/miss in the cache
	        while((line = in.readLine()) != null){
	        	Long address = Long.decode(line);
				if(applyAddress(address)){
					fileOut.println(line + " had a hit.");
				}
				else{
					fileOut.println(line + " had a miss.");
				}
			}
			in.close();
			fileOut.close();
			scanner.close();
		}
		catch (UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        } 
		catch (FileNotFoundException e){
			System.out.println("Error: " + e.getMessage());
		}
		catch (IOException e){
            System.out.println(e.getMessage());
        }
		
	}

	public static void main(String[] args) {
		DirectMappedCache cache = new DirectMappedCache(64, 16);
		
		cache.printHits();

	}

}
