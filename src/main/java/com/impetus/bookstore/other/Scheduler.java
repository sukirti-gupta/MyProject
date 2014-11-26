package com.impetus.bookstore.other;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.impetus.bookstore.service.BooksService;

// TODO: Auto-generated Javadoc
/**
 * The Class Scheduler.
 */
public class Scheduler extends TimerTask {

    /** The file service. */
    private BooksService booksService = new BooksService();

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(Scheduler.class);

    /** The path. */
    private String path;

    /**
     * Instantiates a new scheduler.
     * 
     * @param booksService
     *            the books service
     * @param path
     *            the path
     */
    public Scheduler(BooksService booksService, String path) {
        this.booksService = booksService;
        this.path = path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    public void run() {
        try {
            LOG.debug("Scheduler watcher array size = " + Watcher.arr.size());
            LOG.debug("Scheduler watcher number of files = "
                    + Watcher.noOfFiles);

            for (int i = 0; i < Watcher.arr.size(); i++) {
                LOG.debug("Watcher array includes = " + Watcher.arr.get(i));
                String str = booksService.readCSV(path + "\\"
                        + Watcher.arr.get(i));
                booksService.uploadCsvToDatabse(str, Watcher.arr.get(i));
            }
            Watcher.arr.clear();
            LOG.debug("Watcher array cleared..");
        } catch (Exception ex) {
            LOG.error("Error running thread -> " + ex.getMessage());
        }
    }

}
