import javax.swing.*;

public class project1 extends JApplet
 {
  private final int WIDTH = 800;
  private final int HEIGHT = 340;

  public void init()
   {
       TextPanel panel = new TextPanel();
       getContentPane().add(panel);
       setSize(WIDTH,HEIGHT);
   }
 }