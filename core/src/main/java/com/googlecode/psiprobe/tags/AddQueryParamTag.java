/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.googlecode.psiprobe.tags;

import java.util.Enumeration;
import java.io.IOException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

public class AddQueryParamTag extends TagSupport {

    private Log logger = LogFactory.getLog(getClass());
    private String param;
    private String value;

    public int doStartTag() throws JspException {
        StringBuffer query = new StringBuffer();
        query.append(param).append("=").append(value);
        for (Enumeration en = pageContext.getRequest().getParameterNames(); en.hasMoreElements(); ){
            String name = (String) en.nextElement();
            if (!param.equals(name)) {
                query.append("&").append(name).append("=").append(ServletRequestUtils.getStringParameter(pageContext.getRequest(), name, ""));
            }
        }
        try {
            pageContext.getOut().print(query);
        } catch (IOException e) {
            logger.debug(e);
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
