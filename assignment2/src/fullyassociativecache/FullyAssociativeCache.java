package fullyassociativecache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class FullyAssociativeCache {
	
	private CacheNode mostRecentNode;
	private int tagLength;
	private int numLines;
	
	public FullyAssociativeCache(int totalSizeInKB, int bytesPerCacheLine){
		int totalSize = 1024 * totalSizeInKB;
		numLines = totalSize/bytesPerCacheLine;
		
		tagLength = 32 - (int) (Math.log(bytesPerCacheLine)/Math.log(2));
		
		int range = (int) Math.pow(2, tagLength);
		int tag = (int) (Math.random() * (range));
		mostRecentNode = new CacheNode(tag, this);
		for(int i = 1; i < numLines; i++){
			range = (int) Math.pow(2, tagLength);
			tag = (int) (Math.random() * (range));
			mostRecentNode.addFollower(tag);
		}
	}
	
	public boolean applyAddress(long address){
		String bits = Long.toBinaryString(address);
		String tagBits = bits.substring(0, tagLength);
		int tag = Integer.parseInt(tagBits, 2);
		return mostRecentNode.searchCache(tag);
	}
	
	public void printHits(){
		PrintWriter fileOut;
		String fileName;
		String fileInName;
		
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the name of the file containing the addresses you want to check.");
			fileInName = scanner.nextLine();
			if(fileInName.isEmpty()) fileInName = "Address.txt";
			System.out.println("Please enter the name of the file you want to save the results to.");
			fileName = scanner.nextLine();
			if(fileName.isEmpty()) fileName = "AssociativeCacheHits.txt";
			
			fileOut = new PrintWriter(fileName);
			
			File fileDir = new File(fileInName);

	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	        
	        String line;
	        
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

	public CacheNode getMostRecentNode() {
		return mostRecentNode;
	}

	public void setMostRecentNode(CacheNode mostRecentNode) {
		this.mostRecentNode = mostRecentNode;
	}

	public static void main(String[] args) {
		FullyAssociativeCache cache = new FullyAssociativeCache(1, 64);
		
		cache.printHits();

	}
	
}
