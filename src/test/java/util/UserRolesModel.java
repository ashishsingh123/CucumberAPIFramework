package util;

/*
@Author : Ashish Kr Singh
 */
public class UserRolesModel
{
    private int userId;

    private String safetyRoleName;

    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setSafetyRoleName(String safetyRoleName){
        this.safetyRoleName = safetyRoleName;
    }
    public String getSafetyRoleName(){
        return this.safetyRoleName;
    }
    
    @Override
    public String toString() {
        return "UserRoles [userId=" + userId + ", safetyRoleName=" + safetyRoleName + "]";
    }
    
    
}
