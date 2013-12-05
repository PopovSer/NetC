package com.netc.task3.commons;

import java.io.Serializable;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netc.task3.commons.EmptyParameter;
import java.net.Socket;

public class User implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    private String nickname;
    private String oldNickname;
    private String password;
    private String email;
    private boolean needToRegister;

    public User(String nickname, String email, String password) throws EmptyParameter {
        if (nickname.isEmpty()) {
            throw new EmptyParameter("Nickname is Empty");
        }
        if (email.isEmpty()) {
            throw new EmptyParameter("Email is Empty");
        }
        if (password.isEmpty()) {
            throw new EmptyParameter("Password is Empty");
        } else {
            this.nickname = nickname;
            this.password = password;
            this.email = email;
            oldNickname = nickname;
            needToRegister = true;
        }
    }
    

    public User(String nickname, String password) throws EmptyParameter {
        if (nickname.isEmpty()) {
            throw new EmptyParameter("Nickname is Empty");
        }
        if (password.isEmpty()) {
            throw new EmptyParameter("Password is Empty");
        } else {
            this.nickname = nickname;
            this.password = password;
            this.email = "";
            oldNickname = nickname;
            needToRegister = false;
        }
    }

    public User(User client) {
        nickname = client.getNickname();
        password = client.getPassword();
        email = client.getEmail();
        oldNickname = nickname;
        needToRegister = true;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOldNickname() {
        return oldNickname;
    }

    public void setNewNickname(String newNickname) {
        this.oldNickname = nickname;
        nickname = newNickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmptyParameter {
        if (!email.isEmpty()) {
            throw new EmptyParameter("Email is Empty");
        } else {
            this.email = email;
        }
    }

    public void setNickname(String nickname) throws EmptyParameter {
        if (nickname.isEmpty()) {
            throw new EmptyParameter("Nickname is Empty");
        } else {
            this.nickname = nickname;
        }
    }

    public void setPassword(String password) throws EmptyParameter {
        if (!password.isEmpty()) {
            throw new EmptyParameter("Password is Empty");
        } else {
            this.password = password;
        }
    }

    @Override
    public String toString() {
        return nickname + " | " + password + " | " + email;
    }
}
