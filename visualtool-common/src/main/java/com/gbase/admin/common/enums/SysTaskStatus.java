package com.gbase.admin.common.enums;

/**
 * task status
 *
 * @author xielibo
 */
public enum SysTaskStatus {

    CREATED(0, "created"),
    PROCESSING(1, "processing"),
    FINISH(2, "finish"),
    EXEC_FAIL(3, "execution failed"),
    STOP(4, "stop")
    ;

    private final Integer code;
    private final String info;

    SysTaskStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
