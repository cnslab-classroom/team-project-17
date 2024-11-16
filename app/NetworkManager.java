// 네트워크 관련 클래스
// Socket을 이용한 네트워크 통신.
// 친구와 학습 목표 데이터를 공유.

package app;

public class NetworkManager {
    public void sendGoalData(Goal goal, String recipientIp);

    public List<Goal> receiveGoalData();
}
