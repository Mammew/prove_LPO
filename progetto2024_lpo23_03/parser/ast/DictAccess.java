package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class DictAccess implements Exp {
    private final Exp dict;
    private final Exp index;

    public DictAccess(Exp dict, Exp index) {
        this.dict = dict;
        this.index = index;
    }

    // Implement methods here, e.g., toString, eval, etc.
    @Override
    public String toString() {
        return String.format("%s[%s]", dict, index);
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}
