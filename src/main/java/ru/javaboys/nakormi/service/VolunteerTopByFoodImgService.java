package ru.javaboys.nakormi.service;

import jakarta.annotation.PostConstruct;
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
    private static final Point secondPoint = new Point(60, 170);
    private static final Point thirdPoint = new Point(650, 200);
    private static final Point firstPointWeight = new Point(350, 40);
    private static final Point secondPointWeight = new Point(40, 170);
    private static final Point thirdPointWeight = new Point(800, 190);
    private static final Rectangle secondRectName = new Rectangle(40, 1040, 320, 100);
    private static final Rectangle firstRectName = new Rectangle(370, 1040, 320, 100);
    private static final Rectangle thirdRectName = new Rectangle(700, 1040, 320, 100);
    private static final float nameFontSize = 48.0f;
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
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);

        g.drawImage(baseImg, 0, 0, null);

        if (topList.size() >= 1) {
            VolunteerStatData statData = topList.get(0);
            printData(g, statData, firstPoint, firstPointWeight, firstRectName);
        }

        if (topList.size() >= 2) {
            VolunteerStatData statData = topList.get(1);
            printData(g, statData, secondPoint, secondPointWeight, secondRectName);
        }

        if (topList.size() >= 3) {
            VolunteerStatData statData = topList.get(2);
            printData(g, statData, thirdPoint, thirdPointWeight, thirdRectName);
        }

        g.dispose();

        return image;
    }

    private void printData(Graphics g, VolunteerStatData statData, Point imgPoint, Point weightPoint, Rectangle rect) {
        g.drawImage(resolvePersonImg(statData.getPerson()), imgPoint.x, imgPoint.y, null);

        String name = statData.getPerson().getName();
        String surname = statData.getPerson().getSurname();
        String weightStr = convertToWeightStr(statData.getCnt());

        printStringInRect(g, rect, 0, name);
        printStringInRect(g, rect, 60, surname);

        printString(g, weightPoint, weightStr);
    }

    private void printStringInRect(Graphics g, Rectangle rect, int yOffset, String str) {
        float lastSize = g.getFont().getSize();
        g.setFont(g.getFont().deriveFont(nameFontSize));

        var strWidth = g.getFontMetrics().stringWidth(str);
        int xOffset = (rect.width - strWidth) / 2;
        g.drawString(str, rect.x + xOffset, rect.y + yOffset);

        g.setFont(g.getFont().deriveFont(lastSize));
    }

    private void printString(Graphics g, Point point, String str) {
        float lastSize = g.getFont().getSize();
        g.setFont(g.getFont().deriveFont(nameFontSize));

        g.drawString(str, point.x, point.y);

        g.setFont(g.getFont().deriveFont(lastSize));
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

    private String convertToWeightStr(Long cnt) {
        return String.format("%1$,.2f", (cnt / 1000.0f)) + " кг.";
    }

}
