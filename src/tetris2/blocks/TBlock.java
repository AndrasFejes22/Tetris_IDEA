package tetris2.blocks;

import tetris2.MiniBlock;

public class TBlock extends Block {
	
	public TBlock() {
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(-1, 0));
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(0, 1));
	}

	@Override
	public Block copy() {
		Block copy = new LongBlock();
		copy(this, copy);
		return copy;
	}

}
