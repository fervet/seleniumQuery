package io.github.seleniumquery.selector;

import io.github.seleniumquery.locator.ElementFilter;
import io.github.seleniumquery.locator.Locator;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SqXPathSelector implements Locator {

	private String xPathExpression;
	private List<ElementFilter> elementFilters;
	private List<SqXPathSelector> xpathLine = new ArrayList<SqXPathSelector>();
	
	public SqSelectorKind kind;

	SqXPathSelector(String xPathExpression, List<ElementFilter> elementFilter) {
		this.xPathExpression = xPathExpression;
		this.elementFilters = elementFilter;
		this.kind = SqSelectorKind.CONDITIONAL_SIMPLE;
	}
	
	@Override
	public String toString() {
		return "SqXPathSelector: "+xPathExpression+" // Filters: "+elementFilters;
	}
	
	@Override
	public List<WebElement> locate(SearchContext context) {
		String finalXPathExpression = this.toXPath();
		List<WebElement> elements = new By.ByXPath(finalXPathExpression).findElements(context);
		WebDriver driver = SelectorUtils.getWebDriver(context);
		return filter(driver, elements);
	}
	
	public List<WebElement> filter(WebDriver driver, List<WebElement> elements) {
		for (ElementFilter elementFilter : elementFilters) {
			elements = elementFilter.filterElements(driver, elements);
		}
		return elements;
	}
	
	public SqXPathSelector combine(SqXPathSelector other) {
		this.xpathLine.add(other);
		this.xpathLine.addAll(other.xpathLine);
		return this;
	}
	
	private SqXPathSelector merge(SqXPathSelector other) {
		switch (other.kind) {
		case ADJACENT:
			this.xPathExpression = this.xPathExpression + "/following-sibling::" + other.xPathExpression;
			break;
		case DESCENDANT_DIRECT:
			this.xPathExpression = this.xPathExpression + "/" + other.xPathExpression;
			break;
		case DESCENDANT_GENERAL:
			this.xPathExpression = this.xPathExpression + "//" + other.xPathExpression;
			break;
		case CONDITIONAL_SIMPLE:
			if (this.xPathExpression.endsWith("]")) {
				this.xPathExpression = this.xPathExpression.substring(0, this.xPathExpression.length()-1) + " and " + other.xPathExpression.substring(1);
			} else {
				this.xPathExpression = this.xPathExpression + other.xPathExpression;
			}
			break;
		case CONDITIONAL_TO_ALL:
			this.xPathExpression = "(" + this.xPathExpression + ")" + other.xPathExpression;
			break;
		case TAG:
			if (!"*".equals(other.xPathExpression)) {
				this.merge(XPathSelectorFactory.createNoFilterSelector("[name() = '"+other.xPathExpression+"']"));
			} else {
				this.merge(XPathSelectorFactory.createNoFilterSelector("[name()]"));
			}
			break;
		default:
			throw new RuntimeException("Kind '"+other.kind + "' is not supported yet!");
		}
		
		this.elementFilters.addAll(other.elementFilters);
		return this;
	}
	
	public String toXPath() {
		if (this.kind != SqSelectorKind.TAG) {
			throw new RuntimeException("Weird... i didnt think this was possible!");
		}
		this.xPathExpression = "//" + this.xPathExpression;
		for (SqXPathSelector x : xpathLine) {
			this.merge(x);
		}
		return "(" + this.xPathExpression + ")";
	}
	
	public String toXPathCondition() {
		if (this.kind != SqSelectorKind.TAG) {
			throw new RuntimeException("Weird... i didnt think this was possible!");
		}
		if (!"*".equals(this.xPathExpression)) {
			this.xPathExpression = "[name() = '"+this.xPathExpression+"']";
		} else {
			this.xPathExpression = "[name()]";
		}
		for (SqXPathSelector x : xpathLine) {
			this.merge(x);
		}
		return this.xPathExpression.substring(1, this.xPathExpression.length()-1); // removes [] around
	}

}