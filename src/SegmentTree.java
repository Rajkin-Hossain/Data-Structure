public class SegmentTree {
    int [] tree;
    int treeLength;
    
    int [] datas;
   
    public SegmentTree(int max_size) {
        tree = new int[max_size * 4];
        treeLength = max_size;
       
        initialize(1, 1, treeLength);
    }
       
    public void initialize(int nodeId, int i, int j) {
        if (i == j) {
            tree[nodeId] = datas[i];
           
            return;
        }
           
        int left = nodeId * 2;
        int right = nodeId * 2 + 1;
        int mid = (i + j) / 2;
       
        initialize(left, i, mid);
        initialize(right, mid + 1, j);
    }
       
    public int query(int b, int e) {
        return query(1, 1, treeLength, b, e);
    }
       
    public int query(int nodeId, int i, int j, int b, int e) {
        if (i>e || j<b) {  
            return 0;
        }
       
        if (i>=b && j<=e) {
            return tree[nodeId];
        }
       
        int left = nodeId * 2;
        int right = nodeId * 2 + 1;
        int mid = (i + j) / 2;
           
        return query(left, i, mid, b , e) + query(right, mid + 1, j, b, e);
    }
	
    public void update(int index, int value) {
        update(1, 1, treeLength, index, value);
    }
   
    public void update(int nodeId, int i, int j, int index, int value) {
        if (j<index || i>index) {
            return;
        }
       
        if (i == index && index == j) {
            tree[nodeId] = value;
           
            return;
        }
       
        int left = nodeId * 2;
        int right = nodeId * 2 + 1;
        int mid = (i + j) / 2;
       
        update(left, i, mid, index, value);
        update(right, mid + 1, j, index, value);
       
        tree[nodeId] = tree[left] + tree[right];
    }
}
