package org.com.gradleplugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class CheckResource extends DefaultTask{
    private boolean string;
    private boolean drawable;
    private boolean color;

    boolean getString() {
        return string
    }

    @Input
    public void setString(boolean string) {
        this.string = string
    }

    public boolean getDrawable() {
        return drawable
    }

    @Input
    public void setDrawable(boolean drawable) {
        this.drawable = drawable
    }

    public boolean getColor() {
        return color
    }

    @Input
    public void setColor(boolean color) {
        this.color = color
    }

    @TaskAction
    public void sayResult(){
        System.out.printf("%s, %s!\n", getString(), getColor());
    }


}