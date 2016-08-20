package com.wordpress;

public class TextMaker {
	public static void main(String[] args) {
		TextMaker textMaker = new TextMaker();
		Image image = new Image();
		image.setImgUrl(
				"https://lh4.googleusercontent.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s0-c-k-no-ns/photo.jpg");
		image.setReference(
				"https://www.google.co.jp/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwj4_LzBwcLNAhXINJQKHcoVAckQjxwIAw&url=http%3A%2F%2Fb.hatena.ne.jp%2Fentry%2Fwww.businessnewsline.com%2Fnews%2F201509031605380000.html&psig=AFQjCNGnyTwzf0zilYw5rcWzsRjxgSmEsg&ust=1466920400100139&cad=rjt");
		System.out.println(textMaker.makeReferenceImg(image));
	}

	public String makeHeadlines(String text) {
		return "<h2>" + text + "</h2>";
	}

	public String makeReferenceImg(Image image) {
		String text = "";
		text = text + "<img class = 'refImg' src = " + image.getImgUrl() + ">";
		text = text + "<div class='refSrc size-large' ><a href='" + image.getReference() + "'>"
				+ makeDomain(image.getReference()) + "</a></div>\n";
		return text;
	}

	public String makeDomain(String url) {
		String domain = url.substring(8);
		int len = domain.indexOf("/");
		domain = domain.substring(0, len);
		return domain;
	}
}
