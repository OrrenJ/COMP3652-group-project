package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.syntax.Exp;
import smpl.sys.SmplException;
import java.util.*;

public class StmtDefinition extends Statement {

	ArrayList<String> vars;
	ArrayList<Exp> exps;

	public StmtDefinition(ArrayList<String> ids, ArrayList<Exp> e) {
		vars = ids;
		exps = e;
	}

	public ArrayList<String> getVars(){
		return vars;
	}

	public ArrayList<Exp> getExps(){
		return exps;
	}

	@Override
	public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
		return v.visitStmtDefinition(this, arg);
	}

	@Override
	public String toString() {
		return String.format("%s := %s", vars, exps.toString());
	}
}