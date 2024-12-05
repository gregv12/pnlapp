package com.telamin.app.pnl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxtion.server.lib.pnl.InstrumentPosition;
import com.fluxtion.server.lib.pnl.PositionSnapshot;
import com.fluxtion.server.lib.pnl.dto.PositionSnapshotDto;
import com.fluxtion.server.lib.pnl.refdata.Instrument;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Log4j2
public class PositionMapDeserialiser implements Function<List<String>, PositionSnapshotDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PositionSnapshotDto apply(List<String> lines) {
        String json = lines.get(0);
        PositionSnapshot positionSnapshot = new PositionSnapshot();
        try {
            Map<String, Double> positionMap = objectMapper.readValue(json, new TypeReference<>() {});
            positionMap.forEach((inst, pos) -> positionSnapshot.getPositions().add(new InstrumentPosition(new Instrument(inst), pos)));
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        PositionSnapshotDto positionSnapshotDto = new PositionSnapshotDto();
        positionSnapshotDto.setPositionSnapshot(positionSnapshot);
        return positionSnapshotDto;
    }
}
