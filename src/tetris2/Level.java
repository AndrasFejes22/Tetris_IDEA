package tetris2;

import java.util.List;

import tetris2.blocks.Block;

//import org.graalvm.compiler.nodes.cfg.Block;

public class Level {

	public static final int WIDTH =10; //szélesség, column
	public static final int HEIGHT =15;
	private String[][] level = new String[HEIGHT][WIDTH];
	
	public Level() {
		for(int row = 0; row < HEIGHT; row++) {
			for(int column = 0; column < WIDTH; column++) {
				level[row][column] = "  ";
			}
		}
		/*
		//teszt ütközésvizsgálathoz: fekvő "straight tetromino"
		level[14][2] = "██";
		level[14][3] = "██";
		level[14][4] = "██";
		level[14][5] = "██";
		//teszt: álló "straight tetromino"
		level[14][6] = "██";
		level[13][6] = "██";
		level[12][6] = "██";
		//level[11][6] = "██";
		//level[10][6] = "██";
		//level[9][6] = "██";
		*/
		
		
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(level[row][column]);
			}
			builder.append("\n");
		}

		return builder.toString();
	}
	
	public String toString(Block block) { //átadjuk a block-ot, a pályának nem a része a block
		String[][] drawBuffer = copyArray(level);
		block.draw(drawBuffer); //a block rajzolja bele magát a pályába*
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(drawBuffer[row][column]);
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	private String[][] copyArray(String[][] arrayToCopy) {
		String[][] copy = new String[arrayToCopy.length][arrayToCopy[0].length];
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				copy[row][column] = arrayToCopy[row][column];
			}
		}
		return copy;
	}

	public String getCellAt(int row, int column) {
		if(isCoordinatesWithinBounds(row, column)) {
		return level[row][column];
		} else {
			return "██";
		}
	}
	
	private boolean isCoordinatesWithinBounds(int row, int column) { //copyzva lett  a Block class-ból!
		return row >= 0 && row < Level.HEIGHT && column >= 0 && column < Level.WIDTH;
	}

	public void mergeWith(Block block) {
		List<MiniBlock> miniBlocks = block.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = block.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = block.getColumn() + miniBlock.getColumnOffset();
			level[miniblockRow][miniblockColumn] = "██";
			
			
		}
		
		
	}

	public int removeCompletedRows() {
		int rowCounter = 0;
		for (int row = 0; row < HEIGHT; row++) {
			int columnCounter = 0;
			for (int column = 0; column < WIDTH; column++) {
				if("██".equals(level[row][column])) {
					columnCounter++;
				}
			}
			if(columnCounter >= WIDTH) {
				rowCounter++;
				remove(row);
				
			}
		}
		
		return rowCounter;
	}

	private void remove(int rowToRemove) {
		for (int row = rowToRemove; row >= 0; row--) {
			for (int column = 0; column < WIDTH; column++) {
				level[row][column] = row <= 0 ? "  " : level[row-1][column];
			}
		}
	}

	public void setCellAt(int row, int column, String value) {
		level[row][column] = value;
		
	}
}
