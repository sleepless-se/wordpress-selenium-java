package com.wordpress;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.selenium.Browser;

public class WpBrowser extends  Browser{
	public static void main(String[] args) {
		WpBrowser wpBrowser = new WpBrowser();
		wpBrowser.startFireFox();
		wpBrowser.setHomePage("http://amore-men.com");
		wpBrowser.login("UserName", "Pass");
		wpBrowser.openPostPage();
		wpBrowser.writeTitle("title");
		wpBrowser.writeBody("body");
		wpBrowser.writeTag("タグ１,タグ２,タグ３");
		wpBrowser.uploadThumbnail("localImgPath");
		wpBrowser.selectCategory("1");
		wpBrowser.save();
		System.exit(0);
	}
	public boolean login(String id ,String pass) {
		driver.get(getHomePage()+"/wp-login.php");
		inputTextByXpath(id, "//input[@id='user_login']");
		inputTextByXpath(pass, "//input[@id='user_pass']");
		clickByXpath("//input[@id='wp-submit']");
		if (driver.getPageSource().indexOf("外観")!=-1) {
			return true;
		} else {
			return false;
		}
	}
	public void openPostPage() {
		driver.get(getHomePage()+"/wp-admin/post-new.php");
		sleep(2000);
	}
	public void writeTitle(String title) {
		inputTextByXpath(title, "//div[@id='titlewrap']/input");
		sleep(1000);
	}
	public void writeBody(String text){
		clickByXpath("//button[@id='content-html']");
		int num = 100;
		for (int i = 0; i < text.length(); i=i+num) {
			String xpath = "//textarea[@id='content']";
			
			try{
				if (text.length()<i+num) {
					driver.findElement(By.xpath(xpath)).sendKeys(text.substring(i,text.length()));
				}else{
					driver.findElement(By.xpath(xpath)).sendKeys(text.substring(i,i+num));
				}
			}catch (Exception e) {
				e.printStackTrace();
				if (text.length()<i+num) {
					driver.findElement(By.xpath(xpath)).sendKeys(text.substring(i,text.length()));
				}else{
					driver.findElement(By.xpath(xpath)).sendKeys(text.substring(i,i+num));
				}
				
			}
			
		}
		
		sleep(1000);

	}
	public void writeTag(String tag){
		inputTextByXpath(tag, "//input[@id='new-tag-post_tag']");
		sleep(1000);

		clickByXpath("//input[@value='追加']");
		sleep(1000);

	}
	
	public void selectCategory(String categoryNos){
		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(categoryNos.split(",")));
		for (String string : myList) {
			String xpath = "//input[@id='in-category-"+string+"']";
			System.out.println(xpath);
			try {
				clickByXpath(xpath);

			} catch (Exception e) {
				clickByText("未分類");
			}
			sleep(1000);

		}
	}
	public void save(){
		try {
			sleep(1000);
			try {
				driver.findElement(By.xpath("//a[@id='irc_cb']"));
			} catch (Exception e) {
				driver.findElement(By.xpath("//input[@id='save-post']"));
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			save();
		}
		sleep(2000);

	}
	public void publish(){
		clickByText("公開");
		sleep(1000);
	}
	public void uploadThumbnail(String imgPath) {
		try {
	    driver.findElement(By.id("set-post-thumbnail")).click();
	    sleep(3000);
	    click(By.linkText("ファイルをアップロード"));
	    //select この投稿へアップロード
	    sleep(1000);
	    driver.findElement(By.id("__wp-uploader-id-1")).click();
	    //upload
	    driver.findElement(By.xpath("//div[7]/input")).sendKeys("/Users/trmt_8/Desktop/patagonia2.jpg");
	    sleep(1000);
	    sleep(1000);
	    driver.findElement(By.xpath("//div[@id='__wp-uploader-id-0']/div[5]/div/div[2]/button")).click();
		} catch (Exception e) {
			e.printStackTrace();
			uploadThumbnail(imgPath);
		}
	}
}
