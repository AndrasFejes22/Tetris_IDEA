package tetris2.blocks;

import tetris2.MiniBlock;

public class LongBlock extends TwoStateBlock{
	
	
	
	public LongBlock() {
		miniBlocks.add(new MiniBlock(0, 0)); //tulajdonk�ppen csak koordin�t�kat rakunk be list�ba, de ez alapj�n rajzolhat� majd a draw()-ban***
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
