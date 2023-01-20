package tetris2.blocks;

import tetris2.MiniBlock;

public class ZBlock extends TwoStateBlock {
	
	
	private boolean rotateClockwise;
	
	
	public ZBlock() {
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(1, 0));
		miniBlocks.add(new MiniBlock(1, 1));
	}

	@Override
	public Block copy() {
		Block copy = new LongBlock();
		copy(this, copy);
		return copy;
	}
	
	@Override
	public void rotate() {
		if(rotateClockwise) {
			super.rotate();
		} else {
			rotateCounterClockWise();
		}
		rotateClockwise = !rotateClockwise;
	}
	
	
}
