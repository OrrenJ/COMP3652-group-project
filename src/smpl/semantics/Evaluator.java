package smpl.semantics;

import smpl.syntax.SmplProgram;
import smpl.syntax.StmtSequence;
import smpl.syntax.Statement;
import smpl.syntax.StmtDefinition;
import smpl.syntax.Exp;
import smpl.syntax.ExpLit;
import smpl.syntax.ExpVar;
import smpl.syntax.ExpAdd;
import smpl.syntax.ExpSub;
import smpl.syntax.ExpMul;
import smpl.syntax.ExpDiv;
import smpl.syntax.ExpMod;
import smpl.syntax.ExpPow;
import smpl.syntax.ExpPair;
import smpl.syntax.ExpPairCheck;
import smpl.syntax.ExpCar;
import smpl.syntax.ExpCdr;
import smpl.syntax.ExpEq;
import smpl.syntax.ExpGt;
import smpl.syntax.ExpLt;
import smpl.syntax.ExpLe;
import smpl.syntax.ExpGe;
import smpl.syntax.ExpNeq;
import smpl.syntax.ExpLogicNot;
import smpl.syntax.ExpLogicAnd;
import smpl.syntax.ExpLogicOr;
import smpl.sys.SmplException;
import smpl.values.TypeSmplException;
import smpl.values.SmplValue;
import smpl.values.SmplInt;
import smpl.values.SmplType;
import smpl.values.SmplReal;
import smpl.values.SmplPair;
import smpl.values.SmplBool;
import java.util.*;

public class Evaluator implements Visitor<Environment<SmplValue<?>>, SmplValue<?>> {

	protected SmplValue<?> result;

	protected Environment<SmplValue<?>> globalEnv;

	public Evaluator() {
		result = SmplValue.make(0);
		globalEnv = new Environment<>();
	}

	public Environment<SmplValue<?>> getGlobalEnv(){
		return globalEnv;
	}

	// program

	@Override
	public SmplValue<?> visitSmplProgram(SmplProgram p, Environment<SmplValue<?>> env) throws SmplException {
		result = p.getSeq().visit(this, env);
		return result;
	}

	// statements

	@Override
	public SmplValue<?> visitStmtSequence(StmtSequence sseq, Environment<SmplValue<?>> env) throws SmplException{
		ArrayList<Statement> seq = sseq.getSeq();
		result = SmplValue.make(0); // defaut result
		for (Statement s : seq){
			result = s.visit(this, env);
		}
		// return last evaluated value
		return result;
	}

	@Override
	public SmplValue<?> visitStmtDefinition(StmtDefinition sd, Environment<SmplValue<?>> env) throws SmplException{
		result = sd.getExp().visit(this, env);
		env.put(sd.getVar(), result);
		return result;
	}

	@Override
	public SmplValue<?> visitExpAdd(ExpAdd exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.add(rval);
	}

	@Override
	public SmplValue<?> visitExpSub(ExpSub exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.sub(rval);
	}

	@Override
	public SmplValue<?> visitExpMul(ExpMul exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.mul(rval);
	}

	@Override
	public SmplValue<?> visitExpDiv(ExpDiv exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.div(rval);
	}

	@Override
	public SmplValue<?> visitExpMod(ExpMod exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.mod(rval);
	}

	@Override
	public SmplValue<?> visitExpPow(ExpPow exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.pow(rval);
	}

	@Override
	public SmplValue<?> visitExpLit(ExpLit exp, Environment<SmplValue<?>> env) throws SmplException {
		return exp.getVal();
	}

	@Override
	public SmplValue<?> visitExpVar(ExpVar exp, Environment<SmplValue<?>> env) throws SmplException {
		return env.get(exp.getVar());
	}

	@Override
	public SmplValue<?> visitExpPair(ExpPair exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> v1 = exp.getExpL().visit(this, env);
		SmplValue<?> v2 = exp.getExpR().visit(this, env);
		return new SmplPair(v1, v2);
	}

	@Override
	public SmplValue<?> visitExpPairCheck(ExpPairCheck exp, Environment<SmplValue<?>> env) throws SmplException {
		Exp toCheck = exp.getExp();
		SmplValue<?> checked = toCheck.visit(this, env);
		SmplType type = checked.getType();

		return new SmplBool(type == SmplType.PAIR);
	}

	@Override
	public SmplValue<?> visitExpCar(ExpCar exp, Environment<SmplValue<?>> env) throws SmplException {
		// check that expression is a pair
		SmplValue<?> val = exp.getExp().visit(this, env);
		SmplType type = val.getType();

		if(type == SmplType.PAIR)
			return ((SmplPair)val).getFirstValue();
		else
			throw new TypeSmplException(SmplType.PAIR, type);
	}

	@Override
	public SmplValue<?> visitExpCdr(ExpCdr exp, Environment<SmplValue<?>> env) throws SmplException {
		// check that expression is a pair
		SmplValue<?> val = exp.getExp().visit(this, env);
		SmplType type = val.getType();

		if(type == SmplType.PAIR)
			return ((SmplPair)val).getSecondValue();
		else
			throw new TypeSmplException(SmplType.PAIR, type);
	}

	@Override
	public SmplValue<?> visitExpEq(ExpEq exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.eq(rval);
	}

	@Override
	public SmplValue<?> visitExpGt(ExpGt exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.gt(rval);
	}

	@Override
	public SmplValue<?> visitExpLt(ExpLt exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.lt(rval);
	}

	@Override
	public SmplValue<?> visitExpLe(ExpLe exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.le(rval);
	}

	@Override
	public SmplValue<?> visitExpGe(ExpGe exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.ge(rval);
	}

	@Override
	public SmplValue<?> visitExpNeq(ExpNeq exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.neq(rval);
	}

	@Override
	public SmplValue<?> visitExpLogicNot(ExpLogicNot exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> val = exp.getExp().visit(this, env);
		return val.not();
	}

	@Override
	public SmplValue<?> visitExpLogicAnd(ExpLogicAnd exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.and(rval);
	}

	@Override
	public SmplValue<?> visitExpLogicOr(ExpLogicOr exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.or(rval);
	}

}