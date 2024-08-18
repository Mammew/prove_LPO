package progetto2024_lpo23_03.visitors.typechecking;

public interface Type {
	default void checkEqual(Type found) throws TypecheckerException {
		if (!equals(found))
			throw new TypecheckerException(found.toString(), toString());
	}
	
	default void checkIsRangeType() throws TypecheckerException {
		if (!(this instanceof RangeType))
			throw new TypecheckerException(toString(), RangeType.TYPE_NAME);
	}

	default PairType checkIsPairType() throws TypecheckerException {
		if (this instanceof PairType pt)
			return pt;
		throw new TypecheckerException(toString(), PairType.TYPE_NAME);
	}

	default Type getFstPairType() throws TypecheckerException {
		return checkIsPairType().fstType();
	}

	default Type getSndPairType() throws TypecheckerException {
		return checkIsPairType().sndType();
	}
	
}
