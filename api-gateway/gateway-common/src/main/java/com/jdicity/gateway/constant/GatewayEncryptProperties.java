package com.jdicity.gateway.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网关加密所需秘钥信息。
 *
 * @author sunjianzhou
 * @date 2020/12/18 15:49
 */
@Data
@AllArgsConstructor
public class GatewayEncryptProperties {
    /**
     * 公钥。
     */
    private String publicKey;
    /**
     * 私钥。
     */
    private String privateKey;
}
