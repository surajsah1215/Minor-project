package com;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
public class Classifier{
	
    static ArrayList<String> uniquewords;
	static ArrayList<double[]> vector = new ArrayList<double[]>();
	static ArrayList<String> dataset = new ArrayList<String>(); 
	static ArrayList<String[]> tokens = new ArrayList<String[]>();
	
public static void clear(){
	dataset.clear();
	tokens.clear();
	if(uniquewords != null){
		uniquewords.clear();
	}
	vector.clear();
}
public static void testClassifier(String tweet,DefaultTableModel dtm){
	double test[] = generateVector(tweet);
	double distance = 0;
	int index = 0;
	for(int i=0;i<vector.size();i++){
		double train[] = vector.get(i);
		double dis = euclideanDistance(train,test);
		if(dis > distance){
			distance = dis;
			index = i;
		}
	}
	String result = "Unable to classify";
	if(index > 0){
		String data = dataset.get(index);
		String classified = ReadTweets.classifier.get(data);
		Object row[] = {tweet,data,classified};
		dtm.addRow(row);
	} else {
		Object row[] = {tweet,result,result};
		dtm.addRow(row);
	}
}
public static void buildClassifier(){
	clear();
	HashSet<String> hs = new HashSet<String>();
	for(Map.Entry<String,String> me : ReadTweets.classifier.entrySet()){
		String tweet = me.getKey();
		dataset.add(tweet);
		String tweets = tweet.toLowerCase().replaceAll("[^a-zA-Z\\s+]", "");
		String arr[] = tweets.split("\\s+");
		for(String terms : arr){
			hs.add(terms);
		}
		tokens.add(arr);
	}
	uniquewords = new ArrayList<String>(hs);
	System.out.println(uniquewords.size());
	generateVector();
	System.out.println("done");
}
public static double[] generateVector(String input){
	String arr[] = input.split("\\s+");
	double[] tfidf = new double[uniquewords.size()];
	for(int i=0;i<uniquewords.size();i++){
		tfidf[i] = getTF(arr,uniquewords.get(i)) * getIDF(tokens,uniquewords.get(i));
	}
	return tfidf;
}

public static void generateVector(){
	for(String[] words : tokens){
		double[] tfidf = new double[uniquewords.size()];
		for(int i=0;i<uniquewords.size();i++){
			tfidf[i] = getTF(words,uniquewords.get(i)) * getIDF(tokens,uniquewords.get(i));
		}
		vector.add(tfidf);
	}
}
public static double getTF(String[] document, String word){
	double count = 0;
	for(String term : document){
		if(term.equalsIgnoreCase(word))
			count++;
	}
    return count/document.length;
}
public static double getIDF(ArrayList<String[]> document, String word){
	double count = 0;
	for(String[] doc : document){
		for(String term : doc){
			if(term.equalsIgnoreCase(word)){
				count++;
				break;
			}
		}
	}
    return Math.log(document.size()/count);
}
//cosine sim
public static double euclideanDistance(double[] vector1, double[] vector2){
	double dot = 0, magnitude1 = 0, magnitude2=0;
	for(int i=0;i<vector1.length;i++){
		dot+=vector1[i]*vector2[i];
		magnitude1+=Math.pow(vector1[i],2);
    	magnitude2+=Math.pow(vector2[i],2);
	}
	magnitude1 = Math.sqrt(magnitude1);
    magnitude2 = Math.sqrt(magnitude2);
    double d = dot / (magnitude1 * magnitude2);
    return d == Double.NaN ? 0 : d;
}
}

    