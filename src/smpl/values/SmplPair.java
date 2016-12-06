package smpl.values;

public class SmplPair extends SmplValue<SmplPair> {

	SmplValue<?> val1, val2;

	public SmplPair(SmplValue<?> val1, SmplValue<?> val2){
		this.val1 = val1;
		this.val2 = val2;
	}

	@Override
	public SmplType getType(){
		return SmplType.PAIR;
	}

	public SmplValue<?> getFirstValue(){
		return val1;
	}

	public SmplValue<?> getSecondValue(){
		return val2;
	}

	@Override
	public String toString() {
		return "Pair: <" + val1.toString() + ", " + val2.toString() + ">";
	}
}