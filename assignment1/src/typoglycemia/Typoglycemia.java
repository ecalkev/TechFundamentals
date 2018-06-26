package typoglycemia;
 
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Typoglycemia {
	
	public Typoglycemia(){
		
		PrintWriter fileOut;
		String fileName = "output.txt";
		String fileInName = "textFile.txt";
		
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the name of the file you want to jumble the text in.");
			String tempFileInName = scanner.nextLine();
			if(!tempFileInName.equals("")) fileInName = tempFileInName;
			System.out.println("Please enter the name of the file you want to save the new text.");
			String tempFileName = scanner.nextLine();
			if(!tempFileName.equals("")) fileName = tempFileName;
			
			fileOut = new PrintWriter(fileName);
			
			File fileDir = new File(fileInName);

	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	        
	        String line;
	        
			while((line = in.readLine()) != null){
				printJumble(line, fileOut);
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
	
	//method for splitting initial string of text into individual strings for each word
	//each of the strings consists solely of letters, or no letters
	//this means only strings with letters will be scrambled (works correctly)
	public ArrayList<String> textToWords(String string){
		ArrayList<String> list = new ArrayList<String>();
		if(string.length() > 0){
			int firstLetter = 0;
			boolean lettersFound = true;
			//if the first letter isn't a letter
			if(!Character.isLetter(string.charAt(0))){
				//mark lettersFound as false
				lettersFound = false;
			}
			for(int i = 0; i < string.length(); i++){
				//if first letter isn't a letter
				if(!lettersFound){
					//if the character at i is a letter
					if(Character.isLetter(string.charAt(i))){
						//add the substring from firstLetter to i-1 to the list of strings (this is a string of non-letters)
						list.add(string.substring(firstLetter, i));
						//marks character at i as a letter and start of next string
						lettersFound = true;
						firstLetter = i;
					}
				}
				//if first letter is a letter
				else{
					//if the character at i isn't a letter
					if(!Character.isLetter(string.charAt(i))){
						//add the substring from firstLetter to i-1 to the list of strings (this is a string of letters not separated by any grammar)
						list.add(string.substring(firstLetter, i));
						//marks character at i as a letter and start of next string
						lettersFound = false;
						firstLetter = i;
					}
				}
			}	
		}
		return list;
	}
	
	//randomly jumbles inner letters of each string of letters in the arrayList
	public ArrayList<String> randomPermute(ArrayList<String> a){
		int size = a.size();
		//loops through each string in list
		for(int i = 0; i < size; i++){
			//indicates the string is a letter string
			if(Character.isLetter((a.get(i).charAt(0)))){
				int length = a.get(i).length();
				char[] letters = a.get(i).toCharArray();
				if(length > 3){
					//loops through each letter in string starting at the second letter
					for(int j = 1; j < length; j++){
						//a random number for swapping letter with a letter beyond it in the string
						int random = j + (int) (Math.random() * (length - j - 1));
						//remember the letter
						char b = letters[j];
						//swaps the two letters based on random number
						letters[j] = letters[random];
						letters[random] = b;
					}
					a.set(i, String.valueOf(letters));
				}
			}	
		}
		return a;
	}
	
	//method takes in a string turns and prints the scrambled result to a file
	public void printJumble(String string, PrintWriter w){
		//if not an empty line
		if(string.length() > 0){
			//creates a list of strings
			ArrayList<String> words = textToWords(string);
			words = randomPermute(words);
			for(int i = 0; i < words.size(); i++){
				//prints each of the strings
				w.print(words.get(i));
			}
			//ensures lines take same format as original document
			w.println("");
		}
		//if an empty line
		else{
			//print an empty line
			w.print("");
		}
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		new Typoglycemia();
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 
	}

}
