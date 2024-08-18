package progetto2024_lpo23_03.parser;
import java.io.IOException;

//import java.util.Dictionary;
//import javax.lang.model.element.VariableElement;

import progetto2024_lpo23_03.parser.ast.*;

import static java.util.Objects.requireNonNull;
import static progetto2024_lpo23_03.parser.TokenType.*;

/*
Prog ::= StmtSeq EOF
StmtSeq ::= Stmt (';' StmtSeq)?
Stmt ::= 'var'? IDENT '=' Exp | 'print' Exp |  'if' '(' Exp ')' Block ('else' Block)? | 'for' '(' 'var' IDENT 'of Exp ')' Block 
Block ::= '{' StmtSeq '}'
Exp ::= And (',' And)* 
And ::= Eq ('&&' Eq)* 
Eq ::= Add ('==' Add)*
Add ::= Mul ('+' Mul)*
Mul::= Unary ('*' Unary)*
Unary ::= 'fst' Unary | 'snd' Unary | '-' Unary | '!' Unary | Dict 
Dict ::= Atom ('[' Exp (':' Exp?)? ']')* 
Atom :: = '[' Exp ':' Exp ']' | BOOL | NUM | IDENT | '(' Exp ')'
*/

public class MyLangParser implements Parser {

	private final MyLangTokenizer tokenizer; // the tokenizer used by the parser

	/*
	 * reads the next token through the tokenizer associated with the
	 * parser; TokenizerExceptions are chained into corresponding ParserExceptions
	 */
	private void nextToken() throws ParserException {
		try {
			tokenizer.next();
		} catch (TokenizerException e) {
			throw new ParserException(e);
		}
	}

	// decorates error message with the corresponding line number
	private String lineErrMsg(String msg) {
		return String.format("on line %s: %s", tokenizer.getLineNumber(), msg);
	}

	/*
	 * checks whether the token type of the currently recognized token matches
	 * 'expected'; if not, it throws a corresponding ParserException
	 */
	private void match(TokenType expected) throws ParserException {
		final var found = tokenizer.tokenType();
		if (found != expected)
			throw new ParserException(
					lineErrMsg(String.format("Expecting %s, found %s('%s')", expected, found, tokenizer.tokenString())));
	}

	/*
	 * checks whether the token type of the currently recognized token matches
	 * 'expected'; if so, it reads the next token, otherwise it throws a
	 * corresponding ParserException
	 */
	private void consume(TokenType expected) throws ParserException {
		match(expected);
		nextToken();
	}

	// throws a ParserException because the current token was not expected
	private <T> T unexpectedTokenError() throws ParserException {
		throw new ParserException(lineErrMsg(
				String.format("Unexpected token %s ('%s')", tokenizer.tokenType(), tokenizer.tokenString())));
	}

	// associates the parser with a corresponding non-null  tokenizer
	public MyLangParser(MyLangTokenizer tokenizer) {
		this.tokenizer = requireNonNull(tokenizer);
	}

	/*
	 * parses a program Prog ::= StmtSeq EOF
	 */
	@Override
	public Prog parseProg() throws ParserException {
		nextToken(); // one look-ahead symbol
		final var prog = new MyLangProg(parseStmtSeq());
		match(EOF); // last token must have type EOF
		return prog;
	}

	@Override
	public void close() throws IOException {
		if (tokenizer != null)
			tokenizer.close();
	}

	/*
	 * parses a non empty sequence of statements, binary operator STMT_SEP is right
	 * associative StmtSeq ::= Stmt (';' StmtSeq)?
	 */
	private StmtSeq parseStmtSeq() throws ParserException {
	    //  completare
		final var stmt = parseStmt();
		if (tokenizer.tokenType() == STMT_SEP) {
			nextToken();
			return new NonEmptyStmtSeq(stmt, parseStmtSeq());
		}
		return new NonEmptyStmtSeq(stmt, new EmptyStmtSeq());
	}

