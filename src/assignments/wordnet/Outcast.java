package assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordnet;
	
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;	
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String ret = nouns[0];
		int distI, distMax = 0;
		for (int i = 0; i < nouns.length; i++) {
			distI = 0;
			for (int j = 0; j < nouns.length; j++) 
				distI += wordnet.distance(nouns[i], nouns[j]);
			if (distI > distMax) {
				distMax = distI;
				ret = nouns[i];
			}			
		}
		return ret;
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}
