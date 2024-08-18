package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class DictUpdate implements Exp{
	private final Exp dict;
    private final Exp index;
    private final Exp value;

    public DictUpdate(Exp dict, Exp index, Exp value) {
        this.dict = dict;
        this.index = index;
        this.value = value;
    }

    // Implement methods here, e.g., toString, eval, etc.
    @Override
    public String toString() {
        return String.format("%s[%s:%s]", dict, index, value);
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}

