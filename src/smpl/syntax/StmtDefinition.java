package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.syntax.Exp;
import smpl.sys.SmplException;
import java.util.*;

public class StmtDefinition extends Statement {

	ArrayList<String> vars;
	Exp exp;

	public StmtDefinition(ArrayList<String> ids, Exp e) {
		vars = ids;
		exp = e;
	}

	public ArrayList<String> getVars(){
		return vars;
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
		return String.format("%s := %s", vars, exp.toString());
	}
}