package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;


public class ExpIf extends Exp {

  Exp con, ifArg, elseArg;
  Boolean els;

  public ExpIf(Exp con, Exp ifArg){
    this.con = con;
    this.ifArg = ifArg;
    this.els = false;
  }

  public ExpIf(Exp con, Exp ifArg, Exp elseArg){
    this.con = con;
    this.ifArg = ifArg;
    this.elseArg = elseArg;
    this.els= true;
  }

  public Exp getCondition(){
    return this.con;
  }

  public Exp getIfArg(){
    return this.ifArg;
  }

  public Exp getElseArg(){
    return this.elseArg;
  }

  public Boolean getElse(){
    return this.els;
  }

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpIf(this, arg);
  }

  @Override
  public String toString() {
    if(els)
    {
      return "IF " +con.toString() +" THEN " +ifArg.toString() +" [ELSE " +elseArg.toString() +"]"; 
    }else{ return "IF " +con.toString() +" THEN " +ifArg.toString(); }
  }
}