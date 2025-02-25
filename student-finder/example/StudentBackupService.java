package example;

/**
 * StudentBackupService 인터페이스는 학생 데이터를 파일에 백업하는 기능을 정의합니다.
 */
@FunctionalInterface
public interface StudentBackupService {
    void backupToFile();
}
