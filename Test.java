import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Test extends JFrame implements ActionListener, ChangeListener {
	

	public Test() {
        JPanel background = new JPanel();
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton("Click Me!");
            button.addActionListener(this);
            button.addChangeListener(this);
            Border buttonBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // create a line border with the specified color and width
            button.setBorder(buttonBorder); // set the border of this component
            button.setPreferredSize(new Dimension(50,50));
            background.add(button);
            add(background);
        }
	    setVisible(true);
	    pack();

    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

    public void actionPerformed(ActionEvent e) {
        JPanel foreground = new JPanel();
    	JButton clicked = (JButton)(e.getSource());
        JLabel label = new JLabel("hello");
        foreground.add(label);
        System.out.println(clicked.getText());
        clicked.setBorder(BorderFactory.createLineBorder(Color.BLACK,0));
        clicked.setEnabled(false);
        clicked.setText("-1");
        add(foreground);
        
    }

    public void stateChanged(ChangeEvent e) {
    	JButton abutton = (JButton) (e.getSource());
    	ButtonModel model = abutton.getModel();
    	
    	if (model.isPressed()) {
    		abutton.setBackground(Color.lightGray);
    		abutton.setOpaque(true);
    	}
    	else {
    		abutton.setBackground(Color.WHITE);
    	}

    }

	public static void main(String[] args) {
		Test t = new Test();
	}
}