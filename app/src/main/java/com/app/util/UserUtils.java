package com.app.util;

interface MyUserUtils {
    public void setUser(Integer id, String name, String password);

    public void setUser(User user);

    public User getUser();
}

public enum UserUtils implements MyUserUtils {
    INSTANCE {
        private User user = new User();

        @Override
        public void setUser(Integer id, String name, String password) {
            this.user.setId(id);
            this.user.setName(name);
            this.user.setPassword(password);
        }

        @Override
        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public User getUser() {
            return user;
        }
    };

    public static UserUtils getInstance() {
        return UserUtils.INSTANCE;
    }
}