package tetris2.blocks;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tetris2.BlockType;
import tetris2.Level;
import tetris2.MiniBlock;
import tetris2.MiniBlockColumnComparator;
import tetris2.MiniBlockRowComparator;

public abstract class Block {
	
	// pozició, koordináták: legfelülről és középről indul mindig az aktuális Tetromino
	protected int row = 0;
	protected int column = Level.WIDTH/2;
	
	private static Random random = new Random();
	
	protected List<MiniBlock> miniBlocks = new ArrayList<>();
	
	/*
	public Block(BlockType blocktype) { //enum elegánsabb a felsorolás a switch-case-hez
		switch (blocktype) {
		case LONG : 
			// Block 1: 
			// ████
			miniBlocks.add(new MiniBlock(0, 0)); //tulajdonképpen csak koordinátákat rakunk be listába, de ez alapján rajzolható majd a draw()-ban***
			miniBlocks.add(new MiniBlock(0, -1));
			miniBlocks.add(new MiniBlock(0, 1));
			miniBlocks.add(new MiniBlock(0, 1));
			break;
		case SQUARE : 
			// Block 2:
			// ██
			// ██
			miniBlocks.add(new MiniBlock(0, 0));
			miniBlocks.add(new MiniBlock(0, 1));
			miniBlocks.add(new MiniBlock(1, 0));
			miniBlocks.add(new MiniBlock(1, 1));
			break;
		case Z : 
			// Block 3:
			// ██
			//  ██
			miniBlocks.add(new MiniBlock(0, 0));
			miniBlocks.add(new MiniBlock(0, -1));
			miniBlocks.add(new MiniBlock(1, 0));
			miniBlocks.add(new MiniBlock(1, 1));
			break;
		case T : 
			// Block 4:
			// ███
			//  █
			miniBlocks.add(new MiniBlock(0, 0));
			miniBlocks.add(new MiniBlock(-1, 0));
			miniBlocks.add(new MiniBlock(0, -1));
			miniBlocks.add(new MiniBlock(0, 1));
			break;
		}
	}
	*/
	
	// Statikus factory metódus:
	public static Block random() {
		BlockType[] blockTypes = BlockType.values();
		BlockType randomBlockType = blockTypes[random.nextInt(blockTypes.length)];
		 
		Block randomBlock = switch (randomBlockType) { // switch expression
		case LONG -> randomBlock = new LongBlock();

		case SQUARE -> randomBlock = new SquareBlock();

		case Z -> randomBlock = new ZBlock();
		
		case Z_MIRRORED -> randomBlock = new ZMirroredBlock();

		case T -> randomBlock = new TBlock();

		case L -> randomBlock = new LBlock();
		
		case L_MIRRORED -> randomBlock = new LMirroredBlock();

		};
		int numberOfRotations = random.nextInt(4);
		for(int i = 0; i < numberOfRotations; i++) {
		randomBlock.rotate(); // véletlenszerűen forgatunk rajt 0-át, 1-et, 2-öt, vagy 3-at
		}
		//row beállítása:
		List<MiniBlock> miniBlocksCopy = new ArrayList<>(randomBlock.miniBlocks);
		Collections.sort(miniBlocksCopy, new MiniBlockRowComparator());
		MiniBlock topMiniBlock = miniBlocksCopy.get(0);
		randomBlock.setRow(-topMiniBlock.getRowOffset());
		return randomBlock;
	}
	
	//public Block() {} //üres konstruktor
	
	public abstract Block copy(); 
	
