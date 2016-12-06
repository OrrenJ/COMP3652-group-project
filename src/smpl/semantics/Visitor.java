package smpl.semantics;

import smpl.syntax.SmplProgram;
import smpl.syntax.StmtSequence;
import smpl.syntax.Statement;
import smpl.syntax.StmtDefinition;
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

public interface Visitor<S, T> {

	// program
	public T visitSmplProgram(SmplProgram p, S arg) throws SmplException;

	// statements
	public T visitStmtSequence(StmtSequence exp, S arg) throws SmplException;
	public T visitStmtDefinition(StmtDefinition exp, S arg) throws SmplException;
	public T visitExpAdd(ExpAdd exp, S arg) throws SmplException;
	public T visitExpSub(ExpSub exp, S arg) throws SmplException;
	public T visitExpMul(ExpMul exp, S arg) throws SmplException;
	public T visitExpDiv(ExpDiv exp, S arg) throws SmplException;
	public T visitExpMod(ExpMod exp, S arg) throws SmplException;
	public T visitExpPow(ExpPow exp, S arg) throws SmplException;
	public T visitExpLit(ExpLit exp, S arg) throws SmplException;
	public T visitExpVar(ExpVar exp, S arg) throws SmplException;
	public T visitExpPair(ExpPair exp, S arg) throws SmplException;
	public T visitExpPairCheck(ExpPairCheck exp, S arg) throws SmplException;
	public T visitExpCar(ExpCar exp, S arg) throws SmplException;
	public T visitExpCdr(ExpCdr exp, S arg) throws SmplException;
	public T visitExpEq(ExpEq exp, S arg) throws SmplException;
	public T visitExpGt(ExpGt exp, S arg) throws SmplException;
	public T visitExpLt(ExpLt exp, S arg) throws SmplException;
	public T visitExpLe(ExpLe exp, S arg) throws SmplException;
	public T visitExpGe(ExpGe exp, S arg) throws SmplException;
	public T visitExpNeq(ExpNeq exp, S arg) throws SmplException;
	public T visitExpLogicNot(ExpLogicNot exp, S arg) throws SmplException;
	public T visitExpLogicAnd(ExpLogicAnd exp, S arg) throws SmplException;
	public T visitExpLogicOr(ExpLogicOr exp, S arg) throws SmplException;
}