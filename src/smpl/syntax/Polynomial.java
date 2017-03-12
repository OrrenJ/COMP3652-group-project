package smpl.syntax;

/*
* Class to model the basic struction of a polynoimial
* Assumption: quotient and exponent will only be of type intger 
*/
public class Polynomial {

    String var;
    Integer quotient, power;

    public Polynomial(Integer q, String v, Integer p) {
	   var = id;
	   valExp = v;
    }

    public String getVar() {
	   return var;
    }

    public Integer getQuotient() {
	   return valExp;
    }

    public Integer getPower() {
       return valExp;
    }

    public String toString() {
       return quotient.toString() +var +"^" +power.toString();
    }

}
