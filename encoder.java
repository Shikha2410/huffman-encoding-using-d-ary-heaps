/*
 * Encoder implementation
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map.Entry;

public class encoder {
	
		public static void main(String[] args) {
		String filename="";
		if(0<args.length)
			filename=args[0];
		else 
		{
		   System.err.println("Invalid arguments count:" + args.length);
		   System.exit(0);
		}
		HashMap<Integer,Integer> freq_table = build_freq_table(filename);
		HashMap<Integer,String> code_table = build_tree_and_code_table(freq_table);
		encode_data(filename,code_table);
	}

	private static HashMap<Integer,Integer> build_freq_table(String filename)
	{
		HashMap<Integer,Integer> freq_table = new HashMap<Integer,Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
		    	Integer key=Integer.parseInt(line.trim());
		    	Integer old_freq=freq_table.putIfAbsent(key, 1);
		    	if(old_freq!=null)
		    		freq_table.replace(key, ++old_freq);
		    }
		    br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return freq_table;
	}
	
	private static HashMap<Integer,String> build_tree_and_code_table(HashMap<Integer,Integer> freq_table)
	{
		Instant start_time;
		long timeElapsed;
		
		HuffmanTree tree=new HuffmanTree();
		
		/*
		//binary heap
		start_time = Instant.now();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
			tree.root = build_tree_using_binaryHeap(freq_table);
		}
		timeElapsed = Duration.between(start_time, Instant.now()).toMillis();
		System.out.println("Time using binary heap (microsecond): " + timeElapsed/1000);
		
		
		//pairing heap
		start_time = Instant.now();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
			tree.root = build_tree_using_pairingHeap(freq_table);
		}
		timeElapsed = Duration.between(start_time, Instant.now()).toMillis();
		System.out.println("Time using pairing heap (microsecond): " + timeElapsed/1000);
		
		//4-way heap
		start_time = Instant.now();
		for(int i = 0; i < 10; i++){ //run 10 times on given data set
		tree.root = build_tree_using_fourWayHeap(freq_table);
		}
		timeElapsed = Duration.between(start_time, Instant.now()).toMillis();
		System.out.println("Time using 4-way heap (microsecond): " + timeElapsed/1000);
		*/
		
		tree.root = build_tree_using_fourWayHeap(freq_table);
		HashMap<Integer,String> code_table = generateHuffmanCodes(tree.root,"",new HashMap<Integer,String>());
		
		String filename="code_table.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
		{
            for(Entry<Integer,String> e:code_table.entrySet())
            	writer.write(e.getKey().toString()+" "+e.getValue()+'\n');
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return code_table;
	}
	
	/*
	private static HuffmanNode build_tree_using_binaryHeap(HashMap<Integer, Integer> freq_table) 
	{
		HuffmanNode root = null;
		
		BinaryHeap minheap = new BinaryHeap();
		
		for(Entry<Integer,Integer> e:freq_table.entrySet())
			minheap.insert(new HuffmanNode(e.getValue(),e.getKey(),null,null));
		
		while(!minheap.isEmpty())
		{
			HuffmanNode node1=minheap.removeMin();
			if(minheap.isEmpty())
			{
				root=node1;
				break;
			}
			HuffmanNode node2=minheap.removeMin();
			Integer newFreq=node1.key_freq+node2.key_freq;
			if(minheap.isEmpty())
			{
				if(node1.key_freq>node2.key_freq)
					root=new HuffmanNode(newFreq,-1,node2,node1);
				else
					root=new HuffmanNode(newFreq,-1,node1,node2);
				break;
			}
			if(node1.key_freq>node2.key_freq)
				minheap.insert(new HuffmanNode(newFreq,-1,node2,node1));
			else
				minheap.insert(new HuffmanNode(newFreq,-1,node1,node2));	
		}
		return root;
	}
	
	private static HuffmanNode build_tree_using_pairingHeap(HashMap<Integer, Integer> freq_table) 
	{
		HuffmanNode root = null;
		
		PairingHeap minheap = new PairingHeap();
		
		for(Entry<Integer,Integer> e:freq_table.entrySet())
			minheap.insert(new HuffmanNode(e.getValue(),e.getKey(),null,null));
		
		while(!minheap.isEmpty())
		{
			HuffmanNode node1=minheap.removeMin();
			if(minheap.isEmpty())
			{
				root=node1;
				break;
			}
			HuffmanNode node2=minheap.removeMin();
			Integer newFreq=node1.key_freq+node2.key_freq;
			if(minheap.isEmpty())
			{
				if(node1.key_freq>node2.key_freq)
					root=new HuffmanNode(newFreq,-1,node2,node1);
				else
					root=new HuffmanNode(newFreq,-1,node1,node2);
				break;
			}
			if(node1.key_freq>node2.key_freq)
				minheap.insert(new HuffmanNode(newFreq,-1,node2,node1));
			else
				minheap.insert(new HuffmanNode(newFreq,-1,node1,node2));
		}
		return root;
	}
	*/

	private static HuffmanNode build_tree_using_fourWayHeap(HashMap<Integer, Integer> freq_table) 
	{
		HuffmanNode root = null;
		
		FourWayHeap minheap = new FourWayHeap();
		
		for(Entry<Integer,Integer> e:freq_table.entrySet())
			minheap.insert(new HuffmanNode(e.getValue(),e.getKey(),null,null));
		
		while(!minheap.isEmpty())
		{
			HuffmanNode node1=minheap.removeMin();
			if(minheap.isEmpty())
			{
				root=node1;
				break;
			}
			HuffmanNode node2=minheap.removeMin();
			Integer newFreq=node1.key_freq+node2.key_freq;
			if(minheap.isEmpty())
			{
				if(node1.key_freq>node2.key_freq)
					root=new HuffmanNode(newFreq,-1,node2,node1);
				else
					root=new HuffmanNode(newFreq,-1,node1,node2);
				break;
			}
			if(node1.key_freq>node2.key_freq)
				minheap.insert(new HuffmanNode(newFreq,-1,node2,node1));
			else
				minheap.insert(new HuffmanNode(newFreq,-1,node1,node2));
		}
		return root;
	}
	
	private static HashMap<Integer,String> generateHuffmanCodes(HuffmanNode root,String huffmanCode,HashMap<Integer,String> code_table) 
	{
		if(root!=null)
		{
			huffmanCode=huffmanCode+"0";
			generateHuffmanCodes(root.leftChild, huffmanCode,code_table);  
			huffmanCode=huffmanCode.substring(0, huffmanCode.length()-1);
			if(root.leftChild==null && root.rightChild==null)
				code_table.put(root.value, huffmanCode);
			huffmanCode=huffmanCode+"1";
			generateHuffmanCodes(root.rightChild,huffmanCode,code_table);
		}
		return code_table;
	}
	
	private static void encode_data(String filename, HashMap<Integer, String> code_table)
	{
		String outputFilename="encoded.bin";
		try(FileOutputStream outputStream=new FileOutputStream(outputFilename))
		{
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			    String line;
			    String buffer="";
			    while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
			    	buffer=buffer + code_table.get(Integer.parseInt(line.trim()));
			    	if(buffer.length() % 8 == 0)
			    	{
			    		for (int i = 0; i < buffer.length(); i += 8) 
				        {
				            String byteString = buffer.substring(i, i + 8); // get 1 byte
				            outputStream.write(Integer.parseInt(byteString, 2)); // write it to output
				        }
			    		buffer="";
			    	}
			    }
			    if(buffer.length()!=0)
			    {
			    	while (buffer.length() % 8 != 0)
			    		buffer+="0"; // add extra bits until we have full bytes
			    	for (int i = 0; i < buffer.length(); i += 8) 
			        {
			            String byteString = buffer.substring(i, i + 8); // get 1 byte
			            outputStream.write(Integer.parseInt(byteString, 2)); // write it to output
			        }
			    }
			    br.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			outputStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}