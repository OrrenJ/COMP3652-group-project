package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;

public class ExpPlot extends Exp {

	Exp func;
    String var;
    Exp start;
    Exp end;

    public ExpPlot() {
        super();
    }

    public ExpPlot(Exp f, String v, Exp start, Exp end) {
        func = f;
        var = v;
        this.start =start;
        this.end = end;

    }

    public Exp getExp() {
        return func;
    }    

    public String getVar() {
        return var;
    }
    
    public Exp getLo(){
        return this.start;
    }

    public Exp getHi(){
        return this.end;
    }

    @Override
    public <S, T> T visit(Visitor<S, T> v, S state) throws SmplException {
        return v.visitExpPlot(this, state);       
    }

    @Override
    public String toString(){
    	return String.format("plot %s for %s in [%s:%s]", func.toString(), var, start.toString(), end.toString()); 
    }
    
}