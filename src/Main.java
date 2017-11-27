import javax.swing.JFrame;
import javax.swing.JPanel;

class Main extends JFrame {

	private JFrame thisFrame;

	//Constructor - this runs first
	private Main() {
		super("Start Screen");
		this.thisFrame = this;

		this.setSize(1920,1080);
		this.setLocationRelativeTo(null); //start the frame in the center of the screen;
		this.setResizable (true);

		JPanel main = new JPanel();

		//add the main panel to the frame
		this.add(main);

		this.add(new StartingFrame());

		//Start the app
		this.setVisible(true);
	}


	//Analysis method starts this application
	public static void main(String[] args) {
		new Main();
	}

}

