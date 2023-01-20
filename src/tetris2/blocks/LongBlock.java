package tetris2.blocks;

import tetris2.MiniBlock;

public class LongBlock extends TwoStateBlock{
	
	
	
	public LongBlock() {
		miniBlocks.add(new MiniBlock(0, 0)); //tulajdonképpen csak koordinátákat rakunk be listába, de ez alapján rajzolható majd a draw()-ban***
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(0, 1));
		miniBlocks.add(new MiniBlock(0, 1));
	}

	@Override
	public Block copy() {
		Block copy = new LongBlock();
		copy(this, copy);
		return copy;
	}

	

	
}
