package edu.baylor.ecs.models;

public enum CodeCloneType {
    ONE, TWO, THREE;

    public static int toInt(CodeCloneType codeCloneType){
        switch (codeCloneType){
            case ONE: return 1;
            case TWO: return 2;
            case THREE: return 3;
            default: return -1;
        }
    }
}
