import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProblemOnObjectPool {

    MyL [] graph = new MyL[1001];
    MyQ queue = new MyQ();
    
    ObjectPool pool = new ObjectPool();
    
    FastInput k = new FastInput("/home/rajkin/Desktop/input.txt");
    //FastInput k = new FastInput(System.in);
    FastOutput z = new FastOutput();
    
    int nodes;
    
    void bfs(int startNode) {
        queue.add(startNode);

        int[] d = new int[nodes + 1];
        for (int i = 1; i <= nodes; i++) {
            d[i] = Integer.MAX_VALUE;
        }

        d[startNode] = 0;

        while (!queue.isEmpty()) {
        	int u = queue.poll().element;
            for (MyLNode cursor = graph[u].head; cursor != null; cursor = cursor.next) {
                int v = cursor.element;
                if (d[v] > d[u] + 6) {
                    d[v] = d[u] + 6;
                    
                    queue.add(v);
                }
            }
        }

        for (int i = 1; i <= nodes; i++) {
            if (d[i] == Integer.MAX_VALUE)
                z.print(-1 + " ");
            else if (i != startNode) {
                z.print(d[i] + " ");
            }
        }

        z.println();
    }
    
    void startAlgorithm(){
    	int queries = k.nextInt();
        while (queries-- > 0) {
        	pool.clear();
        	queue.clear();
        	
            nodes = k.nextInt();
            int edges = k.nextInt();

            for (int i = 1; i <= nodes; i++) {
                if(graph[i] == null){
                	graph[i] = new MyL();
                }
                else{
                	graph[i].clear();
                }
            }

            while (edges-- > 0) {
                int u = k.nextInt();
                int v = k.nextInt();

                graph[u].add(v);
                graph[v].add(u);
            }
            
            bfs(k.nextInt());
        }
        
        /*long a = 2l * Integer.MAX_VALUE;
        long b = (long)2 * Integer.MAX_VALUE;
        System.out.println(a == b ? true : false);*/
        
        long sum = 2;
        sum *= Integer.MAX_VALUE;
        System.out.println(sum);
        
        //z.flush();
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
    	new ProblemOnObjectPool().startAlgorithm();
    }

	class MyL{
		MyLNode head, tail;
		int size;

		public MyL() {
			size = 0;
			head = tail = null;
		}

		void add(int element){
			if(head == null){
				head = tail = pool.getMyLNode(element);
			}
			else {
				tail.next = pool.getMyLNode(element);
				tail = tail.next;
			}
			
			// FOCUS ON SIZE FOR BOTH IF ELSE
			
			size++;
		}
		
		void clear(){
			size = 0;
			head = tail = null;
		}
	}
	
	class MyLNode {
		MyLNode next;
		int element;
		
		public MyLNode(int element) {
			this.element = element;
		}
		
		void clear(){
			next = null;
			element = 0;
		}
	}
	
	class MyQ{
		MyQNode head, tail;
		int size;
		
		public MyQ() {
			head = tail = null;
			size = 0;
		}
		
		void add(int element){
			if(head == null){
				head = tail = pool.getMyQNode(element);
			}
			else {
				tail.next = pool.getMyQNode(element);
				tail = tail.next;
			}
			
			// FOCUS ON SIZE FOR BOTH IF ELSE
			
			size++;
		}
		
		MyQNode poll(){
			
			MyQNode returnNode = head;
			head = head.next;
			size--;
			
			return returnNode;
		}
		
		boolean isEmpty(){
			return head == null;
		}
		
		void clear(){
			size = 0;
			head = tail = null;
		}
	}
	
	class MyQNode {
		MyQNode next;
		int element;
		
		public MyQNode(int element) {
			this.element = element;
		}
		
		void clear(){
			next = null;
			element = 0;
		}
	}
	
	class ObjectPool{
		MyQNode [] qNode;
		MyLNode [] lNode;
		
		int qNodeC, lNodeC;
		
		public ObjectPool(){
			qNode = new MyQNode[100000];
			lNode = new MyLNode[100000];
			
			qNodeC = lNodeC = 0;
		}
		
		void clear(){
			qNodeC = 0;
			lNodeC = 0;
		}
		
		MyQNode getMyQNode(int element){
			if(qNodeC >= qNode.length){
				return new MyQNode(element);
			}
			
			if(qNode[qNodeC] == null){
				qNode[qNodeC] = new MyQNode(element);
			}
			else {
				qNode[qNodeC].clear();
			}
			
			// FOCUS ON THISSSSSSS LINEEEEEEEEEEE
			qNode[qNodeC].element = element;
			
			return qNode[qNodeC++];
		}
		
		MyLNode getMyLNode(int element){
			if(lNodeC >= lNode.length){
				return new MyLNode(element);
			}
			
			if(lNode[lNodeC] == null){
				lNode[lNodeC] = new MyLNode(element);
			}
			else{
				lNode[lNodeC].clear();
			}
			
			// FOCUS ON THISSSSSSS LINEEEEEEEEEEE
			lNode[lNodeC].element = element;

			return lNode[lNodeC++];
		}
	}

    /* MARK: FastInput and FastOutput implementation */

    class FastInput {
        BufferedReader reader;
        StringTokenizer tokenizer;

        public FastInput(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
        }

        public FastInput(String path) {
            try {
                reader = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tokenizer = null;
        }

        public String next() {
            return nextToken();
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        public boolean hasNext() {
            try {
                return reader.ready();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }

        public String nextToken() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line == null) {
                    return null;
                }
                tokenizer = new StringTokenizer(line);
            }
            return tokenizer.nextToken();
        }

        public Integer nextInt() {
            return Integer.parseInt(nextToken());
        }

        public Long nextLong() {
            return Long.valueOf(nextToken());
        }
    }

    class FastOutput extends PrintWriter {
        public FastOutput() {
            super(new BufferedOutputStream(System.out));
        }

        public void debug(Object... obj) {
            System.err.println(Arrays.deepToString(obj));
        }
    }
}
