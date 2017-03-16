package smpl.values;

import smpl.sys.SmplException;
import java.util.*;
import java.io.*;
import static smpl.values.SmplValue.make;

public class SmplQuadratic extends SmplValue<SmplQuadratic>  {

	
	ArrayList<Term> exp;
	String expression = "";

	public SmplQuadratic(String q){
		
		q = q.trim();
		for(int i = 0; i < q.length(); i++)
		{
			if( !(q.charAt(i) == '*'))
			{
				if((q.charAt(i) == ' ' && q.charAt(i + 1) == '*') || (q.charAt(i) == ' ' && q.charAt(i + 1) == '^'))
				{

				}
				else
				{
					if((q.charAt(i) == '^' && q.charAt(i + 1) == ' ') || (q.charAt(i) == '+' && q.charAt(i + 1) == ' ') || (q.charAt(i) == '-' && q.charAt(i + 1) == ' '))
					{
						expression += q.charAt(i);
						i++;
					}
					else{
						expression += q.charAt(i);
					}
					
				}
				
			}
			else
			{

				i++;
			}

			 
		}
		//debugger
		//System.out.println(expression);
	}


	public String differentiateThis(){

		String differential = "";

		try{

			InputStream is =  new ByteArrayInputStream(expression.getBytes());
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			StringTokenizer st = st = new StringTokenizer(in.readLine());;
			boolean firstToken = true;

		
			 
			 
			
			
		
			

		while(st.hasMoreTokens())
		{
			boolean isPositive;
			int power;
			int coefficient;
			String name;
			String input = st.nextToken();
			int endOfCoefficient = 0;
			int endOfTerm = 0;
			
			if(firstToken)
			{
				if(input.charAt(0) == '-')
				{
					isPositive = false;
				}else { isPositive = true; }
				
			
			}
			else 
			{
				if(input.charAt(0) == '+')
					isPositive = true;
				else 
					isPositive = false;
			}

			for(int i = ((isPositive && firstToken) ? (0) : (1)); i < input.length(); i++ )
				{
					
					if(input.charAt(i) > '9' || input.charAt(i) < '0' || input.charAt(i) == ' ')
					{
						
						endOfCoefficient = i;
						

						break;
					}
				}


			//TODO modify such that it accomadates trig functions
			if(endOfCoefficient != 0)
			{

				String j = input.substring(((isPositive && firstToken) ? (0) : (1)), endOfCoefficient);
				
				coefficient = Integer.parseInt(j);

				//name = input.substring(endOfCoefficient + 1, input.indexOf("^"));
				power = Integer.parseInt(input.substring(endOfCoefficient + 2));
				//debugger
				//System.out.println( coefficient +"x^" + power);
			}
			else 
			{ 
				coefficient = 1;
				//name = input.substring(endOfCoefficient + 1, input.indexOf("^"));
				power = Integer.parseInt(input.substring(endOfCoefficient + 3));
			}

			if(firstToken)
			{
				firstToken = false;
			}
				
			name = "x";
			Term term = new Term(coefficient,name,power, isPositive);
			//exp.add(term);
			differential+=  differentiateTerm(term);
		}
		
		}
		catch (IOException e) { 
		
			e.printStackTrace(System.out);
		}
		

		return differential;
	}



	public String differentiateTerm(Term x){
		int coefficient = x.getCoefficient();
		int power = x.getPower();
		boolean isPositive = x.positive();
		String name = x.getName();

		return (((isPositive && power > 0) ? ("+") : (!isPositive && power < 0) ? ("+") : ("-")) + " "
				+ ((coefficient * power < 0) ? (coefficient * power * -1) : (coefficient * power))
				+ " * x ^ " +(power - 1) +" ");

		

	}

	@Override
	public SmplType getType(){
		return SmplType.QUADRATIC;
	}



	@Override
	public String toString() {

		String result = "";

		
		return result;
	}

	public  class Term {

		int coefficient, power;
		String name;
		boolean isPositive;

		public Term(int coefficient, String name, int power, boolean isPositive){
			this.name = name;
			this.coefficient = coefficient;
			this.power = power;
			this.isPositive = isPositive;
		}

		public int getCoefficient(){
			return this.coefficient;
		}

		public int getPower(){
			return this.power;
		}

		public String getName(){
			return this.name;
		}

		public boolean positive (){
			return this.isPositive;
		}
	}
}