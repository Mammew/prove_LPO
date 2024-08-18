package progetto2024_lpo23_03.visitors.execution;

public interface Value {
	/* default conversion methods */
	default int toInt() {
		throw new InterpreterException("Expecting an integer");
	}

	default boolean toBool() {
		throw new InterpreterException("Expecting a boolean");
	}

	default PairValue toPair() {
		throw new InterpreterException("Expecting a pair");
	}

	default RangeValue toRange() {
		throw new InterpreterException("Expecting a range");
	}
}
