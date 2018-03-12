import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;


public class controlPanel1 extends jPanel{

	 private JButton selectInput, swapJustification, createOutput, showAnalysis ;
	 private JPanel buttons1;
	 private int width, height;
	 private controlPanel1 cPanel;
	 
	 
	 public controlPanel1(int width, int height, Color color){
		 this.width = width;
         this.height = height;
         
         
         cPanel = new controlPanel1(height, height, color);
         
         
         //create the buttons
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
         
         buttons1.add(selectInput);
         buttons1.add(swapJustification);
         buttons1.add(createOutput);
         buttons1.add(showAnalysis);
         
       
         JPanel panel2 = new JPanel();
         panel2.setLayout(new BorderLayout());
         panel2.add(buttons1, BorderLayout.CENTER);
        
         
         
	 }
	 
	/* private class ButtonListener implements ActionListener
	   {
	       public void actionPerformed(ActionEvent event)
	        {
	    	  
	            Object action = event.getSource();
	            if(action == selectInput){
	            	cPanel.selectInput();
	            }
	           if(action== start){
	        	   tPanel.resume();
	           }
	            
	        }	 
}*/
}