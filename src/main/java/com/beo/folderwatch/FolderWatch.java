package com.beo.folderwatch;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;

/**
 * 1 mai 2019
 * @author bennyben54
 */
public class FolderWatch implements FileChangeListener {

	private final File folderWatched;
	private int threshold = 100;

	/**
	 * 
	 */
	public FolderWatch(final File folderWatched, final int threshold) {
		this.folderWatched = folderWatched;
		this.threshold = threshold;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.boot.devtools.filewatch.FileChangeListener#onChange(
	 * java.util.Set)
	 */
	@Override
	public void onChange(final Set<ChangedFiles> changeSet) {
		synchronized (changeSet) {
			changeSet.forEach(c -> c.forEach(d -> {
				if (folderWatched.list().length > threshold) {
					List<File> files = Arrays.asList(folderWatched.listFiles());
					files.sort((a, b) -> {
						long diff = a.lastModified() - b.lastModified();
						if (diff == 0) {
							return 0;
						}
						return diff < 0 ? -1 : 1;
					});
					files.subList(0, threshold / 2).stream().forEach(f -> {
						System.out.println("DELETING " + f.toString());
						f.delete();
					});
				}
			}));
		}
	}

}
