package com.appdeveloper.rh.yelpreviewapp;

public class Category {
    String alias;
    String title;
    String parentAliases;
    String countryWhitelist;
    String countryBlacklist;

    public Category() {
    }

    public Category(String alias, String title, String parentAliases, String countryWhitelist, String countryBlacklist) {
        this.alias = alias;
        this.title = title;
        this.parentAliases = parentAliases;
        this.countryWhitelist = countryWhitelist;
        this.countryBlacklist = countryBlacklist;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentAliases() {
        return parentAliases;
    }

    public void setParentAliases(String parentAliases) {
        this.parentAliases = parentAliases;
    }

    public String getCountryWhitelist() {
        return countryWhitelist;
    }

    public void setCountryWhitelist(String countryWhitelist) {
        this.countryWhitelist = countryWhitelist;
    }

    public String getCountryBlacklist() {
        return countryBlacklist;
    }

    public void setCountryBlacklist(String countryBlacklist) {
        this.countryBlacklist = countryBlacklist;
    }
}
