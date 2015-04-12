import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class UpdateManifest {
	public static void main(String[] args) {
		try {
			System.out.println("\n-------------------UPDATE AndroidManifest.xml-------------------");
			
			System.out.println("Update version");
			new UpdateManifest().updateVersion(new File(args[0]), args[1]);
			
			System.out.println("AndroidManifest.xml HAS BEEN SUCCESSFULLY UPDATED\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
		

	public void updateVersion(File file, String versionName) throws IOException {
		String manifest = readFile(file);
		manifest = updateVersionCode(manifest, 0);
		manifest = updateVersionName(manifest, versionName);
		
		writeFile(file, manifest);
	}

	private String updateVersionCode(String manifest, int step) {
		String c = "android:versionCode";
		int start = manifest.indexOf(c);
		int begin = manifest.indexOf("\"", start);
		int end = manifest.indexOf("\"", begin + 1);

		String h = manifest.substring(0, begin + 1);
		String v = manifest.substring(begin + 1, end);
		String b = manifest.substring(end, manifest.length());
		int versionCode = Integer.parseInt(v);
		v = "" + (versionCode + step);
		System.out.println("  - versionCode has been updated from " + versionCode + " to " + v);
		return h + v + b;
	}
	
	private String updateVersionName(String manifest, String version) {
		String c = "android:versionName";
		int start = manifest.indexOf(c);
		int begin = manifest.indexOf("\"", start);
		int end = manifest.indexOf("\"", begin + 1);

		String h = manifest.substring(0, begin + 1);
		String b = manifest.substring(end, manifest.length());
		System.out.println("  - versionName has been updated to " + version);
		return h + version + b;
	}


	public static void writeFile(String file, String value) throws IOException {
		writeFile(new File(file), value);
	}

	public static void writeFile(File file, String value) throws IOException {
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"));
		try {
			out.write(value);
		} finally {
			out.close();
		}
	}

	public static String readFile(String filename) throws IOException {
		return readFile(new File(filename));
	}

	public static String readFile(File file) throws IOException {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				file), "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
		String result = "";
		while ((line = br.readLine()) != null) {
			if (result.length() > 0) {
				result += "\n";
			}
			result += line;
		}
		br.close();
		return result;

	}

}
