package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import static java.lang.System.exit;

class Picture{
    BufferedImage image;
    String name;
    double pictureSize;
    double averageRed;
    double averageGreen;
    double averageBlue;

    public Picture(BufferedImage img, double pictureSize, String name){
        image = img;
        this.name = name;
        this.pictureSize = pictureSize;

        averageRed = 0;
        averageGreen = 0;
        averageBlue = 0;
        for (int i = 0; i < img.getHeight(); i++){
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                averageRed += color.getRed();
                averageGreen += color.getGreen();
                averageBlue += color.getBlue();
            }
        }
        averageRed /= img.getHeight()*img.getWidth();
        averageGreen /= img.getHeight()*img.getWidth();
        averageBlue /= img.getHeight()*img.getWidth();
    }

    public double getAverageRed(){
        return  averageRed;
    }

    public double getAverageGreen() {
        return averageGreen;
    }

    public double getAverageBlue() {
        return averageBlue;
    }

    public double getPictureSize() {
        return pictureSize;
    }

    public String getName(){
        return name;
    }
}

class PictureByNameComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture p1, Picture p2){
        return p1.getName().compareTo(p2.getName());
    }
}

class PictureBySizeComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture p1, Picture p2) {
        if (p1.getPictureSize() < p2.getPictureSize()) {
            return -1;
        }
        if (p1.getPictureSize() > p2.getPictureSize()) {
            return 1;
        }
        return 0;
    }
}

class PictureByRedComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture p1, Picture p2) {
        if (p1.getAverageRed() < p2.getAverageRed()) {
            return -1;
        }
        if (p1.getAverageRed() > p2.getAverageRed()) {
            return 1;
        }
        return 0;
    }
}

class PictureByGreenComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture p1, Picture p2) {
        if (p1.getAverageGreen() < p2.getAverageGreen()) {
            return -1;
        }
        if (p1.getAverageGreen() > p2.getAverageGreen()) {
            return 1;
        }
        return 0;
    }
}

class PictureByBlueComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture p1, Picture p2) {
        if (p1.getAverageBlue() < p2.getAverageBlue()) {
            return -1;
        }
        if (p1.getAverageBlue() > p2.getAverageBlue()) {
            return 1;
        }
        return 0;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Comparator comparatorByName = new PictureByNameComparator();
        Comparator comparatorBySize = new PictureBySizeComparator();
        Comparator comparatorByRed = new PictureByRedComparator();
        Comparator comparatorByGreen = new PictureByGreenComparator();
        Comparator comparatorByBlue = new PictureByBlueComparator();

        ArrayList<Picture> collection = new ArrayList<>();

        while (true) {
            System.out.print("Enter command:\n" +
                    "1) [add] if you want to add image to collection\n" +
                    "2) [print] if you want to print all collection in current order\n" +
                    "3) [remove] if you want to remove image by name\n" +
                    "4) [sort] if you want to sort collection\n" +
                    "5) [distance] if you want to calculate similarity coefficients in current order\n" +
                    "6) [exit] if you want to exit the program\n");
            String command = in.nextLine();

            switch (command) {
                case "add":
                    System.out.print("Enter path to image\n");
                    String path = in.nextLine();
                    File file;
                    BufferedImage image;
                    try {
                        file = new File(path);
                        image = ImageIO.read(file);
                    } catch (IOException e) {
                        System.out.print("Error due file reading\n");
                        continue;
                    }
                    collection.add(new Picture(image, file.length(), file.getName()));
                    break;
                case "print":
                    System.out.print(collection.size() + " elements detected\n");
                    for (Picture p : collection) {
                        System.out.print(p.getName() + " size: " + p.getPictureSize()
                                + " aR: " + p.getAverageRed()
                                + " aG: " + p.getAverageGreen()
                                + " aB: " + p.getAverageBlue() + '\n');
                    }
                    break;
                case "remove":
                    System.out.print("Enter name to remove\n");
                    String name = in.nextLine();
                    for (int i = 0; i < collection.size(); i++) {
                        if (collection.get(i).getName().equals(name)) {
                            collection.remove(i);
                            i--;
                        }
                    }
                    break;
                case "sort":
                    System.out.print("Chose type of sort:\n" +
                            "1) [name] if you want to sort collection by names of pictures\n" +
                            "2) [size] if you want to sort collection by sizes of pictures\n" +
                            "3) [red] if you want to sort collection by average red value\n" +
                            "4) [green] if you want to sort collection by average green value\n" +
                            "5) [blue] if you want to sort collection by average blue value\n");
                    String sortType = in.nextLine();
                    switch (sortType){
                        case "name":
                            collection.sort(comparatorByName);
                            break;
                        case "size":
                            collection.sort(comparatorBySize);
                            break;
                        case "red":
                            collection.sort(comparatorByRed);
                            break;
                        case "green":
                            collection.sort(comparatorByGreen);
                            break;
                        case "blue":
                            collection.sort(comparatorByBlue);
                            break;
                        default:
                            System.out.print("Unrecognized command. Please, try again.\n");
                            break;
                    }
                    break;
                case "distance":
                    System.out.print("Enter name to calculate\n");
                    String nameToCalculate = in.nextLine();
                    int index = -1;
                    for (int i = 0; i < collection.size(); i++){
                        if (collection.get(i).getName().equals(nameToCalculate)){
                            index = i;
                            break;
                        }
                    }
                    if (index == -1){
                        System.out.print("Name was not found\n");
                        continue;
                    }
                    for (int i = 0; i < collection.size(); i++){
                        if (i != index) {
                            System.out.print(collection.get(i).getName() + " "
                                    + (float)1/(1 + Math.abs(i - index)) +"\n");
                        }else{
                            System.out.print(collection.get(i).getName() + " 1" +"\n");
                        }
                    }
                    break;
                case "exit":
                    System.out.print("Good bye.\n");
                    exit(0);
                    break;
                default:
                    System.out.print("Unrecognized command. Please, try again.\n");
                    break;
            }
        }
    }
}
