package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	WebDriver driver;

	@FindBy(css = "[placeholder='Select Country']")
	private WebElement country;

	@FindBy(css = "button[class*='ta-item']:nth-child(3)")
	private WebElement selectCountry;

	By resultElement = By.cssSelector(".ta-results");

	@FindBy(css = ".action__submit")
	private WebElement submit;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(resultElement);
		selectCountry.click();
	}
	
	public ConfirmationPage submitOrder() {
		Actions action = new Actions(driver);
		action.moveToElement(submit).click().build().perform();
//		submit.click();
		return new ConfirmationPage(driver);
	}
}
