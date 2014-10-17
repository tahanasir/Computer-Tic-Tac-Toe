/**
 * Imports java class needed to use linked lists.
 */
import java.util.LinkedList;

/**
 * This class implements a dictionary using a hash table with separate chaining.
 * Implementation class for DictionaryADT
 * @author Taha Nasir
 */
public class Dictionary implements DictionaryADT {
	
	private int size;
	private LinkedList<DictEntry>[] T;	
	private int n = 0;
	
	/** 
	 * Method to compute the has function for the hash table. 
     * @param config represents the key
     * @param a is a constant
     * @return hash 
     */
	public int hashFunction(String config, int a) {
		int hash = 0;
		for (int i = 0; i < config.length(); i++){	
			hash = ((hash * a) + config.charAt(i)) % T.length;
			}
		return hash;
	}
	
	/** 
	 * Constructor for Dictionary that takes an integer size of the hash table.
	 * @param size input of Dictionary
     */
	public Dictionary(int size) {
		this.size = size;
		T = (LinkedList<DictEntry>[]) new LinkedList[size];
	}

	/** 
	 * Method to find a key in the hash table.
     * @param config represents the key
     * @return T[pos].get(i).getScore() score stored in the dictionary for a given configuration
     * @return -1 configuration is not in the dictionary 
     */
	public int find(String config) {
		
		int pos = hashFunction(config, 31);
		
		if (T[pos] != null) {
			
			for (int i=0; i < T[pos].size(); i++){
				if (config.compareTo(T[pos].get(i).getConfig()) == 0) {
					return T[pos].get(i).getScore();		
				}
			}		
		}
		return -1;
	}

	/** 
	 * Method to insert a DictEntry into the hash table.
     * @param pair DictEntry made up of config and score
     * @return 1 insertion of pair produces a collision
     * @return 0 no collision 
     */
	public int insert(DictEntry pair) throws DictionaryException {
		
		int pos = hashFunction(pair.getConfig(), 31);
		
		if (T[pos] != null){	
			if (find(pair.getConfig()) != -1){
				throw new DictionaryException("The exact same configuration already exists."); 
			}
			
			else {
				T[pos].add(pair);
				n ++;
				return 1;
			}			
		}	
		
		LinkedList<DictEntry> p = new LinkedList<DictEntry>();
		p.add(pair);	
		T[pos] = p;	
		n ++;	
		return 0;		
	}
	
	/** 
	 * Method to remove a DictEntry from the hash table.
     * @param config represents the key
     * return empty
     */
	public void remove(String config) throws DictionaryException {
		
		int pos = hashFunction(config, 31);
		
		if (T[pos]!=null){
			
			if (find(config) != -1) {
				T[pos].remove(config); 
				n--;	
				return;
			}
		}
		
		throw new DictionaryException("This configuration does not exist.");		
	}
	
	/** 
	 * Method that counts the number of entries in the dictionary.
     * @return n number of DictEntry objects
     */
	public int numElements() {
		return n;
	}
	
}

