package system;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import venmo.Transaction;
import venmo.User;

public class LRUCache {
	TreeSet<Transaction> stream;
	HashMap<String, User> userMap;
	int sec;// 60

	public LRUCache(int sec) {
		stream = new TreeSet();
		this.userMap = new HashMap();
		this.sec = sec;
	}

	public boolean add(Transaction trans) {
		try {
			if (stream.isEmpty()) {
				stream.add(trans);
				addTrans(trans);
				return true;
			}

			Timestamp lastTimestamp = new Timestamp(
					stream.last().created_time.getTime() - sec * 1000);
			// normal
			if (lastTimestamp.after(trans.created_time))
				return false;
			addTrans(trans);
			stream.add(trans);
			lastTimestamp = new Timestamp(stream.last().created_time.getTime() - sec
					* 1000);
			while (lastTimestamp.compareTo(stream.first().created_time) > 0) {
				removeTrans(stream.pollFirst());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addTrans(Transaction trans) {
		User actor = new User(trans.actor);
		if (userMap.containsKey(trans.actor)) {
			actor = userMap.get(trans.actor);
		}
		User target = new User(trans.target);
		if (userMap.containsKey(trans.target)) {
			target = userMap.get(trans.target);
		}
		actor.degree++;
		target.degree++;
		userMap.put(trans.actor, actor);
		userMap.put(trans.target, target);
	}

	public double findMedian() {
		if (userMap.size() == 0)
			return 0;
		int[] temp = new int[userMap.size()];
		int i = 0;
		for (Entry<String, User> entry : userMap.entrySet()) {
			temp[i++] = entry.getValue().degree;
		}

		Arrays.sort(temp);
		double median = 0;
		if (temp.length % 2 == 1) {
			return temp[temp.length / 2];
		} else {
			return (temp[temp.length / 2] + temp[temp.length / 2 - 1])*1.0 / 2;
		}
	}

	private void removeTrans(Transaction trans) {
		if (userMap.containsKey(trans.actor)) {
			User actor = userMap.get(trans.actor);
			actor.degree--;
			userMap.put(trans.actor, actor);
			if (actor.degree <= 0)
				userMap.remove(trans.actor);
		}
		if (userMap.containsKey(trans.target)) {
			User target = userMap.get(trans.target);
			target.degree--;
			userMap.put(trans.target,target);
			if (target.degree <= 0)
				userMap.remove(trans.target);
		}

	}
}
