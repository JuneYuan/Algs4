package assignment;
import java.util.HashSet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
	private ST<Integer, String>  myst1;				// one id -> one synset
	private ST<String, HashSet<Integer>> myst2;		// one synset word -> several indexes
	private HashSet<Integer> roots;

	private final SAP sap;
	private Digraph G;

	// (NlogN) constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		myst1 = new ST<Integer, String>();
		myst2 = new ST<String, HashSet<Integer>>();	
		roots = new HashSet<Integer>();
		
		// 读入synsets*.txt
		String[] lines = new In(synsets).readAllLines(), a;
		String synset;
		HashSet<Integer> hs;
		for (int i = 0; i < lines.length; i++) {
			roots.add(i);
			
			synset = lines[i].split(",")[1];
			myst1.put(i, synset);
			
			a = synset.split(" ");
			for (String word : a) {
				hs = myst2.get(word);
				if (hs == null)	hs = new HashSet<Integer>();
				hs.add(i);
				myst2.put(word, hs);
			}			
		}

		G = new Digraph(lines.length);
		
		// 读入hypernyms*.txt
		lines = new In(hypernyms).readAllLines();
		for (int i = 0; i < lines.length; i++) {
			a = lines[i].split(",");
			int v = Integer.parseInt(a[0]);
			if (a.length >= 2)
				roots.remove(v);
			for (int j = 1; j < a.length; j++)
				G.addEdge(v, Integer.parseInt(a[j]));
		}
		
		// 检查输入的是否为一个单根有向无环图
		if (roots.size() > 1) {
			System.out.println(roots.size());
			throw new IllegalArgumentException("NOT Single Root");
		}
		DirectedCycle cyclefinder = new DirectedCycle(G);
		if (cyclefinder.hasCycle())
			throw new IllegalArgumentException("NOT a DAG");
		//showG();
		
		sap = new SAP(G);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return myst2.keys();
	}

	// (logN) is the word a WordNet noun?
	public boolean isNoun(String word) {
		return myst2.contains(word);
	}

	// (N) distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))	throw new IllegalArgumentException();
		
		Iterable<Integer> v = myst2.get(nounA);
		Iterable<Integer> w = myst2.get(nounB);
		return sap.length(v, w);
	}

	// (N) a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		Iterable<Integer> v = myst2.get(nounA);
		Iterable<Integer> w = myst2.get(nounB);
		int k = sap.ancestor(v, w);
		return myst1.get(k);
	}
	
	private void showG() {
		StringBuilder s = new StringBuilder();
		s.append(G.V() + " vertices, " + G.E() + " edges \n");
		for (int v = 0; v < G.V(); v++) {
			s.append(myst1.get(v) + ": ");
			for (int w : G.adj(v)) {
				s.append(myst1.get(w) + "; ");
			}
			s.append("\n");
		}
		StdOut.println(s.toString());
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		//WordNet wn = new WordNet(args[0], args[1]);
		WordNet wn = new WordNet("../wordnet/synsets.txt", "../wordnet/hypernyms.txt");
		StdOut.println(wn.myst2.size() + " entries.");
		
/*		
		String nounA = "Brown_Swiss";
		String nounB = "barrel_roll";
		int dist = wn.distance(nounA, nounB);
		String sap = wn.sap(nounA, nounB);
		StdOut.printf("distance = %d, sap = %s\n", dist, sap);*/
		
/*		
 		int N = 100;
		while (N-- > 0) {
			String nounA = wn.st2.get(StdRandom.uniform(wn.st2.size()));
			String nounB = wn.st2.get(StdRandom.uniform(wn.st2.size()));
			StdOut.println(String.format("dist(%s, %s)=%d", nounA, nounB, wn.distance(nounA, nounB)));			
		}*/
		
	}
}
