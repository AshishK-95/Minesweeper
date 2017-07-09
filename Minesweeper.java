public class Minesweeper {
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		GameModel g = new GameModel(16, 16, 10);
		Display d = new Display(g);
	}
}