	/*
	 * parses a statement 
	 * Stmt ::= 'var'? IDENT '=' Exp | 'print' Exp |  'if' '(' Exp ')' Block ('else' Block)? | 'for' '(' 'var' IDENT 'of Exp ')' Block
	 */
	private Stmt parseStmt() throws ParserException {
		return switch (tokenizer.tokenType()) {
		case PRINT -> parsePrintStmt();
		case VAR -> parseVarStmt();
		case IDENT -> parseAssignStmt();
		case IF -> parseIfStmt();
		case FOR -> parseForStmt();
		default -> unexpectedTokenError();
		};
	}

	/*
	 * parses the 'print' statement Stmt ::= 'print' Exp
	 */
	private PrintStmt parsePrintStmt() throws ParserException {
	    // completare
		consume(PRINT);
		final var exp = parseExp();
		return new PrintStmt(exp);
	}

	/*
	 * parses the 'var' statement Stmt ::= 'var' IDENT '=' Exp
	 */
	private VarStmt parseVarStmt() throws ParserException {
	    // completare
		consume(VAR);
		Variable var = parseVariable();
		consume(ASSIGN);
		return new VarStmt(var,parseExp());
	}

	/*
	 * parses the assignment statement Stmt ::= IDENT '=' Exp
	 */
	private AssignStmt parseAssignStmt() throws ParserException {
	    // completare
		Variable var = parseVariable();
		consume(ASSIGN);
		return new AssignStmt(var, parseExp());
	}

	/*
	 * parses the 'if' statement Stmt ::= 'if' '(' Exp ')' Block ('else' Block)?
	 */
	private IfStmt parseIfStmt() throws ParserException {
	    // completare
		consume(IF);
		consume(OPEN_PAR);
		final var exp = parseExp();
		consume(CLOSE_PAR);
		final var block = parseBlock();
		if (tokenizer.tokenType() == ELSE) {
			consume(ELSE);
			final var else_block = parseBlock();
			return new IfStmt(exp, block, else_block);
		}
		return new IfStmt(exp, block);
	}

	/*
	* parses the 'for' statement Stmt ::= 'for' '(' 'var' IDENT 'of Exp ')' Block
	*/
	private ForStmt parseForStmt() throws ParserException {
		consume(FOR);
		consume(OPEN_PAR);
		consume(VAR);
		// da capire come va trattato IDENT
		// in parseVariable IDENT si fa conume(IDENT)
		Variable var = parseVariable(); 
		consume(OF);
		final var exp = parseExp();
		consume(CLOSE_PAR);
		final var block = parseBlock();
		// il return della funzione potrebbe essere questo
		//return new ForStmt(exp, block);
		// return corretto gli passo la variabile del ciclo for 
		return new ForStmt(var, exp, block);
	}



	/*
	 * parses a block of statements Block ::= '{' StmtSeq '}'
	 */
	private Block parseBlock() throws ParserException {
	    // completare
		consume(OPEN_BLOCK);
		final var exp = parseStmtSeq();
		consume(CLOSE_BLOCK);
		return new Block(exp);
	}

	/*
	 * parses expressions, starting from the lowest precedence operator PAIR_OP
	 * which is left-associative Exp ::= And (',' And)*
	 */

	private Exp parseExp() throws ParserException {
		var exp = parseAnd();
		while (tokenizer.tokenType() == PAIR_OP) {
			nextToken();
			exp = new PairLit(exp, parseAnd());
		}
		return exp;
	}

	/*
	 * parses expressions, starting from the lowest precedence operator AND which is
	 * left-associative And ::= Eq ('&&' Eq)*
	 */
	private Exp parseAnd() throws ParserException {
	    // completare
		var exp = parseEq();
		while (tokenizer.tokenType() == AND) {
			nextToken();
			exp = new And(exp, parseEq());
		}
		return exp;
	}

	/*
	 * parses expressions, starting from the lowest precedence operator EQ which is
	 * left-associative Eq ::= Add ('==' Add)*
	 */
	private Exp parseEq() throws ParserException {
	    // completare
		var exp = parseAdd();
		while (tokenizer.tokenType() == EQ) {
			nextToken();
			exp = new Eq(exp, parseAdd());
		}
		return exp;
		
	}

