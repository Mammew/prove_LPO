package progetto2024_lpo23_03.visitors.typechecking;

import static java.util.Objects.requireNonNull;

public class RangeType implements Type {

	private final Type startType;
	private final Type endType;

	public static final String TYPE_NAME = "RANGE";

	public RangeType(Type startType, Type endType) {
		this.startType = requireNonNull(startType);
		this.endType = requireNonNull(endType);
	}

	public Type getStartType() {
		return startType;
	}

	public Type getEndType() {
		return endType;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RangeType))
			return false;
		RangeType rt = (RangeType) obj;
		return startType.equals(rt.startType) && endType.equals(rt.endType) || startType.equals(endType) && rt.startType.equals(rt.endType);
	}

	@Override
	public int hashCode() {
		return startType.hashCode() + endType.hashCode();
	}

	@Override
	public String toString() {
		return TYPE_NAME;
	}

}
