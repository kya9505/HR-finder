package cli.controller;

import dao.EventBackupDao;
import dao.EventBackupDaoImp;
import dto.EventBackup;
import java.util.List;

public class BackupController extends BaseController {
    private final EventBackupDao backupDao = new EventBackupDaoImp();

    public void run() {
        printSectionHeader("Backup Menu");
        List<EventBackup> backupList = backupDao.backup();
        if (backupList.isEmpty()) {
            System.out.println("No backup records found.");
        }
    }
}