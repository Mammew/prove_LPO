package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class DictLiteral implements Exp {
    private final Exp key;
    private final Exp value;

    public DictLiteral(Exp key, Exp value) {
        this.key = requireNonNull(key);
        this.value = requireNonNull(value);
    }

    public Exp getKey() {
        return key;
    }

    public Exp getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("[%s:%s]", key, value);
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}