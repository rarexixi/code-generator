package ${baseCommonPackage}.constant;

public enum SortConstants {

    ASC(0),
    DESC(1);

    private int code;

    SortConstants(int code) {
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
