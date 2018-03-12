import javax.swing.JApplet;

public class project1 extends JApplet
 {
  private final int WIDTH = 800;
  private final int HEIGHT = 340;

  public void init()
   {
       jPanel panel = new jPanel(WIDTH,HEIGHT);
       getContentPane().add(panel);
       setSize(WIDTH,HEIGHT);
   }
 }