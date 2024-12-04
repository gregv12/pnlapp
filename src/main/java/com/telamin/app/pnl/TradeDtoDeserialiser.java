/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fluxtion.server.lib.pnl.dto.TradeBatchDto;
import com.fluxtion.server.lib.pnl.dto.TradeDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TradeDtoDeserialiser extends BatchDtoDeserialiser<TradeDto> {

    public TradeDtoDeserialiser() {
        super(TradeDto.class, TradeBatchDto::new);
    }
}