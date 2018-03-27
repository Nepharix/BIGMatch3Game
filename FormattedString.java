import java.util.Vector;

// The FormattedString class is created from a single string.
// It can then be accessed to provide strings with the same
// text, but different formatting, such as right justification.
// An instance of this class can also be used to provide 
// analytical data for the conversion between strings.
// Created by Spencer Bouck

public class FormattedString
{
	private String toFormat;
	private Vector<String> words;
	private Vector<String> lines;
	private int spacing;
	private int lineLength;
	private int spaceCount;
	
	public FormattedString(String input)
	{
		toFormat = input;
		lineLength = 80;
		spacing = 1;
		spaceCount = 0;
		parseWords();
	}

	//reads the string into words
	private void parseWords()
	{
		words = new Vector<String>();
		String[] rawWords = toFormat.split(" |\t|\n|\r");
		for(int i = 0; i < rawWords.length; ++i)
		{
			if(rawWords[i].length() > 0)
			{
				words.add(rawWords[i]);
			}
		}
	}
	
	//takes the words of the file and assembles them into individual lines
	private void parseLines()
	{
		lines = new Vector<String>();
		if(!words.isEmpty()) //guards against empty array
		{ 
			String currentLine = words.get(0); //initialize first line to first word
			for(int i = 1; i < words.size(); ++i) //start from second word
			{
				String toAdd = currentLine + " " + words.get(i);
				if(toAdd.length() <= lineLength) //short enough
				{
					spaceCount++; //count how many spaces were added
					currentLine = toAdd;
				}
				else //too long to add the new word
				{
					//move onto new line
					lines.add(currentLine);
					currentLine = words.get(i);
				}
			}
			//leftover line needs to be added
			lines.addElement(currentLine);			
		}
	}

	//prints the original input string
	public void printStr()
	{
		System.out.println(toFormat);
	}

	//prints the array of words
	public void printWords()
	{
		for(int i = 0; i < words.size(); ++i)
		{
			System.out.println("_" + words.get(i) + "_");
		}
	}

	//prints the array of lines
	public void printLines()
	{
		for(int i = 0; i < lines.size(); ++i)
		{
			System.out.println("_" + lines.get(i) + "_");
		}
	}

	//returns a string padded on the left with spaces to the selected number of characters
	private String rightJustifyLine(String line)
	{
		while(line.length() < lineLength)
		{
			line = " " + line;
			spaceCount++; //keeps track of adding a space
		}
		return line;
	}

	//returns a string padded in the middle with spaces to the selected number of characters
	private String fullJustifyLine(String line)
	{
		int spacesToAdd = lineLength - line.length();
		String[] wordList = line.split(" |\t|\n|\r");
		
		//optimization to return string early
		//also protects against empty or single-word lines
		if(spacesToAdd == 0 || wordList.length < 2) 
		{ 
			return line; 
		}
		else
		{
			int separations = wordList.length - 1;
			spacesToAdd += separations; //spaces removed when words were separated
			int spacesPerWord = spacesToAdd / separations;
			int remainingSpaces = spacesToAdd % separations;
			
			//represents the spaces to be added
			String bufferSpace = "";
			for(int i = 0; i < spacesPerWord; ++i)
			{
				bufferSpace += " ";
			}
			
			line = wordList[0];
			
			//accumulates words into a single string
			for(int i = 1; i < wordList.length; ++i)
			{
				line += bufferSpace;
				spaceCount += bufferSpace.length(); //keeps track of adding spaces
				//adds additional space between words
				if(remainingSpaces > 0)
				{
					line += " ";
					spaceCount++; //keeps track of adding spaces
					remainingSpaces--;
				}
				line += wordList[i];
			}
			
			return line;
		}
	}
	
	/************************
	 * BEGIN SPECIFICATIONS *
	 ************************/
	
	//Sets the length of the lines to a given value
	public void setLineLength(int length)
	{
		lineLength = length;
	}
	
	//Sets the spacing of the lines to a given value
	public void setSpacing(int spacing)
	{
		this.spacing = spacing;
	}
	
	//Returns the given string in left-justified format 
	public String leftJustify()
	{
		spaceCount = 0;
		parseLines();
		if(!lines.isEmpty()) //guards against empty array
		{
			String text = lines.get(0); //accumulator of lines
			for(int i = 1; i < lines.size(); ++i)
			{
				if(spacing == 2)
				{
					text += "\n";
				}
				text += "\n" + lines.get(i);
			}
			return text;
		}
		else
		{
			return "";
		}
	}

	//Returns the given string in right-justified format
	public String rightJustify()
	{
		parseLines();
		if(lines.size() > 0)
		{
			spaceCount = 0;
			String text = rightJustifyLine(lines.get(0)); //accumulator of lines
			for(int i = 1; i < lines.size(); ++i)
			{
				if(spacing == 2)
				{
					text += "\n";
				}
				text += "\n" + rightJustifyLine(lines.get(i));
			}
			return text;
		}
		else
		{
			return "";
		}
	}

	//Returns the given string in full-justified format
	public String fullJustify()
	{
		parseLines();
		if(lines.size() > 0)
		{
			spaceCount = 0;
			String text = fullJustifyLine(lines.get(0)); //accumulator of lines
			for(int i = 1; i < lines.size(); ++i)
			{
				if(spacing == 2)
				{
					text += "\n";
				}
				text += "\n" + fullJustifyLine(lines.get(i));
			}
			return text;
		}
		else
		{
			return "";
		}
	}

	//Returns the number of words in the string
	public int wordCount()
	{
		return words.size();
	}
	
	//Returns the number of lines in the output string
	public int lineCount()
	{
		//number of lines with empty spaces after them, 
		//times the number of lines each takes, 
		//plus the final line with no spaces after it
		return (lines.size() - 1) * spacing + 1;
	}

	//Returns the number of blank lines removed from the string
	public int linesRemoved()
	{
		int count = 0;
		boolean newLine = true; //records if the current line of the string is on its own line
		for(int i = 0; i < toFormat.length(); ++i)
		{
			//if the character is a newline
			if(toFormat.charAt(i) == '\n')
			{
				if(newLine) //blank line
				{
					count++;
				}
				else //simply a next line, not a blank line
				{
					newLine = true; //set the flag that this is a new line
				}
			}
			else //not a new line
			{
				newLine = false;
			}
		}
		
		return count;
	}

	//Returns the average number of words per line in the output string
	public double wordsPerLine()
	{
		if(lineCount() == 0)
		{
			return 0.0;
		}
		else
		{
			return ((double) wordCount()) / lineCount();
		}
	}

	//Returns the average length of line in the output string
	public double lineLength()
	{
		int sum = 0;
		for(int i = 0; i < lines.size(); ++i)
		{
			sum += lines.get(i).length();
		}
		if(lineCount() == 0)
		{
			return 0.0;
		}
		else
		{
			return ((double) sum) / lineCount();
		}
	}
	
	//Returns the number of spaces added to the output string
	public int spacesAdded()
	{
		return spaceCount;
	}
}