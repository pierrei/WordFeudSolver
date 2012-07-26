package nu.mrpi.wordfeudsolver.domain;

/**
 *
 */
public class WFAccount {
    private final String email;
    private final String password;

    public WFAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
