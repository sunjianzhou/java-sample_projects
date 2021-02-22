package com.jdicity.gateway.authority;

import com.google.gson.Gson;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.mapper.ApplicationMapper;
import com.jdicity.gateway.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/23 15:18
 */

@Service
public class AuthorityToNacos {
    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private ApplicationMapper applicationMapper;

    public String getAuthority(long id) {
        Authority authorityByAppId = authorityMapper.findAuthorityByAppId(id);
        return new Gson().toJson(authorityByAppId);
    }

    public String getAuthorities() {
        List<Long> allIds = applicationMapper.findAllIds();
        List<String> authorities = new ArrayList<>();
        for (Long id : allIds) {
            String authority = getAuthority(id);
            authorities.add(authority);
        }
        return authorities.toString();
    }
}
