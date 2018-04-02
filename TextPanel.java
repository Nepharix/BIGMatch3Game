import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class TextPanel extends JPanel{
	 private JButton selectInput, createOutput;
	 private JPanel buttons1, mainPanel;
	 private JPanel justificationPanel, analysisPanel, lineLengthPanel, spacingPanel;
	 private JTextArea textArea;
	 private FileManipulate tester;
	 private FormattedString formatter;
	 private String filePath;
	 private boolean doAnalysis;
	 private int lineLength, spacing;
	 
	 public TextPanel()
	 {
		 mainPanel = new JPanel();
		 
		 selectInput = new JButton("Select New Input");
         createOutput = new JButton("Create New Output");
		 
		 
		 //listeners for the buttons
		 selectInput.addActionListener(new ButtonListener());
		 createOutput.addActionListener(new ButtonListener());
        
		 //panel to show justification options
		 justificationPanel = new JPanel();
		 justificationPanel.setLayout(new GridLayout(1, 4));
		 justificationPanel.add(new JLabel("Justification"));
		 justificationPanel.add(new JRadioButton("Left"));
		 justificationPanel.add(new JRadioButton("Right"));
		 justificationPanel.add(new JRadioButton("Full"));

	        
		 //panel to show analysis options
		 analysisPanel = new JPanel();
		 analysisPanel.setLayout(new GridLayout(1, 3));
		 analysisPanel.add(new JLabel("Analysis"));
		 analysisPanel.add(new JRadioButton("Hide"));
		 analysisPanel.add(new JRadioButton("Show"));

	        
		 //panel to show line length options
		 lineLengthPanel = new JPanel();
		 lineLengthPanel.setLayout(new GridLayout(1, 2));
		 lineLengthPanel.add(new JLabel("Line Length"));
		 lineLengthPanel.add(new JTextArea("80"));


		 //panel to show spacing options
		 spacingPanel = new JPanel();
		 spacingPanel.setLayout(new GridLayout(1, 3));
		 spacingPanel.add(new JLabel("Spacing"));
		 spacingPanel.add(new JRadioButton("Single"));
		 spacingPanel.add(new JRadioButton("Double"));
		 
		 
		 buttons1 = new JPanel();
         buttons1.setLayout(new GridLayout(6, 1));
         buttons1.add(selectInput);
         buttons1.add(createOutput);
         buttons1.add(analysisPanel);
         buttons1.add(justificationPanel);
         buttons1.add(spacingPanel);
         buttons1.add(lineLengthPanel);
         buttons1.setPreferredSize(new Dimension(300, 240));
         
         
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
		 doAnalysis = true;
		 lineLength = 80;
		 spacing = 2;
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
			
			//outputs to new file
			else if(event.getSource() == createOutput)	{
				if(formatter != null)
				{
					if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						try {
							if(!filePath.equals(file.getAbsolutePath())) {
								
								formatter.setLineLength(lineLength);
								formatter.setSpacing(spacing);
								
								//if(leftJustify)
									tester.WriteFile(formatter.leftJustify(), file.getAbsolutePath());
								//else if(rightJustify)
									tester.WriteFile(formatter.rightJustify(), file.getAbsolutePath());
								//else
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
		}
	 }
}
