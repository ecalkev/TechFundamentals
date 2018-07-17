package typoglycemia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Typoglycemia {
	
	public static void main(String[] args){
		Typoglycemia jumbler = new Typoglycemia();
		jumbler.jumbleInputAndPrint();
	}

	private void jumbleInputAndPrint(){
		
		PrintWriter fileOut;
		String fileName = "TypoglycemiaOutput.txt";
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
				if(line.length() > 0){
					char[] letters = line.toCharArray();
					letters = jumbleWordsInLine(letters);
					fileOut.print(letters);
					fileOut.println("");
				}
				else{
					fileOut.println("");
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
	
	char[] jumbleWordsInLine(char[] letters){
		ArrayList<Integer> indices = getWordBoundaryIndices(letters);
		int i = (letters[0] != ' ') ? 0 : 1;
		while(i + 1 < indices.size()){
			letters = jumbleWord(letters, indices.get(i), indices.get(i + 1));
			i = i + 2;
		}
		return letters;
	}
	
	ArrayList<Integer> getWordBoundaryIndices(char[] letters){
		ArrayList<Integer> indices = new ArrayList<>();
		indices.add(0);
		boolean isLetter = true;
		for(int i = 0; i < letters.length; i++){
			if(isLetter && (letters[i] == ' ')){
				indices.add(i);
				isLetter = false;
			} else if(!isLetter && (letters[i] != ' ')){
				indices.add(i);
				isLetter = true;
			}
		}
		indices.add(letters.length);
		return indices;
	}
	
	char[] jumbleWord(char[] letters, int start, int end){
		int length = end - start - 1;
		if(length > 3){
			for(int i = start + 1; i < start + length; i++){
				int random = i + (int) (Math.random() * (end - i - 1));
				char temp = letters[i];
				letters[i] = letters[random];
				letters[random] = temp;
			}
		}
		return letters;
	}

}
