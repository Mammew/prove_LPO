package progetto2024_lpo23_03.visitors.execution;

public class IntValue extends AtomicValue<Integer> {

	public IntValue(Integer value) {
		super(value);
	}

	@Override
	public int toInt() {
		return value;
	}

}