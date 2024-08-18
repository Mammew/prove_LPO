package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class BoolLiteral extends AtomicLiteral<Boolean> {

	public BoolLiteral(boolean b) {
		super(b);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitBoolLiteral(value);
	}
}
