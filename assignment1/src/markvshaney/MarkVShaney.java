package markvshaney;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class MarkVShaney {
	
	private LinkedHashMap map = new LinkedHashMap();
    private int prefixLength = 3;
    private int followerStart = 0;
    private String follower = "";
    private String prefix;
    private int outputWordCount;
    
    public static void main(String[] args){
    	MarkVShaney m = new MarkVShaney();
    	m.scanFileAndPrintRandomSentences();
    }
    
	public void scanFileAndPrintRandomSentences(){
		
		String fileName = "MarkVShaneyOutput.txt";
		String fileInName = "big.txt";
		
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the name of the file you want to jumble the text in.");
			String tempFileInName = scanner.nextLine();
			if(!tempFileInName.equals("")) fileInName = tempFileInName;
			System.out.println("Please enter the name of the file you want to save the new text.");
			String tempFileName = scanner.nextLine();
			if(!tempFileName.equals("")) fileName = tempFileName;
			System.out.println("Please enter the prefix length that you wish to use.");
			prefixLength = scanner.nextInt();
			int[] positions = new int[prefixLength];
			System.out.println("Please enter the number of words you wish to be printed to the file.");
			outputWordCount = scanner.nextInt();
			scanner.close();
			
			storePrefixesAndFollowers(fileInName, positions);
			


			randomSentences(outputWordCount, fileName, positions);
			
		}
		catch (FileNotFoundException e){
			System.out.println("Error: " + e.getMessage());
		}
		catch (IOException e){
            System.out.println(e.getMessage());
        }
		
	}
	
    /*
     * Reads through a line and obtains all prefixes and their followers, and adds them to a LinkedHashMap.
     */
	public void storePrefixesAndFollowers(String inputFile, int[] positions) throws IOException{
		File fileDir = new File(inputFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileDir), "UTF8"));
		String line;
        
        line = in.readLine();
        int i = 0;

        i = getFirstPrefix(i, line, positions);
		storePrefixesAndFollowers(i, line, positions);
		while((line = in.readLine()) != null){
			i = 0;
			followerStart = 0;
			storePrefixesAndFollowers(i, line, positions);
		}
		in.close();
	}
	
	/*
	 * Uses the @w to print random strings based on the prefix and follower LinkdHashMap @map.
	 */
	public void randomSentences(int numWords, String  fileName, int[] positions) throws FileNotFoundException{
		PrintWriter fileOut = new PrintWriter(fileName);
		

		int stringLength = printInitialPrefix(positions, fileOut);
		
		while(stringLength < numWords){
			try{
				prefix = nextWord(prefix, fileOut, positions);
				stringLength++;
			} catch(NullPointerException e){
				stringLength += printInitialPrefix(positions, fileOut);
			}
		}
		fileOut.close();
	}
	
    public LinkedHashMap getMap() {
		return map;
	}
    
    private int printInitialPrefix(int[] positions, PrintWriter fileOut){
    	int randomStart = (int) (Math.random() * map.getLength());
    	while(map.get(randomStart) == null){
			randomStart = (int) (Math.random() * map.getLength());
		}
		int randomFollower = (int) (Math.random() * map.get(randomStart).getNumFollowers());
		int count = 0;
		int stringLength = 0;
		int i = 0;
		while(count < prefixLength & i < map.get(randomStart).getPrefix().length()){
			if(map.get(randomStart).getPrefix().charAt(i) == ' ' & count < prefixLength){
				positions[count] = i + 1;
				followerStart = i + 1;
				count++;
			}
			i++;
		}
		stringLength = prefixLength + 1;
		fileOut.print(map.get(randomStart).getPrefix() + map.get(randomStart).getFollower().get(randomFollower));
		prefix = map.get(randomStart).getPrefix().substring(positions[0], positions[prefixLength - 1]) + map.get(randomStart).getFollower().get(randomFollower);
		int oldPos1 = positions[0];
		for(int j = 0; j < prefixLength - 1; j++){
			positions[j] = positions[j + 1] - oldPos1;
		}
		if(prefixLength > 1){
			positions[prefixLength - 1] = positions[prefixLength - 2] + map.get(randomStart).getFollower().get(randomFollower).length();
		}
		else{
			positions[prefixLength - 1] = map.get(randomStart).getFollower().get(randomFollower).length() - 1;
		}
		return stringLength;
    }
	
	private void storePrefixesAndFollowers(int startIndex, String line, int[] positions){
		for(int i = startIndex; i < line.length(); i++){
			if(line.charAt(i) == ' '){
				if(i == followerStart){
					followerStart = i + 1;
				}
				else{
					follower = line.substring(followerStart, i+1);
					followerStart = i + 1;
					addPrefixAndFollower();
					setNextPrefix(positions);
				}
			}
			else if(i == line.length() - 1){
				follower = line.substring(followerStart, i+1) + " ";
				followerStart = i + 1;
				addPrefixAndFollower();
				setNextPrefix(positions);
			}
		}
	}
    
    private int getFirstPrefix(int i, String line, int[] positions){
        int count = 0;
		while(count < prefixLength & i < line.length()){
			if(line.charAt(i) == ' ' & count < prefixLength){
				positions[count] = i + 1;
				followerStart = i + 1;
				count++;
			}
			i++;
		}
		prefix = line.substring(0, positions[prefixLength - 1]);
		return i;
    }
	
	private String nextWord(String input, PrintWriter w, int[] positions){
		LinkedKeyValuePair inputPrefix = map.get(input);
		int randomFollower = (int) (Math.random() * inputPrefix.getNumFollowers());
		String newFollower = inputPrefix.getFollower().get(randomFollower);
		w.print(newFollower);
		prefix = inputPrefix.getPrefix().substring(positions[0], positions[prefixLength - 1]) + newFollower;
		int oldPos1 = positions[0];
		for(int j = 0; j < prefixLength - 1; j++){
			positions[j] = positions[j + 1] - oldPos1;
		}
		if(prefixLength > 1){
			positions[prefixLength - 1] = positions[prefixLength - 2] + newFollower.length();
		}
		else{
			positions[prefixLength - 1] = newFollower.length() - 1;
		}
		return prefix;
	}
	
	private void addPrefixAndFollower(){
		LinkedKeyValuePair keyValuePair = map.putKey(prefix);
		keyValuePair.addFollower(follower);
	}
	
	private void setNextPrefix(int[] positions){
		prefix = prefix.substring(positions[0], positions[prefixLength - 1]) + follower;
		int oldPos1 = positions[0];
		for(int i = 0; i < prefixLength - 1; i++){
			positions[i] = positions[i + 1] - oldPos1;
		}
		if(prefixLength > 1){
			positions[prefixLength - 1] = positions[prefixLength - 2] + follower.length();
		} else{
			positions[prefixLength - 1] = follower.length() - 1;
		}
	}

}
