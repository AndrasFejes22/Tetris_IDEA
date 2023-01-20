package tetris2.blocks;

public abstract class TwoStateBlock extends Block{
	
	private boolean rotateClockwise;
	
	
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
