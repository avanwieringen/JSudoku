package nl.falcon108.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class Collections {
	
	/**
	 * Creates 2-dimensional ArrayList from a List with all the combinations of length k
	 * @param collection
	 * @param k
	 * @return 2-dimensional ArrayList with combinations
	 */
	public static <T> ArrayList<ArrayList<T>> combinations(List<T> collection, int k) {
		ArrayList<ArrayList<T>> comb    = new ArrayList<ArrayList<T>>();
		ArrayList<ArrayList<T>> chCombs;
		ArrayList<T> temp;
		for(int i = 0; i < collection.size() - k + 1; i++) {
			if(k > 1) {
				chCombs = combinations(collection.subList(i + 1, collection.size()), k - 1);
				for(ArrayList<T> c : chCombs) {
					temp = new ArrayList<T>(c);
					temp.add(0, collection.get(i));
					comb.add(temp);
				}
			} else {
				temp = new ArrayList<T>();
				temp.add(collection.get(i));
				comb.add(temp);
			}
		}
		return comb;
	}
	
	/**
	 * Creates an ArrayList with all the unique values from a List
	 * @param collection
	 * @return ArrayList with unique values
	 */
	public static <T> ArrayList<T> unique(List<T> collection) {
		ArrayList<T> unique = new ArrayList<T>();
		for(T t : collection) {
			if(!unique.contains(t)) {
				unique.add(t);
			}
		}
		return unique;
	}
	
	public static <T> boolean fits(List<T> collection, List<T> into) {
		if(collection.size() > into.size()) { return false; }
		for(T t : collection) {
			if(!into.contains(t)) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> boolean covers(List<List<T>> collection, List<T> over) {
		ArrayList<T> values = new ArrayList<T>();
		for(List<T> lt : collection) {
			if(!fits(lt, over)) { return false; }
			
			for(T t : lt) {
				if(!values.contains(t)) { values.add(t); }
			}
		}
		
		if(values.size() == over.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean fits(int[] collection, List<Integer> into) {
		return fits(convert(collection), into);
	}
	
	public static ArrayList<Integer> convert(int[] collection) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int i : collection) {
			ret.add(i);
		}
		return ret;
	}
}
