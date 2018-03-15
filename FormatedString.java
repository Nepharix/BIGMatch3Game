import java.util.Vector;

public class FormatedString
{
	private String toFormat;
	private Vector<String> words;
	private Vector<String> lines;
	
	public FormatedString(String input)
	{
		toFormat = input;
		
		parseWords();
		parseLines();
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
				if(toAdd.length() <= 80) //short enough
				{
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
	
	//returns a string padded on the left with spaces to 80 characters
	private String rightJustifyLine(String line)
	{
		while(line.length() < 80)
		{
			line = " " + line;
		}
		return line;
	}
	
	/************************
	 * BEGIN SPECIFICATIONS *
	 ************************/
	
	//Returns the given string in left-justified format 
	public String leftJustify()
	{
		if(!lines.isEmpty()) //guards against empty array
		{
			String text = lines.get(0); //accumulator of lines
			for(int i = 1; i < lines.size(); ++i)
			{
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
		if(lines.size() > 0)
		{
			String text = rightJustifyLine(lines.get(0)); //accumulator of lines
			for(int i = 1; i < lines.size(); ++i)
			{
				text += "\n" + rightJustifyLine(lines.get(i));
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
		return lines.size();
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
		return ((double) sum) / lineCount();
	}
}