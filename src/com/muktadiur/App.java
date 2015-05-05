package com.muktadiur;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

	public static final int THREAD_SLEEP = 5000;
	public static final int THREAD_SLEEP_SMALL = 2000;
	private static Scanner scanner;

	public static void main(String[] args) throws InterruptedException {

		
		WebDriver driver = new ChromeDriver();

		loginToGmail(driver);

		navigateToWebmasterTool(driver);

		submitUrlForFetch(driver);

		quit(driver);

	}

	private static void submitUrlForFetch(WebDriver driver) {
		Config config = Config.getConfig();
		Path path = Paths.get(config.getPath(), config.getFile());
		try {
			Files.lines(path).forEach(line -> fetchUrl(driver, line));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void fetchUrl(WebDriver driver, String line) {
		try {
			WebElement pathInput = driver.findElement(By.id("path-input"));
			pathInput.sendKeys(line.trim());

			WebElement fetch = driver.findElement(By
					.className("jfk-button-primary"));
			fetch.click();
			Thread.sleep(THREAD_SLEEP);
			//Thread.sleep(THREAD_SLEEP);
			pathInput = driver.findElement(By.id("path-input"));
			if(pathInput != null) 
				pathInput.clear();
			
			submitToIndex(driver);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void submitToIndex(WebDriver driver) {
		try {
			List<WebElement> submittoindexs = driver.findElements(By
					.xpath("//div[text() = 'Submit to index']"));
			for (WebElement submittoindex : submittoindexs) {

				submittoindex.click();
				Thread.sleep(THREAD_SLEEP_SMALL);
				WebElement radio = driver.findElement(By
						.id("verified-addurl-dialog-radio-url"));
				radio.click();
				WebElement go = driver.findElement(By.name("go"));
				go.click();
				Thread.sleep(THREAD_SLEEP);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void quit(WebDriver driver) throws InterruptedException {
		Thread.sleep(THREAD_SLEEP);
		System.out.println("Press q to quit");
		scanner = new Scanner(System.in);
		if (scanner.next() == "q")
			driver.quit();
	}

	private static void navigateToWebmasterTool(WebDriver driver)
			throws InterruptedException {
		driver.navigate()
				.to("https://www.google.com/webmasters/tools/googlebot-fetch?hl=en&siteUrl=http://[siteurl].com/");
		Thread.sleep(THREAD_SLEEP);
	}

	private static void loginToGmail(WebDriver driver)
			throws InterruptedException {
		driver.get("https://accounts.google.com/ServiceLogin?sacu=1&continue=https%3A%2F%2Fwww.google.com%2Fwebmasters%2Ftools%2Fhome%3Fhl%3Den&hl=en&service=sitemaps");
		Thread.sleep(THREAD_SLEEP);
		WebElement email = driver.findElement(By.id("Email"));
		WebElement passwd = driver.findElement(By.id("Passwd"));
		WebElement signin = driver.findElement(By.id("signIn"));

		Config config = Config.getConfig();
		email.sendKeys(config.getEmail());
		passwd.sendKeys(config.getPassword());
		//Thread.sleep(THREAD_SLEEP);
		signin.click();
		Thread.sleep(THREAD_SLEEP);

	}

}
