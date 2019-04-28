package Compare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Main.Main;

public class FileOps {
	static String retorno="";
	static int [] results = new int [2];



	public void extractJsonFromSB2File(String caminhoArquivosSb2) throws IOException {
		File folder = new File(caminhoArquivosSb2);
		
		
		File[] listOfFiles = folder.listFiles(
				new FileFilter() {
		               public boolean accept(File b){
		                  return b.getName().endsWith(".sb2");
		               }
		            });
	

		for (int i = 0; i < listOfFiles.length; i++) {

			File old = listOfFiles[i];

			if (old.isFile()) {
				File f = new File(folder.getPath() + "/" + old.getName());
				String normalizedFileName = normalizeFileName(old.getName());
				File zip = new File(folder.getPath() + "/" + normalizedFileName + ".zip");
				f.renameTo(zip);
				InputStream extract = getSingleFile("project.json",folder.getPath() + "/" + normalizedFileName + ".zip");
				copyToFile(extract, new File(folder.getPath() + "/" + normalizedFileName + ".json"));
			}
		}

		File folder2 = new File(caminhoArquivosSb2);
		File[] listOfFiles2 = folder2.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".zip");
			}
		});

		for (int i = 0; i < listOfFiles2.length; i++) {

			File zip = listOfFiles2[i];

			boolean d = zip.delete();

			// O while a seguir é utilizado para remover todos os arquivos zip do diretório de arquivos do scratch
			while (!d) {
				d = zip.delete();
				System.gc();
			}
		}
	}	
	
	public InputStream getSingleFile(final String filenameToExtract, final String zipName) throws IOException {
		final ZipFile zipFile = new ZipFile(new File(zipName));
		final ZipEntry zipEntry = zipFile.getEntry(filenameToExtract);
		final InputStream file = zipFile.getInputStream(zipEntry);
		return file;
	}

	public void copyToFile(InputStream inputStream, File file) throws IOException {
		try (OutputStream outputStream = new FileOutputStream(file)) {
			IOUtils.copy(inputStream, outputStream);
		}
	}

	public String normalizeFileName(String oldName) {
		String[] fileName = oldName.split("_");
		return fileName[0];
	}
	public static void compare(String pathFilesSb2, String pathResult, int percentage
			,boolean sounds, boolean costumes, boolean script,
			boolean objName) throws JsonProcessingException, IOException {
		File folder = new File(pathFilesSb2);
		
		
		
		File[] listOfFiles = folder.listFiles(
				new FileFilter() {
		               public boolean accept(File b){
		                  return b.getName().endsWith(".json");
		               }
		            });
		String laudo = "";// váriavel que será impressa no arquivo de mesmo nome
 
	        try {
	        	ObjectMapper mapper = new ObjectMapper();
	        	

	    		for (int x = 0; x < listOfFiles.length - 1; x++) {
	    			String nomeArquivo1= listOfFiles[x].getName();
	    			JsonNode rootNode = mapper.readTree(listOfFiles[x]);
	    			JsonNode nd = rootNode.path("children");

	    			

	    		
	    		for (int y = (x + 1); y < listOfFiles.length; y++) {
	    			results[0]=0;
	    			results[1]=0;
	    			
	    			String nomeArquivo2= listOfFiles[y].getName();
	    			JsonNode rootNode1 = mapper.readTree(listOfFiles[y]);
	    			JsonNode nd1 = rootNode1.path("children");	
	    			
	    			if(sounds==true) {
	    			verifyCopy(buscarNo(nd,mapper,"sounds"), buscarNo(nd1,mapper,"sounds"));
	    			}
	    			if(costumes==true) {
	    			verifyCopy(buscarNo(nd,mapper,"costumes"), buscarNo(nd1,mapper,"costumes"));	
	    			}
	    			if(script==true) {
	    			verifyCopy(buscarNo(nd,mapper,"scripts"), buscarNo(nd1,mapper,"scripts"));
	    			}
	    			if(objName==true) {
	    			verifyCopy(buscarNo(nd,mapper,"objName"), buscarNo(nd1,mapper,"objName"));
	    			}
	    			

		    		//System.out.println("linhas iguais: "+results[0]+"\n"
		    				//+"Linhas Totais: "+results[1]);
		    		
		    		
		    		
		    		int aux=calculatePercentage(results[0], results[1]);
		    		
		    		 if(aux>=percentage) {
							laudo+="Há plágio de: "+aux+"% entre o arquivo: "+nomeArquivo1+" e o arquivo: "+nomeArquivo2+".\n";
					}
		    		
		    		retorno+="Comparando arquivo: "+nomeArquivo1+"como o arquivo: "+nomeArquivo2+"\n";
		    		 
		    		
	    	
	    		}
	    		}  	
	    		writer(laudo, pathResult);
	    		JOptionPane.showMessageDialog(null, "Concluído com sucesso");
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	     retorno+="Concluído";
	}
	
	public static int calculatePercentage(int equalLines, int numLines) {
		return (equalLines*100)/numLines;
	}

	public static  String buscarNo(JsonNode nd, ObjectMapper mapper, String no) throws JsonProcessingException, IOException {
		
		String content = String.valueOf(nd);
		content = content.substring(1, content.length()-1);
		nd = mapper.readTree(content);
		nd = nd.path(no);
		content= String.valueOf(nd);
		
		return content;
	}
	public static void verifyCopy(String a, String b) {
		
		String [] c = a.split(",");
		results[1]+=c.length;
		
		for(int i=0;i<c.length;i++) {
			if(b.contains(c[i])) {
				results[0]++;
			}
		}
	}
	


	public static void writer(String laudo, String pathResult) throws IOException {

		File file = new File(pathResult+"\\laudo.txt");

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(laudo);
		bw.close();

	}
}
