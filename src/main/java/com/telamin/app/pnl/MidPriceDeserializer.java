/*
 * SPDX-FileCopyrightText: © 2024 Gregory Higgins <greg.higgins@v12technology.com>
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package com.telamin.app.pnl;

import com.fluxtion.server.lib.pnl.MidPrice;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MidPriceDeserializer extends JsonDeserializer<MidPrice> {

    public MidPriceDeserializer() {
        super(MidPrice.class);
    }
}