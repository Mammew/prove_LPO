package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class DictUpdate extends DictTripleOp{

    public DictUpdate(Exp exp, Exp key, Exp value) {
        super(exp, key,value);
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}

