import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Display extends JFrame implements ActionListener, ChangeListener, MouseListener {

	private GameModel gameModel;
	private JButton[][] buttonArray;
	private JLabel[][] labelArray; 
	private JPanel foreground;
	private JPanel background;
	private JPanel canvas;
	private JButton reset;
	private JLabel minesLeft;
	private JLabel timer; 
	int numMinesLeft;
	private static final int BORDER = 30; 
	StopWatch s;
	boolean start = false; 
	Menu m;


	public Display(GameModel gameModel) {
		super("Minesweeper");
		this.gameModel = gameModel;
		buttonArray = new JButton[gameModel.getLength()][gameModel.getWidth()];
		labelArray = new JLabel[gameModel.getLength()][gameModel.getWidth()];	
		m = new Menu();
		setJMenuBar(m.getMenuBar());

		numMinesLeft = gameModel.getNumberOfMines();			
		setBackground(Color.WHITE);
		
		background = createBackground();
		foreground = createForeground();
		add(background);

		JPanel south = createSouthPanel();
		add(south, BorderLayout.SOUTH);

		pack();
		setSize(BORDER*gameModel.getWidth(), BORDER*gameModel.getLength()+BORDER*3);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.start();
	}

	private void reset() {
		
		remove(background);

		for (int i = 0; i < gameModel.getLength(); i++) {
			for (int j = 0; j < gameModel.getWidth(); j++) {
				remove(buttonArray[i][j]);
			}
		}

		gameModel.reset();
		numMinesLeft = gameModel.getNumberOfMines();
		minesLeft.setText("Mines left: " + Integer.toString(numMinesLeft));

		background = createBackground();
		createForeground();
		add(background);
		updateResetIcon(true);
		s.reset();
		background.updateUI();
	}

	public JPanel createBackground() {
		JPanel background = new JPanel();
		background.setLayout(null);
		background.setOpaque(false);
		background.setBackground(Color.WHITE);

		for (int i = 0; i < gameModel.getLength(); i++) {
	    	for (int j = 0; j < gameModel.getWidth(); j++) {
	    		
	    		labelArray[i][j] = new JLabel();
	    		
	    		if (gameModel.getStatus(i,j) == -1) {
	    			labelArray[i][j] = new JLabel(new ImageIcon("data/bomb1.png"), SwingConstants.CENTER);
	    		}
	    		else if (gameModel.getStatus(i,j) == 0) {
	    			labelArray[i][j] = new JLabel();
	    		}
	    		else {
	    			labelArray[i][j] = new JLabel(Integer.toString(gameModel.getStatus(i,j)), SwingConstants.CENTER);
	    		}

		        Border labelBorder = BorderFactory.createLineBorder(Color.lightGray, 1); // create a line border with the specified color and width
		        labelArray[i][j].setBorder(labelBorder); // set the border of this component
		        labelArray[i][j].setBounds(BORDER*j,BORDER*i,BORDER,BORDER);
		        labelArray[i][j].setPreferredSize(new Dimension(BORDER,BORDER));
		        background.add(labelArray[i][j]);
			}
		}
		return background;
	}

	public JPanel createForeground() {

	    for (int i = 0; i < gameModel.getLength(); i++) {
	    	for (int j = 0; j < gameModel.getWidth(); j++) {
		        
		        buttonArray[i][j] = new JButton();

		        buttonArray[i][j].setActionCommand(Integer.toString(i) + "," + Integer.toString(j));
		        buttonArray[i][j].addActionListener(this);
		        buttonArray[i][j].addChangeListener(this);
		        buttonArray[i][j].addMouseListener(this);

		        Border raisedBorder = BorderFactory.createRaisedBevelBorder();
		        buttonArray[i][j].setBorder(raisedBorder);
		        buttonArray[i][j].setBackground(new Color(224,224,224));
		        buttonArray[i][j].setOpaque(true);
		        buttonArray[i][j].setBounds(BORDER*j,BORDER*i,BORDER,BORDER);
		        add(buttonArray[i][j]);
	    	}
	    }
	    return foreground;
	}

	private JPanel createSouthPanel() {
		JPanel south = new JPanel();

		minesLeft = new JLabel("Mines left: " + Integer.toString(gameModel.getNumberOfMines()), SwingConstants.CENTER);
		south.add(minesLeft);

		reset = new JButton();
		reset.setActionCommand("Reset");
		reset.setSize(new Dimension(50,50));
		ImageIcon icon = new ImageIcon("data/happy1.png");
		Image imgSmall = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon smallicon = new ImageIcon(imgSmall);
		reset.setIcon(smallicon);
		reset.addActionListener(this);
		south.add(reset);

		timer = new JLabel("", SwingConstants.CENTER);
		s = new StopWatch(timer);
		south.add(timer);

		south.setLayout(new FlowLayout(FlowLayout.CENTER, (BORDER*gameModel.getWidth())/9, 0));


		
		return south;
	}
	private void showAllMines() {
		for (int i = 0; i < gameModel.getLength(); i++) {
			for (int j = 0; j < gameModel.getWidth(); j++) {
				if (gameModel.getStatus(i,j) == -1) {
					buttonArray[i][j].setVisible(false);
				}
			}	
		}
	}

	private void disableButtons() {
		for (int i = 0; i < gameModel.getLength(); i++) {
			for (int j = 0; j < gameModel.getWidth(); j++) {
					buttonArray[i][j].setEnabled(false);
			}
		}
	}

	private void updateResetIcon(boolean happy) {
		if (happy) {
			
			ImageIcon icon = new ImageIcon("data/happy1.png");
			Image imgSmall = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			ImageIcon smallicon = new ImageIcon(imgSmall);
			reset.setIcon(smallicon);
		}
		else {
			reset.setIcon(new ImageIcon("data/mad.png"));
		}
	}

	private void updateResetIcon(String party) {
		reset.setIcon(new ImageIcon("data/party1.png"));
	} 

	private JButton[] getNeighborButtonsOf(int x, int y) {

		JButton[] neighbors = new JButton[8];

		try {neighbors[0] = buttonArray[x-1][y-1];} catch (IndexOutOfBoundsException e) {neighbors[0] = null;}
		try {neighbors[1] = buttonArray[x-1][y];} catch (IndexOutOfBoundsException e) {neighbors[1] = null;}
		//hold y
		try {neighbors[2] = buttonArray[x-1][y+1];} catch (IndexOutOfBoundsException e) {neighbors[2] = null;}
		try {neighbors[3] = buttonArray[x][y+1];} catch (IndexOutOfBoundsException e) {neighbors[3] = null;}
		//+x +y
		try {neighbors[4] = buttonArray[x+1][y+1];} catch (IndexOutOfBoundsException e) {neighbors[4] = null;}
		//-x -y
		try{neighbors[5] = buttonArray[x+1][y];} catch (IndexOutOfBoundsException e) {neighbors[5] = null;}
		//+x -y
		try {neighbors[6] = buttonArray[x+1][y-1];} catch (IndexOutOfBoundsException e) {neighbors[6] = null;}
		//-x +y
		try {neighbors[7] = buttonArray[x][y-1];} catch (IndexOutOfBoundsException e) {neighbors[7] = null;}
	

	return neighbors;

	}

	private Point[] getNeighborPointsOf(int x, int y) {
		return gameModel.getNeighborsOf(x,y);
	}

	private boolean checkWin() {
		boolean win = false; 
		int f = 0;
		for (int x = 0; x < gameModel.getLength(); x++) {
			for (int y = 0; y < gameModel.getWidth(); y++) {
				if (buttonArray[x][y].isVisible()) {
					if (gameModel.getStatus(x,y) != -1) {
						f++;
					}
					else {
						win = true;
					}
				}
			}
		}
		if (f > 0) {
			return false;
		}
		else {
			return win;
		}
	}
		
	public void actionPerformed(ActionEvent e) {
		
		JButton clicked = (JButton)(e.getSource());
		JButton tmp;

		if (clicked.getActionCommand() == "Reset") {
			tmp = clicked;
			reset();
		}

		else {

			String actionCommand = clicked.getActionCommand();
			int commaLoc = actionCommand.indexOf(",");
			int x = Integer.parseInt(actionCommand.substring(0,commaLoc));
			int y = Integer.parseInt(actionCommand.substring(commaLoc+1));

			if (gameModel.hasFlag(x,y)) {
				//do nothing 
			}

			else {

				clicked.setVisible(false);

				if (gameModel.getStatus(x,y) == -1) {
					showAllMines();
					disableButtons();
					updateResetIcon(false);
					s.stop();
				}
			
				else if (gameModel.getStatus(x,y) == 0) {
					LinkedQueue<Point[]> queue = new LinkedQueue<Point[]>();
					Point[] neighbors = getNeighborPointsOf(x,y);
					queue.enqueue(neighbors);
					buttonArray[x][y].setVisible(false);

					while (!queue.isEmpty()) {
						neighbors = queue.dequeue();

						for (int i = 0; i < neighbors.length; i++) {
							if (neighbors[i] != null) {
								if (buttonArray[neighbors[i].getX()][neighbors[i].getY()].isVisible()) {

									if (gameModel.hasFlag(neighbors[i].getX(), neighbors[i].getY())) {
										gameModel.setFlag(neighbors[i].getX(), neighbors[i].getY(), false); 
								 		numMinesLeft++;
								 		minesLeft.setText("Mines left: " + Integer.toString(numMinesLeft));
									}

									buttonArray[neighbors[i].getX()][neighbors[i].getY()].setVisible(false);

									if (neighbors[i].getStatus() == 0) {
										Point[] subNeighbors = getNeighborPointsOf(neighbors[i].getX(), neighbors[i].getY());
										queue.enqueue(subNeighbors);
									}
								} 
							}
						}
					}
				}
			}			
		}
		boolean win = checkWin();	
		if (win) {
			disableButtons();
			updateResetIcon("party");
			s.stop();
		}
	}
	public void stateChanged(ChangeEvent e) {
    	JButton abutton = (JButton) (e.getSource());
    	ButtonModel model = abutton.getModel();
    	
    	if (model.isPressed()) {
    		abutton.setBackground(Color.lightGray);
    		abutton.setOpaque(true);
    	}
    	else {
    		abutton.setBackground(new Color(224,224,224));
    	}

    }

    public void mousePressed(MouseEvent e) {
    	JButton abutton = (JButton) (e.getSource());
    	ImageIcon i = new ImageIcon("data/flag.png");

		String actionCommand = abutton.getActionCommand();
		int commaLoc = actionCommand.indexOf(",");
		int x = Integer.parseInt(actionCommand.substring(0,commaLoc));
		int y = Integer.parseInt(actionCommand.substring(commaLoc+1));
    	
    	if (SwingUtilities.isRightMouseButton(e)) {
    		if (abutton.getIcon() == null) {
    			abutton.setIcon(i);
    			gameModel.setFlag(x,y,true);
    			numMinesLeft--;
    			minesLeft.setText("Mines left: " + Integer.toString(numMinesLeft));
    		}
    		else {
    			abutton.setIcon(null);
    			gameModel.setFlag(x,y,false);
    			numMinesLeft++;
    			minesLeft.setText("Mines left: " + Integer.toString(numMinesLeft));
    		}
    	}
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }







}