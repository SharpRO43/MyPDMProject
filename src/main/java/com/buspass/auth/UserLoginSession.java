package com.buspass.auth;

import java.util.Map;
import java.util.List;

import com.buspass.db.QueryExecutionModule;
import com.buspass.queries.UserService;
import com.buspass.utils.AuthUtils;

public class UserLoginSession {
    private Integer userId;
    private Integer userRoleId;
    private Integer age;
    private String username;
    private String fullName;
    private String address;
    private String phoneNumber;

    UserService userService = new UserService();

    public Integer getUserId()     { return userId; }
    public Integer getUserRoleId() { return userRoleId; }
    public Integer getAge()        { return age; }
    public String getUsername()    { return username; }
    public String getFullName()    { return fullName; }
    public String getAddress()     { return address; }
    public String getPhoneNumber() { return phoneNumber;}


    private void setUserId(Integer userId)          { this.userId = userId; }
    private void setUserRoleId(Integer userRoleId)  { this.userRoleId = userRoleId; }
    private void setAge(Integer age)                { this.age = age; }
    private void setUsername(String username)       { this.username = username; }
    private void setFullName(String fullName)       { this.fullName = fullName; }
    private void setAddress(String address)         { this.address = address; }
    private void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}
    
    
    /**
     * Return the result of the User login
     * @param userId - The UserID of the User
     * @param plainPW - Password in plaintext
     * @return -1 if the User doesn't exist, 0 if the password was incorrect, and 1 if the login was successful 
     */
    public int attemptLogin(String username, String plainPW) {
        // Fetch stored hash and role id for the user
        List<Map<String, Object>> results;
        results = QueryExecutionModule.executeQuery(
            "SELECT UserID, Username, UserPassword, FullName, Age, Phone, UserAddress, UserRoleID FROM `User` WHERE Username = ?", 
            username
        );

        if (results == null || results.isEmpty()) {
            // user not found
            return -1;
        }

        Map<String, Object> user = results.get(0);
        Object pwObj = user.get("UserPassword");
        if (pwObj == null) {
            setInfo(user);

            return 1; // no password set
        }

        String hashedPW = pwObj.toString();

        // IMPORTANT: BCrypt generates a different hash each time because it uses a random salt.
        // To verify a password, use BCrypt.checkpw(plainPassword, storedHash) instead of
        // hashing the plaintext again.
        boolean matches = false;
        try {
            matches = AuthUtils.verify(plainPW, hashedPW);
        } 
        catch (Exception e) {
            // any parsing/format issue with the stored hash
            matches = false;
        }

        if (matches) {
            setInfo(user);

            return 1;
        }
        return 0;
    }

    private void setInfo(Map<String, Object> userInfo) {
        clearSessionInfo();

        Object usernameObj = userInfo.get("Username");
        Object userIdObj   = userInfo.get("UserID");
        Object roleObj     = userInfo.get("UserRoleID");
        Object ageObj      = userInfo.get("Age");
        Object fullNameObj = userInfo.get("FullName");
        Object addressObj  = userInfo.get("UserAddress");
        Object phoneObj    = userInfo.get("Phone");

        if (fullNameObj != null)
            setUsername(usernameObj.toString());
        if (userIdObj != null)
            setUserId(Integer.parseInt(userIdObj.toString()));
        if (roleObj != null)
            setUserRoleId(Integer.parseInt(roleObj.toString()));
        if (ageObj != null)
            setAge(Integer.parseInt(ageObj.toString()));
        if (fullNameObj != null)
            setFullName(fullNameObj.toString());
        if (addressObj != null)
            setAddress(addressObj.toString());
        if (phoneObj != null)
            setPhoneNumber(phoneObj.toString());
    }

    private void clearSessionInfo() {
        userId = null;
        userRoleId = null;
        age = null;
        username = null;
        fullName = null;
        address = null;
        phoneNumber = null;
    }
}
