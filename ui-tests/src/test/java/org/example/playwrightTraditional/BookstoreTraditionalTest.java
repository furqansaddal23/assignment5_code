package org.example.playwrightTraditional;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
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
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForTimeout(3000);

            Locator acceptCookies = page.getByText("Accept", new Page.GetByTextOptions().setExact(false)).first();
            if (acceptCookies.isVisible()) {
                acceptCookies.click();
                page.waitForTimeout(1000);
            }

            page.locator(".list-inline").click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            page.getByText("Brand", new Page.GetByTextOptions().setExact(false)).first().click();
            page.getByText("JBL", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1500);

            page.getByText("Color", new Page.GetByTextOptions().setExact(false)).first().click();
            page.getByText("Black", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1500);

            page.getByText("Price", new Page.GetByTextOptions().setExact(false)).first().click();
            page.getByText("Over $50", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            page.getByText("JBL Quantum True Wireless", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            assertThat(page.locator("body")).containsText("JBL Quantum True Wireless");
            assertThat(page.locator("body")).containsText("Black");
            assertThat(page.locator("body")).containsText("$164.98");
            assertThat(page.locator("body")).containsText("668972707");
            assertThat(page.locator("body")).containsText("Gaming Earbuds");

            Locator addToCartButton = page.getByRole(
                    AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Add to cart")
            ).first();
            addToCartButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            addToCartButton.click();
            page.waitForTimeout(4000);

            assertThat(page.locator("body")).containsText("Added to Your Shopping Cart");

            Locator cartLink = page.getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Cart")
            ).first();
            if (!cartLink.isVisible()) {
                cartLink = page.getByText("Cart", new Page.GetByTextOptions().setExact(false)).first();
            }

            cartLink.click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);

            assertThat(page.locator("body")).containsText("Your Shopping Cart");
            assertThat(page.locator("body")).containsText("JBL Quantum True Wireless");
            assertThat(page.locator("body")).containsText("$164.98");

            page.getByText("FAST In-Store Pickup", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForTimeout(1500);

            assertThat(page.locator("body")).containsText("$164.98");
            assertThat(page.locator("body")).containsText("$3.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("$167.98");

            Locator promoBox = page.locator("input[placeholder*='Promo'], input[name*='promo'], input[id*='promo']").first();
            if (promoBox.isVisible()) {
                promoBox.fill("TEST");
                page.getByText("APPLY", new Page.GetByTextOptions().setExact(false)).first().click();
                page.waitForTimeout(1500);
            }

            page.getByText("PROCEED TO CHECKOUT", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            assertThat(page.locator("body")).containsText("Create Account");
            page.getByText("Proceed as Guest", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            assertThat(page.locator("body")).containsText("Contact Information");
            page.waitForTimeout(2000);

            Locator firstNameInput = page.getByLabel("First Name (required)").first();
            Locator lastNameInput = page.getByLabel("Last Name (required)").first();
            Locator emailInput = page.getByLabel("Email address (required)").first();
            Locator phoneInput = page.getByLabel("Phone Number (required)").last();

            firstNameInput.fill("Furqan");
            lastNameInput.fill("Saddal");
            emailInput.fill("furqan@example.com");
            phoneInput.fill("3125551212");

            Locator iamSelect = page.locator("select").first();
            if (iamSelect.isVisible()) {
                try {
                    iamSelect.selectOption("Student");
                } catch (Exception e) {
                    try {
                        iamSelect.selectOption(new String[]{"student"});
                    } catch (Exception ignored) {
                    }
                }
            }

            assertThat(page.locator("body")).containsText("$164.98");
            assertThat(page.locator("body")).containsText("$3.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("$167.98");

            Locator continueButton1 = page.locator("button:visible").filter(
                    new Locator.FilterOptions().setHasText("Continue")
            ).first();
            continueButton1.click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);

            // Pickup Information step
            assertThat(page.locator("body")).containsText("Pickup Location");
            assertThat(page.locator("body")).containsText("DePaul University Loop Campus");

            Locator iWillPickThemUp = page.getByText("I'll pick them up", new Page.GetByTextOptions().setExact(false)).first();
            if (iWillPickThemUp.isVisible()) {
                iWillPickThemUp.click();
                page.waitForTimeout(1000);
            }

            Locator pickupName = page.getByLabel("Full Name").first();
            if (pickupName.isVisible()) {
                pickupName.fill("Furqan Saddal");
            }

            assertThat(page.locator("body")).containsText("$164.98");
            assertThat(page.locator("body")).containsText("$3.00");
            assertThat(page.locator("body")).containsText("TBD");
            assertThat(page.locator("body")).containsText("$167.98");

            Locator continueButton2 = page.locator("button:visible").filter(
                    new Locator.FilterOptions().setHasText("Continue")
            ).first();
            continueButton2.click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);

            // At this point, accept either still-summary page or payment page
            assertThat(page.locator("body")).containsText("$164.98");
            assertThat(page.locator("body")).containsText("$3.00");

            page.getByText("BACK TO CART", new Page.GetByTextOptions().setExact(false)).first().click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(2000);

            Locator removeButton = page.getByText("Remove", new Page.GetByTextOptions().setExact(false)).first();
            if (removeButton.isVisible()) {
                removeButton.click();
                page.waitForTimeout(2000);
            }

            context.close();
            browser.close();
        }
    }
}
