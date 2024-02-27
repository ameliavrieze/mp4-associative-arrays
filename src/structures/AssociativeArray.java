package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Amelia Vrieze
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> cloned = new AssociativeArray<K, V>();
    for (int i = 0; i < this.size; i++) {
      try {
      cloned.set(this.pairs[i].key, this.pairs[i].value);
      } catch (NullKeyException e) {
        System.err.println("Null key exception");
      }
    }
    return cloned; 
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String str = "";
    for (int i = 0; i < this.size; i++) {
      str += "" + i + ": " + this.pairs[i].toString() + "\n";
    }
    return str; 
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } else {
      try {
        int knum = this.find(key);
        this.pairs[knum].value = value;
      } catch (KeyNotFoundException e) {
        if (this.size == this.pairs.length) {
          this.expand();
        }
        this.pairs[this.size++] = new KVPair<K, V>(key, value);
      }
    }
    // STUB
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if (key == null) {
      throw new KeyNotFoundException("Key is null.");
    }
    return this.pairs[this.find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key == null) {
      return false;
    }
    for (int i = 0; i < this.size; i++) {
      if (key.equals(this.pairs[i].key)) {
        return true;
      }
    }
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int knum = this.find(key);
      this.pairs[knum] = new KVPair<K, V>(this.pairs[size - 1].key, this.pairs[size - 1].value);
      this.pairs[--size] = null;
      if (this.size < 1) {
        this.pairs[0] = null;
      }
      
    } catch (KeyNotFoundException e) {}
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    if (key == null) {
      throw new KeyNotFoundException("key is null");
    }
    for (int i = 0; i < this.size; i++) {
      if (key.equals(this.pairs[i].key)) {
        return i;
      }
    }
    throw new KeyNotFoundException();   // STUB
  } // find(K)

} // class AssociativeArray
