package progetto2024_lpo23_03.parser.ast;

import progetto2024_lpo23_03.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class DictInsert implements Exp {
    private final Exp dict;
    private final Exp key;
    private final Exp value;

    public DictInsert(Exp dict, Exp key, Exp value) {
        this.dict = requireNonNull(dict);
        this.key = requireNonNull(key);
        this.value = requireNonNull(value);
    }

    public Exp getDict() {
        return dict;
    }

    public Exp getKey() {
        return key;
    }

    public Exp getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s, [%s:%s]", dict, key, value);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return null;//visitor.visit(this);
    }
}