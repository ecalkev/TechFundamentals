package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarkVShaney {
	
	//an array of prefixes for our hashmap
	//should be prime number in length
	Prefix[] prefixes = new Prefix[10000000];
	//define the prefix length
    int prefixLength;
    //ints representing indexes for word positions
    int[] positions;
    //represents index of string where follower starts
    int followerStart = 0;
    String follower = "";
    String prefix;
    int outputWordCount;
	
	public MarkVShaney(){
		
		PrintWriter fileOut;
		String fileName = "/Users/Kevin/Documents/untitled.txt";
		String fileInName = "/Users/Kevin/Documents/big.txt";
		
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the name of the file you want to jumble the text in.");
			fileInName = scanner.nextLine();
			System.out.println("Please enter the name of the file you want to save the new text.");
			fileName = scanner.nextLine();
			System.out.println("Please enter the prefix length that you wish to use.");
			prefixLength = scanner.nextInt();
			positions = new int[prefixLength];
			System.out.println("Please enter the number of words you wish to be printed to the file.");
			outputWordCount = scanner.nextInt();
			
			fileOut = new PrintWriter(fileName);
			
			File fileDir = new File(fileInName);

	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	        
	        String line;
	        int count = 0;
	        
	        //finds the initial string of prefixLength words long
	        line = in.readLine();
	        int i = 0;
			while(count < prefixLength & i < line.length()){
				if(line.charAt(i) == ' ' & count < prefixLength){
					positions[count] = i + 1;
					followerStart = i + 1;
					count++;
				}
				i++;
			}
			prefix = line.substring(0, positions[prefixLength - 1]);
			
			//gets all prefixes and followers for remainder of line
			prefixLine(i, line);
			//for every line
			while((line = in.readLine()) != null){
				//restart i and followerStart for prefixLine method
				i = 0;
				followerStart = 0;
				//carry out prefixLine on all lines
				prefixLine(i, line);
			}
			
			randomSentences(outputWordCount, fileOut);
			
			scanner.close();
			in.close();
			fileOut.close();
			
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
	
	//creates a hashCode within the bounds of the array boundaries
	public int hashCode(String prefix){
		int hash = prefix.hashCode();
		hash = hash % prefixes.length;
		hash = Math.abs(hash);
		return hash;
	}
	
	//method for reading through a line and obtaining all prefixes and adding their followers
	public void prefixLine(int i, String line){
		//while still characters on this line
		while(i < line.length()){
			//if find a space
			if(line.charAt(i) == ' '){
				//if a space is followed by a space
				if(i == followerStart){
					//shift the followerStart by 1
					followerStart = i + 1;
				}
				else{
					//follower is string from follower start to space (inclusive)
					follower = line.substring(followerStart, i+1);
					followerStart = i + 1;
					//if the hashCode directs to a prefix
					if(prefixes[hashCode(prefix)] != null){
						//search the linked list for the prefix and add the follower
						prefixes[hashCode(prefix)].searchPrefix(prefix).addFollower(follower);
					}
					else{
						//if none at this hashCode create new Prefix
						prefixes[hashCode(prefix)] = new Prefix(prefix);
						//add the follower
						prefixes[hashCode(prefix)].addFollower(follower);
					}
					//removes the first word and adds the follower to the prefix
					prefix = prefix.substring(positions[0], positions[prefixLength - 1]) + follower;
					//changes indexes for new prefix
					int oldPos1 = positions[0];
					for(int j = 0; j < prefixLength - 1; j++){
						positions[j] = positions[j + 1] - oldPos1;
					}
					if(prefixLength > 1){
						positions[prefixLength - 1] = positions[prefixLength - 2] + follower.length();
					}
					else{
						positions[prefixLength - 1] = follower.length() - 1;
					}
				}
			}
			//accounts for where the last word on a line is not followed by a space
			else if(i == line.length() - 1 & line.charAt(i) != ' '){
				//follower is string from follower start to space (inclusive)
				follower = line.substring(followerStart, i+1) + " ";
				followerStart = i + 1;
				//if the hashCode directs to a prefix
				if(prefixes[hashCode(prefix)] != null){
					//search the linked list for the prefix and add the follower
					prefixes[hashCode(prefix)].searchPrefix(prefix).addFollower(follower);
				}
				else{
					//if none at this hashCode create new Prefix
					prefixes[hashCode(prefix)] = new Prefix(prefix);
					//add the follower
					prefixes[hashCode(prefix)].addFollower(follower);
				}
				//removes the first word and adds the follower to the prefix
				prefix = prefix.substring(positions[0], positions[prefixLength - 1]) + follower;
				//changes indexes for new prefix
				int oldPos1 = positions[0];
				for(int j = 0; j < prefixLength - 1; j++){
					positions[j] = positions[j + 1] - oldPos1;
				}
				if(prefixLength > 1){
					positions[prefixLength - 1] = positions[prefixLength - 2] + follower.length();
				}
				else{
					positions[prefixLength - 1] = follower.length() - 1;
				}
			}
			i++;
		}
	}
	
	//method for printing sentences based on hashMap
	public void randomSentences(int numWords, PrintWriter w){
		//find a random index in the array to start with
		int randomStart = (int) (Math.random() * prefixes.length);
		while(prefixes[randomStart] == null){
			randomStart = (int) (Math.random() * prefixes.length);
		}
		//find a random index for followers arraylist
		int randomFollower = (int) (Math.random() * prefixes[randomStart].getFollowers().size());
		//get positions of each word in prefix string
		int count = 0;
		int stringLength = 0;
		int i = 0;
		while(count < prefixLength & i < prefixes[randomStart].getPrefix().length()){
			if(prefixes[randomStart].getPrefix().charAt(i) == ' ' & count < prefixLength){
				positions[count] = i + 1;
				followerStart = i + 1;
				count++;
			}
			i++;
		}
		stringLength = prefixLength + 1;
		w.print(prefixes[randomStart].getPrefix() + prefixes[randomStart].getFollowers().get(randomFollower));
		//remove first word from prefix and add the follower to make a new prefix
		String prefix = prefixes[randomStart].getPrefix().substring(positions[0], positions[prefixLength - 1]) + prefixes[randomStart].getFollowers().get(randomFollower);
		//changes indexes for new prefix
		int oldPos1 = positions[0];
		for(int j = 0; j < prefixLength - 1; j++){
			positions[j] = positions[j + 1] - oldPos1;
		}
		if(prefixLength > 1){
			positions[prefixLength - 1] = positions[prefixLength - 2] + prefixes[randomStart].getFollowers().get(randomFollower).length();
		}
		else{
			positions[prefixLength - 1] = prefixes[randomStart].getFollowers().get(randomFollower).length() - 1;
		}
		
		
		while(stringLength < numWords){
			prefix = nextWord(prefix, w);
			stringLength++;
		}
	}
	
	//method for picking a random follower for the current prefix
	public String nextWord(String input, PrintWriter w){
		Prefix inputPrefix = prefixes[hashCode(input)].searchPrefix(input);
		int randomFollower = (int) (Math.random() * inputPrefix.getFollowers().size());
		w.print(inputPrefix.getFollowers().get(randomFollower));
		prefix = inputPrefix.getPrefix().substring(positions[0], positions[prefixLength - 1]) + inputPrefix.getFollowers().get(randomFollower);
		//changes indexes for new prefix
		int oldPos1 = positions[0];
		for(int j = 0; j < prefixLength - 1; j++){
			positions[j] = positions[j + 1] - oldPos1;
		}
		if(prefixLength > 1){
			positions[prefixLength - 1] = positions[prefixLength - 2] + inputPrefix.getFollowers().get(randomFollower).length();
		}
		else{
			positions[prefixLength - 1] = inputPrefix.getFollowers().get(randomFollower).length() - 1;
		}
		return prefix;
	}
	

	public static void main(String[] args) {
		
		new MarkVShaney();

	}

}
