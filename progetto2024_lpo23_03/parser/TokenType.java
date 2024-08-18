package progetto2024_lpo23_03.parser;
//package parser;

public enum TokenType {
	// used internally by the tokenizer, should never been accessed by the parser
	SYMBOL, KEYWORD, SKIP, 
	// non singleton categories
	IDENT, NUM,  
	// end-of-file
	EOF, 	
	// symbols
	// Add OPEN_S_PAR, CLOSE_S_PAR, DOUBLE_DOT
	ASSIGN, MINUS, PLUS, TIMES, NOT, AND, EQ, STMT_SEP, PAIR_OP, OPEN_PAR, OPEN_S_PAR, CLOSE_PAR, CLOSE_S_PAR, OPEN_BLOCK, CLOSE_BLOCK, DOUBLE_DOT,
	// keywords
	// case DICT NON SERVE!!!! perchè in parserunary riconosco il dizionario quando trovo [
	// Add DICT, FOR, OF
	PRINT, VAR, BOOL, IF, ELSE, FST, SND, FOR, OF
}
