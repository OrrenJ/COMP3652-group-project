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

		// create copy of parameters since we will be manipulating this list
		ArrayList<Exp> args = new ArrayList(exp.getArguments());
		String id = exp.getVar();

		SmplProcedure proc = (SmplProcedure) env.get(id);
		ExpProcedure procExp = proc.getProcExp();

		// create copy of parameters since we will be manipulating this list
		ArrayList<String> params = new ArrayList(procExp.getParameters());
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
	public SmplValue<?> visitExpVector(ExpVector exp, Environment<SmplValue<?>> env) throws SmplException {

		ArrayList<Exp> lst = exp.getList();
		ArrayList<SmplValue<?>> vals = new ArrayList();

		for(Exp e : lst){
			result = e.visit(this, env);
			if(result.getType() == SmplType.SUBVECTOR){
				int size = ((SmplSubVector)result).getSizeInt();
				SmplProcedure proc = ((SmplSubVector)result).getProcedure();
				ExpProcedure procExp = proc.getProcExp();
				// get parameters and expression body
				ArrayList<String> params = new ArrayList(procExp.getParameters());
				if(params.size() > 1 || procExp.getListVar() != null)
					throw new SmplException("Procedure must have 1 or no parameters.");
				Exp body = procExp.getBody();
				// evaluate for 0 through size
				for(int i=0; i<size; i++){
					ArrayList<SmplInt> args = new ArrayList();
					args.add(SmplValue.make(i));
					Environment<SmplValue<?>> newEnv = new Environment(params, args, env);
					vals.add(body.visit(this,newEnv));
				}
			} else {
				vals.add(e.visit(this, env));
			}
		}

		return SmplValue.makeVector(vals);
	}

	@Override
	public SmplValue<?> visitExpVectorRef(ExpVectorRef exp, Environment<SmplValue<?>> env) throws SmplException {

		Exp ref = exp.getRef();
		result = ref.visit(this, env);

		if(result.getType() != SmplType.INTEGER && result.getType() != SmplType.REAL)
			throw new TypeSmplException(SmplType.INTEGER, result.getType());

		int _ref = result.intValue();

		String var = exp.getVar();
		SmplValue<?> val = env.get(var);

		if(val.getType() != SmplType.VECTOR)
			throw new TypeSmplException(SmplType.VECTOR, result.getType());

		ArrayList<SmplValue<?>> lst = ((SmplVector)val).getList();

		return lst.get(_ref);
	}

	@Override
	public SmplValue<?> visitExpSize(ExpSize exp, Environment<SmplValue<?>> env) throws SmplException {

		Exp body = exp.getBody();
		result = body.visit(this, env);

		if(result.getType() != SmplType.VECTOR)
			throw new TypeSmplException(SmplType.VECTOR, result.getType());

		ArrayList<SmplValue<?>> lst = ((SmplVector)result).getList();

		return SmplValue.make(lst.size());
	}

	@Override
	public SmplValue<?> visitExpSubVector(ExpSubVector exp, Environment<SmplValue<?>> env) throws SmplException {

		SmplInt size;
		SmplProcedure proc;

		result = exp.getSize().visit(this, env);
		if(result.getType() == SmplType.INTEGER)
			size = (SmplInt) result;
		else
			throw new TypeSmplException(SmplType.INTEGER, result.getType());

		result = exp.getProcedure().visit(this,env);
		if(result.getType() == SmplType.PROCEDURE)
			proc = (SmplProcedure) result;
		else
			throw new TypeSmplException(SmplType.PROCEDURE, result.getType());

		return SmplValue.makeSubVector(size,proc);
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

		Exp expl = exp.getExpL();
		Exp expr = exp.getExpR();

		// confirm that first argument is a procedure
		SmplType expltype = expl.visit(this, env).getType();

		if(expltype != SmplType.PROCEDURE)
			throw new TypeSmplException(SmplType.PROCEDURE, expltype);

		// grab procedure
		SmplProcedure proc = (SmplProcedure) expl.visit(this, env);
		ExpProcedure toEval = proc.getProcExp();

		// get procedure parameters
		ArrayList<String> _params = new ArrayList(toEval.getParameters());
		int p_size = _params.size();

		// get procedure body
		Exp body = toEval.getBody();

		// confirm that second argument is a list
		SmplType exprtype = expr.visit(this, env).getType();

		if(exprtype != SmplType.LIST && exprtype != SmplType.EMPTYLIST)
			throw new TypeSmplException(SmplType.LIST, exprtype);

		// grab list
		SmplList lst = (SmplList) expr.visit(this, env);

		// convert to ArrayList
		ArrayList<SmplValue<?>> args = new ArrayList();
		ArrayList<SmplValue<?>> extras = new ArrayList();

		// ArrayList of parameters that are matched by arguments
		ArrayList<String> params = new ArrayList();

		// add value for each parameter,
		// create arraylist of extras,
		// add extras as argument
		int i = 0;	// counter
		while(lst.getType() != SmplType.EMPTYLIST){
			if(i < p_size){
				args.add(lst.getCurrentValue());
				params.add(_params.get(i));
			} else {
				extras.add(lst.getCurrentValue());
			}

			lst = lst.getNextValue();

			i++;	// increment counter
		}

		// get extra veriable
		String e = toEval.getListVar();
		if(e != null)
			params.add(e);

		// add extras to args
		if(!extras.isEmpty())
			args.add(SmplValue.makeList(extras));

		//System.out.println(args);
		Environment<SmplValue<?>> newEnv = new Environment<SmplValue<?>>(params, args, env);

		//System.out.println(newEnv);
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

	@Override
	public SmplValue<?> visitExpRead(ExpRead exp, Environment<SmplValue<?>> env) throws SmplException {

		Scanner input = new Scanner(System.in);
		result = SmplValue.makeStr(input.nextLine());
		return result;
	}

	@Override
	public SmplValue<?> visitExpReadInt(ExpReadInt exp, Environment<SmplValue<?>> env) throws SmplException {
		
		Scanner input = new Scanner(System.in);
		if(input.hasNextInt()){
			result = SmplValue.make(input.nextInt());
			return result;
		} else {
			throw new TypeSmplException("Type Error: Input must be of type " + SmplType.INTEGER);
		}
	}

	@Override
	public SmplValue<?> visitExpIf(ExpIf exp, Environment<SmplValue<?>> env) throws SmplException {
		
		Boolean conBool;
		if(exp.getElse())
		{
			Exp conExp = exp.getCondition();
			SmplValue conValue = conExp.visit(this, env);

			try{
				conBool = conValue.boolValue();
			}catch (Exception e){ throw new SmplException("Condition must evaluate to a boolean."); }
		
			if(conBool)
			{
				Exp ifArgBody = exp.getIfArg();
				result = ifArgBody.visit(this, env);
			}
			else
			{ 
				Exp elseArgBody = exp.getElseArg();
				result = elseArgBody.visit(this, env); 
			}
		}
		else
		{
			Exp conExp = exp.getCondition();
			SmplValue conValue = conExp.visit(this, env);

			try{
				conBool = conValue.boolValue();
			}catch (Exception e){ throw new SmplException("Condition must evaluate to a boolean."); }
			
			if(conBool)
			{
				Exp ifArgBody = exp.getIfArg();
				result = ifArgBody.visit(this, env);
			}
		}

		return result;

	}

	@Override
	public SmplValue<?> visitExpCase(ExpCase exp, Environment<SmplValue<?>> env) throws SmplException {

		// get cases
		ArrayList<ExpPair> lst = exp.getList();
		Exp elseCond = null;
		// examine each case
		for(ExpPair _case : lst){
			Exp cond = _case.getExpL();
			result = cond.visit(this, env);
			// skip evaluation for else condition
			if(result.getType() == SmplType.STRING){
				if(result.stringValue().equals("else")){
					elseCond = _case.getExpR();
					continue;
				}
			}
			// check condition is booleans
			if(result.getType() != SmplType.BOOLEAN)
				throw new TypeSmplException(SmplType.BOOLEAN, result.getType());

			if(result.boolValue()){
				Exp body = _case.getExpR();
				return body.visit(this, env);
			}
		}
		// true case has not been found
		if(elseCond != null)
			return elseCond.visit(this, env);
		// return false if no true case and no else case
		return SmplValue.make(false);
	}
}