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
import org.springframework.boot.devtools.filewatch.FileSystemWatcherFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FolderWatchApplication {

	private final static List<String> args = new ArrayList<>();

	public static void main(final String[] args) {

		FolderWatchApplication.args.addAll(Arrays.asList(args));
		SpringApplication.run(FolderWatchApplication.class, args);
	}

	@Bean
	public FileSystemWatcher getFileWatcher() {
		return new FolderWatcherService(args).getFileSystemWatcher();
	}

}