	protected void copy(Block original, Block copied) {
		copied.row = original.row;
		copied.column = original.column;
		for(MiniBlock miniBlock : original.miniBlocks) {
			copied.miniBlocks.add(new MiniBlock(miniBlock)); //inlined
		}
	}
		
	
	public List<MiniBlock> getMiniBlocks() {
		return miniBlocks;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	
	public void setRow(int row) {
		this.row = row;
	}



	public void setColumn(int column) {
		this.column = column;
	}



	public void draw(String[][] drawBuffer) { //*ezért átadjuk neki a pályát (drawbuffer)
		for(MiniBlock miniBlock : miniBlocks) {
			int actualRow = row + miniBlock.getRowOffset(); //***kiszámoljuk a tényleges koordinátákat 1
			int actualColumn = column + miniBlock.getColumnOffset(); //***kiszámoljuk a tényleges koordinátákat 2
			if(isCoordinatesWithinBounds(actualRow, actualColumn)) { // belerajoljuk a pályába az aktuális Tetromino-t. (If feltétel ne tudjon kimenni)
				drawBuffer[actualRow][actualColumn] = "██"; // ez a tényleges "rajz", a ██-oknak minden ciklusban megvan az egymáshoz képesti, és a pályabeli helyük is.
			}
		}
		
	}

	private boolean isCoordinatesWithinBounds(int actualRow, int actualColumn) {
		return actualRow >= 0 && actualRow < Level.HEIGHT && actualColumn >= 0 && actualColumn < Level.WIDTH;
	}

	public void moveDown() {
		row++ ;
		
	}
	
	public void moveLeft() {
		if(!isAtLeft()) {
		column--;
		}
	}
	
	public void moveRight() {
		if(!isAtRight()) {
			column++;
		}
	}

	// legalsó, és a két legszélső (jobboldali, baloldali), miniblock koordinátájának meghatározásai:
	public boolean isAtBottom() {
		// másolat a miniblokkokról:
		List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlocks);
		Collections.sort(miniBlocksCopy, new MiniBlockRowComparator());
		MiniBlock bottomMiniBlock = miniBlocksCopy.get(miniBlocksCopy.size() - 1);
		int actualRow = row + bottomMiniBlock.getRowOffset();
		return actualRow >= Level.HEIGHT -1 ;
	}

	
	private boolean isAtRight() {
		// másolat a miniblokkokról:
		List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlocks);
		Collections.sort(miniBlocksCopy, new MiniBlockColumnComparator());
		MiniBlock rightMiniBlock = miniBlocksCopy.get(miniBlocksCopy.size() - 1);
		int actualColumn = column + rightMiniBlock.getColumnOffset();
		return actualColumn >= Level.WIDTH-1;
	}
	
	private boolean isAtLeft() {
		// másolat a miniblokkokról:
		List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlocks);
		Collections.sort(miniBlocksCopy, new MiniBlockColumnComparator());
		MiniBlock leftMiniBlock = miniBlocksCopy.get(0);
		int actualColumn = column + leftMiniBlock.getColumnOffset();
		return actualColumn <= 0;
		
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String [][] drawBuffer = new String[5][5]; // itt ez majd legyen int valami, int valami2**
		for (int row = 0; row < 5; row++) { //**itt is a beégetett számok, most 5, 5
			for (int column = 0; column < 5; column++) {
				drawBuffer[row][column] = "  ";
			}
			
		}
		//ez is ismétlődő kódrészlet!*
		
		int rowAnchor = 2;
		int columnAnchor = 2;
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = rowAnchor + miniBlock.getRowOffset();
			int miniblockColumn = columnAnchor + miniBlock.getColumnOffset();
			// berakjuk a pályára:
			drawBuffer[miniblockRow][miniblockColumn] = "██";
		//* idáig	
		}
		for (int row = 0; row < 5; row++) {//**itt is a beégetett számok, most 5, 5
			for (int column = 0; column < 5; column++) {
				builder.append(drawBuffer[row][column]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}



	public void rotate() {
		for(MiniBlock miniBlock : miniBlocks) {
			miniBlock.rotateClockwise();
		}
		
	}
	
	protected void rotateCounterClockWise() {
		for(MiniBlock miniBlock : miniBlocks) {
			miniBlock.rotateCounterClockwise();
		}
		
	}
	
	

	
	
	// Block 1: 
	// ████
	// Block 2:
	// ██
	// ██
	// Block 3:
	// ██
	//  ██
	// Block 4:
	// ███
	//  █

}