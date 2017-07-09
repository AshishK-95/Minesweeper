import java.util.Random;

public class GameModel {
	
	private int length;
	private int width;
	private int numberOfMines; 
	private Point[][] pointArray;
	private static final int NUM_NEIGHBORS = 8; 

	public boolean AVAILABLE = true;


	public GameModel(int length, int width, int numberOfMines) {
		this.length = length;
		this.width = width;
		this.numberOfMines = numberOfMines;

		if (numberOfMines > (length*width)) {
			throw new IllegalArgumentException("Can't have more mines than size of board"); 
		}

		reset();
	}

	public void reset() {
		createBoard();
		randomizeMines();
		setStatusOfAllAvailableSquares();
	}
	
	public void createBoard() {
		pointArray = new Point[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				pointArray[i][j] = new Point(i,j,AVAILABLE, 0, false); 
			}
		}
	}

	public void randomizeMines() {
		Random rand = new Random();

		for (int i = 0; i < numberOfMines; i++) {
			int x = rand.nextInt(length);
			int y = rand.nextInt(width);

			if (pointArray[x][y].isAvailable()) {
				pointArray[x][y].setAvailability(false);
				pointArray[x][y].setStatus(-1);
			}
			else {
				i--;
			}
		}
	}

	public void setStatusOfAllAvailableSquares() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (pointArray[i][j].getStatus() != -1) {
					int c = countNeighborMinesOf(pointArray[i][j]);
					pointArray[i][j].setStatus(c);
				}
			}
		}
	}

	private int countNeighborMinesOf(Point p) {
		int c = 0;
		int x = p.getX();
		int y = p.getY();

		//hold x
		try {if (getStatus(x, y-1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		try {if (getStatus(x, y+1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		//hold y
		try {if (getStatus(x-1, y) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		try {if (getStatus(x+1, y) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		//+x +y
		try {if (getStatus(x+1, y+1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		//-x -y
		try{ if (getStatus(x-1, y-1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		//+x -y
		try {if (getStatus(x+1, y-1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}
		//-x +y
		try {if (getStatus(x-1, y+1) == -1) {c++;}} catch (IndexOutOfBoundsException e) {}

		return c;
	}

	public Point[] getNeighborsOf(int x, int y) {

		Point[] neighbors = new Point[NUM_NEIGHBORS];

		try {neighbors[0] = pointArray[x-1][y-1];} catch (IndexOutOfBoundsException e) {neighbors[0] = null;}
		try {neighbors[1] = pointArray[x-1][y];} catch (IndexOutOfBoundsException e) {neighbors[1] = null;}
		//hold y
		try {neighbors[2] = pointArray[x-1][y+1];} catch (IndexOutOfBoundsException e) {neighbors[2] = null;}
		try {neighbors[3] = pointArray[x][y+1];} catch (IndexOutOfBoundsException e) {neighbors[3] = null;}
		//+x +y
		try {neighbors[4] = pointArray[x+1][y+1];} catch (IndexOutOfBoundsException e) {neighbors[4] = null;}
		//-x -y
		try{neighbors[5] = pointArray[x+1][y];} catch (IndexOutOfBoundsException e) {neighbors[5] = null;}
		//+x -y
		try {neighbors[6] = pointArray[x+1][y-1];} catch (IndexOutOfBoundsException e) {neighbors[6] = null;}
		//-x +y
		try {neighbors[7] = pointArray[x][y-1];} catch (IndexOutOfBoundsException e) {neighbors[7] = null;}

	return neighbors;

	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getStatus(int x, int y) {
		return pointArray[x][y].getStatus();
	}

	public int getNumberOfMines() {
		return numberOfMines;
	}

	public void setFlag(int x, int y, boolean value) {
		pointArray[x][y].setFlag(value);
	}

	public boolean hasFlag(int x, int y) {
		return pointArray[x][y].hasFlag();
	}
}