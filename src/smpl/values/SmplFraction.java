

package smpl.values;

import smpl.sys.SmplException;
import java.util.*;

public class SmplFaction extends SmplValue<SmplPair>  {

	public SmplFraction(SmplValue<?> numerator, SmplValue<?> denominator){
		super (numerator, denominator);
	}

	@Override
	public SmplType getType(){
		return SmplType.FRACTION;
	}


	@Override
	public String toString() {
		return "(" + this.getFirstValue().toString() + " / " + this.getSecondValue().toString() + ")";
	}
}