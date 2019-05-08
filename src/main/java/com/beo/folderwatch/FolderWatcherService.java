package com.beo.folderwatch;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.FileSystemWatcherFactory;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log
@Getter
public class FolderWatcherService extends FileSystemWatcher implements FileSystemWatcherFactory {

    public static final int DEFAULT_THRESHOLD = 100;
    public static final String DEFAULT_FOLDER = "/var/lib/motion";

    private Integer threshold;
    private List<String> foldersToWatch = new ArrayList<>();


    public FolderWatcherService(List<String> parameters) {
        super(true, Duration.of(2000, ChronoUnit.MILLIS),
                Duration.of(500, ChronoUnit.MILLIS));
        initParameters(parameters);
    }

    private void initParameters(List<String> parameters) {
        log.info(String.format("parameter [size, values] = [%d, %s ]", parameters.size(), parameters.toString()));
        Optional<String> first = parameters.stream().findFirst();
        if (first.isPresent()) {
            try {
                threshold = Integer.parseInt(first.get());
                foldersToWatch.addAll(parameters.stream().skip(1).collect(Collectors.toList()));
            } catch (NumberFormatException e) {
                foldersToWatch.addAll(parameters);
            }
        }
        if (threshold == null) {
            threshold = DEFAULT_THRESHOLD;
        }
        if (foldersToWatch.isEmpty()) {
            foldersToWatch.add(DEFAULT_FOLDER);
        }
    }

    private FolderWatcherService init() {
        foldersToWatch.forEach(folderToWatch -> addFolderListener(this, folderToWatch, threshold));
        return this;
    }

    /**
     * @param fileSystemWatcher
     * @param folderToWatch
     * @param threshold
     */
    private void addFolderListener(final FileSystemWatcher fileSystemWatcher, final String folderToWatch, int threshold) {
        File folder = new File(folderToWatch);
        fileSystemWatcher.addSourceFolder(folder);
        fileSystemWatcher.addListener(new FolderListener(folder, threshold));
    }


    @Override
    public FileSystemWatcher getFileSystemWatcher() {
        init().start();
        return this;
    }

}
