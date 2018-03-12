import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;

public class gui1 extends jPanel{
	
	 
	 public gui1(int wIDTH, int hEIGHT) {
		super(wIDTH, hEIGHT);
		// TODO Auto-generated constructor stub
	}


	private int width;
	private int height;
	private int panelNum;
	
	
	public void gui1(int width, int height)
	   {
	       this.width = width;
	       this.height = height;
	       panelNum = 2; 
	       
	       
	

	       setPreferredSize(new Dimension(width,height));
	   }


	private void setPreferredSize(Dimension dimension) {
		// TODO Auto-generated method stub
		
	}


	private void setLayout(GridLayout gridLayout) {
		// TODO Auto-generated method stub
		
	}
	 }


