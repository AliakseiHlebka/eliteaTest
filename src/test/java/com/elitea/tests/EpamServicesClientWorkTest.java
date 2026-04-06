package com.elitea.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EpamServicesClientWorkTest {

  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;

  @BeforeAll
  static void setupBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(
      new BrowserType.LaunchOptions().setHeadless(true)
    );
  }

  @BeforeEach
  void setupContext() {
    context = browser.newContext(new Browser.NewContextOptions()
      .setViewportSize(1440, 900)
    );
    page = context.newPage();
  }

  @Test
  void servicesMenu_exploreClientWork_shouldShowClientWorkHeading() {
    // 1. Navigate to https://www.epam.com/
    page.navigate("https://www.epam.com/");

    // 1a. Accept cookies/consent if the banner is displayed
    Locator acceptAll = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept All"));
    if (acceptAll.isVisible()) {
      acceptAll.click();
    }

    // 2. Select "Services" from the header menu.
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Services")).click();

    // 3. Click the "Explore Our Client Work" link.
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Explore Our Client Work")).click();

    // 4. Verify that the "Client Work" text is visible on the page.
    assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Client Work"))).isVisible();
  }

  @AfterEach
  void closeContext() {
    context.close();
  }

  @AfterAll
  static void teardown() {
    browser.close();
    playwright.close();
  }
}
