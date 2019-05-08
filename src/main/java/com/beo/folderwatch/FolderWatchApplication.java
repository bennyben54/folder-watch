package com.beo.folderwatch;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FolderWatchApplication {

	private final static List<String> args = new ArrayList<>();

	public static void main(final String[] args) {
		System.out.println(args.length);
		Arrays.asList(args).stream().forEach(System.out::println);
		FolderWatchApplication.args.addAll(Arrays.asList(args));
		SpringApplication.run(FolderWatchApplication.class, args);
	}

	@Bean
	public FileSystemWatcher getFileWatcher() {
		FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.of(2000, ChronoUnit.MILLIS),
				Duration.of(500, ChronoUnit.MILLIS));

		if (FolderWatchApplication.args.isEmpty()) {
			addFolderListener(fileSystemWatcher, "/var/lib/motion");
		} else {
			FolderWatchApplication.args.stream()
					.forEach(folderToWatch -> addFolderListener(fileSystemWatcher, folderToWatch));
		}

		fileSystemWatcher.start();
		return fileSystemWatcher;
	}

	/**
	 * @param fileSystemWatcher
	 * @param folderToWatch
	 */
	private void addFolderListener(final FileSystemWatcher fileSystemWatcher, final String folderToWatch) {
		File folder = new File(folderToWatch);
		fileSystemWatcher.addSourceFolder(folder);
		fileSystemWatcher.addListener(new FolderWatch(folder, 50));
	}

}
