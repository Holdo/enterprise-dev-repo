package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gasior on 14.05.2016
 */
@Service
public class PathParser {

	public int getLastVersion(String fullPath, String files) {
		String suffix = fullPath.substring(fullPath.lastIndexOf("."));
		String[] paths = files.split("\n");
		String noSuffix = fullPath.substring(0, fullPath.lastIndexOf("."));
		if (noSuffix.startsWith("/")) {
			noSuffix = noSuffix.substring(1);
		}
		ArrayList<String> filteredPaths = new ArrayList<>();
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(noSuffix) && x.endsWith(suffix)) {
				filteredPaths.add(x);
			}
		}
		int version = 0;
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffix)) {
				String x = p.substring(noSuffix.length() + 1);
				String[] y = x.split("\\.");
				if (version < Integer.valueOf(y[0])) {
					version = Integer.valueOf(y[0]);
				}
			}
		}
		return version;
	}

	public String getVersionedPath(int version, String fullPath, FileType type) {
		String noSuffix = fullPath.substring(0, fullPath.lastIndexOf("."));
		return noSuffix + "_" + version + type.toString();
	}


	public List<Integer> getAllVersions(String fullPath, String files) {
		List<Integer> output = new ArrayList<>();

		String suffix = fullPath.substring(fullPath.lastIndexOf("."));
		String[] paths = files.split("\n");
		String noSuffix = fullPath.substring(0, fullPath.lastIndexOf("."));
		if (noSuffix.startsWith("/")) {
			noSuffix = noSuffix.substring(1);
		}
		ArrayList<String> filteredPaths = new ArrayList<>();
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(noSuffix) && x.endsWith(suffix)) {
				filteredPaths.add(x);
			}
		}
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffix)) {
				String x = p.substring(noSuffix.length() + 1);
				String[] y = x.split("\\.");
				output.add(Integer.valueOf(y[0]));
			}
		}
		return output;
	}

	public List<PathVersionPair> getAllFiles(String files, String namespace) {
		List<PathVersionPair> output = new ArrayList<>();

		String[] paths = files.split("\n");
		String noSuffix = namespace;
		if (noSuffix.equals("/")) noSuffix = "";
		if (noSuffix.startsWith("/")) noSuffix = noSuffix.substring(1);
		ArrayList<String> filteredPaths = new ArrayList<>();
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(noSuffix)) {
				for (FileType fileType : FileType.values()) {
					if (x.endsWith(fileType.toString())) {
						filteredPaths.add(x);
						break;
					}
				}

			}
		}
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffix)) {
				String x = p.substring(p.lastIndexOf("_") + 1);
				String[] dotSplit = x.split("\\.");
				String path = p.substring(0, p.lastIndexOf("_")) + "." + dotSplit[dotSplit.length - 1];
				output.add(new PathVersionPair(path, Integer.valueOf(dotSplit[0])));
			}
		}

		return output;
	}

	public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String files, String namespace) {
		List<PathVersionPair> output = new ArrayList<>();

		String suffix = fileType.toString();
		String[] paths = files.split("\n");
		String noSuffix = namespace;
		if (noSuffix.equals("/")) noSuffix = "";
		if (noSuffix.startsWith("/")) noSuffix = noSuffix.substring(1);
		ArrayList<String> filteredPaths = new ArrayList<>();
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(noSuffix) && x.endsWith(suffix)) {
				filteredPaths.add(x);
			}
		}
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffix)) {
				String x = p.substring(p.lastIndexOf("_") + 1);
				String version = x.split("\\.")[0];
				String path = p.substring(0, p.lastIndexOf("_")) + suffix;
				output.add(new PathVersionPair(path, Integer.valueOf(version)));
			}
		}

		return output;
	}
}
