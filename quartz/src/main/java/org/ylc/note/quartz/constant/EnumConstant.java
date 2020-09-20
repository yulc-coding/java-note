package org.ylc.note.quartz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-12
 */
public class EnumConstant {

    @Getter
    @AllArgsConstructor
    public enum JobStatus {

        PAUSED("0", "暂停"),
        NORMAL("1", "正常");

        private final String code;

        private final String value;

    }

    @Getter
    @AllArgsConstructor
    public enum ExecutionStatus {

        FAIL("0", "正常"),
        SUCCESS("1", "暂停");

        private final String code;

        private final String value;
    }


}
