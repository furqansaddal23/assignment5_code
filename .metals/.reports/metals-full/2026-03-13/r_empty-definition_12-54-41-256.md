error id: file://<WORKSPACE>/ui-tests/src/test/java/org/example/playwrightTraditional/BookstoreTraditionalTest.java:Page/GetByTextOptions#
file://<WORKSPACE>/ui-tests/src/test/java/org/example/playwrightTraditional/BookstoreTraditionalTest.java
empty definition using pc, found symbol in pc: Page/GetByTextOptions#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 5044
uri: file://<WORKSPACE>/ui-tests/src/test/java/org/example/playwrightTraditional/BookstoreTraditionalTest.java
text:
```scala
package org.example.playwrightTraditional;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookstoreTraditionalTest {

    @Test
    void bookstorePurchasePathTraditional() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setRecordVideoDir(Paths.get("videos/"))
                            .setRecordVideoSize(1280, 720)
            );

            Page page = context.newPage();

            page.navigate("https://depaul.bncollege.com/");
            page.waitForLoadState();

            // Search for earbuds
            Locator searchBox = page.locator("input[type='search'], input[placeholder*='Search']").first();
            searchBox.fill("earbuds");
            searchBox.press("Enter");
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Apply filters
            page.getByText("Brand").first().click();
            page.getByText("JBL", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1000);

            page.getByText("Color").first().click();
            page.getByText("Black", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1000);

            page.getByText("Price").first().click();
            page.getByText("Over $50", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Open product
            page.getByText("JBL Quantum True Wireless", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Product page assertions
            assertThat(page.getByText(
                    "JBL Quantum True Wireless Noise Cancelling Gaming Earbuds",
                    new Page.GetByTextOptions().setExact(false)
            ).first()).isVisible();
            assertThat(page.getByText("Black", new Page.GetByTextOptions().setExact(false)).first()).isVisible();
            assertThat(page.getByText("$149.98", new Page.GetByTextOptions().setExact(false)).first()).isVisible();

            // Try SKU / description checks
            assertThat(page.locator("body")).containsText("SKU");
            assertThat(page.locator("body")).containsText("Gaming");
            assertThat(page.locator("body")).containsText("Earbuds");

            // Add to cart
            Locator addToCart = page.getByText("Add to Cart", new Page.GetByTextOptions().setExact(false)).first();
            addToCart.click();
            page.waitForTimeout(4000);

            // Cart icon should show 1 item
            assertThat(page.locator("body")).containsText("1 Items");

            // Go to cart
            page.getByText("Cart", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Your Shopping Cart page
            assertThat(page.getByText("Your Shopping Cart", new Page.GetByTextOptions().setExact(false))).isVisible();
            assertThat(page.locator("body")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds");
            assertThat(page.locator("body")).containsText("$149.98");

            // Pickup option
            page.getByText("FAST In-Store Pickup", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1500);

            // Cart totals before guest info
            assertThat(page.locator("body")).containsText("149.98");
            assertThat(page.locator("body")).containsText("2.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("151.98");

            // Bad promo
            page.locator("input[placeholder*='Promo'], input[name*='promo'], input[id*='promo']").first().fill("TEST");
            page.getByText("APPLY", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1500);
            assertThat(page.locator("body")).containsText("invalid");

            // Proceed to checkout
            page.getByText("PROCEED TO CHECKOUT", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Create Account page
            assertThat(page.getByText("Create Account", new Page.GetByTe@@xtOptions().setExact(false))).isVisible();
            page.getByText("Proceed as Guest", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Contact Information
            assertThat(page.getByText("Contact Information", new Page.GetByTextOptions().setExact(false))).isVisible();

            page.locator("input[name*='first'], input[id*='first']").first().fill("Furqan");
            page.locator("input[name*='last'], input[id*='last']").first().fill("Saddal");
            page.locator("input[type='email']").first().fill("furqan@example.com");
            page.locator("input[type='tel'], input[name*='phone'], input[id*='phone']").first().fill("3125551212");

            assertThat(page.locator("body")).containsText("149.98");
            assertThat(page.locator("body")).containsText("2.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("151.98");

            page.getByText("CONTINUE", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Pickup Information
            assertThat(page.locator("body")).containsText("Furqan Saddal");
            assertThat(page.locator("body")).containsText("furqan@example.com");
            assertThat(page.locator("body")).containsText("3125551212");
            assertThat(page.locator("body")).containsText("DePaul University Loop Campus");
            assertThat(page.locator("body")).containsText("I’ll pick them up");
            assertThat(page.locator("body")).containsText("149.98");
            assertThat(page.locator("body")).containsText("2.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("151.98");

            page.getByText("CONTINUE", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Payment Information
            assertThat(page.locator("body")).containsText("149.98");
            assertThat(page.locator("body")).containsText("2.00");
            assertThat(page.locator("body")).containsText("15.58");
            assertThat(page.locator("body")).containsText("167.56");

            page.getByText("BACK TO CART", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);

            // Delete from cart
            page.getByText("Remove", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(2000);
            assertThat(page.locator("body")).containsText("empty");

            context.close();
            browser.close();
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: Page/GetByTextOptions#