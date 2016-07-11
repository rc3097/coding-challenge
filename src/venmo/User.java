package venmo;

import java.sql.Timestamp;
import java.util.TreeSet;

public class User implements Comparable<User>{
	public String name;
	public int degree;
	public User(String name) {
		this.name = name;
		degree = 0;
	}
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.degree-o.degree;
	}
	
}
