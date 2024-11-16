// 데이터베이스 관련 클래스
// Firebase Firestore와 연동하여 서버와 데이터를 동기화.
// 로컬 데이터와 서버 데이터를 일치시킴.

package app;

public class RemoteDatabaseManager {
    public void uploadGoal(Goal goal);

    public List<Goal> fetchGoals();

    public void deleteGoal(String goalId);
}
