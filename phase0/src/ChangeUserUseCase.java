public class ChangeUserUseCase {

    private final User user;

    public ChangeUserUseCase(User user) {
        this.user = user;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(this.user.getPassword())) {
            boolean setPasswordSuccess = this.user.setPassword(newPassword);
            if (!setPasswordSuccess) {
                throw new NewPasswordIsTheSameAsOldPasswordException();
            }
        }
        else {
            throw new PasswordsDontMatchException();
        }
    }
}
