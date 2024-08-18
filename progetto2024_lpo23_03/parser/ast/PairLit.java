package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class PairLit extends BinaryOp {
	public PairLit(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitPairLit(left, right);
	}
}