	/*
	 * parses expressions, starting from the lowest precedence operator PLUS which
	 * is left-associative Add ::= Mul ('+' Mul)*
	 */
	private Exp parseAdd() throws ParserException {
	    // completare
		var exp = parseMul();
		while (tokenizer.tokenType() == PLUS) {
			nextToken();
			exp = new Add(exp, parseMul());
		}
		return exp;
	}

	/*
	 * Mul::= Unary ('*' Unary)*
	 */
	private Exp parseMul() throws ParserException {
	    // completare
		var exp = parseUnary();
		while (tokenizer.tokenType() == TIMES) {
			nextToken();
			exp = new Mul(exp, parseUnary());
		}
		return exp;
	}

	/*
	* parses expressions of type Unary 
	* Unary ::= 'fst' Unary | 'snd' Unary | '-' Unary | '!' Unary | Dict
	*/

	//VERSIONE PIPPO
	/*private Exp parseUnary() throws ParserException {
		return switch (tokenizer.tokenType()) {
			case MINUS -> parseMinus();
			case NOT -> parseNot();
			case FST -> parseFst();
			case SND -> parseSnd();
			case DICT -> parseDict();
			// non ho capito bene come ci si sposta in dict
			default -> unexpectedTokenError();
			};
	}*/

	//se trovo la [ chiamo parseDict()
	private Exp parseUnary() throws ParserException {
		return switch (tokenizer.tokenType()) {
			case MINUS -> parseMinus();  // Parsing del segno meno unario
			case NOT -> parseNot();  // Parsing della negazione unaria
			case FST -> parseFst();  // Parsing della funzione 'fst'
			case SND -> parseSnd();  // Parsing della funzione 'snd'
			case OPEN_S_PAR -> parseDict();  // Parsing del dizionario (parsing della struttura con '[')
			default -> parseAtom();  // In assenza di operatori unari, considera l'espressione come un atom
		};
	}

	/*
	* parses expressions of type Atom 
	* Atom :: = '[' Exp ':' Exp ']' | BOOL | NUM | IDENT | '(' Exp ')'
	*/
	private Exp parseAtom() throws ParserException {
		return switch (tokenizer.tokenType()) {
		case OPEN_S_PAR -> parseSquarePar();
		case BOOL -> parseBoolean();
		case NUM -> parseNum();
		case IDENT -> parseVariable();
		case OPEN_PAR -> parseRoundPar();
		default -> unexpectedTokenError();
		};
	}

	/* 
	* il tipo della funzione potrebbe essere dictionary dato che ritorna un dizionario 
	* aggiornato. camcellato o solo l'elemento richiesto richiesto
	* parses expressions of type Dict 
	* Dict ::= Atom ('[' Exp (':' Exp?)? ']')*
	*/

	//VERSIONE PIPPO INCOMPLETA
	/*private Dictionary parseDict() throws ParserException {
		final var atom = parseAtom();
		while (tokenizer.tokenType() == TIMES) {
			nextToken();
			//consume(OPEN_S_PAR);
			final var exp = parseExp();
			if (tokenizer.tokenType() == DOUBLE_DOT) {
				nextToken();
				if (tokenizer.tokenType() == ' ') {
					// allora cè la cancellazione 
				}
				// allora cè l'update
			}
			// allora cè il get
		}
		return exp;
	}*/

	//versione di chat da provare
	// ci abbiamo ragionato ci sembra corretta

