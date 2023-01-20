package tetris2;

import java.util.List;

import tetris2.blocks.Block;

/**
 * Need to improve:
 * 1.: L block rotation
 * 2.: scoring: for example, double points for removing two lines
 * 3.: save individual record
 */

public class TetrisGame implements Runnable{
	
	private MainWindow mainWindow;
	private Level level;
	private Block fallingBlock;
	private Block nextBlock;
	private int score;
	private boolean drop;
	private boolean pause;
	private boolean start = true;
	

	public TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.fallingBlock = Block.random();
		this.nextBlock = Block.random();
		//this.block = new Block(BlockType.LONG);// ez átmeneti első körben rajzolja ki valamelyik alakzat (lefele) mozgását
		//this.block = new Block(BlockType.SQUARE);// ez átmeneti első körben rajzolja ki valamelyik alakzat (lefele) mozgását
		//this.block = new Block(BlockType.T);// ez átmeneti első körben rajzolja ki valamelyik alakzat (lefele) mozgását
		mainWindow.setTetrisGame(this);
	}
	
	
	/*
	private Block generateRandomBlock() {
		BlockType[] blockTypes = BlockType.values();
		BlockType randomBlockType = blockTypes[random.nextInt(blockTypes.length)];
		Block block = new Block(randomBlockType);
		// block.rotate(); // véletlenszerűen forgatunk rajt 0-át, 1-et, 2-öt, vagy 3-at
		return block;
	}
	*/


	@Override
	public void run() {
		while(true) {
			while(!start) {
				sleep(1000);
			}
			start = false;
		while(!isGameOver()) {
		boolean blockLanded = false;
		updateNextBlockonView();
		 do {
			 drawWithFallingBlock();
			sleep(500);
			if(!pause) {
			blockLanded = move();
			}
		
		} while(!blockLanded); // a !block.isAtBottom() már nem kell mert a canMoveDown() megoldja!*
		 level.mergeWith(fallingBlock);
		 // meg kell nézni, lett-e teljes sor:
		 int NumberOfCompletedRows = level.removeCompletedRows();
		 score += NumberOfCompletedRows; // az alap 0 pontot megnöveljük az eltüntetett sorok számával
		 updateScore(); //kiírjuk az új pontot
		 drop = false;
		 fallingBlock = nextBlock;
		 nextBlock = Block.random();
		}
		mainWindow.getGameStateLabel().setText("GAME OVER");
		animateGameOver();
		mainWindow.getPauseOrResumeButton().setText("Start");
		mainWindow.getScoreTextField().setText("0");
		}
		
	}
	
	private void animateGameOver() {
		//lentről felfele betelíti a pályát:
		for (int row = Level.HEIGHT - 1; row >= 0; row--) {
			for (int column = 0; column < Level.WIDTH; column++) {
				level.setCellAt(row, column, "██");
			}
			drawLevelOnly();
			sleep(100);
		}
		//fenttről lefele betelíti a pályát:
		for (int row = 0; row < Level.HEIGHT; row++) {
			for (int column = 0; column < Level.WIDTH; column++) {
				level.setCellAt(row, column, "  ");
			}
			drawLevelOnly();
			sleep(100);
		}
	}


	private void updateNextBlockonView() { //oldalsó ablak
		//Block block = generateRandomBlock(); //egyelőre beégetve de ez lesz a random
		mainWindow.getNextBlockTextArea().setText(nextBlock.toString());
	}



	private void updateScore() {
		mainWindow.getScoreTextField().setText(Integer.toString(score));
	}



	//* mivel a (int row, int column) return "██";-al tér vissza, tehát minta az egész pálya "██"-al lenne körbevéve



	private boolean move() {
		if (canMoveDown()) {
			if (!pause) {
				fallingBlock.moveDown(); // csak ha nincs megállítva a játék
			}
			return false; // nem landolt
		} else {
			return true; // landolt
		}

	}



	private boolean canMoveDown() { //tud-e mozogni (lefele)
		List<MiniBlock> miniBlocks = fallingBlock.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = fallingBlock.getColumn() + miniBlock.getColumnOffset();
			// pálya adott koordinátán lévő cellája lekérdezése:
			String cellBelow = level.getCellAt(miniblockRow +1 , miniblockColumn);
			if(!"  ".equals(cellBelow)) {
				return false;
			} 
			
		}
		return true;
	}



	private void drawLevelOnly() {
		String buffer = level.toString(); 
		mainWindow.getGameArea().setText(buffer);
	}
	
	private void drawWithFallingBlock() {
		String buffer = level.toString(fallingBlock); // az egész pálya mindenestül
		mainWindow.getGameArea().setText(buffer);
	}

	private void sleep(long sleep) {
		try {
			if(drop) {
				Thread.sleep(100);
			} else {
				Thread.sleep(sleep);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveBlockLeft() {
		if(canMoveLeft()) {
		fallingBlock.moveLeft();
		}
	}
	
	public void moveBlockRight() {
		if(canMoveRight()) {
		fallingBlock.moveRight();
		}
	}
	
	private boolean canMoveLeft() {
		List<MiniBlock> miniBlocks = fallingBlock.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = fallingBlock.getColumn() + miniBlock.getColumnOffset();
			// pálya adott koordinátán lévő cellája lekérdezése:
			String cellToTheLeft = level.getCellAt(miniblockRow, miniblockColumn - 1);
			if (!"  ".equals(cellToTheLeft)) {
				return false;
			}
		}
		return !drop && !pause;
	}
	
	private boolean canMoveRight() {
		List<MiniBlock> miniBlocks = fallingBlock.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = fallingBlock.getColumn() + miniBlock.getColumnOffset();
			// pálya adott koordinátán lévő cellája lekérdezése:
			String cellToTheRight = level.getCellAt(miniblockRow, miniblockColumn + 1);
			if (!"  ".equals(cellToTheRight)) {
				return false;
			}
		}
		return !drop && !pause; //true volt (így zuhanás közben nem tudom balra-jobbra mozgatni)
	}



	public void dropBlock() {
		if(!pause) {
		drop = true;
		}
	}



	public void pause() {
		pause = true;
	}



	public void resume() {
		pause = false;
		
	}



	public void rotate() {
		if(canRotate() && !pause) {
			fallingBlock.rotate();
		}
		
	}



	private boolean canRotate() {
		//másolat készítése:
		Block copyOfFallingBlock = fallingBlock.copy();
		copyOfFallingBlock.rotate();//elforgatjuk "emuláljuk"
		//megnézzük hul helyezkedik/helyezkedne el forgatás után
		List<MiniBlock> miniBlocks = copyOfFallingBlock.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = copyOfFallingBlock.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = copyOfFallingBlock.getColumn() + miniBlock.getColumnOffset();
			// pálya adott koordinátán lévő cellája lekérdezése:
			String cell = level.getCellAt(miniblockRow, miniblockColumn);
			if (!"  ".equals(cell)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isGameOver() {
		
		List<MiniBlock> miniBlocks = fallingBlock.getMiniBlocks();
		for (MiniBlock miniBlock : miniBlocks) {
			// koordináták lekérése
			int miniblockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
			int miniblockColumn = fallingBlock.getColumn() + miniBlock.getColumnOffset();
			// pálya adott koordinátán lévő cellája lekérdezése:
			String cell = level.getCellAt(miniblockRow, miniblockColumn);
			if (!"  ".equals(cell)) {
				return true;
			}
		}
		return false;
	}


	public void start() {
		this.start = true;
	}
	
	
	
	



	
	
	
	

}
