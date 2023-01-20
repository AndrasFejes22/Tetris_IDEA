package tetris2;

public class MiniBlock {

	private int rowOffset; //h�ny sorral, 
	private int columnOffset; //�s h�ny oszloppal van od�bb a block a referencia block-hoz k�pest
	
	
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
		int oldRowOffset = rowOffset; //eredeti koordin�ta itt csak magyar�zatk�ppen szerepel
		int oldColumnOffset = columnOffset; //eredeti koordin�ta itt csak magyar�zatk�ppen szerepel
		int newRowOffset = oldColumnOffset;
		int newColumnOffset = -oldRowOffset;
		rowOffset = newRowOffset;
		columnOffset  = newColumnOffset;
	}
	
	public void rotateCounterClockwise() {
		int oldRowOffset = rowOffset; //eredeti koordin�ta itt csak magyar�zatk�ppen szerepel
		int oldColumnOffset = columnOffset; //eredeti koordin�ta itt csak magyar�zatk�ppen szerepel
		int newRowOffset = -oldColumnOffset;//itt k�l�nb�z� a fentihez k�pest
		int newColumnOffset = oldRowOffset;//meg itt k�l�nb�z� a fentihez k�pest
		rowOffset = newRowOffset;
		columnOffset  = newColumnOffset;
	}
}
