package shorty97.simplepi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimplePi {
	static BufferedReader input;
	static FileWriter fstream;
	static BufferedWriter out;
	static String classname = "";
	static boolean started = false;
	static boolean ended = false;
	static File inputfile;
	public static void main(String args[]){
		inputfile = new File(args[0]);
		classname = inputfile.getName();
		classname = classname.split("\\.")[0];
		init(inputfile);
		WritetoFile("public class " + classname + " { ");
		WritetoFile("");
		WritetoFile("");
		WritetoFile("  public static void main(String args[]){");
		WritetoFile("");
		gotoStart();
		Process();
		WritetoFile("");
		WritetoFile("  } ");
		WritetoFile("");
		WritetoFile("");
		WritetoFile("} ");
		Close();
		CloseWrite();
		Compile();
	}
	
	
	public static void init(File f){
		try {
			input =  new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try{
			  // Create file 
			 File output = new File(f.toString().replace("simplepi", "java"));
			  fstream = new FileWriter(output);
			  out = new BufferedWriter(fstream);
			  
			 
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
			      
	}
	
	public static String ReadLine() {
		String line = null;
		try {
			line = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	public static void Close(){
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void WritetoFile(String data){
		try {
			out.write(data + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void CloseWrite(){
		 try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void gotoStart(){
		while(started == false){
			String line = ReadLine().trim();
			if (line.isEmpty() == false){
				if (line.equals("start")){
					started = true;
				}
			} 
		}
	}
	
	public static void Process(){
		while(ended == false){
			String line = ReadLine().trim();
			if (line.isEmpty() == false){
			
				if (line.equals("end")){
					CommandEnd();
					ended = true;
				}
				
				if (line.contains(" ")){
					String command = line.split(" ")[0];
					if (command.equals("say")){
						CommandSay(line.split(" ")[1]);
					}
					if (command.equals("wait")){
						CommandWait(line.split(" ")[1]);
					}
					
				}
				
			} 
		}
	}
	
	public static void CommandSay(String details){
		String text = details.split("\"")[1];
		WritetoFile("    System.out.println(\"" + text + "\");");
	}
	
	public static void CommandWait(String details){
		String timestring = details;
		int time = Integer.parseInt(timestring);
		WritetoFile("    try { ");
		WritetoFile("");
		WritetoFile("      Thread.sleep(" + time + ");");
		WritetoFile("");
		WritetoFile("    } catch (Exception e) { ");
		WritetoFile("");
		WritetoFile("      System.out.println(e);");
		WritetoFile("");
		WritetoFile("    }");
	}
	
	public static void CommandEnd(){
		WritetoFile("    System.exit(0);");
	}
	
	public static void Compile(){
	     CreateManifest();
		try {
			Runtime.getRuntime().exec("cmd /c javac " + inputfile.toString().replace("simplepi", "java") + " -d " + inputfile.getParent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		
	}
	public static void CreateManifest(){
		
			 try{
				  // Create file 
				  FileWriter fstream = new FileWriter(inputfile.getParent() + "/" + "manifest.txt");
				  BufferedWriter out = new BufferedWriter(fstream);
				  out.write("Main-Class: " + classname + "\n");
				  //Close the output stream
				  out.close();
				  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
				  }
		}	
	}
	
