package in.co.praveenkumar.poptilesplus;

public class Cell {
	int value = 0;
	Boolean filled = false;

	/**
	 * Get the cell value
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Get the filled state
	 * 
	 * @return
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Set the cell value
	 */
	public Boolean getFilled() {
		return filled;
	}

	/**
	 * Get the filled state
	 */
	public void setFilled(Boolean filled) {
		this.filled = filled;
	}

}
