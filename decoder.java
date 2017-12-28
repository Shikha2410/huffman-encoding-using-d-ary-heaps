/*
 * Decoder implementation
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class decoder {

	public static void main(String[] args) {
		String encodedfile="";
		String codesfile="";
		if(1<args.length)
		{
			encodedfile=args[0];
			codesfile=args[1];
		}
		else 
		{
		   System.err.println("Invalid arguments count:" + args.length);
		   System.exit(0);
		}
		
		DecodeTree tree=new DecodeTree();
		tree.root=construct_decode_tree(codesfile);
		decode_file(encodedfile,tree);
	}

	private static DTreeNode construct_decode_tree(String codesfile) 
	{
		DTreeNode root,ptr;
		root=new DTreeNode(-1,null,null);
		
		try (BufferedReader br = new BufferedReader(new FileReader(codesfile))) {
		    String line;
		    while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
		    	String substrings[]=line.trim().split("\\s+");
		    	
		    	ptr=root;
		    	
		    	for(int charIndex=0;charIndex<substrings[1].length();charIndex++)
		    	{
		    		char i=substrings[1].charAt(charIndex);
		    		if(i=='0')
		    		{
		    			if(ptr.leftChild==null)
		    				ptr.leftChild=new DTreeNode(-1,null,null);
		    			ptr=ptr.leftChild;
		    		}
		    		else //i=='1'
		    		{
		    			if(ptr.rightChild==null)
		    				ptr.rightChild=new DTreeNode(-1,null,null);
		    			ptr=ptr.rightChild;
		    		}
		    	}
		    	ptr.value=Integer.parseInt(substrings[0]);
		    }
		    br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
	
	private static void decode_file(String encodedfile, DecodeTree tree) 
	{
		String outputFilename="decoded.txt";
		DTreeNode ptr;
		try(FileInputStream inputStream=new FileInputStream(encodedfile))
		{
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) 
			{
				int bcount=0;
				byte[] buffer = new byte[4096];
				ptr=tree.root;
				while((bcount=inputStream.read(buffer))!=-1) //-1 if EOF
				{
					for(int j=0;j<bcount;j++)
					{
						String bitStr=String.format("%8s", Integer.toBinaryString(buffer[j] & 0xFF)).replace(' ', '0');
						for(int index=0;index<8;index++)
						{
							char i=bitStr.charAt(index);
				    		if(i=='0')
				    		{
				    			ptr=ptr.leftChild;
				    			if(ptr.leftChild==null)
				    			{
				    				bw.write(ptr.value.toString()+'\n');
				    				ptr=tree.root;
				    			}
				    		}
				    		else //i=='1'
				    		{
				    			ptr=ptr.rightChild;
				    			if(ptr.rightChild==null)
				    			{
				    				bw.write(ptr.value.toString()+'\n');
				    				ptr=tree.root;
				    			}	
				    		}
				    	}
					}
				}
				bw.close();
			} 
			catch (Exception e) {
					e.printStackTrace();
				}
				inputStream.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}