package ru.javaboys.nakormi.service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.model.VolunteerStatData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class VolunteerTopByFoodImgService {
    private static final Point firstPoint = new Point(340, 45);
    private static final Point secondPoint = new Point(60, 210);
    private static final Point thirdPoint = new Point(650, 200);
    private BufferedImage baseImg, boyImg, girlImg;

    @PostConstruct
    public void init() throws IOException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        baseImg = ImageIO.read(contextClassLoader.getResource("META-INF/resources/icons/base.png"));
        boyImg = ImageIO.read(contextClassLoader.getResource("META-INF/resources/icons/boy.png"));
        girlImg = ImageIO.read(contextClassLoader.getResource("META-INF/resources/icons/girl.png"));
    }

    public BufferedImage buildImage(List<VolunteerStatData> topList) {
        BufferedImage image = new BufferedImage(baseImg.getWidth(), baseImg.getHeight(), baseImg.getType());
        Graphics graphics = image.getGraphics();

        graphics.drawImage(baseImg, 0, 0, null);

        if (topList.size() >= 1) {
            var statData = topList.get(0);
            graphics.drawImage(resolvePersonImg(statData.getPerson()), firstPoint.x, firstPoint.y, null);
        }

        if (topList.size() >= 2) {
            var statData = topList.get(1);
            graphics.drawImage(resolvePersonImg(statData.getPerson()), secondPoint.x, secondPoint.y, null);
        }

        if (topList.size() >= 3) {
            var statData = topList.get(2);
            graphics.drawImage(resolvePersonImg(statData.getPerson()), thirdPoint.x, thirdPoint.y, null);
        }

        graphics.dispose();

        return image;
    }

    private BufferedImage resolvePersonImg(Person person) {
        switch (person.getGender()) {
            case MALE -> {
                return boyImg;
            }
            case FEMALE -> {
                return girlImg;
            }
            default -> {
                return boyImg;
            }
        }
    }
}
