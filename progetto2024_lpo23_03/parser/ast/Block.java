package progetto2024_lpo23_03.parser.ast;

import static java.util.Objects.requireNonNull;

import progetto2024_lpo23_03.visitors.Visitor;

public class Block implements Stmt {
	private final StmtSeq stmtSeq;

	public Block(StmtSeq stmtSeq) {
		this.stmtSeq = requireNonNull(stmtSeq);
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", getClass().getSimpleName(), stmtSeq);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitBlock(stmtSeq);
	}

}
