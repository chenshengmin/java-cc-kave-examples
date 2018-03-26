package examples;

public class CompareFileDirectory {
	public static double getSimilarity(String file1,String file2) { //file1是当前事件所在文件，file2是之前可能相关的事件所在文件
		double similarity=0;
		
		if(file1.equals("null")||file2.equals("null"))
			return similarity;
		
		String dir1=file1.substring(file1.indexOf("(")+1,file1.indexOf(")"));
		String dir2=file2.substring(file2.indexOf("(")+1,file2.indexOf(")"));
		
		String []fileNode1=dir1.split("\\\\");
		String []fileNode2=dir2.split("\\\\");
		
		int sameLength=0;
		for(int i=0;i<Math.min(fileNode1.length,fileNode2.length);i++) {
			if(fileNode1[i].equals(fileNode2[i]))
				sameLength++;
			else
				break;
		}
		
		similarity=(double)((double)sameLength/(double)fileNode1.length);
		
		return similarity;
	}
	
}
