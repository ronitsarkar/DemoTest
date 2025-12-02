package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(Map<String, String> inputMap) throws IOException, InterruptedException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(inputMap.get("email"),
				inputMap.get("password"));
		
		productCatalogue.getProductList();
		productCatalogue.addProductToCart(inputMap.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(inputMap.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("testEmail@gmail.com", "StrongPassword1!");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("email", "testEmail@gmail.com");
		dataMap.put("password", "StrongPassword1!");
		dataMap.put("product", "ZARA COAT 3");

		Map<String, String> dataMap1 = new HashMap<String, String>();
		dataMap1.put("email", "shetty@gmail.com");
		dataMap1.put("password", "Iamking@000");
		dataMap1.put("product", "ADIDAS ORIGINAL");
		return new Object[][] { { dataMap }, { dataMap1 } };
	}

}
