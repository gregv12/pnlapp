/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

@Log4j2
public class JsonDeserialiser<T> implements Function<String, Object> {

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true).build();
    private final Class<T> clazz;
    private final Logger logger = LogManager.getLogger("errorLogger");

    public JsonDeserialiser(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object apply(String lineInput) {
        if (lineInput == null || lineInput.isEmpty()) {
            return null;
        }
        try {
            T SymbolDTO = objectMapper.readValue(lineInput, clazz);
            log.debug("dto  - {}", SymbolDTO);
            return SymbolDTO;
        } catch (JsonProcessingException e) {
            log.error(e);
            logger.error("{} error processing input:'{}' error message:{}", getClass().getName(), lineInput, e.getMessage());
        }
        return null;
    }
}