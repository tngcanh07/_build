import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UpdateSpecificResources {
	public static void main(String[] args) {
		if (args == null || args.length < 3) {
			System.out
					.println("Usage : java UpdateSpecificResources [ResourcesDir] [SpecificProject] [TargetProject]");
			System.exit(0);
			return;
		}
		try {
			// new UpdateSpecificResources().execute(
			// "H:/Mobiles/CheatProjects/Riddles/Android-Resources",
			// "Pyrosphere-AYearOfRiddles",
			// "H:/Mobiles/CheatProjects/Riddles/Android-Riddles");
			
			new UpdateSpecificResources().execute(args[0], args[1], args[2]);

		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Usage : java UpdateSpecificResources [ResourcesDir] [SpecificProject] [TargetProject]");
		}
	}

	private HashMap<String, File> _commonFiles = new HashMap<>();
	private HashMap<String, File> _specificFiles = new HashMap<>();
	private Set<String> _allFiles = new HashSet<>();
	private int filesCopied = 0;
	private int filesDeleted = 0;

	public void execute(String resDir, String specFolder, String targetFolder)
			throws Exception {
		System.out.println("\n-------------------UPDATE ANDROID RESOURCES-------------------");
		File root = new File(resDir);
		File specificDir = new File(root, "SpecificProjects\\" + specFolder);

		if (!specificDir.exists()) {
			specificDir.mkdirs();
		}
		File targetDir = new File(targetFolder);

		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		getResoucesInfor(root, specificDir);

		// clear target folder
		clearTargetFile(targetDir, _allFiles);
		System.out.println(filesDeleted + " file(s) deleted");

		// copy file if it not exist
		System.out.println("\nCopy from " + specFolder + " folder");
		copyFileToTarget(targetDir, _specificFiles);
		System.out.println(filesCopied + " specific file(s) deleted");
		int specificFilesCopied = filesCopied;
		filesCopied = 0;
		
		System.out.println("\nCopy from Common folder");
		copyFileToTarget(targetDir, _commonFiles);
		System.out.println(filesCopied + " common file(s) deleted");
		
		System.out.println("\nTotal: " + (specificFilesCopied + filesCopied) + " file(s) copied");
		
		System.out.println("RESOURCES HAVE BEEN SUCCESSFULLY UPDATED\n");

	}
	
	

	private void copyFileToTarget(File targetDir, HashMap<String, File> files)
			throws Exception {
		Set<String> key = files.keySet();
		for (String filename : key) {
			File outFile = new File(targetDir, filename);
			if(copyFile(files.get(filename), outFile, false)) {
				System.out.println("   - File copied: " + filename);
				filesCopied++;
			}
		}
	}

	public void clearTargetFile(File targetDir, Set<String> allFile) {
		System.out.println("Delete from " + targetDir.getAbsoluteFile());
		for (String filename : allFile) {
			File f = new File(targetDir, filename);
			if (f.exists()) {
				f.delete();
				System.out.println("   - File deleted: " + filename);
				filesDeleted ++;
			}
		}
	}

	private void getResoucesInfor(File root, File specificDir) {
		_specificFiles.clear();
		_commonFiles.clear();
		_allFiles.clear();
		File commonDir = new File(root, "Common");
		File specificProjects = new File(root, "SpecificProjects");

		if (specificProjects.exists()) {
			specificProjects.mkdirs();
		}

		if (!commonDir.exists()) {
			commonDir.mkdirs();
		}

		getResouces(specificDir, specificDir, _specificFiles, _allFiles, true);
		getResouces(commonDir, commonDir, _commonFiles, _allFiles, true);

		// collect another file in specific project
		for (File specificPrj : specificProjects.listFiles()) {
			if (!specificPrj.equals(specificDir)) {
				getResouces(specificPrj, specificPrj, null, _allFiles, true);
			}
		}

		// collect specific file
	}

	private void getResouces(File rootDir, File dir,
			HashMap<String, File> result, Set<String> ignore,
			boolean addToIgnore) {
		if (dir.exists() && dir.isDirectory()) {
			File[] list = dir.listFiles();
			for (File file : list) {
				if (file.isDirectory()) {
					getResouces(rootDir, file, result, ignore, addToIgnore);
				} else {
					String name = file.getAbsolutePath().substring(
							rootDir.getAbsolutePath().length());
					if (ignore == null || !ignore.contains(name)) {
						if (result != null)
							result.put(name, file);

						if (addToIgnore && ignore != null) {
							ignore.add(name);
						}
					}
				}
			}
		}
	}

	public static boolean copyFile(File inFile, File outFile, boolean isOverride)
			throws Exception {
		File outParent = outFile.getParentFile();
		if (outParent != null && !outParent.exists()) {
			outParent.mkdirs();
		}
		// delete out file if it's exist
		if (outFile.exists() && isOverride) {
			outFile.delete();
		}
		if (outFile.exists()) {
			return false;
		}
		FileInputStream fis = new FileInputStream(inFile);
		FileOutputStream fos = new FileOutputStream(outFile);
		byte[] buffer = new byte[fis.available()];
		if (fis.read(buffer) > 0) {
			fos.write(buffer);
			fos.flush();
		}
		fos.close();
		fis.close();
		return true;
	}

}
