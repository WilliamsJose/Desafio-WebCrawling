package com.axreng.backend.utils;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void givenMaxPageToVisit_whenReachLimit_thenCorrect() {
        MatcherAssert.assertThat(false, Matchers.equalTo(Utils.shouldExecute(10, 20, 10)));
    }

}