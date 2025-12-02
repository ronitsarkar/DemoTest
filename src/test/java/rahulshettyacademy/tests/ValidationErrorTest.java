package rahulshettyacademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ValidationErrorTest extends BaseTest {

	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException {
		landingPage.loginApplication("bbbEmail@gmail.com", "Stronbbbssword1!");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException {
		String productName = "ZARA COAT 3";

		ProductCatalogue productCatalogue = landingPage.loginApplication("testEmail@gmail.com", "StrongPassword1!");

		productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}	
}
