package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;
import smpl.values.*;

public class ExpConcatLst extends Exp {

  ExpList lst1, lst2;
  String var1, var2;
  Integer combo;

  public ExpConcatLst(Exp lst1, Exp lst2) {
    this.lst1 = (ExpList)lst1;
    this.lst2 = (ExpList)lst2;
    combo = 1;
  
  }

public ExpConcatLst(String var1, String var2) {
    this.var1 = var1;
    this.var2 = var2;
    combo = 2;
  
  }

public ExpConcatLst(Exp lst1, String var2) {
    this.lst1 = (ExpList)lst1;
    this.var2 = var2;
    combo = 3;
  
  }

public ExpConcatLst(String var1, Exp lst2) {
    this.var1 = var1;
    this.lst2 = (ExpList)lst2;
    combo = 4;
  
  }

  public Integer getCombo(){
    return combo;
  }

  public  ExpList getExpList1(){
    return lst1;
  }

  public  ExpList getExpList2(){
    return lst2;
  }

  public  String getVar1(){
    return var1;
  }
 
 public  String getVar2(){
    return var2;
 }

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpConcatLst(this, arg);
  }

  @Override
  public String toString() {
    if(lst1 != null)
    {
      if(lst2 != null)
      {
        return lst1.toString() +" @ " +lst2.toString();
      }else{return lst1.toString() +" @ " +var2;}
    }
    else
    {
      if(lst2 != null)
      {
        return var1 +" @ " +lst2.toString();
      }else{return var1 +" @ " +var2;}
    }
    
  }
}