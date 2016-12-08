package smpl.semantics;

import smpl.syntax.*;
import smpl.sys.SmplException;
import smpl.values.*;
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
		ArrayList<Exp> args = sd.getExps();
		ArrayList<String> vars = sd.getVars();

		int a_size = args.size();
		int v_size = vars.size();

		if(a_size != v_size)
			throw new SmplException("Must assign same number of expressions as variables");

		for(int i=0; i<a_size; i++)
			env.put(vars.get(i), args.get(i).visit(this, env));

		return result;
	}

	@Override
	public SmplValue<?> visitStmtLet(StmtLet let, Environment<SmplValue<?>> env) throws SmplException{
		ArrayList<Binding> bindings = let.getBindings();
		Exp body = let.getBody();

		int size = bindings.size();
		String[] vars = new String[size];
		SmplValue<?>[] vals = new SmplValue<?>[size];
		Binding b;

		for (int i = 0; i < size; i++) {
		    b = bindings.get(i);
		    vars[i] = b.getVar();
		    // evaluate each expression in bindings
		    result = b.getValExp().visit(this, env);
		    vals[i] = result;
		}
		// create new env as child of current
		Environment<SmplValue<?>> newEnv = new Environment<> (vars, vals, env);
		return body.visit(this, newEnv);
	}

	@Override
	public SmplValue<?> visitStmtPrint(StmtPrint sp, Environment<SmplValue<?>> env) throws SmplException{
		result = sp.getExp().visit(this, env);
		System.out.print(result.toString() + sp.getTerminatingCharacter());
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
	public SmplValue<?> visitExpProcedure(ExpProcedure proc, Environment<SmplValue<?>> env) throws SmplException {
		return new SmplProcedure(proc, env);
	}

	@Override
	public SmplValue<?> visitExpProcedureCall(ExpProcedureCall exp, Environment<SmplValue<?>> env) throws SmplException {

		ArrayList<Exp> args = exp.getArguments();
		String id = exp.getVar();

		SmplProcedure proc = (SmplProcedure) env.get(id);
		ExpProcedure procExp = proc.getProcExp();

		ArrayList<String> params = procExp.getParameters();
		Exp body = procExp.getBody();

		int a_size = args.size();
		int p_size = params.size();
		ArrayList<String> vars = new ArrayList();
		ArrayList<SmplValue<?>> vals = new ArrayList();

		String extra = procExp.getListVar();
		ArrayList<SmplValue<?>> extras = new ArrayList();

		// evaluate each argument and add excess to extra list if declared
		for(int i=0; i<a_size; i++){
			if(i<p_size){
				vars.add(params.remove(0));
				vals.add(args.remove(0).visit(this,env));
			} else {
				if(extra != null){
					vars.add(extra);
				}

				break;
			}

		}

		if(!args.isEmpty()){
			for(Exp e : args){
				extras.add(e.visit(this, env));
			}
			vals.add(SmplValue.makeList(extras));
		}

		Environment<SmplValue<?>> newEnv = new Environment<SmplValue<?>>(vars, vals, env);

		return body.visit(this, newEnv);
	}

	@Override
	public SmplValue<?> visitExpPair(ExpPair exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> v1 = exp.getExpL().visit(this, env);
		SmplValue<?> v2 = exp.getExpR().visit(this, env);
		return new SmplPair(v1, v2);
	}

	@Override
	public SmplValue<?> visitExpList(ExpList exp, Environment<SmplValue<?>> env) throws SmplException {
		ArrayList<SmplValue<?>> vals = new ArrayList();
		ArrayList<Exp> list = exp.getList();

		for(Exp lexp : list)
			vals.add(lexp.visit(this, env));

		return SmplValue.makeList(vals);
	}

	@Override
	public SmplValue<?> visitExpPairCheck(ExpPairCheck exp, Environment<SmplValue<?>> env) throws SmplException {
		Exp toCheck = exp.getExp();
		result = toCheck.visit(this, env);
		SmplType type = result.getType();

		return new SmplBool(type == SmplType.PAIR);
	}

	@Override
	public SmplValue<?> visitExpCar(ExpCar exp, Environment<SmplValue<?>> env) throws SmplException {
		// check that expression is a pair
		result = exp.getExp().visit(this, env);
		SmplType type = result.getType();

		if(type == SmplType.PAIR)
			return ((SmplPair)result).getFirstValue();
		else
			throw new TypeSmplException(SmplType.PAIR, type);
	}

	@Override
	public SmplValue<?> visitExpCdr(ExpCdr exp, Environment<SmplValue<?>> env) throws SmplException {
		// check that expression is a pair
		result = exp.getExp().visit(this, env);
		SmplType type = result.getType();

		if(type == SmplType.PAIR)
			return ((SmplPair)result).getSecondValue();
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
		result = exp.getExp().visit(this, env);
		return result.not();
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

	@Override
	public SmplValue<?> visitExpBitWiseNot(ExpBitWiseNot exp, Environment<SmplValue<?>> env) throws SmplException {
		result = exp.getExp().visit(this, env);
		return result.bitnot();
	}

	@Override
	public SmplValue<?> visitExpBitWiseAnd(ExpBitWiseAnd exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.bitand(rval);
	}

	@Override
	public SmplValue<?> visitExpBitWiseOr(ExpBitWiseOr exp, Environment<SmplValue<?>> env) throws SmplException {
		SmplValue<?> lval, rval;
		lval = exp.getExpL().visit(this, env);
		rval = exp.getExpR().visit(this, env);
		return lval.bitor(rval);
	}

	@Override
	public SmplValue<?> visitExpSubStr(ExpSubStr exp, Environment<SmplValue<?>> env) throws SmplException {

		String str = exp.getExpString().visit(this, env).stringValue();
		int lo = exp.getStartIndex().visit(this, env).intValue();
		int hi = exp.getEndIndex().visit(this, env).intValue();

		if(lo < 0 || lo > str.length())
			throw new SmplException("Starting index out of bounds");
		else if (hi > str.length())
			throw new SmplException("Ending index out of bounds");
		else if (hi < lo)
			return SmplValue.makeStr("");
		else
			return SmplValue.makeStr(str.substring(lo,hi));
	}

	@Override
	public SmplValue<?> visitExpEqv(ExpEqv exp, Environment<SmplValue<?>> env) throws SmplException {

		SmplValue<?> exp1 = exp.getExpFirst().visit(this, env);
		SmplValue<?> exp2 = exp.getExpSecond().visit(this, env);

		if(exp1 == exp2)
		{
			return SmplValue.make(true);
		}else { return SmplValue.make(false); }
		
	}

	@Override
	public SmplValue<?> visitExpEqual(ExpEqual exp, Environment<SmplValue<?>> env) throws SmplException {

		String exp1 = exp.getExpFirst().visit(this, env).toString();
		String exp2 = exp.getExpSecond().visit(this, env).toString();
		return SmplValue.make(exp1.equals(exp2));
	}

	@Override
	public SmplValue<?> visitExpCall(ExpCall exp, Environment<SmplValue<?>> env) throws SmplException {

		ExpProcedure functionDef = exp.getExp();
		ArrayList<String> vars = functionDef.getParameters();
		Exp body = functionDef.getBody();
		ArrayList<Exp> expVals;

		ArrayList<SmplValue<?>> vals = new ArrayList<SmplValue<?>>();

		if(exp.getId().equals(" "))
		{
			expVals = exp.getExpList().getList();
			for(int i = 0; i < expVals.size(); i++)
			{
				vals.add((SmplValue) expVals.get(i).visit(this, env));
			}
		}
		else
		{ 
			SmplList l = (SmplList)env.get(exp.getId()); 
			

			while (l.getFirstValue() != null )
			{
				vals.add((SmplValue) l.getFirstValue());
				
				
				l = (SmplList) l.getSecondValue();
			}

			Collections.reverse(vals);

		}


		

		

		Environment<SmplValue<?>> newEnv = new Environment<> (vars, vals, env);
    	return body.visit(this, newEnv);

	}

	@Override
	public SmplValue<?> visitExpLazy(ExpLazy exp, Environment<SmplValue<?>> env) throws SmplException {
		
		Boolean exists;

		try 
		{
		    env.get("101lazy");
		    exists = true;
		}catch(SmplException e) { exists = false; }

		if(!exists)
		{
			Exp body = exp.getExp();
			env.put("101lazy", body.visit(this, env));
		}else { result = env.get("101lazy"); }
		
		return result;

	}

	@Override
	public SmplValue<?> visitExpDef(ExpDef exp, Environment<SmplValue<?>> env) throws SmplException {
		Exp body = exp.getExp();
		String var = exp.getVar();

		env.put(var, body.visit(this, env));

		return result;

	}

	@Override
	public SmplValue<?> visitExpConcatLst(ExpConcatLst exp, Environment<SmplValue<?>> env) throws SmplException {
		Integer combo = exp.getCombo();

		ArrayList<Exp> lst1; 
		ArrayList<Exp> lst2;
  		String var1; 
  		String var2;
  		ArrayList<SmplValue<?>> vals = new ArrayList();
		

		
		if(combo == 1)
		{


			lst2 = exp.getExpList2().getList();
			lst1 = exp.getExpList1().getList(); 
			

			for(Exp lexp : lst1)
			{
				vals.add(lexp.visit(this, env));
			}
				

			for(Exp lexp : lst2)
				{
					vals.add(lexp.visit(this, env));
				}

			return SmplValue.makeList(vals);
		}
		else if (combo == 2)
		{
			var1 = exp.getVar1(); 
  			var2 = exp.getVar2();

  			SmplList l1 = (SmplList)env.get(var1); 
  			SmplList l2 = (SmplList)env.get(var2); 

  			while (l2.getFirstValue() != null )
			{
				vals.add((SmplValue) l2.getFirstValue());
				
				
				l2 = (SmplList) l2.getSecondValue();

			}
  			while (l1.getFirstValue() != null )
			{
				vals.add((SmplValue) l1.getFirstValue());
				
				
				l1 = (SmplList) l1.getSecondValue();
			}
			
			
			Collections.reverse(vals);
			return SmplValue.makeList(vals);
		}
		else if (combo == 3)
		{
			lst1 = exp.getExpList1().getList(); 
  			var2 = exp.getVar2();

  			SmplList l2 = (SmplList)env.get(var2); 

  			for(Exp lexp : lst1)
				vals.add(lexp.visit(this, env));

			Collections.reverse(vals);
			while (l2.getFirstValue() != null )
			{
				vals.add(0,(SmplValue) l2.getFirstValue());
				
				
				l2 = (SmplList) l2.getSecondValue();

			}

			Collections.reverse(vals);
			return SmplValue.makeList(vals);
		}
		else
		{
			var1 = exp.getVar1(); 
			lst2 = exp.getExpList2().getList();

			SmplList l1 = (SmplList)env.get(var1); 

			while (l1.getFirstValue() != null )
			{
				vals.add((SmplValue) l1.getFirstValue());
				
				
				l1 = (SmplList) l1.getSecondValue();

			}
			Collections.reverse(vals);

			for(Exp lexp : lst2)
				vals.add(lexp.visit(this, env));
			
			return SmplValue.makeList(vals);
		}


	}

}