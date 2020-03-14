package org.ylc.note.jpush;

import java.io.Serializable;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数体
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/24
 */
public class JPushEntity implements Serializable {

    private static final long serialVersionUID = -6116684807546418394L;

    /**
     * 额外参数
     */
    private Map<String, String> extras;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
