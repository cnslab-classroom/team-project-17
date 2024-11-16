// 데이터베이스 관련 클래스
// SQLite를 사용해 로컬 데이터 저장소 관리.
// 목표 데이터를 저장, 업데이트, 삭제하는 메서드 포함.

package app;

public class LocalDatabaseManager {
    public void saveGoal(Goal goal);

    public List<Goal> getAllGoals();

    public void deleteGoal(String goalId);
}
