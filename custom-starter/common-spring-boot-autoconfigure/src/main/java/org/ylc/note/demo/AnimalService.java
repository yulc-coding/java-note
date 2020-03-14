package org.ylc.note.demo;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/26
 */
public class AnimalService {

    private AnimalProperties animalProperties;

    public AnimalService(AnimalProperties animalProperties) {
        this.animalProperties = animalProperties;
    }

    public void doing() {
        System.out.println("this is animal service");
        System.out.println("type:" + animalProperties.getType());
        System.out.println("name:" + animalProperties.getName());
        System.out.println("bird:" + animalProperties.getBird().getDoing());
        System.out.println("fish:" + animalProperties.getFish().getDoing());
        System.out.println("\n");
    }

}
