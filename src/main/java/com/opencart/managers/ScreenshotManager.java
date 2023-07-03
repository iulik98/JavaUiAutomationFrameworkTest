package com.opencart.managers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {
    static SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
    static Date date;

    public static void getScreenshotOfTheFullPage(String tool, WebDriver driver, String fileName, String extension) {
        date = new Date();
        switch (tool.toLowerCase()) {
            case "selenium":
                File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(file, new File("src/test/resources/screenshots/" + fileName + formatter.format(date) + "." + extension));
                    System.out.println("The Screenshot is captured.");
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            case "ashot":
                Screenshot screenshot = new AShot().takeScreenshot(driver);
                try {
                    ImageIO.write(screenshot.getImage(), extension, new File("src/test/resources/screenshots/" + fileName + formatter.format(date) + "." + extension));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public static void getScreenshotOfTheElement(WebDriver driver, String fileName, String extension, WebElement element) {
        date = new Date();
        Screenshot webElement = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(50)).takeScreenshot(driver, element);
        try {
            ImageIO.write(webElement.getImage(), extension, new File("src/test/resources/screenshots/" + fileName + formatter.format(date) + "." + extension));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
