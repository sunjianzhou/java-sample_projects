package com.jdicity.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdicity.gateway.dto.Authority;
import org.springframework.stereotype.Repository;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/22 15:16
 */
@Repository
public interface AuthorityMapper extends BaseMapper<Authority> {
    Authority findAuthorityByAppId(Long id);
}
