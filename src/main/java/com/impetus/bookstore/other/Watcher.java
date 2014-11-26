package com.impetus.bookstore.other;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class Watcher.
 */
@Service
public class Watcher {

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(Watcher.class);

    /** The arr. */
    static List<String> arr = new ArrayList<String>();

    /** The no of files. */
    static int noOfFiles = 0;

    /**
     * Test for directory change.
     * 
     * @param myDir
     *            the my dir
     */
    @SuppressWarnings("rawtypes")
    public static void testForDirectoryChange(Path myDir) {

        try {
            WatchService watcher = myDir.getFileSystem().newWatchService();
            myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey watchKey = watcher.take();

            List<WatchEvent<?>> events = watchKey.pollEvents();
            for (WatchEvent event : events) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    arr.add(event.context().toString());
                    noOfFiles = noOfFiles + 1;
                    LOG.debug("Created: " + event.context().toString());
                    break;
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    LOG.debug("Delete: " + event.context().toString());
                    arr.remove(event.context().toString());
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    LOG.debug("Modify: " + event.context().toString());
                }
            }

        } catch (Exception e) {
            LOG.error("Error(watcher): " + e.toString());
        }
    }

}
