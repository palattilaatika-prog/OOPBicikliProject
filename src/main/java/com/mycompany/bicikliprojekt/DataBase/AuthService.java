/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bicikliprojekt.DataBase;

import com.mycompany.bicikliprojekt.model.User;

public interface AuthService {
    
    User authenticate(String email, String password);

    
    boolean register(User user);
}