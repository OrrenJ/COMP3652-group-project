package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.make;
import java.util.*;

public class SmplPolynomial extends SmplValue<SmplPolynomial> {

	String var;
	SmplValue<?> quotient, power;

	public SmplPolynomial(String v, SmplValue q, SmplValue p){
		 
		var = v;
		quotient = q;
		power = p;
	}

	@Override
	public SmplType getType(){
		return SmplType.POLYNOMIAL;
	}

	@Override
	public SmplPolynomial polyValue(){
		return this;
	}

	public String getVariable(){
		return var;
	}

	public SmplValue<?> getQuotient(){
		return quotient;
	}

	public SmplValue<?> getExponent(){
		return power;
	}

	@Override
	public String toString() {
		

		return quotient.toString() +var +"^" +power.toString();
	}
}