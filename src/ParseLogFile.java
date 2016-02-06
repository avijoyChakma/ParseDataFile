import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ParseLogFile {

	public static void main(String[] args) throws Exception {
		/*Tried to split the file in multiple files
		 * 
		 * RandomAccessFile raf = new RandomAccessFile("TestDataGeneration.txt",
		 * "r"); long numSplits = 10; //from user input, extract it from args
		 * long sourceSize = raf.length(); long bytesPerSplit =
		 * sourceSize/numSplits ; long remainingBytes = sourceSize % numSplits;
		 * 
		 * int maxReadBufferSize = 8 * 1024; //8KB for(int destIx=1; destIx <=
		 * numSplits; destIx++) { BufferedOutputStream bw = new
		 * BufferedOutputStream(new FileOutputStream("split"+destIx+".txt"));
		 * if(bytesPerSplit > maxReadBufferSize) { long numReads =
		 * bytesPerSplit/maxReadBufferSize; long numRemainingRead =
		 * bytesPerSplit % maxReadBufferSize; for(int i=0; i<numReads; i++) {
		 * readWrite(raf, bw, maxReadBufferSize); } if(numRemainingRead > 0) {
		 * readWrite(raf, bw, numRemainingRead); } }else { readWrite(raf, bw,
		 * bytesPerSplit); } bw.close(); } if(remainingBytes > 0) {
		 * BufferedOutputStream bw = new BufferedOutputStream(new
		 * FileOutputStream("split."+(numSplits+1))); readWrite(raf, bw,
		 * remainingBytes); bw.close(); }
		 * 
		 * raf.close();
		 * 
		 * ReadFiles rf = new ReadFiles(); for(int destIx=1; destIx <=
		 * numSplits; destIx++) { rf.readFiles("split"+destIx+".txt"); }
		 */

		ReadFiles rf = new ReadFiles();
		rf.readFiles("TestDataGeneration.txt");
		rf.populateAccessList();
		rf.storeAccessNoIntoArray();
		rf.printMostAccessedClientIp();
	}

	static void readWrite(RandomAccessFile raf, BufferedOutputStream bw,
			long numBytes) throws IOException {
		byte[] buf = new byte[(int) numBytes];
		int val = raf.read(buf);
		if (val != -1) {
			bw.write(buf);
		}
	}

}
