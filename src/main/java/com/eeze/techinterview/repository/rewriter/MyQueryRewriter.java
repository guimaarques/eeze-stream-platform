package com.eeze.techinterview.repository.rewriter;

import com.eeze.techinterview.util.QueryUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;

public class MyQueryRewriter implements QueryRewriter {

    @Override
    public String rewrite(String query, Sort sort) {
        return query.replace("field", QueryUtil.getField());
    }
}
