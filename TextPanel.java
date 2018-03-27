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
	 private String filePath;
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
		 filePath = null;
         leftJustify = true;
         doAnalysis = false;
	 }
	
	 private class ButtonListener implements ActionListener	{
		 JFileChooser fileChooser = new JFileChooser();
		 
		
		public void actionPerformed (ActionEvent event)	{
			
			//gets input file
			if(event.getSource() == selectInput)	{
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					filePath = file.getAbsolutePath();
					tester = new FileManipulate(filePath);
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
			
			//outputs to new file
			else if(event.getSource() == createOutput)	{
				if(formatter != null)
				{
					if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						try {
							if(!filePath.equals(file.getAbsolutePath())) {
								
								/*
								 * DELETE THIS:
								 * PURELY TEST PURPOSES
								 * */

								formatter.setLineLength(40);
								formatter.setSpacing(2);
								
								
								/*
								 * OK STOP DELETING
								 * 
								 * */
								
								
								
								if(leftJustify)
									tester.WriteFile(formatter.leftJustify(), file.getAbsolutePath());
								else
									tester.WriteFile(formatter.rightJustify(), file.getAbsolutePath());
								
								//DELETE THIS LINE ALSO THO
								tester.WriteFile(formatter.fullJustify(), file.getAbsolutePath());
								
								//optional analysis output
								if(doAnalysis) {
									String toOutput = "";
									toOutput = toOutput +"# words processed: " + formatter.wordCount();
									toOutput = toOutput + "\n# lines: " + formatter.lineCount();
									toOutput = toOutput + "\n# blank lines removed: " + formatter.linesRemoved();
									toOutput = toOutput + "\nAverage words/line: " + String.format("%.2f", formatter.wordsPerLine());
									toOutput = toOutput + "\nAverage line length: " + String.format("%.2f", formatter.lineLength());
									toOutput = toOutput + "\nSpaces added: " + formatter.spacesAdded();
									textArea.setText(toOutput);
								}
								else {
									textArea.setText("File outputted successfully");
								}
							}
							else {
								textArea.setText("Please chose a file other than the input file to output to");
							}
								
						} catch (IOException e) {
							textArea.setText("Invalid file");
						}
					}
				}
				else {
					textArea.setText("Please select an input file first");
				}
			}
			
			//toggles showing analysis
			else if(event.getSource() == showAnalysis) {

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
	 
	 
