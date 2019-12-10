public class UnionFind {

	int [] parent;
	int [] rank;
	int [] setSize;

	int size;
	int disjointRoots;
	
	public UnionFind(int size) {
		this.size = size;

		rank = new int[size];
		parent = new int[size];
		setSize = new int[size];

		for(int i = 0; i<size; i++) {
			parent[i] = i;
		}
		
		disjointRoots = size;
	}

	public void mergeSets(int x, int y) {
		int PX = findSet(x);
		int PY = findSet(y);

		if(PX == PY) return;
		
		disjointRoots--;

		if (rank[PX] > rank[PY]) {
			parent[PY] = PX;
			setSize[PX] = setSize[PX] + setSize[PY];
		}
		else {
			parent[PX] = PY;
			setSize[PY] = setSize[PX] + setSize[PY];
		}

		if (rank[PX] == rank[PY]) rank[PY] = rank[PY] + 1; // 2 ta rank soman thaka means ekta tree er size barano
	}
	
	public int sizeOfSet(int i) { 
		return setSize[findSet(i)]; 
	}
	
	public int numOfDisjointSets() { 
		return disjointRoots; 
	}

	public int findSet(int x){
		while (x != parent[x]) { 
			x = parent[x];
		}

		return parent[x];
	}

	public boolean isSameSet(int x, int y) {

		return findSet(x) == findSet(y);
	}
}
