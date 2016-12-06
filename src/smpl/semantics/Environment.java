package smpl.semantics;

import smpl.sys.SmplException;
import smpl.values.SmplValue;
import java.util.*;

public class Environment<T extends SmplValue<?>> {

	HashMap<String, T> dictionary;
	Environment<T> parent = null;

	// constructors

	public Environment(){
		dictionary = new HashMap<>();
	}

	public Environment(String[] ids, T[] values){
		dictionary = new HashMap<>();
		for(int i=0; i<ids.length; i++){
			put(ids[i], values[i]);
		}
	}

	public Environment(String[] ids, T[] values, Environment<T> p){
		parent = p;
		dictionary = new HashMap<>();
		for(int i=0; i<ids.length; i++){
			put(ids[i], values[i]);
		}
	}

	public Environment(ArrayList<String> ids, ArrayList<T> values){
		dictionary = new HashMap<>();
		for(int i=0; i<ids.size(); i++){
			put(ids.get(i), values.get(i));
		}
	}

	public Environment(ArrayList<String> ids, ArrayList<T> values, Environment<T> p){
		parent = p;
		dictionary = new HashMap<>();
		for(int i=0; i<ids.size(); i++){
			put(ids.get(i), values.get(i));
		}
	}

	// global environment
	public static <T extends SmplValue<T>> Environment<T> makeGlobalEnv() {
		Environment<T> result = new Environment<>();
		return result;
	}

	// store a binding
	public void put(String id, T value){
		dictionary.put(id, value);
	}

	// return the value of an identifier
	public T get(String id) throws SmplException {
		T result = dictionary.get(id);
		if (result == null)
			if (parent == null)
				throw new SmplException("Unbound variable " + id);
			else
				return parent.get(id);
		else
			return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		Iterator<String> iter = dictionary.keySet().iterator();
		while (iter.hasNext()) {
		    result = result.append(iter.next());
		}
		return result.toString();
	}
}