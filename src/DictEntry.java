/**
 * This class represents an entry in the dictionary, associating a configuration with its integer score.
 * @author Taha Nasir
 */
public class DictEntry {

	private String config;
	private int score;
	
	/**
	 * Constructor for DictEntry that takes a string configuration and an integer score as inputs
	 * @param config input of DictEntry
	 */
	public DictEntry(String config, int score){
		this.config = config;
		this.score = score;
	}
	
	/**
	 * Getter method to return config
	 * @return config
	 */
	public String getConfig(){
		return config;
	}
	
	/**
	 * Getter method to return score
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
}
