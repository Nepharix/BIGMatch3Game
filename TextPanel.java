import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextPanel extends JPanel{
	 private JButton selectInput, swapJustification, createOutput, showAnalysis;
	 private JPanel buttons1, mainPanel, textPanel, input;
	 private JTextArea textArea;
	 private JTextField fileIn;
	 private JLabel label1;
	 private boolean leftJustified, hasOutput;
	 private String fileName;
	 
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
         
         
		textPanel = new JPanel();
         
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
         
         
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250,250));
		add(areaScrollPane);
         
		input = new JPanel();
		input.setLayout(new GridLayout(2,1));
		label1 = new JLabel("Please enter the name of your file");
		fileIn = new JTextField();
		input.add(label1);
		input.add(fileIn);
		add(input);
         
		mainPanel.add(input, BorderLayout.EAST);
		mainPanel.add(buttons1, BorderLayout.WEST);
		mainPanel.add(textPanel, BorderLayout.EAST);
		add(mainPanel);
         
		leftJustified = true;
		hasOutput = false;
		fileName = ""; //to flag that no file has been given
	 }
	
	 private class ButtonListener implements ActionListener	{
		public void actionPerformed (ActionEvent event)	{
			
			//selects an input file
			if(event.getSource() == selectInput) {
				fileName = fileIn.getText();
				//TODO read from input file
				textArea.setText("Input File Selected: " + fileName); //temporary test line
			}
			
			//swaps the justification of the file
			else if(event.getSource() == swapJustification)	{
				leftJustified = !leftJustified;
				if(leftJustified) {
					textArea.setText("Current Justification: Left");
				}
				else {
					textArea.setText("Current Justification: Right");
				}
			}
			
			//writes to an output file
			else if(event.getSource() == createOutput)	{
				if(fileIn.getText().equals(fileName)) {
					textArea.setText("Please enter a file other than the input file to output to");
				}
				else {
					//TODO output to file
					textArea.setText("Output File Selected: " + fileIn.getText()); //temporary test line
					hasOutput = true;
				}
			}
			
			//shows the analysis of the file
			else if(event.getSource() == showAnalysis){
				if(hasOutput) {
					//TODO display formating analysis
					textArea.setText("Displays Analysis"); //temporary test line
				}
				else {
					textArea.setText("No formating has been done yet");
				}
			}
		}
	 }
}
