import javax.swing.*;

public class Menu {

	JMenuBar menubar; 
	JMenu menu; 
	
	public AppleMenu() {
		menubar = new JMenuBar();
		menu = new JMenu("File");
		menubar.add(menu); 
	}

	public JMenuBar getMenuBar() {
		return menubar;
	}
}