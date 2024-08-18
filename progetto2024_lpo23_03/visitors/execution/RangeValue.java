package progetto2024_lpo23_03.visitors.execution;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.hash;

public class RangeValue implements Iterable<Integer>, Value {

	private final IntValue startVal, endVal;

	public RangeValue(IntValue startVal, IntValue endVal) {
		this.startVal = requireNonNull(startVal);
		this.endVal = requireNonNull(endVal);
	}

	public IntValue getStartVal() {
		return startVal;
	}

	public IntValue getEndVal() {
		return endVal;
	}

	@Override
	public RangeValue toRange() {
		return this;
	}

	@Override
	public String toString() {
		return "[" + startVal + ":" + endVal + "]";
	}
	
	@Override
	public RangeIterator iterator() {
		return new RangeIterator(startVal.toInt(), endVal.toInt());
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RangeValue))
			return false;
		var r = (RangeValue) obj;
		return (startVal.equals(r.startVal) && endVal.equals(r.endVal)) || (startVal.equals(endVal) && r.startVal.equals(r.endVal));
	}

	@Override
	public int hashCode() {
		return hash(startVal, endVal);
	}

}
