package in.co.praveenkumar.poptilesplus.model;

public class Cell {
	int value = 0;
	Boolean filled = false;

	/**
	 * Get the cell value
	 * 
	 * @return cellValue
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Get the value of the cell. This will also update the state to filled.
	 * 
	 */
	public void setValue(int value) {
		this.value = value;
		this.filled = true;
	}

	/**
	 * Check if this cell is filled
	 * 
	 * @return filledState
	 */
	public Boolean isFilled() {
		return filled;
	}

	/**
	 * Get the filled state. Used after user clicks the cell
	 */
	public void setFilled(Boolean filled) {
		this.filled = filled;
	}

}
