package com.beo.folderwatch;

import lombok.extern.java.Log;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log
public class FolderListener implements FileChangeListener {

    private int threshold;
    private int toDelete;
    private final File folderWatched;

    public FolderListener(final File folderWatched, final int threshold) {
        this.threshold = threshold;
        this.folderWatched = folderWatched;
        this.toDelete = threshold - (threshold / 4);
        log.info(String.format("FolderListener [%d, %d, %s]", threshold, toDelete, folderWatched));
    }

    @Override
    public void onChange(final Set<ChangedFiles> changeSet) {
        synchronized (folderWatched) {
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
                    files.subList(0, toDelete).stream().forEach(f -> f.delete());
                    log.info(String.format("DELETED %d files of '%s'", toDelete, folderWatched.toString()));
                }
            }));
        }
    }

}
