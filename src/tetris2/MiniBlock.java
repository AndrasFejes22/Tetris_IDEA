package tetris2;

public class MiniBlock {

	private int rowOffset; //hány sorral, 
	private int columnOffset; //és hány oszloppal van odébb a block a referencia block-hoz képest
	
	
	public MiniBlock(int rowOffset, int columnOffset) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
	}
	
	public MiniBlock(MiniBlock other) { //"copy" konstruktor
		this.rowOffset = other.rowOffset;
		this.columnOffset = other.columnOffset;
	}


	public int getRowOffset() {
		return rowOffset;
	}


	public int getColumnOffset() {
		return columnOffset;
	}


	public void rotateClockwise() {
		int oldRowOffset = rowOffset; //eredeti koordináta itt csak magyarázatképpen szerepel
		int oldColumnOffset = columnOffset; //eredeti koordináta itt csak magyarázatképpen szerepel
		int newRowOffset = oldColumnOffset;
		int newColumnOffset = -oldRowOffset;
		rowOffset = newRowOffset;
		columnOffset  = newColumnOffset;
	}
	
	public void rotateCounterClockwise() {
		int oldRowOffset = rowOffset; //eredeti koordináta itt csak magyarázatképpen szerepel
		int oldColumnOffset = columnOffset; //eredeti koordináta itt csak magyarázatképpen szerepel
		int newRowOffset = -oldColumnOffset;//itt különbözõ a fentihez képest
		int newColumnOffset = oldRowOffset;//meg itt különbözõ a fentihez képest
		rowOffset = newRowOffset;
		columnOffset  = newColumnOffset;
	}
}
