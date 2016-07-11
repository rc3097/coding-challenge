package venmo;

import java.sql.Timestamp;

public class Transaction implements Comparable<Transaction>{
	public String actor, target;
	public Timestamp created_time;

	public Transaction(String actor, String target, Timestamp time) {
		this.actor = actor;
		this.target = target;
		this.created_time = time;
	}

	public int compareTo(Transaction o) {
		return this.created_time.compareTo(o.created_time);
	}
	
	

}
