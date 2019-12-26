package org.ylc.note.demo;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/25
 */
public class FishService {

    private AnimalProperties animalProperties;

    public FishService(AnimalProperties animalProperties) {
        this.animalProperties = animalProperties;
    }

    public void doing() {
        System.out.println("this is fish service");
        System.out.println("type:" + animalProperties.getType());
        System.out.println("name:" + animalProperties.getName());
        System.out.println("doing:" + animalProperties.getFish().getDoing());
        System.out.println("\n");
    }
}
