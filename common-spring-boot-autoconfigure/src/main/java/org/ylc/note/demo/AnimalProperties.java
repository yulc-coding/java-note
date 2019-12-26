package org.ylc.note.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/25
 */
@ConfigurationProperties(prefix = "animal")
public class AnimalProperties {

    /**
     * 类别
     */
    private String type = "animal";

    /**
     * 名称
     */
    private String name;

    private final Fish fish = new Fish();

    private final Bird bird = new Bird();

    public static class Fish {

        private String doing;

        public String getDoing() {
            return doing;
        }

        public void setDoing(String doing) {
            this.doing = doing;
        }
    }

    public static class Bird {

        private String doing;

        public String getDoing() {
            return doing;
        }

        public void setDoing(String doing) {
            this.doing = doing;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fish getFish() {
        return fish;
    }

    public Bird getBird() {
        return bird;
    }
}
