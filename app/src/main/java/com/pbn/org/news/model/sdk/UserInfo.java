package com.pbn.org.news.model.sdk;

import java.util.Objects;

public class UserInfo {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(name, userInfo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
