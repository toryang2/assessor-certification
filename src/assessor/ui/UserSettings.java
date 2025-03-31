/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assessor.ui;

import java.util.Arrays;

/**
 *
 * @author Toryang
 */
public class UserSettings {
    private char[] currentPassword;
    
    public UserSettings(String initialPassword) {
        this.currentPassword = initialPassword.toCharArray();
    }
    
    public boolean validateCurrentPassword(char[] inputPassword) {
        // In real applications, compare hashed values instead of plain text
        return Arrays.equals(inputPassword, currentPassword);
    }
    
    public boolean validateNewPassword(char[] newPassword, char[] confirmPassword) {
        if (newPassword.length < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (!Arrays.equals(newPassword, confirmPassword)) {
            throw new IllegalArgumentException("New passwords do not match");
        }
        return true;
    }
    
    public void changePassword(char[] newPassword) {
        // In real applications, store hashed password instead of plain text
        this.currentPassword = Arrays.copyOf(newPassword, newPassword.length);
        
        // Clear sensitive data from memory
        Arrays.fill(newPassword, '\0');
    }
    
    // Helper method to clear password from memory
    public void clearSensitiveData() {
        Arrays.fill(currentPassword, '\0');
    }
}