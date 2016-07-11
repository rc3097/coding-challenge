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
		if (this.created_time.equals(o.created_time)) {
			return 1;
		}
		return this.created_time.compareTo(o.created_time);
	}
	
	public String toString() {
		return created_time.toString();
	}

}
