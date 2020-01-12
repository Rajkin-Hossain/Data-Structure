// While using this template don't copy import part to avoid eclipse copy paste error

import java.util.ArrayList;

public class Treap {
	
    Node root;
	
    int length;
    int g_seed;
    int ans;
    
    ArrayList<Long> sortedList;
       
    public Treap(){
        g_seed = 1;
        length = 0;
        
        sortedList = new ArrayList<Long>();
    }
       
    int fastrand() {
        g_seed = (214013 * g_seed + 2531011);
        
        return (g_seed>>16) & 0x7FFF;
    }
       
    int getSize(Node cursor){
        if(cursor == null) return 0;
        
        int leftSubTreeSize = cursor.left != null ? cursor.left.size : 0;
        int rightSubTreeSize = cursor.right != null ? cursor.right.size : 0;
       
        return 1 + leftSubTreeSize + rightSubTreeSize;
    }
       
    Node rotateRight(Node cursor){
        Node returedNode = cursor.left;
       
        Node z = cursor.left.right;
        cursor.left.right = cursor;
        cursor.left = z;
       
        return returedNode;
    }
   
    Node rotateLeft(Node cursor){
        Node returedNode = cursor.right;
       
        Node z = cursor.right.left;
        cursor.right.left = cursor;
        cursor.right = z;
       
        return returedNode;
    }
       
    Node insert(Node cursor, long value){
        if(cursor == null) {
            return new Node(value);
        }
       
        if(value < cursor.value){
            cursor.left = insert(cursor.left, value);
           
            if(cursor.left.priority > cursor.priority){
                cursor = rotateRight(cursor);
            }
        }
        else{
            cursor.right = insert(cursor.right, value);
           
            if(cursor.right.priority > cursor.priority){
                cursor = rotateLeft(cursor);
            }
        }
       
        if(cursor.left != null) cursor.left.size = getSize(cursor.left);
        if(cursor.right != null) cursor.right.size = getSize(cursor.right);
       
        cursor.size = getSize(cursor);
       
        return cursor;
    }
        
    void insert(long value){
        root = insert(root, value);
        length++;
    }
    
    boolean isContain(long value) {
    	
    	Node cursor = root;
    	
    	while(cursor != null) {
    		if(value < cursor.value) {
    			cursor = cursor.left;
    		}
    		else if(value == cursor.value) {
    			return true;
    		}
    		else {
    			cursor = cursor.right;
    		}
    	}
    	
    	return false;
    }
   
    Node delete(Node cursor, long key) {
        if (cursor == null) return cursor;
       
        if (key < cursor.value) {
            cursor.left = delete(cursor.left, key);
        }
        else if (key > cursor.value) {
            cursor.right = delete(cursor.right, key);
        }
        else if (cursor.left == null) {
            cursor = cursor.right;
        }
        else if (cursor.right == null) {
            cursor = cursor.left;
        }
        else if (cursor.left.priority < cursor.right.priority){
            cursor = rotateLeft(cursor);
            cursor.left = delete(cursor.left, key);
        }
        else {
            cursor = rotateRight(cursor);
            cursor.right = delete(cursor.right, key);
        }
       
        if(cursor != null) {
            if(cursor.left != null) cursor.left.size = getSize(cursor.left);
            if(cursor.right != null) cursor.right.size = getSize(cursor.right);
           
            cursor.size = getSize(cursor);
        }
     
        return cursor;
    }
       
    void delete(long key){
        root = delete(root, key);
        length--;
    }
       
    Node getKth(Node temp, int par, int k){
      	if(temp == null) return null;
            
    	int currSize = par + getSize(temp.left) + 1;
        if(currSize == k) return temp;
 
        else if(currSize <= k) return getKth(temp.right, currSize, k);
        else return getKth(temp.left, par, k);
    }
       
    long getKth(int k){
        if(k > length) return -1;
        return getKth(root, 0, k).value;
    }
   
    long higher(long key){
        
    	long higherKey = Long.MIN_VALUE;
    	Node cursor = root;
    	
    	while(cursor != null) {
    		if(key < cursor.value) {
    			higherKey = cursor.value;
    			cursor = cursor.left;
    		}
    		else {
    			cursor = cursor.right;
    		}
    	}
    	
    	return higherKey;
    }
   
    long ceil(long key){
        
    	long ceilKey = Long.MIN_VALUE;
    	Node cursor = root;
    	
    	while(cursor != null) {
    		if(key <= cursor.value) {
    			ceilKey = cursor.value;
    			cursor = cursor.left;
    		}
    		else {
    			cursor = cursor.right;
    		}
    	}
    	
    	return ceilKey;
    }
       
    long floor(long key){
        
    	long floorKey = Long.MAX_VALUE;
    	Node cursor = root;
    	
    	while(cursor != null) {
    		if(key >= cursor.value) {
    			floorKey = cursor.value;
    			cursor = cursor.right;
    		}
    		else {
    			cursor = cursor.left;
    		}
    	}
    	
    	return floorKey;
    }
   
    long lower(long key){
        
    	long lowerKey = Long.MAX_VALUE;
    	Node cursor = root;
    	
    	while(cursor != null) {
    		if(key > cursor.value) {
    			lowerKey = cursor.value;
    			cursor = cursor.right;
    		}
    		else {
    			cursor = cursor.left;
    		}
    	}
    	
    	return lowerKey;
    }
    
    int getIndex(Node cursor, long key){
        if(cursor == null) return 0;
        
        if(key > cursor.value){
            return getSize(cursor.left) + 1 + getIndex(cursor.right, key);
        }
        else{
            return getIndex(cursor.left, key);
        }
    }
    
    int getIndex(long key){
        return getIndex(root, key);
    }
    
    long first() {
    	Node cursor = root;
    	
    	while(cursor.left != null) {
    		cursor = cursor.left;
    	}
    	
    	return cursor.value;
    }
    
    long last() {
    	Node cursor = root;
    	
    	while(cursor.right != null) {
    		cursor = cursor.right;
    	}
    	
    	return cursor.value;
    }
    
    ArrayList<Long> keyList() {
    	sortedList.clear();
    	generateKeyList(root);
    	
    	return sortedList;
    }
       
    void generateKeyList(Node root) {
        if (root == null)
            return;
        
        generateKeyList(root.left);
        sortedList.add(root.value);
        generateKeyList(root.right);
    }
   
    class Node {
        double priority;
        long value;
        int size;
       
        Node left, right;
        public Node(long value){
            this.value = value;
            //this.priority = Math.random();
            this.priority = fastrand();
        }
    }
}
