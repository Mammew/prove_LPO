package progetto2024_lpo23_03.parser.ast;

import static java.util.Objects.requireNonNull;

public abstract class DictBinOp implements Exp{
	protected final Exp exp;
	protected final Exp right;

	protected DictBinOp(Exp exp, Exp right) {
		this.exp = requireNonNull(exp);
		this.right = requireNonNull(right);
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s]", exp, right);
	}
}
