package progetto2024_lpo23_03.parser;

import progetto2024_lpo23_03.parser.ast.Prog;

public interface Parser extends AutoCloseable {

	Prog parseProg() throws ParserException;

}