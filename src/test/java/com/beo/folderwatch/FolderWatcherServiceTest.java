package com.beo.folderwatch;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderWatcherServiceTest {

    @Test
    public void testInitializationNoParameter() {
        FolderWatcherService watcherService = new FolderWatcherService(Arrays.asList());
        assertThat(watcherService).isNotNull();
        assertThat(watcherService.getThreshold()).isEqualTo(FolderWatcherService.DEFAULT_THRESHOLD);
        assertThat(watcherService.getFoldersToWatch()).isNotEmpty().containsExactly(FolderWatcherService.DEFAULT_FOLDER);
    }

    @Test
    public void testInitializationOnlyThreshold() {
        int threshold = 123;
        FolderWatcherService watcherService = new FolderWatcherService(Arrays.asList(Integer.toString(threshold)));
        assertThat(watcherService).isNotNull();
        assertThat(watcherService.getThreshold()).isEqualTo(threshold);
        assertThat(watcherService.getFoldersToWatch()).isNotEmpty().containsExactly(FolderWatcherService.DEFAULT_FOLDER);
    }

    @Test
    public void testInitializationOnlyFolders() {
        String[] params = {"folder1", "folder2", "folder3"};
        FolderWatcherService watcherService = new FolderWatcherService(Arrays.asList(params));
        assertThat(watcherService).isNotNull();
        assertThat(watcherService.getThreshold()).isEqualTo(FolderWatcherService.DEFAULT_THRESHOLD);
        assertThat(watcherService.getFoldersToWatch()).isNotEmpty().containsExactly(params);
    }

    @Test
    public void testInitialization() {
        int threshold = 123;
        String[] folders = {"folder1", "folder2", "folder3"};
        List<String> params = new ArrayList<>(Arrays.asList(folders));
        params.add(0, Integer.toString(threshold));
        FolderWatcherService watcherService = new FolderWatcherService(params);
        assertThat(watcherService).isNotNull();
        assertThat(watcherService.getThreshold()).isEqualTo(threshold);
        assertThat(watcherService.getFoldersToWatch()).isNotEmpty().containsExactly(folders);
    }

}