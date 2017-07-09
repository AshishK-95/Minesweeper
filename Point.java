public class Point {
	private int x;
	private int y;
	private int status;
	public boolean available;
	public boolean hasFlag; 

	public Point(int x, int y, boolean available, int status, boolean flag) {
		this.x = x;
		this.y = y;
		this.status = status; 
		this.available = available; 
		this.hasFlag = flag;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getStatus() { 
		return status;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailability(boolean value) {
		this.available = value;
	}

	public void setStatus(int value) {
		this.status = value;
	}

	public boolean isBomb() {
		return status == -1;
	}

	public void setFlag(boolean value) {
		hasFlag = value;
	}

	public boolean hasFlag() {
		return hasFlag;
	}

	@Override
	public String toString() {
		String s = "Point (" + x + "," + y + "," + available + "," + status + ")";

		return s;
	}


}