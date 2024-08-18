package progetto2024_lpo23_03.parser.ast;

import static java.util.Objects.requireNonNull;

public abstract class DictTripleOp implements Exp{
	protected final Exp exp;
	protected final Exp left;
	protected final Exp right;
	

	protected DictTripleOp(Exp exp, Exp left, Exp right) {
		this.exp = requireNonNull(exp);
		this.left = requireNonNull(left);
		this.right = requireNonNull(right);
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s]", exp, right);
	}
}