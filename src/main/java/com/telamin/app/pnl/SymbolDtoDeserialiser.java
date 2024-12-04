/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fluxtion.server.lib.pnl.dto.SymbolBatchDto;
import com.fluxtion.server.lib.pnl.dto.SymbolDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SymbolDtoDeserialiser extends BatchDtoDeserialiser<SymbolDto> {

    public SymbolDtoDeserialiser() {
        super(SymbolDto.class, SymbolBatchDto::new);
    }
}