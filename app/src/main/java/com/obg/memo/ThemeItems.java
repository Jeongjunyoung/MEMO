package com.obg.memo;

public class ThemeItems {
    private String window;
    private String actionbar;
    private String background;
    private String textsize;
    public ThemeItems (){}
    public ThemeItems(String window, String actionbar, String background, String textsize) {
        this.window = window;
        this.actionbar = actionbar;
        this.background = background;
        this.textsize = textsize;
    }
    public String getWindow() {
        return window;
    }
    public void setWindow(String window) {
        this.window = window;
    }
    public String getActionbar() {
        return actionbar;
    }
    public void setActionbar(String actionbar) {
        this.actionbar = actionbar;
    }
    public String getTextsize() {
        return textsize;
    }
    public void setTextsize(String textsize) {
        this.textsize = textsize;
    }
    public String getBackground() {
        return background;
    }
    public void setBackground(String background) {
        this.background = background;
    }
}
