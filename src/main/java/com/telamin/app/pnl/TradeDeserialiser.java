/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxtion.server.lib.pnl.TradeBatchDTO;
import com.fluxtion.server.lib.pnl.TradeDTO;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.function.Function;

@Log4j2
public class TradeDeserialiser implements Function<List<String>, Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object apply(List<String> tradeLines) {
        try {
            if (tradeLines.size() == 1) {
                TradeDTO tradeDTO = objectMapper.readValue(tradeLines.get(0), TradeDTO.class);
                log.debug("TradeDTO  - {}", tradeDTO);
                return tradeDTO;
            } else {
                TradeBatchDTO tradeBatchDTO = new TradeBatchDTO();
                for (String line : tradeLines) {
                    TradeDTO tradeDTO = objectMapper.readValue(line, TradeDTO.class);
                    tradeBatchDTO.addTrade(tradeDTO);
                }
                return tradeBatchDTO;
            }
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        return null;
    }
}