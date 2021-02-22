package com.example.demoproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/25 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    private int id;

    private int videoId;

    private String title;
}
