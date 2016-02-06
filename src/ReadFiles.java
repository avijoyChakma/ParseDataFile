import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReadFiles {
	HashMap<Integer, Integer> hashValueMapsIpAccessCount = new HashMap<Integer, Integer>();
	HashMap<Integer, String> hashValueMapsIp = new HashMap<Integer, String>();
	HashMap<Integer, List<Integer>> accessCountMapsHashValues = new HashMap<Integer, List<Integer>>();
	ArrayList<Integer> accessCountList = new ArrayList<Integer>();

	public void readFiles(String fileName) {
		try {
			InputStream is = new FileInputStream(fileName);
			InputStreamReader instrm = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(instrm);

			String strLine;

			int count = 0;
			String strArr[];
			while ((strLine = br.readLine()) != null) {
				count++;
				if (count == 1) {
					continue;
				}
				strLine.trim();
				strArr = strLine.split(" ");
				int hash = generateHash(strArr[11]);
				populateHashMap(hash);
				populateHashMapsIp(hash, strArr[11]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generate Hash Value Against IPaddress
	public int generateHash(String str) {
		int i, hash = 7;
		for (i = 0; i < str.length(); i++) {
			hash = hash * 31 + str.charAt(i);
		}
		return hash;
	}


	// Store IPaddress; increment each time new occurrence/appearance
	public void populateHashMap(Integer key) {

		try {
			int value = hashValueMapsIpAccessCount.get(key);
			if (value > 0) {
				value++;
				hashValueMapsIpAccessCount.put(key, value);
			} else {
				hashValueMapsIpAccessCount.put(key, 1);
			}
		} catch (Exception e) {
			hashValueMapsIpAccessCount.put(key, 1);
		}
	}

	// Save IPaddresses against hash value
	public void populateHashMapsIp(int hash, String ip) {
		String val = hashValueMapsIp.get(hash);
		if (val == null) {
			hashValueMapsIp.put(hash, ip);
		}
	}

	// Save IPaddress list against same no. of appearance
	public void populateAccessList() {
		Iterator<Integer> itr = hashValueMapsIpAccessCount.keySet().iterator();
		while (itr.hasNext()) {
			Integer key = itr.next();
			Integer value = hashValueMapsIpAccessCount.get(key);
			List<Integer> list;
			list = accessCountMapsHashValues.get(value);
			if (list == null) {
				list = new ArrayList<Integer>();
				list.add(key);
				accessCountMapsHashValues.put(value, list);
			} else {
				accessCountMapsHashValues.get(value).add(key);
			}
		}
	}

	// Sort the no. of appearance
	public void storeAccessNoIntoArray() {
		Iterator<Integer> itr = hashValueMapsIpAccessCount.keySet().iterator();
		while (itr.hasNext()) {
			Integer key = itr.next();
			if (!accessCountList.contains(hashValueMapsIpAccessCount.get(key)))
				accessCountList.add(hashValueMapsIpAccessCount.get(key));
		}

		accessCountList.sort(null);
	}

	// Print top 5 appearance. 
	public void printMostAccessedClientIp() {
		int count = 0;
		Collections.sort(accessCountList, Collections.reverseOrder());
		Iterator<Integer> itr = accessCountList.iterator();

		while (itr.hasNext()) {
			count++;
			if (count == 6)
				break;
			Integer integer = (Integer) itr.next();
			List<Integer> hashList = accessCountMapsHashValues.get(integer);
			if (hashList == null) {
				break;
			} else {
				System.out.print("Top " + count + "Visited Ips: ");
				Iterator<Integer> itre = hashList.iterator();
				while (itre.hasNext()) {
					Integer key = (Integer) itre.next();
					System.out.print(" " + hashValueMapsIp.get(key));
				}
				System.out.print("\n");
			}
		}
	}
}
