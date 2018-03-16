import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class TextPanel extends JPanel{
	 private JButton selectInput, swapJustification, createOutput, showAnalysis ;
	 private JPanel buttons1, mainPanel;
	 private JTextArea textArea;
	 private FileManipulate tester;
	 private FormattedString formatter;
	 private boolean leftJustify, doAnalysis;
	 
	 public TextPanel()
	 {
		 mainPanel = new JPanel();
		 
		 selectInput = new JButton("Select New Input");
         swapJustification = new JButton("Swap Justification");
         createOutput = new JButton("Create New Output");
		 showAnalysis = new JButton("Show Analysis");
		 
		 
		 //listeners for the buttons
		 selectInput.addActionListener(new ButtonListener());
		 swapJustification.addActionListener(new ButtonListener());
		 createOutput.addActionListener(new ButtonListener());
		 showAnalysis.addActionListener(new ButtonListener());
        
		 
		 buttons1 = new JPanel();
         buttons1.setLayout(new GridLayout(4,1));
         
         buttons1.add(selectInput).setPreferredSize(new Dimension(200,60));
         buttons1.add(swapJustification).setPreferredSize(new Dimension(200,60));
         buttons1.add(createOutput).setPreferredSize(new Dimension(200,60));
         buttons1.add(showAnalysis).setPreferredSize(new Dimension(200,60));
         
         
         JPanel textPanel = new JPanel();
         
         
         textArea = new JTextArea();
         textArea.setLineWrap(true);
         textArea.setWrapStyleWord(true);
         
        
         JScrollPane areaScrollPane = new JScrollPane(textArea);
         areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         areaScrollPane.setPreferredSize(new Dimension(250,250));
         add(areaScrollPane);
         
         mainPanel.add(buttons1, BorderLayout.WEST);
         mainPanel.add(textPanel, BorderLayout.EAST);
         add(mainPanel);

         tester = null;
         formatter = null;
         leftJustify = true;
         doAnalysis = false;
	 }
	
	 private class ButtonListener implements ActionListener	{
		 JFileChooser fileChooser = new JFileChooser();
		 
		
		public void actionPerformed (ActionEvent event)	{
			
			if(event.getSource() == selectInput)	{
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					tester = new FileManipulate(file.getAbsolutePath());
					try {
						tester.FormatFile();
					} catch (IOException e) {
						textArea.setText("Invalid file");
					}
				}
				
				if(tester != null)
				{
					formatter = new FormattedString(tester.FileContents());
				}
				
			}
			
			//swaps the justification of the file
			else if(event.getSource() == swapJustification)	{
				leftJustify = !leftJustify;
				if(leftJustify) {
					textArea.setText("Current Justification: Left");
				}
				else {
					textArea.setText("Current Justification: Right");
				}
			}
			
			else if(event.getSource() == createOutput)	{
				if(formatter != null)
				{
					if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						try {
							if(leftJustify)
							{
								tester.WriteFile(formatter.leftJustify(), file.getAbsolutePath());
								System.out.println(formatter.leftJustify());
							}
							else
							{
								tester.WriteFile(formatter.rightJustify(), file.getAbsolutePath());
								System.out.println(formatter.rightJustify());
							}
								
						} catch (IOException e) {
							textArea.setText("Invalid file");
						}
					}
				}
				else
				{
					textArea.setText("Please select an input file first");
				}
				
				//optional analysis output
				if(formatter != null)
				{
					if(doAnalysis)
					{
						String toOutput = "";
						toOutput = toOutput +"# words processed: " + formatter.wordCount();
						toOutput = toOutput + "\n# lines: " + formatter.lineCount();
						toOutput = toOutput + "\n# blank lines removed: " + formatter.linesRemoved();
						toOutput = toOutput + "\nAverage words/line: " + formatter.wordsPerLine();
						toOutput = toOutput + "\nAverage line length: " + formatter.lineLength();
						textArea.setText(toOutput);
					}
					else
					{
						textArea.setText("File outputted successfully");
					}
				}
				else
				{
					textArea.setText("Please select an input file first");
				}
			}
			
			//toggles showing analysis
			else if(event.getSource() == showAnalysis){

				doAnalysis = !doAnalysis;
				if(doAnalysis) {
					textArea.setText("Currently showing analysis");
				}
				else {
					textArea.setText("Analysis disabled");
				}
			}
		}
	 }
}
	 
	 
