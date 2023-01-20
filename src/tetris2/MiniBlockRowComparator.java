package tetris2;

import java.util.Comparator;

public class MiniBlockRowComparator implements Comparator<MiniBlock> {

	@Override
	public int compare(MiniBlock miniBlock1, MiniBlock miniBlock2) {
		
		return Integer.compare(miniBlock1.getRowOffset(), miniBlock2.getRowOffset());
	}

	

}
