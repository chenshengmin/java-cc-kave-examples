package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSize {
	
	public static long getFileSize(String userZip) {
		return new File("Events-170301", userZip).length();
	}

}
