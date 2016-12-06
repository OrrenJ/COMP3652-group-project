package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.syntax.Exp;
import smpl.sys.SmplException;

public class StmtDefinition extends Statement {

	String var;
	Exp exp;

	public StmtDefinition(String id, Exp e) {
		var = id;
		exp = e;
	}

	public String getVar(){
		return var;
	}

	public Exp getExp(){
		return exp;
	}

	@Override
	public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
		return v.visitStmtDefinition(this, arg);
	}

	@Override
	public String toString() {
		return String.format("%s := %s", var, exp.toString());
	}
}