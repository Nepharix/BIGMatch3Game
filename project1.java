import javax.swing.*;

public class project1
{
  public static void main(String[] args)
  {
	  JFrame frame = new JFrame("CSE 360 Project 1");
	  TextPanel panel = new TextPanel();
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setBounds(0, 0, 800, 340);
      frame.getContentPane().add(panel);
      frame.pack();
      frame.setVisible(true);
  }
}
