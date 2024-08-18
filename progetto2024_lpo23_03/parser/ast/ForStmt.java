package progetto2024_lpo23_03.parser.ast;

import static java.util.Objects.requireNonNull;

import progetto2024_lpo23_03.visitors.Visitor;

//'for' '(' 'var' IDENT 'of Exp ')' Block 

public class ForStmt implements Stmt {
	private final Variable ident;
	private final Exp exp;
	private final Block block;

	public ForStmt(Variable ident, Exp exp, Block block) {
		this.ident = requireNonNull(ident);
		this.exp = requireNonNull(exp);
		this.block = requireNonNull(block);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + ident + "," + exp + "," + block + ")";
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitForStmt(ident, exp, block);
	}

}
