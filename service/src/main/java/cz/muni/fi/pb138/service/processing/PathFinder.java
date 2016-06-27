package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ondřej Gasior
 */
@Service
public class PathFinder {

	private static final Logger log = LoggerFactory.getLogger(PathFinder.class);

	/**
	 * Transforms full path into versioned path
	 *
	 * @param version to transform into
	 * @param fullPath to transform
	 * @param type FileType
	 * @return versioned path
	 */
	public String getVersionedPath(String fullPath, int version, FileType type) {
		return getPathWithoutSuffix(fullPath) + "_" + version + type.toString();
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
			if (p.startsWith(noSuffixPath + "_")) {
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
	public List<Integer> getAllVersionsReversed(String fileList, String fullPath) {
		ArrayList<String> filteredPaths = filterPaths(fileList, fullPath);
		String noSuffixPath = getPathWithoutSuffix(fullPath);

		Integer[] output = new Integer[filteredPaths.size()];
		for (int i = 0; i < filteredPaths.size(); i++) {
			if (filteredPaths.get(i).startsWith(noSuffixPath)) {
				String x = filteredPaths.get(i).substring(noSuffixPath.length() + 1);
				String[] y = x.split("\\.");
				output[filteredPaths.size() - 1 - i] = Integer.valueOf(y[0]);
			}
		}
		return Arrays.asList(output);
	}

	/**
	 * Parses Java array of Files into list List<VersionedFile>
	 *
	 * @param fileList to parse
	 * @param namespace directory path
	 * @param allVersions hether to return all or only the latest versions
	 * @param fileType whether to filter by file type, null means no filtering
	 * @param recursively whether to parse folders recursively
	 * @return list of versioned files
	 */
	public List<VersionedFile> parseFileList(File[] fileList, String namespace, boolean allVersions, FileType fileType, boolean recursively) {
		List<VersionedFile> output = new ArrayList<>();
		for (File file : fileList) {
			if (file.isDirectory()) {
				if (recursively) {
					output.addAll(parseFileList(file.listFiles(), namespace + File.separator + file.getName(), allVersions, fileType, true));
				}
				output.add(new VersionedFile(file.getName(), namespace, 0, true));
			}
			else {
				String fileName = file.getName();
				if (fileType != null) {
					if (!fileName.endsWith(fileType.toString())) continue;
				}
				String fullPath = namespace + File.separator + fileName;
				String[] dotSplit = fileName.substring(fileName.lastIndexOf("_") + 1).split("\\.");
				String nonVersionedFullPath = fullPath.substring(0, fullPath.lastIndexOf("_")) + "." + dotSplit[dotSplit.length - 1];
				if (!allVersions) {
					VersionedFile previousVersion = new VersionedFile(nonVersionedFullPath, Integer.valueOf(dotSplit[0]) - 1, false);
					if (output.contains(previousVersion)) output.remove(previousVersion);
				}
				output.add(new VersionedFile(nonVersionedFullPath, Integer.valueOf(dotSplit[0]), false));
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
	public List<VersionedFile> parseBaseXFileList(String fileList, String namespace, boolean allVersions, FileType fileTypeFilter) {
		if (namespace.startsWith("/")) namespace = namespace.substring(1);

		List<String> filteredPaths = new ArrayList<>();
		List<VersionedFile> output = new ArrayList<>();

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
					VersionedFile previousVersion = new VersionedFile(path, Integer.valueOf(dotSplit[0]) - 1, false);
					if (output.contains(previousVersion)) output.remove(previousVersion);
				}
				output.add(new VersionedFile(path, Integer.valueOf(dotSplit[0]), false));
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
			if (x.endsWith(suffix)) {
				if (x.startsWith(getPathWithoutSuffix(fullPath) + "_")) {
					filteredPaths.add(x);
				}
			}
		}
		return filteredPaths;
	}
	
	private String getPathWithoutSuffix(String fullPath) {
		if (!fullPath.contains(".")) log.error("There is no suffix in " + fullPath);
		return fullPath.substring(0, fullPath.lastIndexOf(".")).replaceAll("\\\\", "/");
	}
}
