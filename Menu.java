import javax.swing.*;

public class Menu {

	JMenuBar menubar; 
	JMenu file,edit; 
	JMenuItem file_1, edit_1; 
	
	public Menu() {
		menubar = new JMenuBar();
		file = new JMenu("File");
		edit = new JMenu("Edit");
		file_1 = new JMenuItem("New Game");
		file.add(file_1);
		menubar.add(file);
		menubar.add(edit); 
	}

	public JMenuBar getMenuBar() {
		return menubar;
	}
}