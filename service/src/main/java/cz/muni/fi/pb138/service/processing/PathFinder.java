package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.PathVersionPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gasior on 14.05.2016
 * Modified by holic on 16.06.2016
 */
@Service
public class PathFinder {

	/**
	 * Transforms full path into versioned path
	 *
	 * @param version to transform into
	 * @param fullPath to transform
	 * @param type FileType
	 * @return versioned path
	 */
	public String getVersionedPath(String fullPath, int version, FileType type) {
		String noSuffix = getPathWithoutSuffix(fullPath);
		return noSuffix + "_" + version + type.toString();
	}

	/**
	 * Returns latest version from the file version list
	 *
	 * @param fileList list of the same file of different versions
	 * @param fullPath full path of the file
	 * @return latest version
	 */
	public int getLatestVersion(String fileList, String fullPath) {
		ArrayList<String> filteredPaths = filterPaths(fileList, fullPath);
		String noSuffixPath = getPathWithoutSuffix(fullPath);

		int version = 0;
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffixPath)) {
				String x = p.substring(noSuffixPath.length() + 1);
				String[] y = x.split("\\.");
				if (version < Integer.valueOf(y[0])) {
					version = Integer.valueOf(y[0]);
				}
			}
		}
		return version;
	}

	/**
	 * Retrieves a list of all versions of the same file
	 *
	 * @param fileList list of the same file of different versions
	 * @param fullPath full path of the file
	 * @return versions list
	 */
	public List<Integer> getAllVersions(String fileList, String fullPath) {
		ArrayList<String> filteredPaths = filterPaths(fileList, fullPath);
		String noSuffixPath = getPathWithoutSuffix(fullPath);

		List<Integer> output = new ArrayList<>();
		for (String p : filteredPaths) {
			if (p.startsWith(noSuffixPath)) {
				String x = p.substring(noSuffixPath.length() + 1);
				String[] y = x.split("\\.");
				output.add(Integer.valueOf(y[0]));
			}
		}
		return output;
	}

	/**
	 * Parses file list received from List command of BaseX into list of PathVersionPair
	 *
	 * @param fileList list of files to parse
	 * @param namespace namespace of these files
	 * @param allVersions whether to return all or only the latest versions
	 * @param fileTypeFilter whether to filter by file type, null means no filtering
	 * @return list of PathVersionPair
	 */
	public List<PathVersionPair> parseFileList(String fileList, String namespace, boolean allVersions, FileType fileTypeFilter) {
		if (namespace.startsWith("/")) namespace = namespace.substring(1);

		List<String> filteredPaths = new ArrayList<>();
		List<PathVersionPair> output = new ArrayList<>();

		String[] paths = fileList.split("\n");
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(namespace)) {
				if (fileTypeFilter == null) {
					for (FileType fileType : FileType.values()) {
						if (x.endsWith(fileType.toString())) {
							filteredPaths.add(x);
							break;
						}
					}
				} else if (x.endsWith(fileTypeFilter.toString())) {
					filteredPaths.add(x);
				}
			}
		}
		for (String p : filteredPaths) {
			if (p.startsWith(namespace)) {
				String x = p.substring(p.lastIndexOf("_") + 1);
				String[] dotSplit = x.split("\\.");
				String path = p.substring(0, p.lastIndexOf("_")) + "." + dotSplit[dotSplit.length - 1];
				if (!allVersions) {
					PathVersionPair previousVersion = new PathVersionPair(path, Integer.valueOf(dotSplit[0]) - 1);
					if (output.contains(previousVersion)) output.remove(previousVersion);
				}
				output.add(new PathVersionPair(path, Integer.valueOf(dotSplit[0])));
			}
		}

		return output;
	}

	private ArrayList<String> filterPaths(String fileList, String fullPath) {
		String suffix = fullPath.substring(fullPath.lastIndexOf("."));
		String[] paths = fileList.split("\n");
		ArrayList<String> filteredPaths = new ArrayList<>();
		for (String p : paths) {
			String x = p.split(" ")[0];
			if (x.startsWith(getPathWithoutSuffix(fullPath)) && x.endsWith(suffix)) {
				filteredPaths.add(x);
			}
		}
		return filteredPaths;
	}
	
	private String getPathWithoutSuffix(String fullPath) {
		return fullPath.substring(0, fullPath.lastIndexOf("."));
	}
}
