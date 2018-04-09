import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class TextPanel extends JPanel{
	 private JButton selectInput, createOutput;
	 private JPanel buttons1, mainPanel;
	 private JPanel justificationPanel, analysisPanel, lineLengthPanel, spacingPanel;
	 private JTextArea textArea, lengthArea;
	 private JRadioButton hideButton, showButton;
	 private JRadioButton leftButton, rightButton, fullButton;
	 private JRadioButton singleButton, doubleButton;
	 private ButtonGroup analysisButtons, justificationButtons, spacingButtons;
	 private FileManipulate tester;
	 private FormattedString formatter;
	 private String filePath;
	 
	 public TextPanel()
	 {
		 mainPanel = new JPanel();
		 
		 selectInput = new JButton("Select New Input");
         createOutput = new JButton("Create New Output");
		 
		 //listeners for the buttons
		 selectInput.addActionListener(new ButtonListener());
		 createOutput.addActionListener(new ButtonListener());
        
		 //initializes panel
		 initAnalysis();
		 initJustification();
		 initSpacing();
		 initLength();
		 initButtons();   
         
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
	 }

	 //initializes panel to show analysis options
	 private void initAnalysis()
	 {
		 hideButton = new JRadioButton("Hide");
		 showButton = new JRadioButton("Show");
		 
		 analysisPanel = new JPanel();
		 analysisPanel.setLayout(new GridLayout(1, 3));
		 analysisPanel.add(new JLabel("Analysis"));
		 analysisPanel.add(hideButton);
		 analysisPanel.add(showButton);
		 
		 analysisButtons = new ButtonGroup();
		 analysisButtons.add(hideButton);
		 analysisButtons.add(showButton);
		 hideButton.setSelected(true);
	 }
	 
	 //initializes panel to show justification options
	 private void initJustification()
	 {
		 leftButton = new JRadioButton("Left");
		 rightButton = new JRadioButton("Right");
		 fullButton = new JRadioButton("Full");
		 
		 justificationPanel = new JPanel();
		 justificationPanel.setLayout(new GridLayout(1, 4));
		 justificationPanel.add(new JLabel("Justification"));
		 justificationPanel.add(leftButton);
		 justificationPanel.add(rightButton);
		 justificationPanel.add(fullButton);
		 
		 justificationButtons = new ButtonGroup();
		 justificationButtons.add(leftButton);
		 justificationButtons.add(rightButton);
		 justificationButtons.add(fullButton);
		 leftButton.setSelected(true);
	 }
	 
	 //initializes panel to show spacing options
	 private void initSpacing()
	 {
		 singleButton = new JRadioButton("Single");
		 doubleButton = new JRadioButton("Double");
		 
		 spacingPanel = new JPanel();
		 spacingPanel.setLayout(new GridLayout(1, 3));
		 spacingPanel.add(new JLabel("Spacing"));
		 spacingPanel.add(singleButton);
		 spacingPanel.add(doubleButton);

		 spacingButtons = new ButtonGroup();
		 spacingButtons.add(singleButton);
		 spacingButtons.add(doubleButton);
		 singleButton.setSelected(true);
	 }
	 
	 //initializes panel to show line length options
	 private void initLength()
	 {
		 lengthArea = new JTextArea("80");
		 lineLengthPanel = new JPanel();
		 lineLengthPanel.setLayout(new GridLayout(1, 2));
		 lineLengthPanel.add(new JLabel("Line Length"));
		 lineLengthPanel.add(lengthArea);
	 }
	 
	 //initializes buttons panel with all of the components
	 private void initButtons()
	 {
		 buttons1 = new JPanel();
         buttons1.setLayout(new GridLayout(6, 1));
         buttons1.add(selectInput);
         buttons1.add(createOutput);
         buttons1.add(analysisPanel);
         buttons1.add(justificationPanel);
         buttons1.add(spacingPanel);
         buttons1.add(lineLengthPanel);
         buttons1.setPreferredSize(new Dimension(300, 240));
	 }
	
	 private class ButtonListener implements ActionListener	{
		 JFileChooser fileChooser = new JFileChooser();
		 
		
		public void actionPerformed (ActionEvent event)	{
			
			//gets input file
			if(event.getSource() == selectInput)	{
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					filePath = file.getAbsolutePath();
					if(!filePath.endsWith(".txt"))
					{
						textArea.setText("Please chose a .txt file as input");
						tester = null;
					}
					else
					{
						tester = new FileManipulate(filePath);
						try {
							tester.FormatFile();
							textArea.setText("Input file selected successfully");
						} catch (IOException e) {
							textArea.setText("Invalid file");
						}
					}
				}
				
				if(tester == null)
				{
					formatter = null;
				}
				else
				{
					formatter = new FormattedString(tester.FileContents());
				}
				
			}
			
			//outputs to new file
			else if(event.getSource() == createOutput)	
			{
				if(formatter != null)
				{
					try
					{
						//set line length
						int lineLength = Integer.parseInt(lengthArea.getText());
						if(lineLength > 100 || lineLength < 20)
						{
							textArea.setText("Please enter a valid integer between 20 and 100 in the Line Length text area");
						}
						else
						{
							formatter.setLineLength(lineLength);
							if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								
								if(!file.getAbsolutePath().endsWith(".txt"))
								{
									textArea.setText("Please chose a .txt file to output to");
								}
								else if(filePath.equals(file.getAbsolutePath()))
								{
									textArea.setText("Please chose a file other than the input file to output to");
								}
								else
								{
									//set spacing
									if(singleButton.isSelected())
										formatter.setSpacing(1);
									else
										formatter.setSpacing(2);
									
									//set justification
									if(leftButton.isSelected())
										tester.WriteFile(formatter.leftJustify(), file.getAbsolutePath());
									else if(rightButton.isSelected())
										tester.WriteFile(formatter.rightJustify(), file.getAbsolutePath());
									else
										tester.WriteFile(formatter.fullJustify(), file.getAbsolutePath());
									
									//optional analysis output
									if(showButton.isSelected()) {
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
							}
						}
					} catch (NumberFormatException e) {
						textArea.setText("Please enter a valid integer between 20 and 100 in the Line Length text area");
					} catch (IOException e) {
						textArea.setText("Invalid file");
					}
				}
				else //formatter is null
				{
					textArea.setText("Please select an input file first");
				}
			}
		}
	 }
}
