import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;


public class TextPanel extends JPanel{
	 private JButton selectInput, swapJustification, createOutput, showAnalysis ;
	 private JPanel buttons1, mainPanel, textPanel;
	 private int width, height;
	 private controlPanel1 cPanel;
	 
	 public TextPanel()
	 {
		 
		 this.width = width;
		 this.height = height;
		 mainPanel = new JPanel();
		 
		 selectInput = new JButton("Select New Input");
		 //selectInput.addActionListener(new ButtonListener());
         swapJustification = new JButton("Swap Justification");
         //swapJustification.addActionListener(new ButtonListener());
         createOutput = new JButton("Create New Output");
         //createOutput.addActionListener(new ButtonListener());
		 showAnalysis = new JButton("Show Analysis");
		 //showAnalysis.addActionListener(new ButtonListener());
		 
		 /*
		 //listeners for the buttons
		 selectInput.addActionListener(new ButtonListener());
		 swapJustification.addActionListener(new ButtonListener());
		 createOutput.addActionListener(new ButtonListener());
		 showAnalysis.addActionListener(new ButtonListener());
        */
		 
		 buttons1 = new JPanel();
         buttons1.setLayout(new GridLayout(4,1));
         
         buttons1.add(selectInput).setPreferredSize(new Dimension(200,60));
         buttons1.add(swapJustification).setPreferredSize(new Dimension(200,60));
         buttons1.add(createOutput).setPreferredSize(new Dimension(200,60));
         buttons1.add(showAnalysis).setPreferredSize(new Dimension(200,60));
         
         
         JPanel textPanel = new JPanel();
         
         
         JTextArea textArea = new JTextArea();
         textArea.setLineWrap(true);
         textArea.setWrapStyleWord(true);
         
         
         JScrollPane areaScrollPane = new JScrollPane(textArea);
         areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         areaScrollPane.setPreferredSize(new Dimension(250,250));
         add(areaScrollPane);
         
         mainPanel.add(buttons1, BorderLayout.WEST);
         mainPanel.add(textPanel, BorderLayout.EAST);
         add(mainPanel);
	 }
	
	 private class ButtonListener implements ActionListener	{
		public void actionPerformed (ActionEvent event)	{
			if(event.getSource() == selectInput)	{
				
			}
			if(event.getSource() == swapJustification)	{
				
			}
			if(event.getSource() == createOutput)	{
				
			}
			if(event.getSource() == showAnalysis){
				
			}
		}
	 }
	 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 


