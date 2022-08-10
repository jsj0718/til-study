package me.jsj.item6;

public class Deprecation {

    private String name;

    //애노테이션 프로세서에 의해 컴파일 시 경고 메세지 출력

    /**
     * @deprecated in favor of
     * {@link #Deprecation(String)}
     */
    @Deprecated
    public Deprecation() {}

    public Deprecation(String name) {
        this.name = name;
    }
}
