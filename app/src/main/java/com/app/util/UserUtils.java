package com.app.util;

import com.app.entity.User;

public enum UserUtils {
    INSTANCE {
        private User user = new User();

        public void setUser(Integer id, String name,  String password) {
            this.user.setId(id);
            this.user.setName(name);
            this.user.setPassword(password);
        }

        public void setUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    };

    public static UserUtils getInstance() {
        return UserUtils.INSTANCE;
    }
}