
public interface DictionaryADT 
{
	public int hashFunction(String config, int a);
	
    public int insert (DictEntry pair) throws DictionaryException;

    public void remove (String config) throws DictionaryException;

    public int find (String config);

    public int numElements();
}
