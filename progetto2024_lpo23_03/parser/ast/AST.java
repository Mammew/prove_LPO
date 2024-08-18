package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public interface AST {
	<T> T accept(Visitor<T> visitor);
}