	private Exp parseDict() throws ParserException {
		Exp exp = parseAtom();  // Inizia con un'atom, poiché un dizionario potrebbe essere un atom stesso.
	
		while (tokenizer.tokenType() == OPEN_S_PAR) {  // Verifica se c'è un '[' dopo l'atom
			consume(OPEN_S_PAR);  // Consuma '['
			Exp index = parseExp();  // Parsing dell'espressione dell'indice
			
			if (tokenizer.tokenType() == DOUBLE_DOT) {  // Controlla se c'è ':' per set/delete
				consume(DOUBLE_DOT);  // Consuma ':'
				if (tokenizer.tokenType() == CLOSE_S_PAR) {  // Se c'è subito ']', è un delete
					consume(CLOSE_S_PAR);  // Consuma ']'
					exp = new DictDelete(exp, index);  // Cancellazione dell'elemento con l'indice specificato
				} else {  // Altrimenti, è un set
					Exp value = parseExp();  // Parsing del valore da impostare
					consume(CLOSE_S_PAR);  // Consuma ']'
					exp = new DictUpdate(exp, index, value);  // Aggiorna il dizionario con il nuovo valore
				}
			} else {  // Altrimenti, è un get
				consume(CLOSE_S_PAR);  // Consuma ']'
				exp = new DictAccess(exp, index);  // Accesso al dizionario con l'indice specificato
			}
		}
	
		return exp;  // Ritorna l'espressione risultante
	}

	// parses number literals
	private IntLiteral parseNum() throws ParserException {
	    // completare
		final var val = tokenizer.intValue();
		consume(NUM);
		return new IntLiteral(val);
	}

	// parses boolean literals
	private BoolLiteral parseBoolean() throws ParserException {
	    // completare
		final var val = tokenizer.boolValue();
		consume(BOOL);
		return new BoolLiteral(val);
	}

	// parses variable identifiers
	private Variable parseVariable() throws ParserException {
	    // completare
		final var variable = tokenizer.tokenString();
		consume(IDENT);
		return new Variable(variable);
	}

	/*
	 * parses expressions with unary operator MINUS Atom ::= '-' Atom
	 */
	private Sign parseMinus() throws ParserException {
	    // completare
		consume(MINUS);
		final var exp = new Sign(parseAtom());
		return exp;
	}

	/*
	 * parses expressions with unary operator FST Atom ::= 'fst' Atom
	 */
	private Fst parseFst() throws ParserException {
	    // completare
		consume(FST);
		final var exp = new Fst(parseAtom());
		return exp;
	}

	/*
	 * parses expressions with unary operator SND Atom ::= 'snd' Atom
	 */
	private Snd parseSnd() throws ParserException {
	    // completare
		consume(SND);
		final var exp = new Snd(parseAtom());
		return exp;
	}

	/*
	 * parses expressions with unary operator NOT Atom ::= '!' Atom
	 */
	private Not parseNot() throws ParserException {
	    // completare
		consume(NOT);
		final var exp = new Not(parseAtom());
		return exp;
	}

	/*
	 * parses expressions delimited by parentheses Atom ::= '(' Exp ')'
	 */

	private Exp parseRoundPar() throws ParserException {
	    // completare
		consume(OPEN_PAR); // this check is necessary for parsing correctly the 'if' statement
		final var exp = parseExp();
		consume(CLOSE_PAR);
		return exp;
	}

	/*
	* 
	* parses expressions delimited by parentheses Atom :: = '[' Exp ':' Exp ']'
	*/
	// non si dovrebbe trattare di aggiornamento ma dovrebbe essere il dizionario vero e proprio
	// un po come era stato fatto nella funzione sopra 
	// gestiamo tutto in parsedict

	private Exp parseSquarePar() throws ParserException {
	    consume(OPEN_S_PAR);
	    Exp key = parseExp();
	    consume(DOUBLE_DOT);
	    Exp value = parseExp();
	    consume(CLOSE_S_PAR);

	    Exp dictionary = new DictLiteral(key, value); // Inizializza il dizionario con la prima coppia

	    while (tokenizer.tokenType() == PAIR_OP) {
	        consume(PAIR_OP);
	        consume(OPEN_S_PAR);
	        key = parseExp();
	        consume(DOUBLE_DOT);
	        value = parseExp();
	        consume(CLOSE_S_PAR);
	        dictionary = new DictInsert(dictionary, key, value); // Aggiunge nuove coppie al dizionario
	    }
	    return dictionary;
	}
}
