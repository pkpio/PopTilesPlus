package in.co.praveenkumar.poptilesplus.helper;

public class Param {
	public static final int colums = 4;
	public static final int rows = 5;
	public static final int cells = colums * rows;
	/**
	 * Time (in milliseconds) after which screen will be filled with tiles if
	 * user hasn't interacted at all.
	 */
	public static final int fillTime = 10000;
	/**
	 * Time (in milliseconds) after which the next cell appears
	 */
	public static final int cellTime = fillTime / cells;
	/**
	 * Min cell time (in milliseconds) after which speed won't change
	 */
	public static final int minCellTime = 50;
}
