import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import system.LRUCache;
import venmo.Transaction;

// example of program that calculates the  median degree of a 
// venmo transaction graph
public class rolling_median {
	public static void main(String[] args) throws Exception {
		LRUCache q = new LRUCache(60);
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));

			BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'").create();
			String line;
			while ((line = br.readLine()) != null) {
				Transaction trans = gson.fromJson(line, Transaction.class);
				q.add(trans);
				 bw.write(String.format("%.2f",q.findMedian()));
//				System.out.println(String.format("%.2f", q.findMedian()));
				bw.newLine();
			}

			br.close();
			bw.close();
			System.out.println("finished");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}