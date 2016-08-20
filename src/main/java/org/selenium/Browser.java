package org.selenium;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser extends WebDriverException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String url = null;
	protected ProfilesIni profile = new ProfilesIni();
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Set<Cookie> cookies = null;
	protected ArrayList<String> urlList = new ArrayList<String>();

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public void startSafari() {
		// driver = SafariDriver();
	}

	public void setPosition(int x, int y) {
		driver.manage().window().setPosition(new Point(x, y));
	}

	public static void main(String[] args) {
		Browser browser = new Browser();
		browser.startChrome();
		browser.getDriver().navigate().to("https://www.google.com/doodles");
		browser.getDriver().quit();
	}

	public void setPageLoadTimeout(int seconds) {
		driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
	}

	public void setScriptTimeout(int time) {
		driver.manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
	}

	public String getLinkByXpath(String xpath) {
		WebElement webElement = driver.findElement(By.xpath(xpath));
		String link = webElement.getAttribute("href");
		return link;

	}

	public void javaScriptDisable() {
		driver.get("about:config");
		sleep(1000);
		click(By.xpath("/window/deck/vbox[1]/vbox/vbox/hbox/button"));
		Actions act = new Actions(driver);
		act.sendKeys(Keys.RETURN).sendKeys("javascript.enabled").perform();
		sleep(1000);
		act.sendKeys(Keys.TAB).sendKeys(Keys.RETURN).sendKeys(Keys.F5).perform();
		System.out.println("JavaScript turn off");
	}

	public void openUrl(String url) {
		try {
			driver.navigate().to(url);
		} catch (Exception e) {
			try {
				driver.navigate().refresh();
			} catch (Exception e2) {
				openUrl(url);
			}
		}
	}

	public void takeScreenshot(String path) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("img/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WebElement until(final By location) {
		WebElement element = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				return d.findElement(location);
			}
		});
		return element;
	}

	public void click(By location) {
		WebElement element = until(location);
		element.click();
	}

	public void clickEnter(By location) {
		WebElement element = until(location);
		element.sendKeys(Keys.RETURN);
	}

	public void clickTab(By location) {
		WebElement element = until(location);
		element.sendKeys(Keys.TAB);
	}

	public String getValue(By location) {
		WebElement element = until(location);
		return element.getAttribute("value");
	}

	public void quit(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}

	public String getLink(By location) {
		WebElement element = until(location);
		return element.getAttribute("href");
	}

	public void waitFor(int seconds) {
		getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public void uploadImg(By location, String imgPath) {
		System.out.println("location:" + location);
		System.out.println("imgPath:" + imgPath);
		WebElement element = until(location);
		element.sendKeys(imgPath);
	}

	public void inputText(By location, String test) {
		WebElement element = until(location);
		element.clear();
		element.sendKeys(test);
	}

	public void addText(By location, String test) {
		WebElement element = until(location);
		element.sendKeys(test);
	}

	public String getText(By location) {
		String text = null;
		if (checkElement(location)) {
			WebElement element = until(location);
			text = element.getText();
		}
		return text;
	}

	public boolean checkElement(By location) {
		boolean result = false;
		if (driver.findElements(location).size() != 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public String getImgUrlByXpath(By location) {
		WebElement imgWebElement = driver.findElement(location);
		return imgWebElement.getAttribute("src");
	}

	public void uploadImgByName(String name, String imgPath) {
		driver.findElement(By.name(name)).sendKeys(imgPath);
	}

	public void uploadImgByCss(String css, String imgPath) {
		driver.findElement(By.cssSelector(css)).sendKeys(imgPath);
	}

	public void uploadImgByXpath(String xpath, String imgPath) {
		driver.findElement(By.xpath(xpath)).sendKeys(imgPath);
	}

	public void uploadImgById(String id, String imgPath) {
		driver.findElement(By.id(id)).sendKeys(imgPath);
	}

	public String getImgUrlByXpath(String xpath) {
		WebElement imgWebElement = driver.findElement(By.xpath(xpath));
		String imgUrl = imgWebElement.getAttribute("src");
		return imgUrl;
	}

	public String getImagUrlById(String xpath) {
		WebElement imgWebElement = driver.findElement(By.id(xpath));
		String imgUrl = imgWebElement.getAttribute("src");
		return imgUrl;
	}

	public String getImagUrlByCss(String css) {
		WebElement imgWebElement = driver.findElement(By.cssSelector(css));
		String imgUrl = imgWebElement.getAttribute("src");
		return imgUrl;
	}

	public String getTextByXpath(String xpath) {
		WebElement webElement = driver.findElement(By.xpath(xpath));
		return webElement.getText();
	}

	public String getTextById(String id) {
		return driver.findElement(By.id(id)).getText();
	}

	public String getTextByCss(String css) {
		WebElement webElement = driver.findElement(By.cssSelector(css));
		return webElement.getText();
	}

	public int getIntByXpath(String xpath) {
		WebElement webElement = driver.findElement(By.xpath(xpath));
		String text = webElement.getText();
		int num = Integer.valueOf(text);
		return num;
	}

	public void clickByCss(String text) {
		driver.findElement(By.cssSelector(text)).click();
	}

	public void clickByText(String text) {
		driver.findElement(By.linkText(text)).click();
	}

	public void clickByXpath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	public boolean checkElement(String path) {
		boolean result = false;
		if (driver.findElement(By.xpath(path)).getSize() != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void clickById(String id) {
		driver.findElement(By.id(id)).click();
	}

	public String getHomePage() {
		return url;
	}

	public void clearAlert() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.onbeforeunload = function() {};");
	}

	public void inputTextByXpath(String text, String xpath) {
		driver.findElement(By.xpath(xpath)).clear();
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public void inputTextById(String text, String id) {
		driver.findElement(By.id(id)).clear();
		driver.findElement(By.id(id)).sendKeys(text);
	}

	public void inputTextByCss(String text, String css) {
		driver.findElement(By.cssSelector(css)).clear();
		driver.findElement(By.cssSelector(css)).sendKeys(text);
	}

	public void setHomePage(String url) {
		this.url = url;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void startFireFox(String profileName) {
		FirefoxProfile ffprofile = this.profile.getProfile(profileName);
		driver = new FirefoxDriver(ffprofile);
	}

	public void startFireFox() {
		driver = new FirefoxDriver();
	}

	public void startIe(String ieDriver) {
		System.setProperty("webdriver.ie.driver", ieDriver);
		driver = new InternetExplorerDriver();
	}

	public void startChrome() {
		System.setProperty("webdriver.chrome.driver", "/bin/chromedriver");
		driver = new ChromeDriver();
	}

	public void startChrome(String driverPath) {

		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
	}

	public void setTimeOut(int seconds) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setCookie() {
		this.cookies = driver.manage().getCookies();
		for (Cookie loadedCookie : this.cookies) {
			System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
	}

	public Set<Cookie> getCookies() {
		return this.cookies;
	}

	public void setProfile(ProfilesIni profile) {
		this.profile = profile;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}

	public void openMail(int num) {
		// TODO Auto-generated method stub

	}
}
