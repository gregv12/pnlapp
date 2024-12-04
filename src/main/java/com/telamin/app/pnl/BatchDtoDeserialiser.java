/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxtion.server.lib.pnl.dto.BatchDto;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class BatchDtoDeserialiser<T> implements Function<List<String>, Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> clazz;
    private final Supplier<BatchDto<T>> batchDtoSupplier;

    public BatchDtoDeserialiser(Class<T> clazz, Supplier<BatchDto<T>> batchDtoSupplier) {
        this.clazz = clazz;
        this.batchDtoSupplier = batchDtoSupplier;
    }

    @Override
    public Object apply(List<String> lines) {
        try {
            if (lines.size() == 1) {
                T SymbolDTO = objectMapper.readValue(lines.get(0), clazz);
                log.debug("dto  - {}", SymbolDTO);
                return SymbolDTO;
            } else {
                BatchDto<T> tradeBatchDTO = batchDtoSupplier.get();
                for (String line : lines) {
                    T SymbolDTO = objectMapper.readValue(line, clazz);
                    tradeBatchDTO.addBatchItem(SymbolDTO);
                }
                return tradeBatchDTO;
            }
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        return null;
    }
}