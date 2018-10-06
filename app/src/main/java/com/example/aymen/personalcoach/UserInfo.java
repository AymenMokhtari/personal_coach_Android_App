package com.example.aymen.personalcoach;

/**
 * Created by Aymen on 11/13/2017.
 */
public class UserInfo {
    private  int id;
    private String firstname;
    private  String lastname;
    private String email;
    private  String goal;
    private  float currentWheight;
    private  String gender;
    private  String activitylevel;
    private  float height;
    private String dateOfBirth;
    private  String role;
private  String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo(int id, String firstname, String lastname, String email, String goal, float currentWheight, String gender, String activitylevel, float height, String dateOfBirth, String role, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.goal = goal;
        this.currentWheight = currentWheight;
        this.gender = gender;
        this.activitylevel = activitylevel;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.password = password;
    }

    public UserInfo() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public float getCurrentWheight() {
        return currentWheight;
    }

    public void setCurrentWheight(float currentWheight) {
        this.currentWheight = currentWheight;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivitylevel() {
        return activitylevel;
    }

    public void setActivitylevel(String activitylevel) {
        this.activitylevel = activitylevel;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", goal='" + goal + '\'' +
                ", currentWheight=" + currentWheight +
               ", gender='" + gender + '\'' +
                ", activitylevel='" + activitylevel + '\'' +
                ", height=" + height +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }


}
