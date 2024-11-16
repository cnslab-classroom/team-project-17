// 데이터 모델 클래스
// 학습 목표를 저장.
// 목표 제목, 기간, 진척도 정보를 포함.

package app;

public class Goal {
    private String goalId;
    private String title;
    private Date startDate;
    private Date endDate;
    private int progress; // 0~100%
    // Getters and setters
}
