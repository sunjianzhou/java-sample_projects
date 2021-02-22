package com.jdicity.gateway.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Gateway验签所需秘钥。
 *
 * @author sunjianzhou
 * @date 2020/12/17 21:57
 */
@Data
@AllArgsConstructor
public class GatewaySignProperties {
    /**
     * 公钥。
     */
    private String publicKey;
    /**
     * 私钥。
     */
    private String privateKey;
}
