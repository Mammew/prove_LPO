package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

public class DictDelete extends DictBinOp {

    public DictDelete(Exp exp, Exp key) {
        super(exp,key);
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}
