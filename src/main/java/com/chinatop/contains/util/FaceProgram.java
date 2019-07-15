package com.chinatop.contains.util;

public class FaceProgram {
    private String a = "d";

    public FaceProgram() {
        call();
    }

    public static void main(String[] args) {
        FaceProgram faceProgram = new FaceProgramInner();
        faceProgram.call();
    }

    public void call() {
        System.out.println("父类方法调用！");
    }

    static class FaceProgramInner extends FaceProgram {
        private String a = "sdf";

        public void call() {
            System.out.println(a);
        }
    }
}
