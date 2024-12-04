/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

public class MapFunction {

    public static class MapToUpperCase implements Function<Object, String> {
        @Override
        public String apply(Object o) {
            return o == null ? "" : o.toString().toUpperCase();
        }
    }

    public static class MapToJson implements Function<Object, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String apply(Object o) {
            try {
                return o == null ? "" : objectMapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                return "";
            }
        }
    }
}
