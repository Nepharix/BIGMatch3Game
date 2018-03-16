import java.util.*;	
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;


public class controlPanel1 extends JPanel{

	
	 private int width, height;
	 private int panelNum;
	 
	 public controlPanel1(int width, int height){
		 this.width = width;
         this.height = height;
         panelNum=2;
         
         TextPanel[] textpanels;
         textpanels = new TextPanel[panelNum];
         
         
         setLayout(new GridLayout(panelNum, 1));
         for (int i=0; i< panelNum; i++)
        	 add(textpanels[i]);
         setPreferredSize(new Dimension(width, height));
        
         
         
	 }
}