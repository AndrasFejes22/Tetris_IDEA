package tetris2;

import java.util.Comparator;

public class MiniBlockColumnComparator implements Comparator<MiniBlock> {

	@Override
	public int compare(MiniBlock miniBlock1, MiniBlock miniBlock2) {
		
		return Integer.compare(miniBlock1.getColumnOffset(), miniBlock2.getColumnOffset());
	}

}
