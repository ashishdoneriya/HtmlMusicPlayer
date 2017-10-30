import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Music {

	private boolean isDir = false;

	private String filePath;

	private String fileName;

	private List<Music> files;
	
	public int level = 0;
	
	public static int id = 0;

	public Music(File file, int level) {
		fileName = file.getName();
		this.level = level;
		if (file.isDirectory()) {
			isDir = true;
			files = new ArrayList<Music>();
		} else {
			filePath = file.getAbsolutePath();
		}
	}

	public Music() {
		
	}

	public boolean getIsDir() {
		return isDir;
	}

	public void setIsDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Music> getFiles() {
		return files;
	}

	public void setFiles(List<Music> files) {
		this.files = files;
	}

	public void addFile(Music file) {
		files.add(file);
	}

	public static Music getMusic(File dir, int level) {
		if (!dir.isDirectory()) {
			return new Music(dir, level);
		}
		Music music = new Music(dir, level);
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				music.addFile(getMusic(file, level + 1));
			} else {
				music.addFile(new Music(file, level + 1));
			}
		}
		return music;
	}
	
	public static void main(String[] args) {
		File file = new File("/d/My Documents/My Music");
		System.out.println(getJson(getMusic(file, 0)));
	}
	
	public static String getJson(Music music) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"id\":").append(id++).append(",");
		json.append("\"isDir\":").append(music.isDir).append(",");
		json.append("\"fileName\":\"").append(music.fileName).append("\",");
		json.append("\"level\":\"").append(music.level).append("\",");
		if (music.isDir) {
			json.append("\"files\":[");
			for (Music m : music.files) {
				json.append(getJson(m)).append(",");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]");
		} else {
			json.append("\"filePath\":\"").append(music.filePath).append("\"");
		}
		json.append("}");
		return json.toString();
	}
}

