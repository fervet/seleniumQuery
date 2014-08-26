package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selector.CompiledCssSelector;
import io.github.seleniumquery.selector.SqXPathSelector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface PseudoClass {

	boolean isApplicable(String pseudoClassValue);

	boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector);

	CompiledCssSelector compilePseudoClass(WebDriver driver, PseudoClassSelector pseudoClassSelector);

	SqXPathSelector pseudoClassToXPath(WebDriver driver, PseudoClassSelector pseudoClassSelector);

}