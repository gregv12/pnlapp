# Pnl Calculator

The docker application wraps a pnl calculator that operates in real time listening to changes in the file system to
calculate positions and pnl. Inputs and outputs sources are external files mounted into the docker container.

## Running the app

Make sure you have docker installed and running then execute the command

```shell
docker compose up

[+] Running 6/6
 ✔ pnlImage Pulled                                                                                                                                                                                  54.8s 
   ✔ 286c1c922769 Pull complete                                                                                                                                                                     31.5s 
   ✔ da5f8d4b6eb4 Pull complete                                                                                                                                                                     31.7s 
   ✔ 4695d300e5f0 Pull complete                                                                                                                                                                     51.9s 
   ✔ 1fe2660538b0 Pull complete                                                                                                                                                                     52.0s 
   ✔ 4f4fb700ef54 Pull complete                                                                                                                                                                     52.0s 
[+] Running 2/2
 ✔ Network pnlapp_default       Created                                                                                                                                                              0.0s 
 ✔ Container pnlapp-pnlImage-1  Created                                                                                                                                                              0.5s 
Attaching to pnlImage-1
pnlImage-1  | 11:05:37.632 [main] WARN   eventAudit #STARTING FLUXTION SERVER
pnlImage-1  | 11:05:37.779 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/positionSnapshot/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.780 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/symbolFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/rateFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/tradeEventFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/positionSnapshot/onEventCallBack start
pnlImage-1  | 11:05:37.782 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/symbolFeed/onEventCallBack start
pnlImage-1  | 11:05:37.782 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/rateFeed/onEventCallBack start
pnlImage-1  | 11:05:37.782 [pnlCalulator-thread] INFO   EventQueueToEventProcessorAgent.pnlCalulator-thread/tradeEventFeed/onEventCallBack start

```

View one of the output files to check that sample trade set has been processed:

```shell
cat app_var/docker-var/results/netMtm.jsonl

{"instrumentMtm":{"bookName":"global","tradePnl":"NaN","positionMap":{"Instrument[instrumentName=CHF]":45.0,"Instrument[instrumentName=EUR]":-195.0,"Instrument[instrumentName=GBP]":250.0,"Instrument[instrumentName=USD]":-100.0},"mtmPositionsMap":{"Instrument[instrumentName=CHF]":"NaN","Instrument[instrumentName=EUR]":"NaN","Instrument[instrumentName=GBP]":"NaN","Instrument[instrumentName=USD]":-100.0}},"feesMtm":{"bookName":null,"fees":0.0,"feesPositionMap":{},"feesMtmPositionMap":{}},"tradePnl":"NaN","fees":0.0,"pnlNetFees":"NaN"}
{"instrumentMtm":{"bookName":"global","tradePnl":71.02027027027029,"positionMap":{"Instrument[instrumentName=CHF]":45.0,"Instrument[instrumentName=EUR]":-195.0,"Instrument[instrumentName=GBP]":250.0,"Instrument[instrumentName=USD]":-100.0},"mtmPositionsMap":{"Instrument[instrumentName=CHF]":20.270270270270267,"Instrument[instrumentName=EUR]":-224.24999999999997,"Instrument[instrumentName=GBP]":375.0,"Instrument[instrumentName=USD]":-100.0}},"feesMtm":{"bookName":null,"fees":0.0,"feesPositionMap":{},"feesMtmPositionMap":{}},"tradePnl":71.02027027027029,"fees":0.0,"pnlNetFees":71.02027027027029}
{"instrumentMtm":{"bookName":"global","tradePnl":-866.817567567567,"positionMap":{"Instrument[instrumentName=CHF]":627.0,"Instrument[instrumentName=EUR]":-2695.0,"Instrument[instrumentName=GBP]":750.0,"Instrument[instrumentName=USD]":825.0},"mtmPositionsMap":{"Instrument[instrumentName=CHF]":282.4324324324324,"Instrument[instrumentName=EUR]":-3099.2499999999995,"Instrument[instrumentName=GBP]":1125.0,"Instrument[instrumentName=USD]":825.0}},"feesMtm":{"bookName":"USD","fees":0.9702702702702702,"feesPositionMap":{"Instrument[instrumentName=CHF]":0.6000000000000001,"Instrument[instrumentName=USD]":0.7},"feesMtmPositionMap":{"Instrument[instrumentName=CHF]":0.2702702702702703,"Instrument[instrumentName=USD]":0.7}},"tradePnl":-866.817567567567,"fees":0.9702702702702702,"pnlNetFees":-867.7878378378373}
```

## Outputs

Results are published as jsonl files, new lines are appended as the input updates

- Global mark to market and positions app_var/docker-var/results/netMtm.jsonl
- Per instrument breakdown app_var/docker-var/results/instrumentMtm.jsonl
- Position snapshot app_var/docker-var/data_in/positionSnapshot.jsonl

## Inputs

Inputs are stored as jsonl files and constantly watched for inputs

- app_var/docker-var/data_in/trades.jsonl - trades that are used to update position calculations
- app_var/docker-var/data_in/rates.jsonl - rates that are used to calculate the mark to market profit and loss
- app_var/docker-var/data_in/symbols.jsonl - maps symbols to instruments
- app_var/docker-var/data_in/positionSnapshot.jsonl - reads the last line on a restart for an initial position offset
- app_var/docker-var/data_in/trades.jsonl.readPointer trades commited read pointer

## Realtime updates

Appending elements to the trades.jsonl, rates.jsonl, symbols.jsonl triggers a calculation which pushes updates to the
output files

## Recovering state at startup

Input and output is located in the `app_var/docker-var` directory. Startup order:

1. Read symbols table
2. Read position snapshot for any position offset to apply
3. Read trades from the last commit read point
4. Read the rates table

The trades file has an automatically maintained read pointer, when restarted only trades past the pointer are processed.
Any changes to positions from a calculation are published to the position snapshot file which is read at startup.

## Sample input data

* trades: `{"symbol":"USDCHF","dealtVolume":-150,"contraVolume":194,"fee":0.2,"feeInstrument":"CHF"}`
* rates: `{"symbol":"EURUSD", "price":"1.15"}`
* symbols: `{"symbol":"USDCHF", "dealt":"USD", "contra":"CHF"}`

## Configuration

The configuration files are:

- app_var/docker-var/config/pnlAppConfig.yaml defines the input source, output sources and the pnl calculation strategy
- app_var/docker-var/config/log4j2.yaml control of log4j2

## Reset state

To reset the state of the system:

1. Stop the system
2. Delete the trades.jsonl.readPointer
3. Edit positionSnapshot.jsonl as required
4. Edit trades.jsonl as required
5. Restart the system

