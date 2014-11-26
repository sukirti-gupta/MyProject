package com.impetus.bookstore.other;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Scheduler2.
 */
public class Scheduler2 extends TimerTask {

    /** The path. */
    private String path;

    /**
     * Instantiates a new scheduler2.
     * 
     * @param path
     *            the path
     */
    public Scheduler2(String path) {
        this.path = path;
    }

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(Scheduler2.class);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    public void run() {
        try {
            LOG.debug("Scheduler2/Watcher array size = " + Watcher.arr.size());
            Path myDir = Paths.get(path);
            Watcher.testForDirectoryChange(myDir);
        } catch (Exception ex) {
            LOG.error("Error running thread for csv " + ex.getMessage());
        }
    }

}
