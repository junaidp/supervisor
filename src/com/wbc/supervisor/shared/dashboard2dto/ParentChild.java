package com.wbc.supervisor.shared.dashboard2dto;

/**
 * Created by JIM on 11/25/2014.
 */
public class ParentChild {
    int p;
    int c;

    public ParentChild() {
    }

    public ParentChild(int p, int c) {
        this.p = p;
        this.c = c;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p=p;
    }

    @Override
    public String toString() {
        return "ParentChild{" +
                "p=" + p +
                ", c=" + c +
                '}';
    }
}
