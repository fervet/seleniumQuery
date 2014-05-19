package io.github.seleniumquery.by.evaluator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import io.github.seleniumquery.SeleniumQuery;
import io.github.seleniumquery.SetUpAndTearDownDriver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectorEvaluatorTest {
	
	@Rule
	public SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver();
	
	WebDriver driver;
	
	@Before
	public void before() {
		driver = SeleniumQuery.$.browser.getDefaultDriver();
	}
	
    // E ~ F
	@Test
	public void general_adjacent() {
		WebElement optionElement = driver.findElement(By.cssSelector(".e4"));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, optionElement, "option ~ option"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, optionElement, "select ~ option"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, optionElement, "option ~ select"), is(false));
		
		WebElement brazilian_p = driver.findElement(By.id("brazilian-p"));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, brazilian_p, "p ~ p"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, brazilian_p, "body ~ p"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, brazilian_p, "select ~ p"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, brazilian_p, "p ~ select"), is(false));
		
		WebElement french_p = driver.findElement(By.id("french-p"));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, french_p, "p ~ p"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, french_p, "body ~ p"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, french_p, "select ~ p"), is(false));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, french_p, "p ~ select"), is(false));
		
		WebElement hero_combo = driver.findElement(By.id("hero-combo"));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, hero_combo, "p ~ select"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, hero_combo, "select ~ option"), is(false));
		
		WebElement containsDiv = driver.findElement(By.id("containsDiv"));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "p ~ div"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "select ~ div"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "div ~ div"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "div ~ *"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "p ~ p ~ *"), is(true));
		assertThat(SelectorEvaluator.elementMatchesStringSelector(driver, containsDiv, "p ~ p ~ div"), is(true));
	}

}