package com.jdicity.gateway.filters.common;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/20 12:25
 */
@AllArgsConstructor
public class FactoryConfig {
    private Class inClass;
    private Class outClass;
    private String contentType;
    private RewriteFunction rewriteFunction;

    public Class getInClass() {
        return this.inClass;
    }

    public <T> T setInClass(Class inClass) {
        this.inClass = inClass;
        return (T) this;
    }

    public Class getOutClass() {
        return this.outClass;
    }

    public <T> T  setOutClass(Class outClass) {
        this.outClass = outClass;
        return (T) this;
    }

    public RewriteFunction getRewriteFunction() {
        return this.rewriteFunction;
    }

    public <T> T  setRewriteFunction(RewriteFunction rewriteFunction) {
        this.rewriteFunction = rewriteFunction;
        return (T) this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public <T> T  setContentType(String contentType) {
        this.contentType = contentType;
        return (T) this;
    }
}
