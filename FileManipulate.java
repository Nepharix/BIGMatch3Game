package fileManage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;


// Sorry nothing is commented yet, ran out of time. 

public class FileManipulate extends TextPanel {
	
	private Path filePath;
	private Boolean _leftJustified;
	private ArrayList <String> wordList;
	
	
	public FileManipulate(String fileName, Boolean leftJustified) {
		filePath = Paths.get(fileName);
		_leftJustified = leftJustified;
		wordList = new ArrayList<String>();
	}
	
	public FileManipulate(String fileName) {
		filePath = Paths.get(fileName);
		_leftJustified = true;
		wordList = new ArrayList<String>();
	}
	
	public void FilePath (String fileName) {
		filePath = Paths.get(fileName);
	}
	
	private void PopulateWordArray() throws IOException {
		try (Scanner scanner =  new Scanner(filePath)){
			while (scanner.hasNextLine()){
		        //Scanner lineParser = new Scanner(scanner.nextLine());
		        //lineParser.useDelimiter("\");
		        	wordList.add(scanner.nextLine());
		        //lineParser.close();
		     } 
			scanner.close();
		}
	}
	
	private ArrayList<String> LineFormatter() throws IOException {
		ArrayList <String> currentLine= new ArrayList <String>();
		ArrayList <String> lines = new ArrayList <String>();
		int count = 0;
		String word;
		for (int i = 0; i < wordList.size(); i++) {
			word = wordList.get(i);
			count += word.length();
			if (count > 80) {
				lines.add(String.join(" ", currentLine));
				count = word.length() + 1;      // + 1 to account for space
				currentLine.clear();
				currentLine.add(word);
			} else if (count == 79 || count == 80) {
				currentLine.add(word);
				lines.add(String.join(" ", currentLine));
				count = 0;
				currentLine.clear();
			} else {
				count += 1;
				currentLine.add(word);
			}
		}
		return lines;
	}
	
	public void WriteFile(String linesToWrite, String filePath) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(linesToWrite);
			writer.close();
	}
	
	public String FileContents() {
		if (wordList.isEmpty()) {
			return "";
		} else {
			String Accumulate = wordList.get(0);
			for(int i = 1; i < wordList.size(); i++) {
				Accumulate = Accumulate + "\n" + wordList.get(i);
			}
			return Accumulate;
		}
	
	}
		
	
	public void FormatFile() throws IOException {
		this.PopulateWordArray();
		
	}
	
	
	public static void main(String[] args) throws IOException {
		FileManipulate fileFormatter = new FileManipulate(args[0], true); /* for now argument is direct path to text file	this can be changed under run configurations */
		fileFormatter.FormatFile();
		fileFormatter.WriteFile("Hello World", "C:\\Users\\D-Rock\\Desktop\\test.txt");
	}
}
