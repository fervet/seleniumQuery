package org.openqa.selenium.seleniumquery.wait;

import org.openqa.selenium.seleniumquery.SeleniumQueryObject;
import org.openqa.selenium.seleniumquery.wait.SeleniumQueryFluentWait.WaitMethod;
import org.openqa.selenium.seleniumquery.wait.evaluators.IsEvaluator;
import org.openqa.selenium.seleniumquery.wait.getters.AttrGetter;
import org.openqa.selenium.seleniumquery.wait.getters.HtmlGetter;
import org.openqa.selenium.seleniumquery.wait.getters.PropGetter;
import org.openqa.selenium.seleniumquery.wait.getters.SizeGetter;
import org.openqa.selenium.seleniumquery.wait.getters.TextGetter;
import org.openqa.selenium.seleniumquery.wait.getters.ValGetter;

/**
 * @author acdcjunior
 * @since 0.5.0
 */
public class SeleniumQueryQueryUntil {
	
	private SeleniumQueryObject seleniumQueryObject;
	
	/**
	 * @author acdcjunior
	 * @since 0.5.0
	 */
	public SeleniumQueryQueryUntil(SeleniumQueryObject seleniumQueryObject) {
		this.seleniumQueryObject = seleniumQueryObject;
	}
	
	protected WaitMethod getWaitMethod() {
		return SeleniumQueryFluentWait.QUERY_UNTIL;
	}
	
	/**
	 * Requeries the DOM <strong>until at least one element returned</strong> by a query to the selector used
	 * to construct this seleniumQuery object <strong>is matched by the selector given</strong>.
	 * 
	 * @author acdcjunior
	 * @since 0.5.0
	 */
	public SeleniumQueryAndOrThen is(String selector) {
		return new SeleniumQueryAndOrThen(getWaitMethod().evaluateUntil(IsEvaluator.IS_EVALUATOR, selector, seleniumQueryObject));
	}
	
	public SeleniumQueryEvaluateUntil<String> val() {
		return new SeleniumQueryEvaluateUntil<String>(getWaitMethod(), ValGetter.VAL_GETTER, seleniumQueryObject);
	}
	
	public SeleniumQueryEvaluateUntil<String> text() {
		return new SeleniumQueryEvaluateUntil<String>(getWaitMethod(), TextGetter.TEXT_GETTER, seleniumQueryObject);
	}
	
	public SeleniumQueryEvaluateUntil<String> attr(String attributeName) {
		return new SeleniumQueryEvaluateUntil<String>(getWaitMethod(), new AttrGetter(attributeName), seleniumQueryObject);
	}
	
	public <T> SeleniumQueryEvaluateUntil<T> prop(String propertyName) {
		return new SeleniumQueryEvaluateUntil<T>(getWaitMethod(), new PropGetter<T>(propertyName), seleniumQueryObject);
	}
	
	public SeleniumQueryEvaluateUntil<String> html() {
		return new SeleniumQueryEvaluateUntil<String>(getWaitMethod(), HtmlGetter.HTML_GETTER, seleniumQueryObject);
	}
	
	public SeleniumQueryEvaluateUntil<Integer> size() {
		return new SeleniumQueryEvaluateUntil<Integer>(getWaitMethod(), SizeGetter.SIZE_GETTER, seleniumQueryObject);
	}
	
}