package fileManage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;


// Sorry nothing is commented yet, ran out of time. 

public class FileManipulate {
	
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
	
	private void PopulateWordArray() throws IOException {
		try (Scanner scanner =  new Scanner(filePath)){
			while (scanner.hasNextLine()){
		        Scanner lineParser = new Scanner(scanner.nextLine());
		        lineParser.useDelimiter("\\s+");
		        while(lineParser.hasNext()) {
		        	wordList.add(lineParser.next());
		        }
		        lineParser.close();
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
	
	private void WriteFile(ArrayList<String> linesToWrite) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath.toAbsolutePath().toString() + ".formatted", false));
		for(int i = 0; i < linesToWrite.size(); i++) {
			String lineToWrite = linesToWrite.get(i);
			if (!this._leftJustified) {
				lineToWrite = String.format("%80s", lineToWrite);
			}
			writer.write(lineToWrite + "\n");
		}
		writer.close();
	}
	
	public void FormatFile() throws IOException {
		this.PopulateWordArray();
		ArrayList<String> lines = this.LineFormatter();
		this.WriteFile(lines);
	}
	
	public static void main(String[] args) throws IOException {
		FileManipulate fileFormatter = new FileManipulate(args[0], false); /* for now argument is direct path to text file 
																			this can be changed under run configurations */
		fileFormatter.FormatFile();	
	}
}
