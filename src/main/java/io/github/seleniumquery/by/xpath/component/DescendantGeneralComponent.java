package io.github.seleniumquery.by.xpath.component;

import io.github.seleniumquery.by.xpath.CssCombinationType;

public class DescendantGeneralComponent extends XPathComponent {

    public static XPathComponent combine(XPathComponent one, XPathComponent other) {
        ComponentUtils.assertTagComponent(one);
        DescendantGeneralComponent otherCopyWithModifiedType = new DescendantGeneralComponent(other);
        return new TagComponent(one.xPathExpression,
                                ComponentUtils.combineComponents(one, other, otherCopyWithModifiedType),
                                ComponentUtils.combineFilters(one, other, otherCopyWithModifiedType));
    }

    private DescendantGeneralComponent(XPathComponent other) {
        super(other.xPathExpression, other.combinatedComponents, other.elementFilterList);
    }

    @Override
    public String mergeIntoExpression(String sourceXPathExpression) {
        return CssCombinationType.DESCENDANT_GENERAL.merge(sourceXPathExpression, this.xPathExpression);
    }

    @Override
    public String mergeExpressionAsCondition(String sourceXPathExpression) {
        return CssCombinationType.DESCENDANT_GENERAL.mergeAsCondition(sourceXPathExpression, this.xPathExpression);
    }

}