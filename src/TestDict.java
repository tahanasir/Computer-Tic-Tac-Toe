public class TestDict {

  /*
  ** Test program for the Dictionary class.
  ** This program WILL NOT COMPILE if your insert or remove
  ** methods do not throw a DictionaryException.
  */

  public static void main(String[] args) {
    Dictionary dict = new Dictionary(9901);
    DictEntry pair;
    boolean pass;

    // Test 1: insert a data item in the dictionary.
    // Should not throw an exception.
    try {
      dict.insert(new DictEntry("answer", 42));
      System.out.println("   Test 1 succeeded");
    } catch (DictionaryException e) {
      System.out.println("***Test 1 failed");
    }

    // Test 2: try to insert another data item with the same key.
    // Should throw an exception.
    try {
      dict.insert(new DictEntry("answer", 56));
      System.out.println("***Test 2 failed");
    } catch (DictionaryException e) {
      System.out.println("   Test 2 succeeded");
    }

    // Test 3: find a key in the table.
    if (dict.find("answer") == -1) 
	System.out.println("***Test 3 failed");
    else System.out.println("   Test 3 succeeded");

    // Test 4: look for an inexistent key
    if (dict.find("chicken") != -1) 
	  System.out.println("***Test 4 failed");
    else System.out.println("   Test 4 succeeded");

    // Test 5: try to delete a nonexistent entry.
    // Should throw an exception.
    try {
      dict.remove("chicken");
      System.out.println("***Test 5 failed");
    } catch (DictionaryException e) {
      System.out.println("   Test 5 succeeded");
    }

    // Test 6: delete an actual entry.
    // Should not throw an exception.
    try {
      dict.remove("answer");
      System.out.println("   Test 6 succeeded");
    } catch (DictionaryException e) {
      System.out.println("***Test 6 failed");
    }

    int collisions = 0;
    String s;


    // Test 7: insert 10000 different values into the Dictionary
    try {
	for (int i = 0; i < 10000; ++i) {
	    s = (new Integer(i)).toString();
	    for (int j = 0; j < 5; ++j) s += s;
	    collisions += dict.insert(new DictEntry(s,i));
	}
      System.out.println("   Test 7 succeeded");
    } catch (DictionaryException e) {
      System.out.println("***Test 7 failed");
    }

    pass = true;
    // Test 8: check that all these values are in the Dictionary
    for (int i = 0; i < 10000; ++i) {
	s = (new Integer(i)).toString();
	for (int j = 0; j < 5; ++j) s += s;
	if (dict.find(s) == -1) {
	    System.out.println("***Test 8 failed");
	    pass = false;
	    break;
	}
    }
    if (pass) System.out.println("   Test 8 succeeded");

    // Test 9: Remove the first 1000 data items and verify that the rest
    // are in the dictionary 
    pass = true;
    try {
	for (int i = 0; i < 1000; ++i) {
	    s = (new Integer(i)).toString();
	    for (int j = 0; j < 5; ++j) s += s;
	    dict.remove(s);
	}

	for (int i = 1000; i < 10000; ++i) {
	    s = (new Integer(i)).toString();
	    for (int j = 0; j < 5; ++j) s += s;
	    if (dict.find(s) == -1) {
		System.out.println("***Test 9 failed");
		pass = false;
		break;
	    }
	}
	if (pass) System.out.println("   Test 9 succeeded");
    }
    catch (DictionaryException e) {
	System.out.println("***Test 9 failed");
    }



    //Test 9: Number of collisions
    if (collisions >= 6000) {
	System.out.println("***Test 10 failed");
    System.out.println("Too many collisions: "+collisions);
    }
    else  System.out.println("   Test 10 succeeded");
  }
}
