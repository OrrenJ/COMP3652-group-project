package smpl.values;

import smpl.sys.SmplException;
import java.util.*;

public class SmplQuadratic extends SmplValue<SmplQuadratic>  {

	Arraylist<SmplValue<?>> operands;
	ArrayList<String> operators;

	public SmplQuadratic(Arraylist<SmplValue<?>> operands, ArrayList<String> operators){
		this.operators = operators;
		this.operan = operands;
	}

	@Override
	public SmplType getType(){
		return SmplType.QUADRATIC;
	}

	public ArrayList<SmplValue<?>> getOperands(){
		return this.operands; 
	} 

	public ArrayList<SmplValue<?>> getOperators(){
		return this.operators; 
	} 

	public 
	@Override
	public String toString() {

		String result;

		for(int i = 0; i < operands.size(); i++)
		{
			result += operands.get(i).toString();
			if(i < operators.size())
			{
				result += " " +operators.get(i) +" ";
			}
		}
		return result;
	}
}