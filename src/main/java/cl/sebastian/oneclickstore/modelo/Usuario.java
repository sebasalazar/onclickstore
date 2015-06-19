package cl.sebastian.oneclickstore.modelo;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public class Usuario extends BaseBean {

    private String tbkUser = null;
    private String userName = null;
    private String token = null;
    private String email = null;

    public String getTbkUser() {
        return tbkUser;
    }

    public void setTbkUser(String tbkUser) {
        this.tbkUser = tbkUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 97 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }
}
