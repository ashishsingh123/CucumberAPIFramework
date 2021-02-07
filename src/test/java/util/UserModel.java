package util;

import java.util.List;

/*
@Author : Ashish Kr Singh
*/
public class UserModel {
    
    private int userId;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private int associationCount;

    private List<UserRolesModel> userRoles;

    private String created;

    private String status;

    private String modified;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setAssociationCount(int associationCount) {
        this.associationCount = associationCount;
    }

    public int getAssociationCount() {
        return this.associationCount;
    }

    public void setUserRoles(List<UserRolesModel> userRoles) {
        this.userRoles = userRoles;
    }

    public List<UserRolesModel> getUserRoles() {
        return this.userRoles;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return this.created;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModified() {
        return this.modified;
    }

    @Override
    public String toString() {
        return "UserModel [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + email + ", associationCount=" + associationCount + ", userRoles=" + userRoles.toString()
                + ", created=" + created + ", status=" + status + ", modified=" + modified + "]";
    }

}
