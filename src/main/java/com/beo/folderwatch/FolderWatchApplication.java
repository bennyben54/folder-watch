package com.beo.folderwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
