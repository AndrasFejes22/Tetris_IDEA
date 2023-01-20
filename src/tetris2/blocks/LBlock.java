package tetris2.blocks;

import tetris2.MiniBlock;

public class LBlock extends Block {
	
	public LBlock() {
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(-2, 0));
		miniBlocks.add(new MiniBlock(-1, 0));
		miniBlocks.add(new MiniBlock(0, 1));
	}
	

	@Override
	public Block copy() {
		Block copy = new LongBlock();
		copy(this, copy);
		return copy;
	}

}
