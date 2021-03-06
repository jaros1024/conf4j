/*
 * MIT License
 *
 * Copyright 2017 Sabre GLBL Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.sabre.oss.conf4j.spring.handler;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import static com.sabre.oss.conf4j.spring.handler.AttributeConstants.ORDER_ATTRIBUTE;
import static java.lang.Integer.valueOf;
import static org.springframework.util.StringUtils.hasText;

public class ConverterBeanDefinitionParser extends AbstractClassBeanDefinitionParser {
    private static final OrderingProxy proxy = new OrderingProxy();

    @Override
    protected String getBeanClassName(Element element, ParserContext parserContext) {
        String beanClassName = super.getBeanClassName(element, parserContext);
        if (beanClassName == null) {
            return null;
        }

        String order = element.getAttribute(ORDER_ATTRIBUTE);
        if (hasText(order)) {
            ClassLoader classLoader = parserContext.getReaderContext().getBeanClassLoader();
            beanClassName = proxy.create(beanClassName, valueOf(order), classLoader);
        }

        return beanClassName;
    }
}
