// 알람 관련 클래스
// AlarmManager와 Notification을 사용해 목표 알림 기능 구현.

package app;

public class NotificationManager {
    public void scheduleDailyNotification(Goal goal);

    public void cancelNotification(String goalId);
}
