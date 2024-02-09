class ArrList {
	private Array array;// the underlying array
	private int first;// the index of the first element in the list
	private int last;// the index of the first open position after the last element
	private int n;// the number of elements in the list

	// constructor--for the convenience of testing, the capacity will start at 1
	ArrList() {
		this.array = new Array(1);
		this.first = 0;
		this.last = 0;
		this.n = 0;
	}

	// adds a new element (num) to the end of the list
	void addLast(int num) {
		if (n == array.length()) {
			resize(array.length()*2);
		}
		array.setVal(last, num);
		last++;
		n++;
	}

	// adds a new element (num) to the front of the list
	void addFirst(int num) {
		if (n == array.length()) {
			resize(array.length()*2);
		}
		shiftRight(first);
		array.setVal(first, num);
		last++;
		n++;
	}

	// adds a new element (num) at index i
	// throws an exception if i is outside the bounds of the ArrList
	void add(int i, int num) throws ArrayIndexOutOfBoundsException {
		if (i < 0 || i >= n)
			throw new ArrayIndexOutOfBoundsException();
		if (n == array.length()) {
			resize(array.length() + 1);
		}
		shiftRight(i);
		array.setVal(i, num);
		n++;
		last++;
	}

	// gets the element at index i
	int get(int i) throws ArrayIndexOutOfBoundsException {
		if (i < 0 || i > n)
			throw new ArrayIndexOutOfBoundsException();
		return array.getVal(i);
	}

	// gets the index of the first occurrence of <num>
	// returns -1 if <num> is not in the list
	int indexOf(int num) {
		int i = first;
		int c = 0;
		while (c < n) {
			if (array.getVal(i) == num)
				return i;
			i = increment(i);
			c++;
		}
		return -1;
	}

	// returns true if the list contains <num>
	// and false otherwise
	boolean contains(int num) {
		int index = this.indexOf(num);
		return index != -1;
	}

	// returns true if the list is empty
	// and false otherwise
	boolean isEmpty() {
		return first == last;
	}

	int lastIndexOf(int num) {
		int i = first;
		int c = last-1;
		int index = -1;
		while (c >= 0) {
			if (array.getVal(c) == num)
				return c;
			c--;
		}
		return index;
	}

	// removes and returns the first element
	// throws exception if the list is empty
	int removeFirst() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException();
		int num = array.getVal(first);
		first++;
		n--;
		resize(array.length()-1);
		return num;
	}

	// removes and returns the last element
	// throws exception if the list is empty
	int removeLast() throws EmptyListException {
		int num = array.getVal(last-1);
		last = decrement(last, 1);
		n--;
		if (n < array.length() / 2)
			resize(array.length() - 1);
		return num;
	}

	// removes and returns the element at index i
	// throws appropriate exception if the list is empty
	// return dummy value if the index is out of bounds
	int removeByIndex(int i) throws EmptyListException {
		int num = -9999;
		if (i < 0 || i > n - 1)
			return -9999;
		if (i == 0) {
			return removeFirst();
		} else if (i == n - 1) {
			return removeLast();
		} else {
			num = array.getVal(i);
			shiftLeft(i+1, 1);
		}
		n--;
		if (n < array.length() / 2)
			resize(array.length() - 1);
		return num;
	}

	// removes all the elemnts from index i to index j (including the ones at i and
	// at j)
	// throws exception if the list is empty
	void removeRange(int i, int j) throws EmptyListException {
		int count = 0;
		while (count < j - i + 1) {
			removeByIndex(i);
			count++;
		}
	}

	// removes the first occurence of <num>, if it exists
	// return true if the item is removed and false otherwise
	// throws an exception if the list is empty
	boolean removeByValue(int num) throws EmptyListException {
		int index = indexOf(num);
		if (index == -1)
			return false;
		removeByIndex(index);
		return true;
	}

	// sets the element at index <index> to <num>
	int set(int index, int num) {
		int ret = array.getVal(index);
		array.setVal(index, num);
		return ret;
	}

	// returns the number of elements in the list
	int size() {
		return n;
	}

	// used only for testing!!!
	int getAccessCount() {
		return array.getAccessCount();
	}

	// used only for testing!!!
	void resetAccessCount() {
		this.array.resetAccessCount();
	}

	// prints the list--use for testing
	void printList() {
		int i = first;
		while (i != last) {
			System.out.print(array.getVal(i) + " ");
			i = increment(i);
		}
	}

	// shifts all the elements from index i to the end of the list
	// to the left by <by> positions
	private void shiftLeft(int i, int by) {
		int j = decrement(i, by);
		while (i != last) {
			array.setVal(j, array.getVal(i));
			i = increment(i);
			j = increment(j);
		}
	}

	// shifts all the elements from index i to the front of the list
	// by 1 position
	private void shiftRight(int i) {
		int j = last;
		int k = decrement(j, 1);
		while (j != i) {
			array.setVal(j, array.getVal(k));
			j = decrement(j, 1);
			k = decrement(k, 1);
		}
	}

	// increments <i> by 1, wrapping it arond when necessary
	private int increment(int i) {
		return (i + 1) % array.length();
	}

	// decrements <i> by <by>, wrapping it around when necessary
	private int decrement(int i, int by) {
		i = (i - by) % array.length();
		if (i < 0)
			i += array.length();
		return i;
	}

	// resizes the array so that it now has a capacity of <newCap>
	private void resize(int newCap) {
		Array temp = new Array(newCap);
		int i = first;
		int j = 0;
		while (j < n) {
			temp.setVal(j, array.getVal(i));
			j++;
			i++;
		}
		this.array = temp;
		this.first = 0;
		this.last = n;
	}
}
