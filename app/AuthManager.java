//Firebase Authentication 관련 로직을 처리.
// 회원가입, 로그인, 로그아웃 메서드 포함.
// 비밀번호 암호화를 위한 AES 암호화 로직포함.
package app;

public class AuthManager {
    public boolean register(String email, String password);

    public boolean login(String email, String password);

    public void logout();
}
